package bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import coreEJB.BuyingListItemEJBLocal;
import dto.BuyingListItemDTO;
import dto.PackageDTO;
import exceptions.NotValidBuyingListException;
import exceptions.PackageNotValidException;

@ManagedBean(name = "OrderList")
@ViewScoped
public class OrderListBean {
    private List<BuyingListItemDTO> boughtList;
    private List<BuyingListItemDTO> paidList;
    private BuyingListItemDTO selectedItem;

    @EJB
    private BuyingListItemEJBLocal buyingListEJB;

    public OrderListBean() {
	boughtList = new ArrayList<BuyingListItemDTO>();
	paidList = new ArrayList<BuyingListItemDTO>();
    }

    @PostConstruct
    private void init() {
	retrieveLists();
    }

    public BuyingListItemDTO getSelectedItem() {
	return selectedItem;
    }

    public void setSelectedItem(BuyingListItemDTO selectedItem) {
	this.selectedItem = selectedItem;
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
	    try {
		PackageDTO rp = OfferingsListBean.reorderPackage(item.get_package());
		item.set_package(rp);
	    } catch (PackageNotValidException e) {
		e.printStackTrace();
	    }
	    if (!item.isPaid()) {
		boughtList.add(item);
	    } else {
		paidList.add(item);
	    }
	}
    }

    /**
     * Called from order detail. Sets the item paid attribute into the db to true.
     */
    public void confirmPayment() {
	try {
	    buyingListEJB.setPaid(selectedItem);
	} catch (NotValidBuyingListException e) {
	    e.printStackTrace();
	}
    }

}
