package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import coreEJB.UserEJBLocal;
import dto.UserDTO;

@ManagedBean(name="Login")
@ViewScoped
public class LoginBean {
	private String mail = null;
	private String password = null;
	
	@EJB
	UserEJBLocal userEJB;
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
