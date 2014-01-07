package bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJBLocal;
import exceptions.EmptySelectionException;

@ManagedBean(name = "Home")
@ViewScoped
public class HomeBean {

    @EJB
    AuthenticationEJBLocal authenticationEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    @PostConstruct
    public void init() {
	setHotelSelected(false);
	setFlightSelected(false);
	setExcursionSelected(false);
    }

    // Bean properties:

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public void setHotelSelected(boolean b) {
	sessionStorage.setHotelSelected(b);
    }

    public boolean getHotelSelected() {
	return sessionStorage.isHotelSelected();
    }

    public void setFlightSelected(boolean b) {
	sessionStorage.setFlightSelected(b);
    }

    public boolean getFlightSelected() {
	return sessionStorage.isFlightSelected();
    }

    public void setExcursionSelected(boolean b) {
	sessionStorage.setExcursionSelected(b);
    }

    public boolean getExcursionSelected() {
	return sessionStorage.isExcursionSelected();
    }

    // Action controller methods:

    // -------------------------------------
    public String getHelloWorld() {
	if (authenticationEJB.isTDC())
	    return "TDC";
	if (authenticationEJB.isTDE())
	    return "TDE";
	return "Utente non registrato";
    }

    // -------------------------------------

    /**
     * 
     * @return the edit_package page URL
     */
    public String showEditPackage() {
	try {
	    if (!getFlightSelected() && !getHotelSelected() && !getExcursionSelected()) {
		throw new EmptySelectionException();
	    }
	} catch (EmptySelectionException e) {
	    System.out.printf("EmptySelectionException");
	    return null;
	}
	sessionStorage.setSelectedPackage(null);
	if (authenticationEJB.isTDE()) {
	    return "/admin/edit_package?faces-redirect=true";
	} else {
	    return "/user/edit_package?faces-redirect=true";
	}
    }

    /**
     * 
     * @return the offerings_list page URL
     */
    public String showOfferingsList() {
	return "/offerings_list?faces-redirect=true";
    }

}
