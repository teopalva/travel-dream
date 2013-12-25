package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJB;
import coreEJB.InvitationEJBLocal;
import coreEJB.PackageEJBLocal;
import coreEJB.UserEJB;
import dto.GiftListItemDTO;
import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.NotAuthenticatedException;
import exceptions.NotValidInvitationException;

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
    private UserEJB userEJB;

    @EJB
    private AuthenticationEJB authEJB;

    @EJB
    private InvitationEJBLocal invitationEJB;

    // @EJB
    // private BuyingListItemEJBLocal buyingListEJB;

    public CheckoutBean() {
	try {
	    user = authEJB.getAuthenticatedUser();
	} catch (NotAuthenticatedException e) {
	    // No problem: user area
	}
	// price=packageEJB.getTmpPackage().getPrice(); //TODO total price
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
	// TODO add tmpPackage to buying
	return ("user/payment?faces-redirect=true");
    }

    public String showGiftList() {
	// add tmpPackage to giftList
	userEJB.getGiftList(user).add(new GiftListItemDTO(tmpPackage, user));
	return ("user/gift_list?faces-redirect=true");
    }

    public String showInvitationList() {
	// send emails
	for (String email : emails) {
	    UserDTO invited = new UserDTO(email, null, null, null, null);
	    String hash = "2";// TODO
	    try {
		invitationEJB.sendInvitation(new InvitationDTO(user, invited, tmpPackage, hash, false));
	    } catch (NotValidInvitationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return ("user/invitation_list?faces-redirect=true");
    }

}
