package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import coreEJB.UserEJBLocal;
import dto.UserDTO;
import exceptions.NotPresentUserException;

@ManagedBean(name = "Registration")
@RequestScoped
public class RegistrationBean {
    private final static String GROUP = "TDC";
    private String backurl = "/home?faces-redirect=true";
    private UserDTO user;

    @EJB
    private UserEJBLocal userEJB;

    public RegistrationBean() {
	user = new UserDTO();
	user.setGroup(GROUP);
    }

    // Bean properties:

    public UserDTO getUser() {
	return user;
    }

    public void setUser(UserDTO user) {
	this.user = user;
    }

    public String getBackurl() {
	return backurl;
    }

    public void setBackurl(String backurl) {
	this.backurl = backurl;
    }

    // Action controller methods:

    /**
     * 
     * @return the register page URL
     */
    public String register() {
	try {
	    userEJB.getUser(user.getMail());
	} catch (NotPresentUserException e) {
	    userEJB.saveUser(user);
	    return back();
	}
	showAlert();
	// FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Utente gia' registrato", "TITOLO");
	// FacesContext.getCurrentInstance().addMessage("signin:email", message);
	return null;
    }

    private void showAlert() {
	FacesContext.getCurrentInstance().addMessage("alertRegistration",
		new FacesMessage(FacesMessage.SEVERITY_ERROR, "Utente gia' registrato", "La mail che hai inserito e' gia' utilizzata per un altro utente"));

    }

    /**
     * 
     * @return the previous visited page URL
     */
    private String back() {
	String URL = backurl + "?faces-redirect=true";
	return URL;
    }

}
