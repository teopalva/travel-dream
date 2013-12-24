package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import coreEJB.AuthenticationEJBLocal;

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
//	String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
	return "/login?faces-redirect=true";//?faces-redirect=true&backurl=" + url;
    }

    public String logout() {
	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	return "/home?faces-redirect=true";
    }

    public String showHome() {
	if (authEJB.isTDE()) {
	    return "admin/panel";
	} else {
	    return "/home?faces-redirect=true";
	}
    }

    public String showGiftList() {
	if (authEJB.isTDC()) {
	    return "gift_list";
	} else
	    return null;
    }

    public String showBuyingList() {
	if (authEJB.isTDC()) {
	    return "buying_list";
	} else
	    return null;
    }

    public String showInvitationList() {
	if (authEJB.isTDC()) {
	    return "invitation_list";
	} else
	    return null;
    }

}
