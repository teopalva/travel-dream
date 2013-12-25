package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import coreEJB.UserEJBLocal;
import dto.UserDTO;

@ManagedBean(name = "Registration")
@RequestScoped
public class RegistrationBean {
    private String group = "TDC";
    private String backurl = "/home?faces-redirect=true";
    private UserDTO user;

    @EJB
    private UserEJBLocal userEJB;

    public RegistrationBean() {
	user = new UserDTO();
	user.setGroup(group);
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

    public String register() {
	userEJB.saveUser(user);
	return back();
    }

    private String back() {
	String URL = backurl + "?faces-redirect=true";
	return URL;
    }

}
