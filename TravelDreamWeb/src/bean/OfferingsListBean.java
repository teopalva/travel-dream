package bean;

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
import dto.CityDTO;
import dto.PackageDTO;
import dto.PersonalizedExcursionDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedHotelDTO;
import exceptions.PackageNotValidException;

@ManagedBean(name = "OfferingsList")
@ViewScoped
public class OfferingsListBean {

    private String departurePlace = "";
    private String arrivalPlace = "";
    private Date departureDate = null;
    private Date returnDate = null;
    private int numPeople = 0;
    // ------------------------------
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

    public void setNumPeople(int n) {
	numPeople = n;
    }

    public int getNumPeople() {
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
     * Packages to be retrieved directly from jsf
     * 
     * @return
     */
    public List<PackageDTO> submitSearch() {
	List<PackageDTO> offerings;
	offerings = packageEJB.getOfferingPackages();
	return searchFilter(offerings);
    }

    /**
     * 
     * 
     * @param offerings
     * @return
     */
    public List<PackageDTO> searchFilter(List<PackageDTO> offerings) {
	List<PackageDTO> filteredOfferings = new ArrayList<PackageDTO>();
	for (PackageDTO pack : offerings) {
	    PackageDTO rp;
	    try {
		rp = reorderPackage(pack);
		// basic search filters
		if (numPeopleCheck(rp) && departurePlaceCheck(rp) && arrivalPlaceCheck(rp) && departureDateCheck(rp) && returnDateCheck(rp)) {
		    // advanced filters
		    if (hotelStarsCheck(rp)) { // && flightClassCheck(rp) && hotelClassCheck(rp)) {
			filteredOfferings.add(rp);
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
     * 0<=numPeople<=10
     * 
     * @param reorderedPackage
     * @return
     */
    private boolean numPeopleCheck(PackageDTO reorderedPackage) {
	return (numPeople == 0 || reorderedPackage.getNumPeople() == numPeople) ? true : false;
    }

    private boolean departurePlaceCheck(PackageDTO reorderedPackage) {
	return (departurePlace.equals("") || ((PersonalizedFlightDTO) reorderedPackage.getPersonalizedProducts().get(0)).getFlight().getCityDeparture().getName().equalsIgnoreCase(departurePlace)) ? true
		: false;
    }

    private boolean arrivalPlaceCheck(PackageDTO reorderedPackage) {
	return (arrivalPlace.equals("") || ((PersonalizedFlightDTO) reorderedPackage.getPersonalizedProducts().get(0)).getFlight().getCityArrival().getName().equalsIgnoreCase(arrivalPlace)) ? true
		: false;
    }

    private boolean departureDateCheck(PackageDTO reorderedPackage) {
	return (departureDate == null || departureDate.equals(((PersonalizedFlightDTO) reorderedPackage.getPersonalizedProducts().get(0)).getDatePersonalization().getInitialDate())) ? true : false;
    }

    private boolean returnDateCheck(PackageDTO reorderedPackage) {
	return (returnDate == null || ((PersonalizedFlightDTO) reorderedPackage.getPersonalizedProducts().get(1)).getDatePersonalization().getInitialDate().equals(returnDate)) ? true : false;
    }

    /**
     * Both the outbound and return flights have the desired class
     * 
     * @param reorderedPackage
     * @return
     */
    private boolean flightClassCheck(PackageDTO reorderedPackage) {
	return (flightClass.equals("null") || (((PersonalizedFlightDTO) reorderedPackage.getPersonalizedProducts().get(0)).getClassPersonalization().get_class().equalsIgnoreCase(flightClass) && ((PersonalizedFlightDTO) reorderedPackage
		.getPersonalizedProducts().get(1)).getClassPersonalization().get_class().equalsIgnoreCase(flightClass))) ? true : false;
    }

    /**
     * 0<=hotelStars<=5
     * 
     * @param reorderedPackage
     * @return
     */
    private boolean hotelStarsCheck(PackageDTO reorderedPackage) {
	return (hotelStars == 0 || ((PersonalizedHotelDTO) reorderedPackage.getPersonalizedProducts().get(2)).getHotel().getStars() == hotelStars) ? true : false;
    }

    private boolean hotelClassCheck(PackageDTO reorderedPackage) {
	return (hotelClass.equals("null") || ((PersonalizedHotelDTO) reorderedPackage.getPersonalizedProducts().get(2)).getClassPersonalization().get_class().equalsIgnoreCase(hotelClass)) ? true
		: false;
    }

    private PackageDTO reorderPackage(PackageDTO pack) throws PackageNotValidException {
	// if (!packageEJB.isValidForOffering(pack)) { TODO: activate!
	// throw new PackageNotValidException();
	// }
	PackageDTO reorderedPackage = new PackageDTO(pack);
	reorderedPackage.setId(pack.getId());
	CityDTO destinationCity = null;
	// understand which is the outbound flight & set hotel at index 2
	boolean flag = false;
	for (int i = 0; i < pack.getPersonalizedProducts().size() && !flag; i++) {
	    if (pack.getPersonalizedProducts().get(i) instanceof PersonalizedHotelDTO) {
		destinationCity = ((PersonalizedHotelDTO) pack.getPersonalizedProducts().get(i)).getHotel().getCity();
		reorderedPackage.getPersonalizedProducts().set(2, pack.getPersonalizedProducts().get(i));
		flag = true;
	    }
	}
	// set outbound/return flight at index 0/1
	for (int i = 0; i < pack.getPersonalizedProducts().size(); i++) {
	    if (pack.getPersonalizedProducts().get(i) instanceof PersonalizedFlightDTO) {
		if (((PersonalizedFlightDTO) pack.getPersonalizedProducts().get(i)).getFlight().getCityArrival().equals(destinationCity)) {
		    // this is the outbound flight:
		    reorderedPackage.getPersonalizedProducts().set(0, pack.getPersonalizedProducts().get(i));
		} else {
		    // this is the return flight:
		    reorderedPackage.getPersonalizedProducts().set(1, pack.getPersonalizedProducts().get(i));
		}
	    }
	}
	// set excursion at index 3
	flag = false;
	for (int i = 0; i < pack.getPersonalizedProducts().size() && !flag; i++) {
	    if (pack.getPersonalizedProducts().get(i) instanceof PersonalizedExcursionDTO) {
		reorderedPackage.getPersonalizedProducts().set(3, pack.getPersonalizedProducts().get(i));
		flag = true;
	    }
	}
	return reorderedPackage;
    }

    public List<String> dropDownFilterFlight() {
	List<String> flightClasses = new ArrayList<String>();
	// bpEJB.getAllPersonalizations(); //TODO
	return flightClasses;
    }

    public List<String> dropDownFilterHstars() {
	List<String> hStars = new ArrayList<String>();
	// TODO
	return hStars;

    }

    public List<String> dropDownFilterHClasses() {
	List<String> hClasses = new ArrayList<String>();
	// TODO
	return hClasses;
    }

    /**
     * Shows edit_package page related to the selected package
     * 
     * @param p
     *            the PackageDTO selected by the user
     * @return URL String of the page
     */
    public String showEditPackage(PackageDTO p) {
	sessionStorage.setSelectedPackage(p);
	if (authEJB.isTDE()) {
	    return "admin/edit_package?faces-redirect=true";
	} else {
	    return "user/edit_package?faces-redirect=true";
	}
    }

    /*
     * 
     * public void showEditPackage2(ActionEvent actionEvent) { FacesContext.getCurrentInstance().addMessage(null, new
     * FacesMessage(getSelectedPackageString())); }
     * 
     * public String showEditPackage3(int n) { System.out.println("Hai scelto: " + n); return null; }
     */

    public Date getCurrentDate() {
	Date date = new Date();
	return date;
    }

}
