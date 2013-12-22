package coreEJB;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.BaseProductDTO;
import dto.ClassPersonalizationDTO;
import dto.DatePersonalizationDTO;
import dto.ExcursionDTO;
import dto.FieldNotPresentException;
import dto.FlightDTO;
import dto.HotelDTO;
import dto.PossiblePersonalizationDTO;
import entity.Airport;
import entity.ClassPersonalization;
import entity.Company;
import entity.DatePersonalization;
import entity.Excursion;
import entity.Flight;
import entity.Hotel;
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
    
    public void saveBaseProduct(BaseProductDTO baseProduct) {
    	//Create the right entity
    	if(baseProduct instanceof FlightDTO) {
    		FlightDTO flightDTO = (FlightDTO) baseProduct;
    		Flight flight = em.find(Flight.class, flightDTO.getId());
    		
    		if(flight == null) {
    			//The flight is not present. Built it from scratch
    			flight = new Flight();
    			flight.setAirportArrival(em.find(Airport.class, flightDTO.getAirportArrival()));
    			flight.setAirportDeparture(em.find(Airport.class, flightDTO.getAirportDeparture()));
    			flight.setCompany(em.find(Company.class, flightDTO.getCompany()));
    			flight.setName(flightDTO.getName());
    			for(ClassPersonalizationDTO cp : flightDTO.getPossibleClassPersonalizations()) {
    				ClassPersonalization classPersonalization  = em.find(ClassPersonalization.class, cp.getId());
    				
    				//Try to find with name of the class
    				if(classPersonalization == null) {
    					try {
    						classPersonalization  = em.createNamedQuery("SELECT c FROM ClassPersonalization c WHERE c.class_=:class", ClassPersonalization.class).setParameter("class", cp.get_class()).getResultList().get(0);
    					}catch(NullPointerException e) {}	//No problem
    				}
    				
    				if(classPersonalization == null) {
    					//Create new classPersonalization
    					classPersonalization = new ClassPersonalization();
    					classPersonalization.setClass_(cp.get_class());
    				}
    				flight.getClassPersonalizations().add(classPersonalization);
    			}
    			for(DatePersonalizationDTO dp : flightDTO.getPossibleDatePersonalizations()) {
    				DatePersonalization datePersonalization  = em.find(DatePersonalization.class, dp.getId());
    				
    				if(datePersonalization == null) {
    					//Create new classPersonalization
    					datePersonalization = new DatePersonalization();
    					datePersonalization.setDate(dp.getInitialDate());
    					datePersonalization.setDuration(dp.getDuration());
    				}
    				flight.getDatePersonalizations().add(datePersonalization);
    			}
    			em.persist(flight);
    		} else {
    			//The flight is already present in the DB. Update it
    			flight.setAirportArrival(em.find(Airport.class, flightDTO.getAirportArrival()));
    			flight.setAirportDeparture(em.find(Airport.class, flightDTO.getAirportDeparture()));
    			flight.setCompany(em.find(Company.class, flightDTO.getCompany()));
    			flight.setName(flightDTO.getName());
    			for(ClassPersonalizationDTO cp : flightDTO.getPossibleClassPersonalizations()) {
    				ClassPersonalization classPersonalization  = em.find(ClassPersonalization.class, cp.getId());
    				
    				//Try to find with name of the class
    				if(classPersonalization == null) {
    					try {
    						classPersonalization  = em.createNamedQuery("SELECT c FROM ClassPersonalization c WHERE c.class_=:class", ClassPersonalization.class).setParameter("class", cp.get_class()).getResultList().get(0);
    					}catch(NullPointerException e) {}	//No problem
    				}
    				
    				if(classPersonalization == null) {
    					//Create new classPersonalization
    					classPersonalization = new ClassPersonalization();
    					classPersonalization.setClass_(cp.get_class());
    				}
    				flight.getClassPersonalizations().add(classPersonalization);
    			}
    			for(DatePersonalizationDTO dp : flightDTO.getPossibleDatePersonalizations()) {
    				DatePersonalization datePersonalization  = em.find(DatePersonalization.class, dp.getId());
    				
    				if(datePersonalization == null) {
    					//Create new classPersonalization
    					datePersonalization = new DatePersonalization();
    					datePersonalization.setDate(dp.getInitialDate());
    					datePersonalization.setDuration(dp.getDuration());
    				}
    				flight.getDatePersonalizations().add(datePersonalization);
    			}
    			em.merge(flight);
    		}
    		
    	}
    }
    
    public void removeBaseProduct(BaseProductDTO baseProduct) throws NotValidBaseProductException {
    	try {
	    	if(baseProduct instanceof FlightDTO) {
	    		em.remove(em.find(Flight.class, baseProduct.getId()));
	    	}
	    	if(baseProduct instanceof HotelDTO) {
	    		em.remove(em.find(Hotel.class, baseProduct.getId()));
	    	}
	    	if(baseProduct instanceof ExcursionDTO) {
	    		em.remove(em.find(Excursion.class, baseProduct.getId()));
	    	}
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new NotValidBaseProductException();
    	}
    }
    
    public void savePersonlization(PossiblePersonalizationDTO possiblePersonalization) {
    	
    }
    
    public void removePersonalization(PossiblePersonalizationDTO possiblePersonalization) {
    	
    }
    
    /**
     * Return a list of all base product and it garantee that all product has its own possible personalization
     */
    public List<BaseProductDTO> getAllPersonalization() {
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
    	return getAllPersonalization();
    }

}
