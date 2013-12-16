package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJBLocal;

@ManagedBean(name="Home")
@ViewScoped
public class HomeBean {
	
	@EJB
	AuthenticationEJBLocal authenticationEJB;
	
	public String getHelloWorld() {
		if(authenticationEJB.isTDC())
			return "TDC";
		if(authenticationEJB.isTDE())
			return "TDE";
		return "NON AUTENTICATO";
	}

}
