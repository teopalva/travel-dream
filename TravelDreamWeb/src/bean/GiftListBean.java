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
import exceptions.NotValidUserException;

@ManagedBean(name = "GiftList")
@ViewScoped
public class GiftListBean {
    private UserDTO user;
    private PackageDTO selectedPackage = null;

    private String friendMail;

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

    public String getFriendMail() {
	return friendMail;
    }

    public void setFriendMail(String friendMail) {
	this.friendMail = friendMail;
    }

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    public void setSelectedPackage(PackageDTO selectedPackage) {
	this.selectedPackage = selectedPackage;
    }

    /*
     * public List<GiftListItemDTO> retrieveMyList() {
     * return getList(user);
     * }
     * 
     * 
     * public List<GiftListItemDTO> retrieveFriendList() {
     * return getList(new UserDTO(friendMail, null, null, null, null));
     * }
     */

    public String retrieveFriendName() {
	return userEJB.getUser(friendMail).getFirstName();
    }

   /*
      private List<GiftListItemDTO> getList(UserDTO user) {
      List<GiftListItemDTO> l = null;
      try {
      l = giftListEJB.getGiftListItem(user);
      } catch (NotValidUserException e) {
      System.err.print("NotValidUserException");
      e.printStackTrace();
      }
      return l;
      }
     

    public String showCheckout() {
	sessionStorage.setSelectedPackage(selectedPackage);
	List<GiftListItemDTO> l;
	if (retrieveMyList().contains(selectedPackage)) {
	    sessionStorage.setPreviousPage("gift_user");
	} else {
	    sessionStorage.setPreviousPage("gift_friend");
	}
	return "/user/checkout?faces-redirect=true";
    }
    */

}
