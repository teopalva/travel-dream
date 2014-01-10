package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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
	    FacesContext.getCurrentInstance().addMessage("alertInvitation", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Al momento e' impossibile confermare l'invito. Riprova."));
	}
    }

}
