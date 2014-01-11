package bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import coreEJB.AuthenticationEJBLocal;
import coreEJB.BaseProductEJBLocal;
import coreEJB.PackageEJBLocal;
import dto.PackageDTO;
import exceptions.PackageNotValidException;

@ManagedBean(name = "OfferingsList")
@ViewScoped
public class OfferingsListBean {

    private String departurePlace = "";
    private String arrivalPlace = "";
    private Date departureDate = null;
    private Date returnDate = null;
    private Integer numPeople = null;

    private String flightClass = "null";
    private int hotelStars = 0;
    private String hotelClass = "null";

    @EJB
    private AuthenticationEJBLocal authEJB;

    @EJB
    private PackageEJBLocal packageEJB;

    @EJB
    private BaseProductEJBLocal bpEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    @PostConstruct
    private void init() {
	if (!sessionStorage.getDeparturePlace().equals("")) {
	    departurePlace = sessionStorage.getDeparturePlace();
	    sessionStorage.setDeparturePlace("");
	}
	if (!sessionStorage.getArrivalPlace().equals("")) {
	    arrivalPlace = sessionStorage.getArrivalPlace();
	    sessionStorage.setArrivalPlace("");
	}
	// submitSearch();
    }

    // Bean properties:

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

    public void setFlightClass(String c) {
	flightClass = c;
    }

    public String getFlightClass() {
	return flightClass;
    }

    public void setHotelClass(String c) {
	hotelClass = c;
    }

    public String getHotelClass() {
	return hotelClass;
    }

    public void setHotelStars(int s) {
	hotelStars = s;
    }

    public int getHotelStars() {
	return hotelStars;
    }

    // Action controller methods:

    /**
     * Performs the search by calling the search engine which filters the packages.
     * 
     * @return the list of filtered offerings
     */
    public List<PackageDTO> submitSearch() {
	List<PackageDTO> offerings;
	offerings = packageEJB.getOfferingPackages();
	return searchFilter(offerings);
    }

    /**
     * Filters the list of packages based on the selected criteria.
     * 
     * @param offerings the list of all of the packages into the db
     * @return the list of filtered offerings
     */
    public List<PackageDTO> searchFilter(List<PackageDTO> offerings) {
	List<PackageDTO> filteredOfferings = new ArrayList<PackageDTO>();
	for (PackageDTO pack : offerings) {
	    try {
		if (!packageEJB.isValidForOffering(pack)) {
		    throw new PackageNotValidException();
		}
		// basic search filters
		if (numPeopleCheck(pack) && departurePlaceCheck(pack) && arrivalPlaceCheck(pack) && departureDateCheck(pack) && returnDateCheck(pack)) {
		    // advanced filters
		    if (hotelStarsCheck(pack) && flightClassCheck(pack) && hotelClassCheck(pack)) {
			filteredOfferings.add(pack);
		    }
		}
	    } catch (PackageNotValidException e) {
		System.err.print("Pacchetto non valido.");
		e.printStackTrace();
	    }
	}
	return filteredOfferings;
    }

    /**
     * Compares the searched number of people with that of a specific base product.
     * 
     * @param pack
     * @return boolean answer
     */
    private boolean numPeopleCheck(PackageDTO pack) {
	return (numPeople == null || pack.getNumPeople() == numPeople) ? true : false;
    }

    /**
     * Compares the searched departurePlace with that of a specific base product.
     * 
     * @param reorderedPackage
     * @return boolean answer
     */
    private boolean departurePlaceCheck(PackageDTO pack) {
	return (departurePlace.equals("") || (pack.getOutboundFlight().getFlight().getCityDeparture().getName().equalsIgnoreCase(departurePlace))) ? true : false;
    }

    /**
     * Compares the searched arrivalPlace with that of a specific base product.
     * 
     * @param reorderedPackage
     * @return boolean answer
     */
    private boolean arrivalPlaceCheck(PackageDTO pack) {
	return (arrivalPlace.equals("") || (pack.getOutboundFlight().getFlight().getCityDeparture().getName().equalsIgnoreCase(arrivalPlace))) ? true : false;
    }

    /**
     * Compares the searched departureDate with that of a specific base product.
     * 
     * @param reorderedPackage
     * @return boolean answer
     */
    private boolean departureDateCheck(PackageDTO pack) {
	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
	return (departureDate == null || fmt.format(departureDate).equals(fmt.format(pack.getOutboundFlight().getDatePersonalization().getInitialDate()))) ? true : false;
    }

    /**
     * Compares the searched returnDate with that of a specific base product.
     * 
     * @param reorderedPackage
     * @return boolean answer
     */
    private boolean returnDateCheck(PackageDTO pack) {
	SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
	return (returnDate == null || fmt.format(returnDate).equals(fmt.format(pack.getReturnFlight().getDatePersonalization().getInitialDate()))) ? true : false;
    }

    /**
     * Compares the searched flightClass with that of a specific base product.
     * Both the outbound and return flights must have the desired class!
     * 
     * @param reorderedPackage
     * @return boolean answer
     */
    private boolean flightClassCheck(PackageDTO pack) {
	return (flightClass.equals("null") || (pack.getOutboundFlight().getClassPersonalization().get_class().equalsIgnoreCase(flightClass) && (pack.getReturnFlight().getClassPersonalization()
		.get_class().equalsIgnoreCase(flightClass)))) ? true : false;
    }

    /**
     * Compares the searched hotelStars with that of a specific base product.
     * 0<=hotelStars<=5
     * 
     * @param reorderedPackage
     * @return boolean answer
     */
    private boolean hotelStarsCheck(PackageDTO pack) {
	return (hotelStars == 0 || (pack.getHotel().getHotel().getStars() == hotelStars)) ? true : false;
    }

    /**
     * Compares the searched hotelClass with that of a specific base product.
     * 
     * @param reorderedPackage
     * @return boolean answer
     */
    private boolean hotelClassCheck(PackageDTO pack) {
	return (hotelClass.equals("null") || (pack.getHotel().getClassPersonalization().get_class().equalsIgnoreCase(hotelClass))) ? true : false;
    }

    /**
     * Shows edit_package page related to the selected package.
     * 
     * @param p the PackageDTO selected by the user
     * @return URL String of the page
     */
    public String showEditPackage(PackageDTO p) {
	sessionStorage.setSelectedPackage(p);
	if (authEJB.isTDE()) {
	    return "/admin/edit_package?faces-redirect=true";
	} else {
	    return "/user/edit_package?faces-redirect=true";
	}
    }

    /**
     * 
     * @return the Date at the moment of its invocation.
     */
    public Date getCurrentDate() {
	Date date = new Date();
	return date;
    }

}
