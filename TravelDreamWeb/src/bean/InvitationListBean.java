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
import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.NotAuthenticatedException;
import exceptions.NotValidPackageException;
import exceptions.NotValidUserException;

@ManagedBean(name = "InvitationList")
@ViewScoped
public class InvitationListBean {
    private UserDTO user;
    private PackageDTO selectedPackage = null;
    private List<InvitationDTO> invitationList;

    @EJB
    private InvitationEJBLocal invitationEJB;

    @EJB
    private AuthenticationEJBLocal authEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public InvitationListBean() {
	invitationList = new ArrayList<InvitationDTO>();
    }

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

    /**
     * 
     * @return the list of PackageDTOs for which the user has sent invitations
     */
    public List<PackageDTO> retrieveList() {
	try {
	    return PackageDTO.getAllPackagesFromInvitation(invitationEJB.getInvitations(user));
	} catch (NotValidUserException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * 
     * @param p the selected PackageDTO
     * @return the list of the invitations linked to the package p
     */
    public List<InvitationDTO> retrieveInvitationList(PackageDTO p) {
	try {
	    invitationList = invitationEJB.getInvitations(p);
	} catch (NotValidPackageException e) {
	    System.err.print("Pacchetto non valido.");
	    e.printStackTrace();
	}
	return invitationList;
    }

    /**
     * 
     * @return the checkout page URL
     */
    public String showCheckout() {
	sessionStorage.setSelectedPackage(selectedPackage);
	sessionStorage.setPreviousPage("invitation");
	return "/user/checkout?faces-redirect=true";
    }
}
