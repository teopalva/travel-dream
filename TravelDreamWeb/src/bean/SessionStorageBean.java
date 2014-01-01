package bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.BaseProductDTO;
import dto.PackageDTO;

@ManagedBean(name = "SessionStorage")
@SessionScoped
public class SessionStorageBean implements Serializable {
    private static final long serialVersionUID = -339896506641421282L;

    /**
     * The package selected by the user for editing
     */
    private PackageDTO selectedPackage;
    private BaseProductDTO selectedProduct;
    private String previousPage = null;
    private String departurePlace = "";
    private String arrivalPlace = ""; // To be set directly from home.jsf when filling search form

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    public void setSelectedPackage(PackageDTO selectedPackage) {
	this.selectedPackage = selectedPackage;
    }

    public BaseProductDTO getSelectedProduct() {
	return selectedProduct;
    }

    public void setSelectedProduct(BaseProductDTO selectedProduct) {
	this.selectedProduct = selectedProduct;
    }

    public String getPreviousPage() {
	return previousPage;
    }

    /**
     * Possible pages: "gift_user", "gift_friend", "invitation", "edit"
     */
    public void setPreviousPage(String page) {
	previousPage = page;
    }

    public String getDeparturePlace() {
	return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
	this.departurePlace = departurePlace;
    }

    public String getArrivalPlace() {
	return arrivalPlace;
    }

    public void setArrivalPlace(String arrivalPlace) {
	this.arrivalPlace = arrivalPlace;
    }

}
