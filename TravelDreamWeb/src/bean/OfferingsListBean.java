package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import coreEJB.AuthenticationEJBLocal;
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

    @EJB
    AuthenticationEJBLocal authenticationEJB;

    @EJB
    PackageEJBLocal packageEJB;

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
     * Packages to be retrieved directly from jsf: http://goo.gl/dfVYp4
     * @return
     */
    public List<PackageDTO> submitSearch() {
	List<PackageDTO> offerings;
	offerings = packageEJB.getOfferingPackages();
	return searchFilter(offerings);
    }

    /**
     * Now only considering flight filters
     * @param offerings
     * @return
     */
    private List<PackageDTO> searchFilter(List<PackageDTO> offerings) {
	boolean filter = true;
	List<PackageDTO> filteredOfferings = new ArrayList<PackageDTO>();
	for (PackageDTO pack : offerings) {
	    if (pack.getNumPeople() == numPeople || numPeople == null) {
		for (PersonalizedProductDTO pers : pack.getPersonalizedProducts()) {
		    if (pers instanceof PersonalizedFlightDTO) {
			if ((departurePlace == ((PersonalizedFlightDTO) pers).getFlight().getAirportDeparture() || departurePlace == null)
				&& (arrivalPlace == ((PersonalizedFlightDTO) pers).getFlight().getAirportArrival() || arrivalPlace == null)
				&& (departureDate.equals(((PersonalizedFlightDTO) pers).getDatePersonalizatonFlight().getInitialDate()) || departureDate == null)
				&& (returnDate.equals(((PersonalizedFlightDTO) pers).getDatePersonalizatonFlight().getFinalDate()) || returnDate == null)) {
			    filter = true;
			}
			filter = false;
		    }
		}
	    }
	    if (filter) {
		filteredOfferings.add(pack.clone());
	    }
	}
	return filteredOfferings;
    }

    public void dropDownFilter() {

    }

    public String showEditPackage() {
	if (authenticationEJB.isTDC() || authenticationEJB.isTDE()) { // TODO if TDE...
	    return "edit_package";
	} else {
	    return "login";
	}
    }

}
