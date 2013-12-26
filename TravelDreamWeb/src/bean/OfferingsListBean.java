package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import coreEJB.AuthenticationEJBLocal;
import coreEJB.BaseProductEJBLocal;
import coreEJB.PackageEJBLocal;
import dto.PackageDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedProductDTO;

@ManagedBean(name = "OfferingsList")
@SessionScoped
public class OfferingsListBean {

    private String departurePlace = null;
    private String arrivalPlace = null; // To be set directly from home.jsf when filling search form
    private Date departureDate = null;
    private Date returnDate = null;
    private Integer numPeople = null;
    // ------------------------------
    private String flightClass = null;
    private Integer hotelStars = null;
    private String hotelClass = null;

    /**
     * The package selected by the user for editing
     */
    private PackageDTO selectedPackage = null;
    private String selectedPackageString = "pacchetto non selezionato";

    @EJB
    private AuthenticationEJBLocal authEJB;

    @EJB
    private PackageEJBLocal packageEJB;

    @EJB
    private BaseProductEJBLocal bpEJB;

    // Bean properties:

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

    public void setHotelStars(Integer s) {
	hotelStars = s;
    }

    public Integer getHotelStars() {
	return hotelStars;
    }

    public void setSelectedPackage(PackageDTO p) {
	selectedPackage = p;
    }

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    public void setSelectedPackageString(String p) {
	selectedPackageString = p;
    }

    public String getSelectedPackageString() {
	return selectedPackageString;
    }

    // Action controller methods:

    /**
     * Packages to be retrieved directly from jsf: http://goo.gl/dfVYp4
     * 
     * @return
     */
    public List<PackageDTO> submitSearch() {
	List<PackageDTO> offerings;
	offerings = packageEJB.getOfferingPackages();
	return searchFilter(offerings);
    }

    /**
     * Now only considering flight filters
     * 
     * @param offerings
     * @return
     */
    public List<PackageDTO> searchFilter(List<PackageDTO> offerings) {
	boolean filter = true;
	List<PackageDTO> filteredOfferings = new ArrayList<PackageDTO>();
	for (PackageDTO pack : offerings) {
	    if (numPeople == null || pack.getNumPeople() == numPeople) {
		for (PersonalizedProductDTO pers : pack.getPersonalizedProducts()) {
		    if (pers instanceof PersonalizedFlightDTO) {
			if ((departurePlace == null || (departurePlace.equals(((PersonalizedFlightDTO) pers).getFlight().getAirportDeparture())))
				&& (arrivalPlace == null || arrivalPlace.equals(((PersonalizedFlightDTO) pers).getFlight().getAirportArrival()))
				&& (departureDate == null || departureDate.equals(((PersonalizedFlightDTO) pers).getDatePersonalization().getInitialDate()))
				&& (returnDate == null || returnDate.equals(((PersonalizedFlightDTO) pers).getDatePersonalization().getFinalDate()))) {
			    filter = true;
			} else {
			    filter = false;
			}
		    }
		}
	    }
	    if (filter) {
		filteredOfferings.add(pack); // .clone()
	    }
	}
	return filteredOfferings;
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
	selectedPackage = p;
	packageEJB.setTmpPackage(selectedPackage);
	if (authEJB.isTDE()) {
	    return "admin/edit_package?faces-redirect=true";
	} else {
	    return "user/edit_package?faces-redirect=true";
	}
    }

    public String showEditPackageByString(String packageName) {
	selectedPackageString = packageName;
	if (authEJB.isTDE()) {
	    return "admin/edit_package?faces-redirect=true";
	} else {
	    return "user/edit_package?faces-redirect=true";
	}
    }

    /*
     * public void showEditPackage2(ActionEvent actionEvent) { FacesContext.getCurrentInstance().addMessage(null, new
     * FacesMessage(getSelectedPackageString())); }
     * 
     * public String showEditPackage3(int n) { System.out.println("Hai scelto: " + n); return null; }
     */
    
    public Date getCurrentDate() {
    	Date date = new Date();
    	return date;
    }
    
    public double getPrice(PackageDTO p){
	return p.getPrice();
    }
    
}
