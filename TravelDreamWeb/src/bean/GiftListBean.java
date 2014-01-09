package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJBLocal;
import coreEJB.GiftListItemEJBLocal;
import coreEJB.UserEJBLocal;
import dto.GiftListItemDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.NotAuthenticatedException;
import exceptions.NotPresentUserException;
import exceptions.NotValidUserException;
import exceptions.PackageNotValidException;

@ManagedBean(name = "GiftList")
@ViewScoped
public class GiftListBean {
    private UserDTO user;
    private String friendMail = "";

    @EJB
    private GiftListItemEJBLocal giftListEJB;

    @EJB
    private AuthenticationEJBLocal authEJB;

    @EJB
    private UserEJBLocal userEJB;

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

    public String getFriendMail() {
	return friendMail;
    }

    public void setFriendMail(String friendMail) {
	this.friendMail = friendMail;
    }

    /**
     * 
     * @return the user's gift list
     */
    public List<GiftListItemDTO> retrieveGiftList() {
	if (friendMail.equals(""))
	    return getList(user);
	else
	    return getList(new UserDTO(friendMail, null, null, null, null));
    }

    /**
     * 
     * @return the user's friend's gift list
     */
    // public List<GiftListItemDTO> retrieveFriendList() {
    // return getList(new UserDTO(friendMail, null, null, null, null));
    // }

    /**
     * 
     * @return the name of the friend searched by mail
     * @throws NotAuthenticatedException
     */
    public String retrieveName() throws NotAuthenticatedException {
	if (friendMail.equals("")) {
	    return "La mia lista regali";
	} else {
	    try {
		return "Lista regali di " + userEJB.getUser(friendMail).getFirstName();
	    } catch (NotPresentUserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return null;
    }

    /**
     * Retrieves the gift list of a user.
     * 
     * @param user a UserDTO
     * @return
     */
    private List<GiftListItemDTO> getList(UserDTO user) {
	List<GiftListItemDTO> l = null;
	try {
	    l = giftListEJB.getGiftListItem(user);
	} catch (NotValidUserException e) {
	    System.err.print("NotValidUserException");
	    e.printStackTrace();
	}
	for (GiftListItemDTO i : l) {
	    try {
		PackageDTO rp = OfferingsListBean.reorderPackage(i.get_package());	//TODO reorder
		i.set_package(rp);
	    } catch (PackageNotValidException e) {
		e.printStackTrace();
	    }
	}
	return l;
    }

    /**
     * 
     * @param p the selected PackageDTO
     * @return the checkout page URL
     */
    public String showCheckout(PackageDTO p) {
	sessionStorage.setSelectedPackage(p);
	if (retrieveGiftList().contains(p)) {
	    sessionStorage.setPreviousPage("gift_user");
	} else {
	    sessionStorage.setPreviousPage("gift_friend");
	}
	return "/user/checkout?faces-redirect=true";
    }

}
