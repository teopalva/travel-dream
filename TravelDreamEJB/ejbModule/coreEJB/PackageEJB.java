package coreEJB;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.CityDTO;
import dto.PackageDTO;
import dto.PersonalizedExcursionDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedHotelDTO;
import dto.PersonalizedProductDTO;
import entity.Airport;
import entity.ClassPersonalization;
import entity.Company;
import entity.DatePersonalization;
import entity.Excursion;
import entity.Flight;
import entity.Hotel;
import entity.Package;
import entity.PersonalizedProductExcursion;
import entity.PersonalizedProductFlight;
import entity.PersonalizedProductHotel;
import entity.Image;
import exceptions.FieldNotPresentException;
import exceptions.NotValidPackageException;

/**
 * Session Bean implementation class PackageEJB
 */
@Stateful
@LocalBean
public class PackageEJB implements PackageEJBLocal {

	@PersistenceContext
	EntityManager em;
	
	@EJB
	BaseProductEJBLocal baseProductEJB;

	PackageDTO tmpPackage;
	boolean selectedFlight;
	boolean selectedHotel;
	boolean selectedExcursion;

	public PackageEJB() {
		tmpPackage = new PackageDTO();
	}

	/**
	 * Return a list of all PackageDTO that are not in a BuyingList of a TDC or in a gift list
	 */
	public List<PackageDTO> getOfferingPackages() {
		List<PackageDTO> list = new ArrayList<PackageDTO>();
		List<Package> packageList = em
				.createNativeQuery(
						"SELECT * FROM PACKAGE WHERE Id NOT IN(SELECT PackageId" + "	                FROM BUYING_LIST_ITEM)" + "   AND Id NOT IN(SELECT PackageId" + "					FROM GIFT_LIST_ITEM)",
						Package.class).getResultList();
		for (Package p : packageList) {
			try {
				list.add(new PackageDTO(p));
			} catch (FieldNotPresentException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * Save a package inside the DB. Mandatory field are: name, numPeople, reduction Optional field are: personalizedProducts If you include at least
	 * one personalized product every one of them must be coherent with the ER representation (eg: PersonalizedFlightDTO must contain FlightDTO and
	 * may contain Class/DataPersonalization)
	 */
	public void savePackage(PackageDTO packageDTO) throws NotValidPackageException {
		Package _package = new Package();
		_package.setName(packageDTO.getName());
		_package.setNumPeople(packageDTO.getNumPeople());
		_package.setReduction(packageDTO.getReduction());
		
		//try to get the image
		Image image = em.find(Image.class, packageDTO.getImageId());
		if(image != null)
			_package.setImage(image);

		if (_package.getName() == null)
			throw new NotValidPackageException();

		try {
			// Create personalized product list
			for (PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {

				// personalizedProductFlight
				if (p instanceof PersonalizedFlightDTO) {
					PersonalizedFlightDTO flightDTO = (PersonalizedFlightDTO) p;
					PersonalizedProductFlight flight = em.find(PersonalizedProductFlight.class, p.getId());
					if (flight == null) {
						// Create a new Flight
						flight = new PersonalizedProductFlight();

						// Create flight base product
						Flight baseFlight = em.find(Flight.class, flightDTO.getFlight().getId());
						if (baseFlight == null) {
							baseFlight = new Flight();
							Airport airport = em.find(Airport.class, flightDTO.getFlight().getAirportArrival());
							baseFlight.setAirportArrival(airport);
							airport = em.find(Airport.class, flightDTO.getFlight().getAirportDeparture());
							baseFlight.setAirportDeparture(airport);
							Company company = em.find(Company.class, flightDTO.getFlight().getCompany());
							baseFlight.setCompany(company);
							baseFlight.setName(flightDTO.getFlight().getName());
						}
						flight.setFlight(baseFlight);
						// Try to set classPersonalization
						try {
							ClassPersonalization classPersonalization = em.find(ClassPersonalization.class, flightDTO.getClassPersonalization().getId());
							if (classPersonalization == null) {
								// Create new class personalization
								classPersonalization = new ClassPersonalization();
								classPersonalization.setClass_(flightDTO.getClassPersonalization().get_class());
							}
							flight.setClassPersonalization(classPersonalization);
						} catch (NullPointerException e) {
						} // No problem

						// Try to set datePersonalization
						try {
							DatePersonalization datePersonalization = em.find(DatePersonalization.class, flightDTO.getDatePersonalization().getId());
							if (datePersonalization == null) {
								// Create new date personalization
								datePersonalization = new DatePersonalization();
								datePersonalization.setDate(flightDTO.getDatePersonalization().getInitialDate());
								datePersonalization.setDuration(flightDTO.getDatePersonalization().getDuration());
							}
							flight.setDatePersonalization(datePersonalization);
						} catch (NullPointerException e) {
						} // No problem

					}

					_package.addPersonalizedProductFlight(flight);
				}

				// PersonalizedProductExcursion
				if (p instanceof PersonalizedHotelDTO) {
					PersonalizedHotelDTO hotelDTO = (PersonalizedHotelDTO) p;
					PersonalizedProductHotel hotel = em.find(PersonalizedProductHotel.class, p.getId());
					if (hotel == null) {
						// Create a new Flight
						hotel = new PersonalizedProductHotel();

						// Create flight base product
						Hotel baseHotel = em.find(Hotel.class, hotelDTO.getHotel().getId());
						if (baseHotel == null) {
							baseHotel = new Hotel();
							baseHotel.setStars(hotelDTO.getHotel().getStars());
							Company company = em.find(Company.class, hotelDTO.getHotel().getCompany());
							baseHotel.setCompany(company);
							baseHotel.setName(hotelDTO.getHotel().getName());
						}
						hotel.setHotel(baseHotel);
						// Try to set classPersonalization
						try {
							ClassPersonalization classPersonalization = em.find(ClassPersonalization.class, hotelDTO.getClassPersonalization().getId());
							if (classPersonalization == null) {
								// Create new class personalization
								classPersonalization = new ClassPersonalization();
								classPersonalization.setClass_(hotelDTO.getClassPersonalization().get_class());
							}
							hotel.setClassPersonalization(classPersonalization);
						} catch (NullPointerException e) {
						} // No problem

					}

					_package.addPersonalizedProductHotel(hotel);
				}

				// PersonalizedProductExcursion
				if (p instanceof PersonalizedExcursionDTO) {
					PersonalizedExcursionDTO excursionDTO = (PersonalizedExcursionDTO) p;
					PersonalizedProductExcursion excursion = em.find(PersonalizedProductExcursion.class, p.getId());
					if (excursion == null) {
						// Create a new Flight
						excursion = new PersonalizedProductExcursion();

						// Create flight base product
						Excursion baseExcursion = em.find(Excursion.class, excursionDTO.getExcursion().getId());
						if (baseExcursion == null) {
							baseExcursion = new Excursion();
							Company company = em.find(Company.class, excursionDTO.getExcursion().getCompany());
							baseExcursion.setCompany(company);
							baseExcursion.setName(excursionDTO.getExcursion().getName());
						}
						excursion.setExcursion(baseExcursion);

						// Try to set datePersonalization
						try {
							DatePersonalization datePersonalization = em.find(DatePersonalization.class, excursionDTO.getDatePersonalization().getId());
							if (datePersonalization == null) {
								// Create new date personalization
								datePersonalization = new DatePersonalization();
								datePersonalization.setDate(excursionDTO.getDatePersonalization().getInitialDate());
								datePersonalization.setDuration(excursionDTO.getDatePersonalization().getDuration());
							}
							excursion.setDatePersonalization(datePersonalization);
						} catch (NullPointerException e) {
						} // No problem

					}

					_package.addPersonalizedProductExcursion(excursion);
				}
			}
		} catch (NullPointerException e) {
			throw new NotValidPackageException();
		}
		if (packageDTO.getId() >= 0)
			removePackage(packageDTO);
		em.persist(_package);
		em.flush();
		
		//Set the id inside the packageDTO
		packageDTO.setId(_package.getId());
	}

	/**
	 * Remove the package, N.B.: it must contains a valid id
	 */
	public void removePackage(PackageDTO _package) throws NotValidPackageException {
		em.getTransaction().begin();
		Package package_ = em.find(Package.class, _package.getId());

		if (package_ == null)
			throw new NotValidPackageException();

		em.remove(package_);
		em.getTransaction().commit();

	}

	public byte[] getPackageImage(PackageDTO packageDTO) throws NotValidPackageException {
		Package _package = null;
		try {
			_package = em.createQuery("SELECT p FROM Package p JOIN FETCH p.image WHERE p.id=:id", Package.class).setParameter("id", packageDTO.getId()).getResultList().get(0);
		} catch (IndexOutOfBoundsException e) {
			throw new NotValidPackageException();
		}
		if (_package == null)
			throw new NotValidPackageException();
		return _package.getImage().getData();
	}

	public PackageDTO getTmpPackage() {
		return tmpPackage;
	}

	public void setTmpPackage(PackageDTO tmpPackage) {
		this.tmpPackage = tmpPackage;
	}

	public boolean isSelectedFlight() {
		return selectedFlight;
	}

	public void setSelectedFlight(boolean selectedFlight) {
		this.selectedFlight = selectedFlight;
	}

	public boolean isSelectedHotel() {
		return selectedHotel;
	}

	public void setSelectedHotel(boolean selectedHotel) {
		this.selectedHotel = selectedHotel;
	}

	public boolean isSelectedExcursion() {
		return selectedExcursion;
	}

	public void setSelectedExcursion(boolean selectedExcursion) {
		this.selectedExcursion = selectedExcursion;
	}
	
    /** 
     * @return true if the package contains at least 2 flight and 1 hotel (1 excursion is optional).
     * 			If the hotel and flights (or excursion) are incompatible in dates return false.
     * 			Dates can be omitted 
     */
    public boolean isValidForOffering(PackageDTO packageDTO) {
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();
    	PersonalizedHotelDTO hotel = null;
    	PersonalizedExcursionDTO excursion = null;
    	
    	for(PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
			}
			if(p instanceof PersonalizedHotelDTO) {
				if(hotel == null)
					hotel = (PersonalizedHotelDTO)p;
				else
					return false;
			}
			if(p instanceof PersonalizedExcursionDTO) {
				if(excursion == null)
					excursion = (PersonalizedExcursionDTO)p;
				else
					return false;
			}
    	}
    	
    	if(flights.size() != 2 || hotel == null)
    		return false;
    	
    	//Check validity of airports
    	CityDTO city1 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportDeparture());
    	CityDTO city2 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportArrival());
    	CityDTO city3 = baseProductEJB.getCity(flights.get(1).getFlight().getAirportDeparture());
    	CityDTO city4 = baseProductEJB.getCity(flights.get(1).getFlight().getAirportArrival());
    	
    	if(city1 == null || city2 == null || city3 == null || city4 == null)
    		return false;
    	
    	if(!city1.equals(city4) || !city2.equals(city3))
    		return false;
    		
    	//Check validity of city of airports + hotel + excursion
    	CityDTO hotelCity = hotel.getHotel().getCity();
    	CityDTO excursionCity = null;
    	Date excursionDate = null;
    	
    	if(hotelCity == null)
    		return false;
    	
    	if(excursion != null) {	//Excursion is optional
    		excursionCity = excursion.getExcursion().getCity();
    		excursionDate = excursion.getDatePersonalization().getInitialDate();
    		
    		if(excursionCity == null)
    			return false;
    		if(!excursionCity.equals(hotelCity))
    			return false;
    	}
    	
    	if(!hotelCity.equals(city1) && !hotelCity.equals(city2))
    		return false;
    	
    	if(hotelCity.equals(city1)) {
    		//flights[0] return flight
    		//flights[1] departure flight
    		Date dateFlightDeparture = flights.get(1).getDatePersonalization().getFinalDate();
    		Date dateFlightReturn = flights.get(0).getDatePersonalization().getInitialDate();
    		if(dateFlightDeparture != null && dateFlightReturn != null) {
	    		if(dateFlightDeparture.after(dateFlightReturn))
	    			return false;
	    		if(excursionDate != null) {
	    			if(!excursionDate.after(dateFlightDeparture) || excursionDate.before(dateFlightReturn))
	    				return false;
	    		}
    		}
    	}
    	else {
    		//flights[1] return flight
    		//flights[0] departure flight
    		Date dateFlightDeparture = flights.get(0).getDatePersonalization().getFinalDate();
    		Date dateFlightReturn = flights.get(1).getDatePersonalization().getInitialDate();
    		if(dateFlightDeparture != null && dateFlightReturn != null) {
	    		if(dateFlightDeparture.after(dateFlightReturn))
	    			return false;
	    		if(excursionDate != null) {
	    			if(!excursionDate.after(dateFlightDeparture) || excursionDate.before(dateFlightReturn))
	    				return false;
	    		}
    		}
    	}
    		
    	return true;
    }
    
    /** 
     * Same as isValidForOffering() but dates can't be omitted
     * @return true if the package contains at least 2 flight and 1 hotel (1 excursion is optional).
     * 			If the hotel and flights (or excursion) are incompatible in dates return false. 
     */
    public boolean isValidForTDC(PackageDTO packageDTO) {
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();
    	PersonalizedHotelDTO hotel = null;
    	PersonalizedExcursionDTO excursion = null;

    	for(PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
    		}
    		if(p instanceof PersonalizedHotelDTO) {
    			if(hotel == null)
    				hotel = (PersonalizedHotelDTO)p;
    			else
    				return false;
    		}
    		if(p instanceof PersonalizedExcursionDTO) {
    			if(excursion == null)
    				excursion = (PersonalizedExcursionDTO)p;
    			else
    				return false;
    		}
    	}

    	if(flights.size() != 2 || hotel == null)
    		return false;

    	//Check validity of airports
    	CityDTO city1 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportDeparture());
    	CityDTO city2 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportArrival());
    	CityDTO city3 = baseProductEJB.getCity(flights.get(1).getFlight().getAirportDeparture());
    	CityDTO city4 = baseProductEJB.getCity(flights.get(1).getFlight().getAirportArrival());

    	if(city1 == null || city2 == null || city3 == null || city4 == null)
    		return false;

    	if(!city1.equals(city4) || !city2.equals(city3))
    		return false;

    	//Check validity of city of airports + hotel + excursion
    	CityDTO hotelCity = hotel.getHotel().getCity();
    	CityDTO excursionCity = null;
    	Date excursionDate = null;

    	if(hotelCity == null)
    		return false;

    	if(excursion != null) {	//Excursion is optional
    		excursionCity = excursion.getExcursion().getCity();
    		excursionDate = excursion.getDatePersonalization().getInitialDate();

    		if(excursionCity == null)
    			return false;
    		if(!excursionCity.equals(hotelCity))
    			return false;
    	}

    	if(!hotelCity.equals(city1) && !hotelCity.equals(city2))
    		return false;

    	if(hotelCity.equals(city1)) {
    		//flights[0] return flight
    		//flights[1] departure flight
    		Date dateFlightDeparture = flights.get(1).getDatePersonalization().getFinalDate();
    		Date dateFlightReturn = flights.get(0).getDatePersonalization().getInitialDate();
    		if(dateFlightDeparture.after(dateFlightReturn))
    			return false;
    		if(excursionDate != null) {
    			if(!excursionDate.after(dateFlightDeparture) || excursionDate.before(dateFlightReturn))
    				return false;
    		}
    	}
    	else {
    		//flights[1] return flight
    		//flights[0] departure flight
    		Date dateFlightDeparture = flights.get(0).getDatePersonalization().getFinalDate();
    		Date dateFlightReturn = flights.get(1).getDatePersonalization().getInitialDate();
    		if(dateFlightDeparture.after(dateFlightReturn))
    			return false;
    		if(excursionDate != null) {
    			if(!excursionDate.after(dateFlightDeparture) || excursionDate.before(dateFlightReturn))
    				return false;
    		}
    	}

    	return true;
    }
    
    /**
     * @return the date of the departure (departure flight)
     * 		The package must be isValidForTDC()
     */
    public Date dateDeparture(PackageDTO packageDTO) {
    	if(!isValidForTDC(packageDTO))
    		return null;
    	
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();
    	PersonalizedHotelDTO hotel = null;

    	for(PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
    		}
    		if(p instanceof PersonalizedHotelDTO) {
    			hotel = (PersonalizedHotelDTO)p;
    		}
    	}
    	
    	CityDTO city1 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportDeparture());
    	CityDTO hotelCity = hotel.getHotel().getCity();
    	
    	if(hotelCity.equals(city1)) {
    		//flights[0] return flight
    		//flights[1] departure flight
    		Date dateFlightDeparture = flights.get(1).getDatePersonalization().getFinalDate();
    		return dateFlightDeparture;
    	}
    	else {
    		//flights[1] return flight
    		//flights[0] departure flight
    		Date dateFlightDeparture = flights.get(0).getDatePersonalization().getFinalDate();
    		return dateFlightDeparture;
    	}
    }
    
    /**
     * @return the date of the return (return flight)
     * 		The package must be isValidForTDC()
     */
    public Date dateReturn(PackageDTO packageDTO) {
    	if(!isValidForTDC(packageDTO))
    		return null;
    	
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();
    	PersonalizedHotelDTO hotel = null;

    	for(PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
    		}
    		if(p instanceof PersonalizedHotelDTO) {
    			hotel = (PersonalizedHotelDTO)p;
    		}
    	}
    	
    	CityDTO city1 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportDeparture());
    	CityDTO hotelCity = hotel.getHotel().getCity();
    	
    	if(hotelCity.equals(city1)) {
    		//flights[0] return flight
    		//flights[1] departure flight
    		Date dateFlightReturn = flights.get(0).getDatePersonalization().getInitialDate();
    		return dateFlightReturn;
    	}
    	else {
    		//flights[1] return flight
    		//flights[0] departure flight
    		Date dateFlightReturn = flights.get(1).getDatePersonalization().getInitialDate();
    		return dateFlightReturn;
    	}
    }
    
    /**
     * @return the city of the departure of the first flight
     */
    public CityDTO cityArrival(PackageDTO packageDTO) {
    	if(!isValidForTDC(packageDTO))
    		return null;
    	
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();
    	PersonalizedHotelDTO hotel = null;

    	for(PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
    		}
    		if(p instanceof PersonalizedHotelDTO) {
    			hotel = (PersonalizedHotelDTO)p;
    		}
    	}
    	
    	CityDTO city1 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportDeparture());
    	CityDTO city2 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportArrival());
    	CityDTO hotelCity = hotel.getHotel().getCity();
    	
    	if(hotelCity.equals(city1)) {
    		//flights[0] return flight
    		//flights[1] departure flight
    		return city1;
    	}
    	else {
    		//flights[1] return flight
    		//flights[0] departure flight
    		return city2;
    	}
    }
    
    /**
     * @return the city of the hotel [and excursion (optional)]
     */
    public CityDTO cityReturn(PackageDTO packageDTO) {
    	if(!isValidForTDC(packageDTO))
    		return null;
    	
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();
    	PersonalizedHotelDTO hotel = null;

    	for(PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
    		}
    		if(p instanceof PersonalizedHotelDTO) {
    			hotel = (PersonalizedHotelDTO)p;
    		}
    	}
    	
    	CityDTO city1 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportDeparture());
    	CityDTO city2 = baseProductEJB.getCity(flights.get(0).getFlight().getAirportArrival());
    	CityDTO hotelCity = hotel.getHotel().getCity();
    	
    	if(hotelCity.equals(city1)) {
    		//flights[0] return flight
    		//flights[1] departure flight
    		return city2;
    	}
    	else {
    		//flights[1] return flight
    		//flights[0] departure flight
    		return city1;
    	}
    }

}
