package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import coreEJB.InvitationEJBLocal;
import exceptions.NotValidInvitationException;

@ManagedBean(name = "AcceptInvitation")
@RequestScoped
public class AcceptInvitationBean {
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @EJB
    InvitationEJBLocal invitationEJB;

    public void acceptInvitation() {
	try {
	    invitationEJB.acceptInvitation(hash);
	} catch (NotValidInvitationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
