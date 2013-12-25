package coreEJB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.BaseProductDTO;
import dto.CityDTO;
import dto.ClassPersonalizationDTO;
import dto.DatePersonalizationDTO;
import dto.ExcursionDTO;
import dto.FieldNotPresentException;
import dto.FlightDTO;
import dto.HotelDTO;
import dto.PossiblePersonalizationDTO;
import entity.Airport;
import entity.City;
import entity.ClassPersonalization;
import entity.Company;
import entity.DatePersonalization;
import entity.Excursion;
import entity.Flight;
import entity.Hotel;
import entity.PossibleClassPersonalizationFlight;
import entity.PossibleClassPersonalizationHotel;
import entity.PossibleDatePersonalizationExcursion;
import entity.PossibleDatePersonalizationFlight;
import exceptions.NotValidBaseProductException;

/**
 * Session Bean implementation class BaseProductEJB
 */
@Stateful
@LocalBean
public class BaseProductEJB implements BaseProductEJBLocal {
	
	@PersistenceContext
	EntityManager em;
	
	BaseProductDTO tmpBaseProduct;

    /**
     * Default constructor. 
     */
    public BaseProductEJB() {
    }
    
    public void saveBaseProduct(BaseProductDTO baseProduct) throws NotValidBaseProductException{
    	//Create the right entity
    	if(baseProduct instanceof FlightDTO) {
    		FlightDTO flightDTO = (FlightDTO) baseProduct;
    		saveFlight(flightDTO);
    	}
    	if(baseProduct instanceof HotelDTO) {
    		HotelDTO hotelDTO = (HotelDTO) baseProduct;
    		saveHotel(hotelDTO);
    	}
    	if(baseProduct instanceof ExcursionDTO) {
    		ExcursionDTO excursionDTO = (ExcursionDTO) baseProduct;
    		saveExcursion(excursionDTO);
    	}
    }
    
