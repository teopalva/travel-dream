package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BaseProductEJBLocal;
import coreEJB.PackageEJBLocal;
import dto.BaseProductDTO;
import dto.ExcursionDTO;
import dto.FlightDTO;
import dto.HotelDTO;
import dto.PackageDTO;
import dto.PersonalizedHotelDTO;
import dto.PersonalizedProductDTO;

@ManagedBean(name = "EditPackage")
@ViewScoped
public class EditPackageBean {
    private String warningDiscount;
    private PackageDTO selectedPackage = null;
    private double totalPrice;

    private String departurePlace = null;
    private String arrivalPlace = null;
    private Date departureDate = null;
    private Date returnDate = null;
    private Integer numPeople = null;

    private List<HotelDTO> hotels;
    private List<FlightDTO> flights;
    private List<ExcursionDTO> excursions;

    @EJB
    private BaseProductEJBLocal baseProductEJB;

    @EJB
    private PackageEJBLocal packageEJB;

    // @ManagedProperty("#{OfferingsList.selectedPackage}")
    // private PackageDTO selectedPackage; // TODO use this

    @ManagedProperty("#{OfferingsList.selectedPackageString}")
    private String selectedPackageString;

    public EditPackageBean() {
	if (packageEJB.getTmpPackage() == null) {
	    selectedPackage = new PackageDTO();
	} else {
	    selectedPackage = packageEJB.getTmpPackage();
	    calculatePrice();
	}
	flights = new ArrayList<FlightDTO>();
	hotels = new ArrayList<HotelDTO>();
	excursions = new ArrayList<ExcursionDTO>();
    }

    @PostConstruct
    public void init() {
	this.selectedPackage.setName(selectedPackageString);
    }

    // Bean properties:

    public void setPackageDto(PackageDTO p) {
	selectedPackage = p;
    }

    public PackageDTO getPackageDto() {
	return selectedPackage;
    }

    public String getselectedPackageString() {
	return selectedPackageString;
    }

    public void setselectedPackageString(String s) {
	selectedPackageString = s;
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

    // Bean methods:

    public void submitSearch() {
	refreshLists();
    }

    private void refreshLists() {
	hotels.clear();
	flights.clear();
	excursions.clear();
	List<BaseProductDTO> list = baseProductEJB.getAllPersonalizations();
	try {
	    for (BaseProductDTO bp : list) {
		if (bp instanceof HotelDTO) {
		    if (this.getArrivalPlace() == ((HotelDTO) bp).getCity().getName()) {
			hotels.add((HotelDTO) bp);
		    }
		}
		if (bp instanceof FlightDTO) {
		    if ((departurePlace.equals(((FlightDTO) bp).getAirportDeparture())) && arrivalPlace.equals(((FlightDTO) bp).getAirportArrival())
		    // && departureDate.equals(((FlightDTO) bp).g) TODO fix with personalization date
		    ) {
			flights.add((FlightDTO) bp);
		    }
		}
		if (bp instanceof ExcursionDTO) {
		    if (this.getArrivalPlace() == ((ExcursionDTO) bp).getCity().getName()) {
			excursions.add((ExcursionDTO) bp);
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
    public void calculatePrice() {
	for (PersonalizedProductDTO p : selectedPackage.getPersonalizedProducts()) {
	    if (p instanceof PersonalizedHotelDTO) {
		// TODO ((PersonalizedHotelDTO) p).getHotel().getPrices().get(personalization);
	    }
	}
    }

    public String showCheckout() {
	// TODO setter dei campi del pacchetto con aggiunta PB
	packageEJB.setTmpPackage(selectedPackage);
	return "user/checkout?faces-redirect=true";
    }
}
