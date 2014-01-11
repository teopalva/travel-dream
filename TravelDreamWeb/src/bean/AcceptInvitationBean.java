package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import coreEJB.InvitationEJBLocal;
import exceptions.NotValidInvitationException;

@ManagedBean(name = "AcceptInvitation")
@ViewScoped
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
    
    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public String acceptInvitation() {
	try {
		hash = sessionStorage.getHash();
		System.out.println("Hash: " + this.hash);
	    invitationEJB.acceptInvitation(hash);
	} catch (NotValidInvitationException e) {
	    FacesContext.getCurrentInstance().addMessage("alertInvitation", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Al momento e' impossibile confermare l'invito. Riprova."));
	    return null;
	}
		return "/home?faces-redirect=true";
    }
    
    public String continueToAcceptedInvitation() { 	
    	sessionStorage.setHash(hash);
    	return "/user/accepted_invitation?faces-redirect=true";
    }

	public SessionStorageBean getSessionStorage() {
		return sessionStorage;
	}

	public void setSessionStorage(SessionStorageBean sessionStorage) {
		this.sessionStorage = sessionStorage;
	}
}
