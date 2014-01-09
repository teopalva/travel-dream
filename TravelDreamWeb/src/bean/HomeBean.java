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

    // strings for GET retrieval
    private boolean f = false;
    private boolean h = false;
    private boolean e = false;

    @EJB
    AuthenticationEJBLocal authenticationEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    @PostConstruct
    public void init() {
	// setHotelSelected(false);
	// setFlightSelected(false);
	// setExcursionSelected(false);
    }

    // Bean properties:

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    /*
     * public void setHotelSelected(boolean b) {
     * sessionStorage.setHotelSelected(b);
     * }
     * 
     * public boolean getHotelSelected() {
     * return sessionStorage.isHotelSelected();
     * }
     * 
     * public void setFlightSelected(boolean b) {
     * sessionStorage.setFlightSelected(b);
     * }
     * 
     * public boolean getFlightSelected() {
     * return sessionStorage.isFlightSelected();
     * }
     * 
     * public void setExcursionSelected(boolean b) {
     * sessionStorage.setExcursionSelected(b);
     * }
     * 
     * public boolean getExcursionSelected() {
     * return sessionStorage.isExcursionSelected();
     * }
     */

    public boolean getF() {
	return f;
    }

    public void setF(boolean f) {
	this.f = f;
    }

    public boolean getH() {
	return h;
    }

    public void setH(boolean h) {
	this.h = h;
    }

    public boolean getE() {
	return e;
    }

    public void setE(boolean e) {
	this.e = e;
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
	    if (!getF() && !getH() && !getE()) {
		throw new EmptySelectionException();
	    }
	} catch (EmptySelectionException e) {
	    System.out.printf("EmptySelectionException");
	    return null;
	}
	sessionStorage.setSelectedPackage(null);
	String userURL = "/admin/edit_package?faces-redirect=true&f=" + f + "&h=" + h + "&e=" + e;
	String adminURL = "/user/edit_package?faces-redirect=true&f=" + f + "&h=" + h + "&e=" + e;
	f = false;
	h = false;
	e = false;
	if (authenticationEJB.isTDE()) {
	    return userURL;
	} else {
	    return adminURL;
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
