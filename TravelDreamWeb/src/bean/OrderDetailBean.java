package bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BuyingListItemEJBLocal;
import dto.BuyingListItemDTO;
import exceptions.NotValidBuyingListException;

@ManagedBean(name = "OrderDetail")
@ViewScoped
public class OrderDetailBean {
    private BuyingListItemDTO selectedItem = null;

    @EJB
    private BuyingListItemEJBLocal buyingListEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    @PostConstruct
    public void init() {
	selectedItem = sessionStorage.getSelectedItem();
    }

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public BuyingListItemDTO getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(BuyingListItemDTO selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * Sets the item paid attribute into the db to true.
     */
    public void confirmPayment() {
	sessionStorage.getSelectedItem();
	try {
	    buyingListEJB.setPaid(0); // pass selectedItem
	} catch (NotValidBuyingListException e) {
	    e.printStackTrace();
	}
    }

}
