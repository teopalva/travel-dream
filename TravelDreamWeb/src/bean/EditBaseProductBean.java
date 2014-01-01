package bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BaseProductEJBLocal;
import dto.BaseProductDTO;
import dto.PackageDTO;

@ManagedBean(name = "EditBaseProduct")
@ViewScoped
public class EditBaseProductBean {
    private Boolean flight;
    private Boolean hotel;
    private Boolean excursion;
    private BaseProductDTO selectedProduct;

    @EJB
    private BaseProductEJBLocal baseProductEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    @PostConstruct
    public void init() {
	if (sessionStorage.getSelectedProduct() == null) {
	    selectedProduct = new BaseProductDTO();
	} else {
	    selectedProduct = sessionStorage.getSelectedProduct();
	}
    }

    public void addPersonalization() {

    }

    public void removePersonalization() {

    }

    public void confirm() {

    }

}
