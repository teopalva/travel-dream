package bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BuyingListItemEJBLocal;
import dto.PackageDTO;

@ManagedBean(name = "OrderDetail")
@ViewScoped
public class OrderDetailBean {
    private PackageDTO selectedPackage = null;

    @EJB
    private BuyingListItemEJBLocal buyingListEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    @PostConstruct
    public void init() {
	selectedPackage = sessionStorage.getSelectedPackage();
    }

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    public void setSelectedPackage(PackageDTO selectedPackage) {
	this.selectedPackage = selectedPackage;
    }

    public void confirmPayment() {
	// buyingListEJB. TODO setta pagato sul buyinglist item
    }

}
