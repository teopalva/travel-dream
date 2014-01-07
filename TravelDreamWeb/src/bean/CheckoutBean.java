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

    @PostConstruct
    public void init() {
	try {
	    user = authEJB.getAuthenticatedUser();
	} catch (NotAuthenticatedException e) {
	    // No problem: user area
	}
	int numPeople = getNumPeople();
	emails = new ArrayList<String>(numPeople);
	for (int i = 0; i < numPeople; i++) {
	    emails.add("");
	}
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

    public int getNumPeople() {
	return sessionStorage.getSelectedPackage().getNumPeople();
    }

    public void addMail(String mail) {
	this.emails.add(mail);
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

    /**
     * Button "Acquista". Does all the needed checks.
     * 
     * @return the payment page URL
     */
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
	    if (sessionStorage.getPreviousPage().equals("gift_user") || sessionStorage.getPreviousPage().equals("gift_friend")) {
		PackageDTO p = new PackageDTO(selectedPackage);
		boolean gifted;
		if (sessionStorage.getPreviousPage().equals("gift_friend")) {
		    gifted = true;
		} else {
		    gifted = false;
		}
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

    /**
     * Button "Aggiungi a lista regali". Does all the needed checks.
     * 
     * @return the gift_list page URL
     */
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

    /**
     * Button "Invita i tuoi amici". Does all the needed checks.
     * 
     * @return the invitation_list page URL
     */
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
     * @return true if the "Add to giftList" section of the checkout is accessible to the user
     */
    public boolean isGiftActive() {
	return sessionStorage.getPreviousPage().equals("edit") ? true : false;
    }

    /**
     * 
     * @return true if the "Invite your friends" section of the checkout is accessible to the user
     */
    public boolean isInviteActive() {
	return sessionStorage.getPreviousPage().equals("edit") ? true : false;
    }

}
