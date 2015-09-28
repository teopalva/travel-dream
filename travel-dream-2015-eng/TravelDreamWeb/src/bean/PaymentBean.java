package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import coreEJB.AuthenticationEJBLocal;

@ManagedBean(name="Payment")
@RequestScoped
public class PaymentBean {
	
    @EJB
    private AuthenticationEJBLocal authEJB;
	
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

}
