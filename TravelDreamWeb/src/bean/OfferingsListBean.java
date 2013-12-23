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
