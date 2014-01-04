package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BuyingListItemEJBLocal;
import dto.BuyingListItemDTO;

@ManagedBean(name = "OrderList")
@ViewScoped
public class OrderListBean {
    private List<BuyingListItemDTO> boughtList;
    private List<BuyingListItemDTO> paidList;
    private BuyingListItemDTO selectedItem;

    @EJB
    private BuyingListItemEJBLocal buyingListEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public OrderListBean() {
	boughtList = new ArrayList<BuyingListItemDTO>();
	paidList = new ArrayList<BuyingListItemDTO>();
    }

    @PostConstruct
    private void init() {
	retrieveLists();
    }

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public List<BuyingListItemDTO> getBoughtList() {
	return boughtList;
    }

    public List<BuyingListItemDTO> getPaidList() {
	return paidList;
    }

    /**
     * Updates the two lists boughtList and paidList from db.
     */
    private void retrieveLists() {
	for (BuyingListItemDTO item : buyingListEJB.getAllBuyingListItem()) {
	    if (!item.isPaid()) {
		boughtList.add(item);
	    } else {
		paidList.add(item);
	    }
	}
    }

    /**
     * 
     * @param p the selected BuyingListItem
     * @return the order_detail page URL
     */
    public String showOrderDetail(BuyingListItemDTO item) {
	sessionStorage.setSelectedItem(item);
	return ("/admin/order_detail?faces-redirect=true");
    }

	public BuyingListItemDTO getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(BuyingListItemDTO selectedItem) {
		this.selectedItem = selectedItem;
	}

}