    public void saveFlight(FlightDTO flightDTO)  throws NotValidBaseProductException {
    	Flight flight = em.find(Flight.class, flightDTO.getId());
		
		if(flight == null) {
			//The flight is not present. Built it from scratch
			flight = new Flight();
			flight.setAirportArrival(em.find(Airport.class, flightDTO.getAirportArrival()));
			flight.setAirportDeparture(em.find(Airport.class, flightDTO.getAirportDeparture()));
			flight.setCompany(em.find(Company.class, flightDTO.getCompany()));
			flight.setName(flightDTO.getName());
			em.persist(flight);	//save the new flight
				
			//Create personalizations
			for(ClassPersonalizationDTO cp : flightDTO.getPossibleClassPersonalizations()) {
				ClassPersonalization classPersonalization  = em.find(ClassPersonalization.class, cp.getId());
				
				//Try to find with name of the class
				if(classPersonalization == null) {
					try {
						classPersonalization  = em.createQuery("SELECT c FROM ClassPersonalization c WHERE c.class_=:class", ClassPersonalization.class).setParameter("class", cp.get_class()).getResultList().get(0);
					}catch(IndexOutOfBoundsException e) {}	//No problem
				}
				
				if(classPersonalization == null) {
					//Create new classPersonalization
					classPersonalization = new ClassPersonalization();
					classPersonalization.setClass_(cp.get_class());
					em.persist(classPersonalization);
					//flight.getClassPersonalizations().add(classPersonalization);
				}
				
				
				//Try to find the possibleClassPersonalization
				PossibleClassPersonalizationFlight pcp = null;
				try {
					pcp = em.createQuery("SELECT p FROM PossibleClassPersonalizationFlight p WHERE p.flight=:flight AND p.classPersonalization=:class", PossibleClassPersonalizationFlight.class)
																	.setParameter("flight", flight)
																	.setParameter("class", classPersonalization)
																	.getResultList().get(0);
				} catch (IndexOutOfBoundsException e) {}	//No problem
				
				//create new
				if(pcp == null) {
					pcp = new PossibleClassPersonalizationFlight();
					pcp.setFlight(flight);
					pcp.setClassPersonalization(classPersonalization);
					em.persist(pcp);
				}
				
				//Extract the price from the map
				Double price = flightDTO.getPrices().get(cp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pcp.setPrice(new BigDecimal(price.doubleValue()));
		
				flight.addPossibleClassPersonalizationFlight(pcp);
				
			}
			for(DatePersonalizationDTO dp : flightDTO.getPossibleDatePersonalizations()) {
				DatePersonalization datePersonalization  = em.find(DatePersonalization.class, dp.getId());
				
				if(datePersonalization == null) {
					//Create new classPersonalization
					datePersonalization = new DatePersonalization();
					datePersonalization.setDate(dp.getInitialDate());
					datePersonalization.setDuration(dp.getDuration());
					em.persist(datePersonalization);
					//flight.getDatePersonalizations().add(datePersonalization);
				}
				
				//Try to find the possibleClassPersonalization
				PossibleDatePersonalizationFlight pdp = null;
				try {
					pdp = em.createQuery("SELECT p FROM PossibleDatePersonalizationFlight p WHERE p.flight=:flight AND p.datePersonalization=:date", PossibleDatePersonalizationFlight.class)
																	.setParameter("flight", flight)
																	.setParameter("date", datePersonalization)
																	.getResultList().get(0);
				} catch (IndexOutOfBoundsException e) {}	//No problem
			
				if(pdp == null) {
					pdp = new PossibleDatePersonalizationFlight();
					pdp.setFlight(flight);
					pdp.setDatePersonalization(datePersonalization);
					em.persist(pdp);
				}
				
				//Extract the price from the map
				Double price = flightDTO.getPrices().get(dp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pdp.setPrice(new BigDecimal(price.doubleValue()));
				
				
				flight.addPossibleDatePersonalizationFlight(pdp);
			}
			em.merge(flight);
		} else {
			//The flight is already present in the DB. Update it
			flight.setAirportArrival(em.find(Airport.class, flightDTO.getAirportArrival()));
			flight.setAirportDeparture(em.find(Airport.class, flightDTO.getAirportDeparture()));
			flight.setCompany(em.find(Company.class, flightDTO.getCompany()));
			flight.setName(flightDTO.getName());
			
			//Reset possible date and class personalizations
			flight.setPossibleClassPersonalizationFlights(new ArrayList<PossibleClassPersonalizationFlight>());
			flight.setPossibleDatePersonalizationFlights(new ArrayList<PossibleDatePersonalizationFlight>());
			
			
			//PossibleClassPersonalization synchronization
			List<PossibleClassPersonalizationFlight> oldClassList = em.createNativeQuery("SELECT * FROM POSSIBLE_CLASS_PERSONALIZATION_FLIGHT WHERE FlightId='"+flight.getId()+"'", PossibleClassPersonalizationFlight.class)
																		.getResultList();
			List<PossibleClassPersonalizationFlight> newClassList = new ArrayList<PossibleClassPersonalizationFlight>();	//contains all matched personalizations
		
			for(ClassPersonalizationDTO cp : flightDTO.getPossibleClassPersonalizations()) {
				ClassPersonalization classPersonalization  = em.find(ClassPersonalization.class, cp.getId());
				
				//Try to find with name of the class
				if(classPersonalization == null) {
					try {
						classPersonalization  = em.createQuery("SELECT c FROM ClassPersonalization c WHERE c.class_=:class", ClassPersonalization.class).setParameter("class", cp.get_class()).getResultList().get(0);
					}catch(IndexOutOfBoundsException e) {}	//No problem
				}
				
				if(classPersonalization == null) {
					//Create new classPersonalization
					classPersonalization = new ClassPersonalization();
					classPersonalization.setClass_(cp.get_class());
					em.persist(classPersonalization);
					//flight.getClassPersonalizations().add(classPersonalization);
				}
				
				
				//Try to find the possibleClassPersonalization
				PossibleClassPersonalizationFlight pcp = null;
				try {
					pcp = em.createQuery("SELECT p FROM PossibleClassPersonalizationFlight p WHERE p.flight=:flight AND p.classPersonalization=:class", PossibleClassPersonalizationFlight.class)
																	.setParameter("flight", flight)
																	.setParameter("class", classPersonalization)
																	.getResultList().get(0);
				} catch (IndexOutOfBoundsException e) {}	//No problem
				if(pcp == null) {
					pcp = new PossibleClassPersonalizationFlight();
					pcp.setFlight(flight);
					pcp.setClassPersonalization(classPersonalization);
					em.persist(pcp);
				} else {
					newClassList.add(pcp);
				}
				
				//Extract the price from the map
				Double price = flightDTO.getPrices().get(cp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pcp.setPrice(new BigDecimal(price.doubleValue()));
				
				flight.addPossibleClassPersonalizationFlight(pcp);
				
			}
			
			//PossibleDatePersonalization synchronization
			List<PossibleDatePersonalizationFlight> oldDateList = em.createNativeQuery("SELECT * FROM POSSIBLE_DATE_PERSONALIZATION_FLIGHT WHERE FlightId='"+flight.getId()+"'", PossibleDatePersonalizationFlight.class)
																		.getResultList();
			List<PossibleDatePersonalizationFlight> newDateList = new ArrayList<PossibleDatePersonalizationFlight>();	//contains all matched personalizations
			
			for(DatePersonalizationDTO dp : flightDTO.getPossibleDatePersonalizations()) {
				DatePersonalization datePersonalization  = em.find(DatePersonalization.class, dp.getId());
				
				if(datePersonalization == null) {
					//Create new classPersonalization
					datePersonalization = new DatePersonalization();
					datePersonalization.setDate(dp.getInitialDate());
					datePersonalization.setDuration(dp.getDuration());
					em.persist(datePersonalization);
				}
				//flight.getDatePersonalizations().add(datePersonalization);
				
				//Try to find the possibleClassPersonalization
				PossibleDatePersonalizationFlight pdp = null;
				try {
					pdp = em.createQuery("SELECT p FROM PossibleDatePersonalizationFlight p WHERE p.flight=:flight AND p.datePersonalization=:date", PossibleDatePersonalizationFlight.class)
																	.setParameter("flight", flight)
																	.setParameter("date", datePersonalization)
																	.getResultList().get(0);
																	
				} catch (IndexOutOfBoundsException e) {}	//No problem
				if(pdp == null) {
					pdp = new PossibleDatePersonalizationFlight();
					pdp.setFlight(flight);
					pdp.setDatePersonalization(datePersonalization);
					em.persist(pdp);
				} else {
					newDateList.add(pdp);
				}
				
				//Extract the price from the map
				Double price = flightDTO.getPrices().get(dp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pdp.setPrice(new BigDecimal(price.doubleValue()));
				
				flight.addPossibleDatePersonalizationFlight(pdp);
			}
		
			em.merge(flight);
			
			//Eliminate all date personalization no more associated
			for(PossibleDatePersonalizationFlight p : oldDateList) {
				if(!newDateList.contains(p))
					em.remove(p);
			}
			
			//Eliminate all class personalization no more associated
			for(PossibleClassPersonalizationFlight c : oldClassList) {
				if(!newClassList.contains(c))
					em.remove(c);
			}
		}
		em.flush();
    }
   
    public void saveHotel(HotelDTO hotelDTO)  throws NotValidBaseProductException {
    	Hotel hotel = em.find(Hotel.class, hotelDTO.getId());
		
		if(hotel == null) {
			//The hotel is not present. Built it from scratch
			hotel = new Hotel();
			hotel.setCompany(em.find(Company.class, hotelDTO.getCompany()));
			hotel.setName(hotelDTO.getName());
			
			City city = null;
			try {
				city = em.createQuery("SELECT c FROM City c WHERE c.name=:name AND c.country=:country", City.class)
							.setParameter("name", hotelDTO.getCity().getName())
							.setParameter("country", hotelDTO.getCity().getCountry())
							.getResultList()
							.get(0);
			} catch (IndexOutOfBoundsException e) {
				city = new City(hotelDTO.getCity().getName(),hotelDTO.getCity().getCountry());
				em.persist(city);
			}
			
			hotel.setCity(city);
			hotel.setStars(hotelDTO.getStars());
			em.persist(hotel);	//save hotel
			
			//Adding personalizations
			for(ClassPersonalizationDTO cp : hotelDTO.getPossibleClassPersonalizations()) {
				ClassPersonalization classPersonalization  = em.find(ClassPersonalization.class, cp.getId());
				
				//Try to find with name of the class
				if(classPersonalization == null) {
					try {
						classPersonalization  = em.createQuery("SELECT c FROM ClassPersonalization c WHERE c.class_=:class", ClassPersonalization.class).setParameter("class", cp.get_class()).getResultList().get(0);
					}catch(IndexOutOfBoundsException e) {}	//No problem
				}
				
				if(classPersonalization == null) {
					//Create new classPersonalization
					classPersonalization = new ClassPersonalization();
					classPersonalization.setClass_(cp.get_class());
					hotel.getClassPersonalizations().add(classPersonalization);
					em.persist(classPersonalization);
				}
				
				
				//Try to find the possibleClassPersonalization
				PossibleClassPersonalizationHotel pcp = null;
				try {
					pcp = em.createQuery("SELECT p FROM PossibleClassPersonalizationHotel p WHERE p.hotel=:hotel AND p.classPersonalization=:class", PossibleClassPersonalizationHotel.class)
																	.setParameter("hotel", hotel)
																	.setParameter("class", classPersonalization)
																	.getResultList().get(0);
				} catch (IndexOutOfBoundsException e) {}	//No problem
				
				//create new
				if(pcp == null) {
					pcp = new PossibleClassPersonalizationHotel();
					pcp.setHotel(hotel);
					pcp.setClassPersonalization(classPersonalization);
					em.persist(pcp);
				}
				
				//Extract the price from the map
				Double price = hotelDTO.getPrices().get(cp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pcp.setPrice(new BigDecimal(price.doubleValue()));
		
				hotel.addPossibleClassPersonalizationHotel(pcp);
				
			}
			em.merge(hotel);
		} else {
			//The hotel is already present in the DB. Update it
			hotel.setCompany(em.find(Company.class, hotelDTO.getCompany()));
			hotel.setName(hotelDTO.getName());
			
			//Set the city
			City city = null;
			try {
				city = em.createQuery("SELECT c FROM City c WHERE c.name=:name AND c.country=:country", City.class)
							.setParameter("name", hotelDTO.getCity().getName())
							.setParameter("country", hotelDTO.getCity().getCountry())
							.getResultList()
							.get(0);
			} catch (IndexOutOfBoundsException e) {
				city = new City(hotelDTO.getCity().getName(),hotelDTO.getCity().getCountry());
				em.persist(city);
			}
			
			hotel.setCity(city);
			hotel.setStars(hotelDTO.getStars());	
			
			//PossibleClassPersonalization synchronization
			List<PossibleClassPersonalizationHotel> oldClassList = em.createNativeQuery("SELECT * FROM POSSIBLE_CLASS_PERSONALIZATION_FLIGHT WHERE HotelId='"+hotel.getId()+"'", PossibleClassPersonalizationHotel.class)
																		.getResultList();
			List<PossibleClassPersonalizationHotel> newClassList = new ArrayList<PossibleClassPersonalizationHotel>();	//contains all matched personalizations
		
			for(ClassPersonalizationDTO cp : hotelDTO.getPossibleClassPersonalizations()) {
				ClassPersonalization classPersonalization  = em.find(ClassPersonalization.class, cp.getId());
				
				//Try to find with name of the class
				if(classPersonalization == null) {
					try {
						classPersonalization  = em.createQuery("SELECT c FROM ClassPersonalization c WHERE c.class_=:class", ClassPersonalization.class).setParameter("class", cp.get_class()).getResultList().get(0);
					}catch(IndexOutOfBoundsException e) {}	//No problem
				}
				
				if(classPersonalization == null) {
					//Create new classPersonalization
					classPersonalization = new ClassPersonalization();
					classPersonalization.setClass_(cp.get_class());
					hotel.getClassPersonalizations().add(classPersonalization);
					em.persist(classPersonalization);
				}
				
				
				//Try to find the possibleClassPersonalization
				PossibleClassPersonalizationHotel pcp = null;
				try {
					pcp = em.createQuery("SELECT p FROM PossibleClassPersonalizationHotel p WHERE p.hotel=:hotel AND p.classPersonalization=:class", PossibleClassPersonalizationHotel.class)
																	.setParameter("hotel", hotel)
																	.setParameter("class", classPersonalization)
																	.getResultList().get(0);
				} catch (IndexOutOfBoundsException e) {}	//No problem
				if(pcp == null) {
					pcp = new PossibleClassPersonalizationHotel();
					pcp.setHotel(hotel);
					pcp.setClassPersonalization(classPersonalization);
					em.persist(pcp);
				} else {
					newClassList.add(pcp);
				}
				
				//Extract the price from the map
				Double price = hotelDTO.getPrices().get(cp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pcp.setPrice(new BigDecimal(price.doubleValue()));
				
				hotel.addPossibleClassPersonalizationHotel(pcp);
				
			}
			em.merge(hotel);
			
			//Eliminate all class personalization no more associated
			for(PossibleClassPersonalizationHotel c : oldClassList) {
				if(!newClassList.contains(c))
					em.remove(c);
			}
		}
		em.flush();
    }
    
    public void saveExcursion(ExcursionDTO excursionDTO)  throws NotValidBaseProductException {
    	Excursion excursion = em.find(Excursion.class, excursionDTO.getId());
		
		if(excursion == null) {
			//The excursion is not present. Built it from scratch
			excursion = new Excursion();
			excursion.setCompany(em.find(Company.class, excursionDTO.getCompany()));
			excursion.setName(excursionDTO.getName());
			
			City city = null;
			try {
				city = em.createQuery("SELECT c FROM City c WHERE c.name=:name AND c.country=:country", City.class)
							.setParameter("name", excursionDTO.getCity().getName())
							.setParameter("country", excursionDTO.getCity().getCountry())
							.getResultList()
							.get(0);
			} catch (IndexOutOfBoundsException e) {
				city = new City(excursionDTO.getCity().getName(),excursionDTO.getCity().getCountry());
				em.persist(city);
			}
			
			excursion.setCity(city);
			
			em.persist(excursion);	//save the new excursion
				
			for(DatePersonalizationDTO dp : excursionDTO.getPossibleDatePersonalizations()) {
				DatePersonalization datePersonalization  = em.find(DatePersonalization.class, dp.getId());
				
				if(datePersonalization == null) {
					//Create new classPersonalization
					datePersonalization = new DatePersonalization();
					datePersonalization.setDate(dp.getInitialDate());
					datePersonalization.setDuration(dp.getDuration());
					em.persist(datePersonalization);
					//excursion.getDatePersonalizations().add(datePersonalization);
				}
				
				//Try to find the possibleClassPersonalization
				PossibleDatePersonalizationExcursion pdp = null;
				try {
					pdp = em.createQuery("SELECT p FROM PossibleDatePersonalizationExcursion p WHERE p.excursion=:excursion AND p.datePersonalization=:date", PossibleDatePersonalizationExcursion.class)
																	.setParameter("excursion", excursion)
																	.setParameter("date", datePersonalization)
																	.getResultList().get(0);
				} catch (IndexOutOfBoundsException e) {}	//No problem
			
				if(pdp == null) {
					pdp = new PossibleDatePersonalizationExcursion();
					pdp.setExcursion(excursion);
					pdp.setDatePersonalization(datePersonalization);
					em.persist(pdp);
				}
				
				//Extract the price from the map
				Double price = excursionDTO.getPrices().get(dp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pdp.setPrice(new BigDecimal(price.doubleValue()));
				
				
				excursion.addPossibleDatePersonalizationExcursion(pdp);
			}
			em.merge(excursion);
		} else {
			//The excursion is already present in the DB. Update it
			excursion.setCompany(em.find(Company.class, excursionDTO.getCompany()));
			excursion.setName(excursionDTO.getName());
			
			City city = null;
			try {
				city = em.createQuery("SELECT c FROM City c WHERE c.name=:name AND c.country=:country", City.class)
							.setParameter("name", excursionDTO.getCity().getName())
							.setParameter("country", excursionDTO.getCity().getCountry())
							.getResultList()
							.get(0);
			} catch (IndexOutOfBoundsException e) {
				city = new City(excursionDTO.getCity().getName(),excursionDTO.getCity().getCountry());
				em.persist(city);
			}
			
			excursion.setCity(city);
			
			//Reset possible date and class personalizations
			excursion.setPossibleDatePersonalizationExcursions(new ArrayList<PossibleDatePersonalizationExcursion>());
			
			//PossibleDatePersonalization synchronization
			List<PossibleDatePersonalizationExcursion> oldDateList = em.createNativeQuery("SELECT * FROM POSSIBLE_DATE_PERSONALIZATION_FLIGHT WHERE ExcursionId='"+excursion.getId()+"'", PossibleDatePersonalizationExcursion.class)
																		.getResultList();
			List<PossibleDatePersonalizationExcursion> newDateList = new ArrayList<PossibleDatePersonalizationExcursion>();	//contains all matched personalizations
			
			for(DatePersonalizationDTO dp : excursionDTO.getPossibleDatePersonalizations()) {
				DatePersonalization datePersonalization  = em.find(DatePersonalization.class, dp.getId());
				
				if(datePersonalization == null) {
					//Create new classPersonalization
					datePersonalization = new DatePersonalization();
					datePersonalization.setDate(dp.getInitialDate());
					datePersonalization.setDuration(dp.getDuration());
					em.persist(datePersonalization);
				}
				//excursion.getDatePersonalizations().add(datePersonalization);
				
				//Try to find the possibleClassPersonalization
				PossibleDatePersonalizationExcursion pdp = null;
				try {
					pdp = em.createQuery("SELECT p FROM PossibleDatePersonalizationExcursion p WHERE p.excursion=:excursion AND p.datePersonalization=:date", PossibleDatePersonalizationExcursion.class)
																	.setParameter("excursion", excursion)
																	.setParameter("date", datePersonalization)
																	.getResultList().get(0);
																	
				} catch (IndexOutOfBoundsException e) {}	//No problem
				if(pdp == null) {
					pdp = new PossibleDatePersonalizationExcursion();
					pdp.setExcursion(excursion);
					pdp.setDatePersonalization(datePersonalization);
					em.persist(pdp);
				} else {
					newDateList.add(pdp);
				}
				
				//Extract the price from the map
				Double price = excursionDTO.getPrices().get(dp);
				
				if(price == null) {
					throw new NotValidBaseProductException();
				}
				pdp.setPrice(new BigDecimal(price.doubleValue()));
				
				excursion.addPossibleDatePersonalizationExcursion(pdp);
			}
		
			em.merge(excursion);
			
			//Eliminate all date personalization no more associated
			for(PossibleDatePersonalizationExcursion p : oldDateList) {
				if(!newDateList.contains(p))
					em.remove(p);
			}
			
		}
		em.flush();
    }
    
    public void removeBaseProduct(BaseProductDTO baseProduct) throws NotValidBaseProductException {
    	try {
	    	if(baseProduct instanceof FlightDTO) {
	    		Flight flight = em.find(Flight.class, baseProduct.getId());
	    		if(flight == null)
	    			throw new NotValidBaseProductException();
	    		em.remove(flight);
	    	}
	    	if(baseProduct instanceof HotelDTO) {
	    		Hotel hotel = em.find(Hotel.class, baseProduct.getId());
	    		if(hotel == null)
	    			throw new NotValidBaseProductException();
	    		em.remove(hotel);
	    	}
	    	if(baseProduct instanceof ExcursionDTO) {
	    		Excursion excursion = em.find(Excursion.class, baseProduct.getId());
	    		if(excursion == null)
	    			throw new NotValidBaseProductException();
	    		em.remove(excursion);
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new NotValidBaseProductException();
    	}
    }
    
    /**
     * Return a list of all base product and it garantee that all product has its own possible personalization
     */
    public List<BaseProductDTO> getAllPersonalizations() {
    	List<BaseProductDTO> list = new ArrayList<BaseProductDTO>();
    	
    	List<Flight> flights = em.createQuery("SELECT f FROM Flight f", Flight.class).getResultList();
    	List<Hotel> hotels = em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
    	List<Excursion> excursions = em.createQuery("SELECT e FROM Excursion e", Excursion.class).getResultList();
    	
    	try {
	    	for(Flight f : flights) {
	    		list.add(new FlightDTO(f));
	    	}
	    	for(Hotel h : hotels) {
		    	list.add(new HotelDTO(h));
	    	}
		    for(Excursion e : excursions) {
			    list.add(new ExcursionDTO(e));
		    }
    	} catch (FieldNotPresentException e) {
    		e.printStackTrace();
    		System.err.println("Critical application error!");
    	}
    	
    	return list;
    }
    
    /**
     * Return a list of all base product, but it not garantee that all product has its own personalization
     */
    public List<BaseProductDTO> getAllBaseProducts() {
    	return getAllPersonalizations();
    }
    
    public List<CityDTO> getAllCities() {
    	List<City> cities = em.createQuery("SELECT c FROM City c", City.class).getResultList();
    	List<CityDTO> list = new ArrayList<CityDTO>();
    	for(City c : cities) {
    		list.add(new CityDTO(c));
    	}
    	return list;
    }
    
    public List<String> getAllCompanies() {
    	List<Company> companies = em.createQuery("SELECT c FROM Company c", Company.class).getResultList();
    	List<String> list = new ArrayList<String>();
    	for(Company c : companies) {
    		list.add(c.getName());
    	}
    	return list;
    }
    
    public List<String> getAllAirports() {
    	List<Airport> airports = em.createQuery("SELECT a FROM Airport a", Airport.class).getResultList();
    	List<String> list = new ArrayList<String>();
    	for(Airport a : airports) {
    		list.add(a.getId());
    	}
    	return list;
    }

}
