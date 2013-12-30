package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJBLocal;
import coreEJB.BuyingListItemEJBLocal;
import coreEJB.GiftListItemEJBLocal;
import coreEJB.InvitationEJBLocal;
import coreEJB.PackageEJBLocal;
import dto.BuyingListItemDTO;
import dto.GiftListItemDTO;
import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.NotAuthenticatedException;
import exceptions.NotValidBuyingListException;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidInvitationException;
import exceptions.NotValidUserException;

@ManagedBean(name = "Checkout")
@ViewScoped
public class CheckoutBean {
    private List<String> emails;
    private int price;
    private UserDTO user;
    private PackageDTO selectedPackage;

    @EJB
    private PackageEJBLocal packageEJB;

    @EJB
    private AuthenticationEJBLocal authEJB;

    @EJB
    private InvitationEJBLocal invitationEJB;

    @EJB
    private BuyingListItemEJBLocal buyingListEJB;

    @EJB
    private GiftListItemEJBLocal giftListEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public CheckoutBean() {
	try {
	    user = authEJB.getAuthenticatedUser();
	} catch (NotAuthenticatedException e) {
	    // No problem: user area
	}
    }

    @PostConstruct
    public void init() {
	emails = new ArrayList<String>(sessionStorage.getSelectedPackage().getNumPeople());
	selectedPackage = sessionStorage.getSelectedPackage();
    }

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public void setEmails(List<String> emails) {
	this.emails = emails;
    }

    public List<String> getEmails() {
	return emails;
    }

    public void setPrice(int p) {
	this.price = p;
    }

    public int getPrice() {
	return price;
    }

    public String showPayment() {
	if (sessionStorage.getPreviousPage().equals("edit")) {
	    // packageEJB.savePackage(selectedPackage);
	    PackageDTO p = new PackageDTO(selectedPackage);
	    boolean gifted = false;
	    BuyingListItemDTO buyingItem = new BuyingListItemDTO(p, new Date(), gifted, false, user);
	    try {
		buyingListEJB.saveBuyingListItem(buyingItem);
	    } catch (NotValidBuyingListException e) {
		System.err.printf("Qualcosa è andato storto, riprova.");
		e.printStackTrace();
		// packageEJB.removePackage(selectedPackage);
		return null;
	    }
	    return ("/user/payment?faces-redirect=true");
	} else {
	    if (sessionStorage.getPreviousPage().equals("gift")) {
		PackageDTO p = new PackageDTO(selectedPackage);
		boolean gifted = true;
		BuyingListItemDTO buyingItem = new BuyingListItemDTO(p, new Date(), gifted, false, user);
		try {
		    buyingListEJB.saveBuyingListItem(buyingItem);
		} catch (NotValidBuyingListException e) {
		    System.err.printf("Qualcosa è andato storto, riprova.");
		    e.printStackTrace();
		    return null;
		}
		return ("/user/payment?faces-redirect=true");
	    } else if (sessionStorage.getPreviousPage().equals("invitation")) {
		PackageDTO p = new PackageDTO(selectedPackage);
		boolean gifted = false;
		BuyingListItemDTO buyingItem = new BuyingListItemDTO(p, new Date(), gifted, false, user);
		try {
		    buyingListEJB.saveBuyingListItem(buyingItem);
		} catch (NotValidBuyingListException e) {
		    System.err.printf("Qualcosa è andato storto, riprova.");
		    e.printStackTrace();
		    return null;
		}
		return ("/user/payment?faces-redirect=true");
	    } else
		return null;
	}
    }

    public String showGiftList() {
	// packageEJB.savePackage(selectedPackage);
	PackageDTO p = new PackageDTO(selectedPackage);
	GiftListItemDTO giftItem = new GiftListItemDTO(p, user);
	try {
	    giftListEJB.saveGiftListItem(giftItem);
	} catch (NotValidUserException e) {
	    System.err.printf("Qualcosa è andato storto, riprova.");
	    e.printStackTrace();
	    // packageEJB.removePackage(selectedPackage);
	    return null;
	} catch (NotValidGiftListItemException e) {
	    System.err.printf("Qualcosa è andato storto, riprova.");
	    e.printStackTrace();
	    // packageEJB.removePackage(selectedPackage);
	    return null;
	}
	return ("/user/gift_list?faces-redirect=true");
    }

    public String showInvitationList() {
	// send emails & add tmpPackage to invitationList
	for (String email : emails) {
	    UserDTO invited = new UserDTO(email, null, null, null, null);
	    try {
		invitationEJB.sendInvitation(new InvitationDTO(user, invited, selectedPackage, null, false));
	    } catch (NotValidInvitationException e) {
		System.err.printf("Errore durante l'operazione, riprova.");
		e.printStackTrace();
		return null;
	    }
	}
	return ("/user/invitation_list?faces-redirect=true");
    }

    /**
     * 
     * @return true if the "Add to giftList" section of the checkout is accesible to the user
     */
    public boolean isGiftActive() {
	return sessionStorage.getPreviousPage().equals("edit") ? true : false;
    }

    /**
     * 
     * @return true if the "Invite your friends" section of the checkout is accesible to the user
     */
    public boolean isInviteActive() {
	return sessionStorage.getPreviousPage().equals("edit") ? true : false;
    }

}
