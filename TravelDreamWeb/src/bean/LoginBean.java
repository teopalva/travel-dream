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
	
	public void confirmLogin(ActionEvent actionEvent) {
		UserDTO user = new UserDTO();
		user.setMail(mail);
		user.setPassword(password);
		System.out.println("Tentativo di login: "+mail+" password: "+password);
		boolean result = userEJB.authenticateUser(user);
		if(result==false) {
			System.out.println("Autenticazione fallita");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"NON AUTENTICATO", "PrimeFaces rocks!"));
		}
		else {
			System.out.println("Autenticazione riuscita");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Autenticato!", "PrimeFaces rocks!"));
		}
	}
}
