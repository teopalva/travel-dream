package bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.BaseProductDTO;
import dto.BuyingListItemDTO;
import dto.PackageDTO;

@ManagedBean(name = "SessionStorage")
@SessionScoped
public class SessionStorageBean implements Serializable {
    private static final long serialVersionUID = -339896506641421282L;

    // homepage flags
    private boolean hotelSelected = true;
    private boolean flightSelected = true;
    private boolean excursionSelected = true;

    /**
     * The package selected by the user for editing
     */
    private PackageDTO selectedPackage;
    private BaseProductDTO selectedProduct;
    private BuyingListItemDTO selectedItem;
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

    public BuyingListItemDTO getSelectedItem() {
	return selectedItem;
    }

    public void setSelectedItem(BuyingListItemDTO selectedItem) {
	this.selectedItem = selectedItem;
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

    public boolean isHotelSelected() {
	return hotelSelected;
    }

    public void setHotelSelected(boolean hotelSelected) {
	this.hotelSelected = hotelSelected;
    }

    public boolean isFlightSelected() {
	return flightSelected;
    }

    public void setFlightSelected(boolean flightSelected) {
	this.flightSelected = flightSelected;
    }

    public boolean isExcursionSelected() {
	return excursionSelected;
    }

    public void setExcursionSelected(boolean excursionSelected) {
	this.excursionSelected = excursionSelected;
    }

}
