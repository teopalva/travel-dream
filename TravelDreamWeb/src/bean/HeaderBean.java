package bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import coreEJB.AuthenticationEJBLocal;
import exceptions.NotAuthenticatedException;

@ManagedBean(name = "Header")
@SessionScoped
public class HeaderBean implements Serializable {
    private static final long serialVersionUID = -7084933713605366856L;

    @EJB
    private AuthenticationEJBLocal authEJB;

    /**
     * 
     * @return the registration page URL
     */
    public String showRegistration() {
	String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
	return "/registration?faces-redirect=true&backurl=" + url;
    }

    /**
     * 
     * @return the login page URL
     */
    public String showLogin() {
	return "/login?faces-redirect=true";
    }

    /**
     * 
     * @return the home page URL
     */
    public String logout() {
	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	return "/home?faces-redirect=true";
    }

    /**
     * 
     * @return the home page URL. Admin panel if TDE
     */
    public String showHome() {
	if (authEJB.isTDE()) {
	    return "/admin/panel";
	} else {
	    return "/home?faces-redirect=true";
	}
    }

    /**
     * 
     * @return the gift_list page URL
     */
    public String showGiftList() {
	if (authEJB.isTDC()) {
	    return "/user/gift_list?faces-redirect=true";
	} else
	    return null;
    }

    /**
     * 
     * @return the buying_list page URL
     */
    public String showBuyingList() {
	if (authEJB.isTDC()) {
	    return "/user/buying_list?faces-redirect=true";
	} else
	    return null;
    }

    /**
     * 
     * @return the invitation_list page URL
     */
    public String showInvitationList() {
	if (authEJB.isTDC()) {
	    return "/user/invitation_list?faces-redirect=true";
	} else
	    return null;
    }

    /**
     * Asks an EJB for the authentication status of the user.
     * 
     * @return 0->not logged 1->TDC 2->TDE
     */
    public int getUserType() {
	return authEJB.isTDC() ? 1 : authEJB.isTDE() ? 2 : 0;

    }

    /**
     * Retrieves the name of the authenticated user.
     * 
     * @return user name
     * @throws NotAuthenticatedException
     */
    public String getUserName() throws NotAuthenticatedException {
	if (authEJB.isTDC() || authEJB.isTDE())
	    return authEJB.getAuthenticatedUser().getFirstName();
	else
	    return "Utente non registrato";
    }

    // Admin buttons:

    /**
     * Button "Ricerca prodotti base"
     */
    public String showViewBaseProducts() {
	return ("/admin/view_base_product?faces-redirect=true");
    }

    /**
     * Button "Creazione prodotto base"
     */
    public String showEditBaseProduct() {
	return ("/admin/edit_base_product?faces-redirect=true");
    }

    /**
     * Button "Creazione pacchetto"
     */
    public String showEditPackage() {
	return ("/admin/edit_package?faces-redirect=true");
    }

    /**
     * Button "Ricerca pacchetti"
     */
    public String showOfferingsList() {
	return ("/offerings_list?faces-redirect=true");
    }

    /**
     * Button "Gestione ordini"
     */
    public String showOrderList() {
	return ("/admin/order_list?faces-redirect=true");
    }
}
