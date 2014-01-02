package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJBLocal;
import coreEJB.InvitationEJBLocal;
import dto.BuyingListItemDTO;
import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.NotAuthenticatedException;
import exceptions.NotValidUserException;

@ManagedBean(name = "InvitationList")
@ViewScoped
public class InvitationListBean {
    private UserDTO user;
    private PackageDTO selectedPackage = null;

    @EJB
    private InvitationEJBLocal invitationEJB;

    @EJB
    private AuthenticationEJBLocal authEJB;
    
    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    @PostConstruct
    public void init() {
	try {
	    user = authEJB.getAuthenticatedUser();
	} catch (NotAuthenticatedException e) {
	    // No problem: user area
	}
    }

    public SessionStorageBean getSessionStorage() {
        return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public PackageDTO getSelectedPackage() {
        return selectedPackage;
    }

    public void setSelectedPackage(PackageDTO selectedPackage) {
        this.selectedPackage = selectedPackage;
    }
    
    /*
    public List<PackageDTO> retrieveList() {
  	return invitationEJB.getPackages(user);
      }
    */

    public List<InvitationDTO> retrieveInvitationList(PackageDTO p) {
	List<InvitationDTO> l = new ArrayList<InvitationDTO>();
	/*
	for (InvitationDTO i : invitationEJB.getAllInvitation(user))
	    if (i.get_package().equals(p)) {
		l.add(i);
	    }
	    */
	return l;
    }

    public String showCheckout() {
	sessionStorage.setSelectedPackage(selectedPackage);
	    sessionStorage.setPreviousPage("invitation");
	return "/user/checkout?faces-redirect=true";
    }
}
