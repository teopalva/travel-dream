package bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dto.PackageDTO;

@ManagedBean(name = "OrderList")
@ViewScoped
public class OrderListBean {

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public List<PackageDTO> retrieveBoughtList() {

	return null;
    }

    public List<PackageDTO> retrievePaidList() {

	return null;
    }

    public String showOrderDetail(PackageDTO p) {
	sessionStorage.setSelectedPackage(p);
	return ("/admin/order_detail?faces-redirect=true");
    }

}
