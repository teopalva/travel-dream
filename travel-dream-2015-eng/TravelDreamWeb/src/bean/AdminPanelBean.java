package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "AdminPanel")
@RequestScoped
public class AdminPanelBean {

    /**
     * Button "Ricerca prodotti base"
     */
    public String showViewBaseProducts() {
	return ("/admin/view_base_product?faces-redirect=true");
    }

    /**
     * Button "Creazione prodotto base"
     */
    public String showEditBaseProduct() {
	return ("/admin/edit_base_product?faces-redirect=true");
    }

    /**
     * Button "Creazione pacchetto"
     */
    public String showEditPackage() {
	return ("/admin/edit_package?faces-redirect=true");
    }

    /**
     * Button "Ricerca pacchetti"
     */
    public String showOfferingsList() {
	return ("/offerings_list?faces-redirect=true");
    }

    /**
     * Button "Gestione ordini"
     */
    public String showOrderList() {
	return ("/admin/order_list?faces-redirect=true");
    }
}
