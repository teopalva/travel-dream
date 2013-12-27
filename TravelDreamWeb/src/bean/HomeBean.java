package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import coreEJB.AuthenticationEJBLocal;
import exceptions.EmptySelectionException;

@ManagedBean(name = "Home")
@RequestScoped
public class HomeBean {

    /**
     * To be checked directly from edit_package.jsf before rendering the page
     */
    private Boolean hotelSelected;
    private Boolean flightSelected;
    private Boolean excursionSelected;

    @EJB
    AuthenticationEJBLocal authenticationEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    // Bean properties:

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public void setHotelSelected(Boolean b) {
	hotelSelected = b;
    }

    public Boolean getHotelSelected() {
	return hotelSelected;
    }

    public void setFlightSelected(Boolean b) {
	flightSelected = b;
    }

    public Boolean getFlightSelected() {
	return flightSelected;
    }

    public void setExcursionSelected(Boolean b) {
	excursionSelected = b;
    }

    public Boolean getExcursionSelected() {
	return excursionSelected;
    }

    // Action controller methods:

    // -------------------------------------
    public String getHelloWorld() {
	if (authenticationEJB.isTDC())
	    return "TDC";
	if (authenticationEJB.isTDE())
	    return "TDE";
	return "NON AUTENTICATO";
    }

    // -------------------------------------

    public String showEditPackage() {
	try {
	    if (!flightSelected && !hotelSelected && !excursionSelected) {
		throw new EmptySelectionException();
	    }
	} catch (EmptySelectionException e) {
	    System.out.printf("EmptySelectionException");
	    return null;
	}
	sessionStorage.setSelectedPackage(null);
	return "user/edit_package?faces-redirect=true";
    }

    public String showOfferingsList() {
	return "/offerings_list?faces-redirect=true";
    }

}
