package coreEJB;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.FieldNotPresentException;
import dto.PackageDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedProductDTO;
import entity.Airport;
import entity.ClassPersonalization;
import entity.Company;
import entity.DatePersonalization;
import entity.Flight;
import entity.Package;
import entity.PersonalizedProductFlight;

/**
 * Session Bean implementation class PackageEJB
 */
@Stateful
@LocalBean
public class PackageEJB implements PackageEJBLocal {
	
	@PersistenceContext
	EntityManager em;

    PackageDTO tmpPackage;
    boolean selectedFlight;
    boolean selectedHotel;
    boolean selectedExcursion;
    
    public PackageEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public List<PackageDTO> getOfferingPackages() {
    	List<PackageDTO> list = new ArrayList<PackageDTO>();
    	List <Package> packageList = em.createNativeQuery("SELECT * FROM PACKAGE WHERE Id NOT IN(SELECT PackageId"
    																		+ "	                FROM BUYING_LIST_ITEM)"
    																		+ "   AND Id NOT IN(SELECT PackageId"
    																		+ "					FROM GIFT_LIST_ITEM)", Package.class).getResultList();
    	for(Package p : packageList) {
    		try {
				list.add(new PackageDTO(p));
			} catch (FieldNotPresentException e) {
				e.printStackTrace();
			}
    	}
    	return list;
    }
	public void savePackage(PackageDTO packageDTO) {
		Package _package = new Package();
		_package.setName(packageDTO.getName());
		_package.setNumPeople(packageDTO.getNumPeople());
		_package.setReduction(packageDTO.getReduction());
		
		//Create personalized product list
		for(PersonalizedProductDTO p : packageDTO.getPersonalizedProducts()) {
			if(p instanceof PersonalizedFlightDTO) {
				PersonalizedFlightDTO flightDTO = (PersonalizedFlightDTO)p;
				PersonalizedProductFlight flight = em.find(PersonalizedProductFlight.class, p.getId());
				if(flight == null) {
					//Create a new Flight
					flight = new PersonalizedProductFlight();
					
					//Create flight base product
					Flight baseFlight = em.find(Flight.class, flightDTO.getFlight().getId());
					if(baseFlight == null) {
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
					//Try to set classPersonalization
					try {
						ClassPersonalization classPersonalization = em.find(ClassPersonalization.class, flightDTO.getClassPersonalization().getId());
						if(classPersonalization == null) {
							//Create new class personalization
							classPersonalization = new ClassPersonalization();
							classPersonalization.setClass_(flightDTO.getClassPersonalization().get_class());
						}
						flight.setClassPersonalization(classPersonalization);
					} catch (NullPointerException e) {}	//No problem
					
					//Try to set datePersonalization
					try {
						DatePersonalization datePersonalization = em.find(DatePersonalization.class, flightDTO.getDatePersonalization().getId());
						if(datePersonalization == null) {
							//Create new date personalization
							datePersonalization = new DatePersonalization();
							datePersonalization.setDate(flightDTO.getDatePersonalization().getInitialDate());
							datePersonalization.setDuration(flightDTO.getDatePersonalization().getDuration());
						}
						flight.setDatePersonalization(datePersonalization);
					} catch (NullPointerException e) {}	//No problem
					
				}
				
				_package.addPersonalizedProductFlight(flight);
			}
		}
		em.persist(_package);
	}
	public void removePackage(PackageDTO _package){
		
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
	

}
