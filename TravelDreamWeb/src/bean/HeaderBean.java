package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import coreEJB.AuthenticationEJBLocal;
import exceptions.NotAuthenticatedException;

@ManagedBean(name = "Header")
@SessionScoped
public class HeaderBean {

    @EJB
    private AuthenticationEJBLocal authEJB;

    public String showRegistration() {
	String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
	return "/registration?faces-redirect=true&backurl=" + url;
    }

    public String showLogin() {
	return "/login?faces-redirect=true";
    }

    public String logout() {
	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	return "/home?faces-redirect=true";
    }

    public String showHome() {
	if (authEJB.isTDE()) {
	    return "/admin/panel";
	} else {
	    return "/home?faces-redirect=true";
	}
    }

    public String showGiftList() {
	if (authEJB.isTDC()) {
	    return "/user/gift_list?faces-redirect=true";
	} else
	    return null;
    }

    public String showBuyingList() {
	if (authEJB.isTDC()) {
	    return "/user/buying_list?faces-redirect=true";
	} else
	    return null;
    }

    public String showInvitationList() {
	if (authEJB.isTDC()) {
	    return "/user/invitation_list?faces-redirect=true";
	} else
	    return null;
    }

    /**
     * Asks an EJB for the authentication status of the user
     * 
     * @return 0->not logged 1->TDC 2->TDE
     */
    public int getUserType() {
	return authEJB.isTDC() ? 1 : authEJB.isTDE() ? 2 : 0;

    }
    
    public String getUserName() throws NotAuthenticatedException {
    	if(authEJB.isTDC() || authEJB.isTDE()) 
    		return 	authEJB.getAuthenticatedUser().getFirstName();
    	else return "Utente non registrato";
    }
}
