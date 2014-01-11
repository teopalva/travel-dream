package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJBLocal;
import coreEJB.BuyingListItemEJBLocal;
import dto.BuyingListItemDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.NotAuthenticatedException;
import exceptions.NotValidUserException;
import exceptions.PackageNotValidException;

@ManagedBean(name = "BuyingList")
@ViewScoped
public class BuyingListBean {
    private UserDTO user;

    @EJB
    private BuyingListItemEJBLocal buyingListEJB;

    @EJB
    private AuthenticationEJBLocal authEJB;

    @PostConstruct
    public void init() {
	try {
	    user = authEJB.getAuthenticatedUser();
	} catch (NotAuthenticatedException e) {
	    // No problem: user area
	}
    }

    /**
     * Retrieves the user's buying list form db.
     * 
     * @return the list of BuyingListItemDTOs of the buying list
     */
    public List<BuyingListItemDTO> retrieveList() {
	List<BuyingListItemDTO> l = null;
	try {
	    l = buyingListEJB.getBuyingListItem(user);
	} catch (NotValidUserException e) {
	    System.err.print("NotValidUserException");
	    e.printStackTrace();
	}
	/*
	for (BuyingListItemDTO i : l) {
	    try {
		PackageDTO rp = OfferingsListBean.reorderPackage(i.get_package());
		i.set_package(rp);
	    } catch (PackageNotValidException e) {
		e.printStackTrace();
	    }
	}
	*/
	return l;
    }
}
