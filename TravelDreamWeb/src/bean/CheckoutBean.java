package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJB;
import coreEJB.BuyingListItemEJBLocal;
import coreEJB.GiftListItemEJBLocal;
import coreEJB.InvitationEJBLocal;
import coreEJB.PackageEJBLocal;
import coreEJB.PagesEJBLocal;
import dto.BuyingListItemDTO;
import dto.GiftListItemDTO;
import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.NotAuthenticatedException;
import exceptions.NotValidBuyingListException;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidInvitationException;
import exceptions.NotValidPackageException;
import exceptions.NotValidUserException;

@ManagedBean(name = "Checkout")
@ViewScoped
public class CheckoutBean {
    private List<String> emails;
    private int price;
    private UserDTO user;
    private PackageDTO tmpPackage;

    @EJB
    private PackageEJBLocal packageEJB;

    @EJB
    private AuthenticationEJB authEJB;

    @EJB
    private InvitationEJBLocal invitationEJB;

    @EJB
    private BuyingListItemEJBLocal buyingListEJB;

    @EJB
    private GiftListItemEJBLocal giftListEJB;

    @EJB
    private PagesEJBLocal pagesEJB;

    // @ManagedProperty("#{GiftList.gifting}")
    // private boolean gifting;

    public CheckoutBean() {
	try {
	    user = authEJB.getAuthenticatedUser();
	} catch (NotAuthenticatedException e) {
	    // No problem: user area
	}
	emails = new ArrayList<String>(packageEJB.getTmpPackage().getNumPeople());
	tmpPackage = packageEJB.getTmpPackage();
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
	if (pagesEJB.getPreviousPage().equals("edit")) {
	    try {
		packageEJB.savePackage(tmpPackage);
		boolean gifted = false;
		BuyingListItemDTO buyingItem = new BuyingListItemDTO(tmpPackage, new Date(), gifted, false, user);
		try {
		    buyingListEJB.saveBuyingListItem(buyingItem);
		} catch (NotValidBuyingListException e) {
		    System.err.printf("Qualcosa è andato storto, riprova.");
		    e.printStackTrace();
		    packageEJB.removePackage(tmpPackage);
		    return null;
		}
		return ("user/payment?faces-redirect=true");
	    } catch (NotValidPackageException e1) {
		System.err.printf("Qualcosa è andato storto, riprova.");
		e1.printStackTrace();
		return null;
	    }
	} else {
	    if (pagesEJB.getPreviousPage().equals("gift")) {
		boolean gifted = true;
		BuyingListItemDTO buyingItem = new BuyingListItemDTO(tmpPackage, new Date(), gifted, false, user);
		try {
		    buyingListEJB.saveBuyingListItem(buyingItem);
		} catch (NotValidBuyingListException e) {
		    System.err.printf("Qualcosa è andato storto, riprova.");
		    e.printStackTrace();
		    return null;
		}
		return ("user/payment?faces-redirect=true");
	    } else if (pagesEJB.getPreviousPage().equals("invitation")) {
		boolean gifted = false;
		BuyingListItemDTO buyingItem = new BuyingListItemDTO(tmpPackage, new Date(), gifted, false, user);
		try {
		    buyingListEJB.saveBuyingListItem(buyingItem);
		} catch (NotValidBuyingListException e) {
		    System.err.printf("Qualcosa è andato storto, riprova.");
		    e.printStackTrace();
		    return null;
		}
		return ("user/payment?faces-redirect=true");
	    } else
		return null;
	}
    }

    public String showGiftList() {
	try {
	    packageEJB.savePackage(tmpPackage);
	    GiftListItemDTO giftItem = new GiftListItemDTO(tmpPackage, user);
	    try {
		giftListEJB.saveGiftListItem(giftItem);
	    } catch (NotValidUserException e) {
		System.err.printf("Qualcosa è andato storto, riprova.");
		e.printStackTrace();
		packageEJB.removePackage(tmpPackage);
		return null;
	    } catch (NotValidGiftListItemException e) {
		System.err.printf("Qualcosa è andato storto, riprova.");
		e.printStackTrace();
		packageEJB.removePackage(tmpPackage);
		return null;
	    }
	    return ("user/gift_list?faces-redirect=true");
	} catch (NotValidPackageException e1) {
	    System.err.printf("Qualcosa è andato storto, riprova.");
	    e1.printStackTrace();
	    return null;
	}
    }

    public String showInvitationList() {
	// send emails & add tmpPackage to invitationList
	for (String email : emails) {
	    UserDTO invited = new UserDTO(email, null, null, null, null);
	    try {
		invitationEJB.sendInvitation(new InvitationDTO(user, invited, tmpPackage, null, false));
	    } catch (NotValidInvitationException e) {
		System.err.printf("Errore durante l'operazione, riprova.");
		e.printStackTrace();
		return null;
	    }
	}
	return ("user/invitation_list?faces-redirect=true");
    }

    /**
     * 
     * @return true if the "Add to giftList" section of the checkout is accesible to the user
     */
    public boolean isGiftActive() {
	return pagesEJB.getPreviousPage().equals("edit") ? true : false;
    }

    /**
     * 
     * @return true if the "Invite your friends" section of the checkout is accesible to the user
     */
    public boolean isInviteActive() {
	return pagesEJB.getPreviousPage().equals("edit") ? true : false;
    }

    /*
     * private static void removeSessionScopedBean(String beanName) { FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(beanName); }
     */
}
