package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.DragDropEvent;

import coreEJB.BaseProductEJBLocal;
import dto.BaseProductDTO;
import dto.DatePersonalizationDTO;
import dto.ExcursionDTO;
import dto.FlightDTO;
import dto.HotelDTO;
import dto.PackageDTO;
import dto.PersonalizedExcursionDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedHotelDTO;
import dto.PersonalizedProductDTO;

@ManagedBean(name = "EditPackage")
@ViewScoped
public class EditPackageBean {
    // private String warningDiscount; TODO
    private PackageDTO selectedPackage = null;
    private double totalPrice;

    private String departurePlace = null;
    private String arrivalPlace = null;
    private Date departureDate = null;
    private Date returnDate = null;
    private Integer numPeople = null;

    private List<PersonalizedHotelDTO> hotels;
    private List<PersonalizedFlightDTO> outboundFlights;
    private List<PersonalizedFlightDTO> returnFlights;
    private List<PersonalizedExcursionDTO> excursions;

    @EJB
    private BaseProductEJBLocal baseProductEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public EditPackageBean() {
	outboundFlights = new ArrayList<PersonalizedFlightDTO>();
	returnFlights = new ArrayList<PersonalizedFlightDTO>();
	hotels = new ArrayList<PersonalizedHotelDTO>();
	excursions = new ArrayList<PersonalizedExcursionDTO>();
    }

    @PostConstruct
    public void init() {
	if (sessionStorage.getSelectedPackage() == null) {
	    selectedPackage = new PackageDTO();
	} else {
	    selectedPackage = sessionStorage.getSelectedPackage();
	}
    }

    // Bean properties:

    public void setSelectedPackage(PackageDTO p) {
	selectedPackage = p;
    }

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public void setDeparturePlace(String p) {
	departurePlace = p;
    }

    public String getDeparturePlace() {
	return departurePlace;
    }

    public void setArrivalPlace(String p) {
	arrivalPlace = p;
    }

    public String getArrivalPlace() {
	return arrivalPlace;
    }

    public void setDepartureDate(Date d) {
	departureDate = d;
    }

    public Date getDepartureDate() {
	return departureDate;
    }

    public void setReturnDate(Date d) {
	returnDate = d;
    }

    public Date getReturnDate() {
	return returnDate;
    }

    public void setNumPeople(Integer n) {
	numPeople = n;
    }

    public Integer getNumPeople() {
	return numPeople;
    }

    public void setTotalPrice(double n) {
	totalPrice = n;
    }

    public double getTotalPrice() {
	return totalPrice;
    }

    public List<PersonalizedHotelDTO> getHotels() {
	return hotels;
    }

    public List<PersonalizedFlightDTO> getOutboundFlights() {
	return outboundFlights;
    }

    public List<PersonalizedFlightDTO> getReturnFlights() {
	return returnFlights;
    }

    public List<PersonalizedExcursionDTO> getExcursions() {
	return excursions;
    }

    // Bean methods:

    /**
     * Refreshes the lists of base products. Do a new getHotels() with Ajax to get the refreshed list. Requires that all search fields are non-empty!
     */
    public void submitSearch() {
	refreshLists();
    }

    private void refreshLists() {
	hotels.clear();
	outboundFlights.clear();
	returnFlights.clear();
	excursions.clear();
	List<BaseProductDTO> list = baseProductEJB.getAllPersonalizations();
	try {
	    for (BaseProductDTO bp : list) {
		if (bp instanceof HotelDTO) {
		    if (arrivalPlace.equals(((HotelDTO) bp).getCity().getName())) {
			PersonalizedHotelDTO h = new PersonalizedHotelDTO((HotelDTO) bp);
			hotels.add(h);
		    }
		}
		if (bp instanceof FlightDTO) {
		    if ((departurePlace.equals(((FlightDTO) bp).getAirportDeparture())) && arrivalPlace.equals(((FlightDTO) bp).getAirportArrival())) {
			for (DatePersonalizationDTO d : ((FlightDTO) bp).getPossibleDatePersonalizations()) {
			    if (departureDate.equals(d.getInitialDate())) {
				PersonalizedFlightDTO f = new PersonalizedFlightDTO((FlightDTO) bp);
				f.setDatePersonalization(d);
				outboundFlights.add(f);
			    }
			}
		    } else {
			if ((departurePlace.equals(((FlightDTO) bp).getAirportArrival())) && arrivalPlace.equals(((FlightDTO) bp).getAirportDeparture())) {
			    for (DatePersonalizationDTO d : ((FlightDTO) bp).getPossibleDatePersonalizations()) {
				if (returnDate.equals(d.getInitialDate())) {
				    PersonalizedFlightDTO f = new PersonalizedFlightDTO((FlightDTO) bp);
				    f.setDatePersonalization(d);
				    returnFlights.add(f);
				}
			    }
			}
		    }
		}
		if (bp instanceof ExcursionDTO) {
		    if (arrivalPlace.equals(((ExcursionDTO) bp).getCity().getName())) {
			PersonalizedExcursionDTO e = new PersonalizedExcursionDTO((ExcursionDTO) bp);
			excursions.add(e);
		    }
		}
	    }
	} catch (NullPointerException e) {
	    System.err.printf("Devi riempire tutti i campi!");
	}
    }

    /**
     * Recalculates the total cost of the current package
     */
    public double calculatePrice() {
	double p = 0;
	try {
	    p = selectedPackage.getPrice();
	} catch (NullPointerException e) {
	    System.err.print("Empty Package is free!");
	}
	return p;
    }

    /**
     * Substitutes current product at index based on its type with the selected one (new)
     * 
     * @param _new
     * @param index
     */
    public void updatePackage(PersonalizedProductDTO _new, int index) {
	selectedPackage.getPersonalizedProducts().set(index, _new);
    }

    public String showCheckout() {
	sessionStorage.setSelectedPackage(selectedPackage);
	sessionStorage.setPreviousPage("edit");
	return "user/checkout?faces-redirect=true";
    }
    
    /**
     * Function called by the jsf when a drag and drop happens
     * String is draggedId type _category_id
     * Example: for excursion (category = 3) and with id 11 the string is _3_11
     *			for hotels (category = 2) and with id 23 string is _2_23
     */
    public void onDrop(DragDropEvent ddEvent) {
    	
    	String draggedId = ddEvent.getDragId();
        String[] parts = draggedId.split("_");
        
        String category = parts[1];
        String id = parts[2];
        
        // now you have the variables category and id ready ;) by cesco :) VERY HAPPY!
        
        System.out.println("Categoria: "+category);
        System.out.println("ID: "+id);
    	
    }
    
    
}
