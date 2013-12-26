package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import coreEJB.BaseProductEJBLocal;
import coreEJB.PackageEJBLocal;
import coreEJB.PagesEJBLocal;
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

    @EJB
    private PackageEJBLocal packageEJB;

    @EJB
    private PagesEJBLocal pagesEJB;

    // @ManagedProperty("#{OfferingsList.selectedPackage}")
    // private PackageDTO selectedPackage;

    // @ManagedProperty("#{OfferingsList.selectedPackageString}")
    // private String selectedPackageString;

    public EditPackageBean() {
	outboundFlights = new ArrayList<PersonalizedFlightDTO>();
	returnFlights = new ArrayList<PersonalizedFlightDTO>();
	hotels = new ArrayList<PersonalizedHotelDTO>();
	excursions = new ArrayList<PersonalizedExcursionDTO>();
    }

    @PostConstruct
    public void init() {
	// this.selectedPackage.setName(selectedPackageString);
	if (packageEJB.getTmpPackage() == null) {
	    selectedPackage = new PackageDTO();
	} else {
	    selectedPackage = packageEJB.getTmpPackage();
	}
    }

    // Bean properties:

    public void setSelectedPackage(PackageDTO p) {
	selectedPackage = p;
    }

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    /*
     * public String getselectedPackageString() { return selectedPackageString; }
     * 
     * public void setselectedPackageString(String s) { selectedPackageString = s; }
     */

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
	return selectedPackage.getPrice();
    }

    /**
     * Substitutes current product (old) with the selected one (new)
     * 
     * @param bp
     */
    public void updatePackage(PersonalizedProductDTO _new, PersonalizedProductDTO _old) {
	if (_old != null) {
	    selectedPackage.getPersonalizedProducts().remove(_old);
	}
	selectedPackage.addPersonalizedProduct(_new);
    }

    public String showCheckout() {
	packageEJB.setTmpPackage(selectedPackage);
	pagesEJB.setPreviousPage("edit");
	return "user/checkout?faces-redirect=true";
    }
}
