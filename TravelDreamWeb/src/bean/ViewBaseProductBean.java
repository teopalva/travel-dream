package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BaseProductEJBLocal;
import dto.BaseProductDTO;
import exceptions.NotValidBaseProductException;

@ManagedBean(name = "ViewBaseProduct")
@ViewScoped
public class ViewBaseProductBean {
    private String name = ""; // text-field
    private String company = "null"; // dropdown
    
    private BaseProductDTO selectedProduct;

    @EJB
    private BaseProductEJBLocal bpEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getCompany() {
	return company;
    }

    public void setCompany(String company) {
	this.company = company;
    }

    /**
     * Performs the search by calling the search engine which filters the products.
     * 
     * @return the list of filtered products
     */
    public List<BaseProductDTO> submitSearch() {
	List<BaseProductDTO> products;
	products = bpEJB.getAllBaseProducts();
	return searchFilter(products);
    }

    /**
     * Filters the list of products based on the selected criteria.
     * 
     * @param products the list of all of the products into the db
     * @return the list of filtered products
     */
    private List<BaseProductDTO> searchFilter(List<BaseProductDTO> products) {
	List<BaseProductDTO> filteredProducts = new ArrayList<BaseProductDTO>();
	for (BaseProductDTO bp : products) {
	    if (nameCheck(bp) && companyCheck(bp)) {
		filteredProducts.add(bp);
	    }
	}
	return filteredProducts;
    }

    /**
     * Compares the searched name with that of a specific base product.
     * 
     * @param bp
     * @return boolean answer
     */
    private boolean nameCheck(BaseProductDTO bp) {
	return (name.equals("") || bp.getName().equals(name)) ? true : false;
    }

    /**
     * Compares the searched company with that of a specific base product.
     * 
     * @param bp
     * @return boolean answer
     */
    private boolean companyCheck(BaseProductDTO bp) {
	return (company.equals("null") || bp.getCompany().equals(company)) ? true : false;
    }

    /**
     * Retrieves the list of all the companies into the db.
     * 
     * @return list of companies' names
     */
    public List<String> dropDownFilterCompany() {
	return bpEJB.getAllCompanies();
    }

    /**
     * To be called when the user clicks on the delete icon.
     * 
     * @param bp the selected BaseProductDTO in a row of the list
     */
    public void deleteBaseProduct(BaseProductDTO bp) {
	try {
	    bpEJB.removeBaseProduct(bp);
	} catch (NotValidBaseProductException e) {
	    // no problem: bp previously extracted from db
	    e.printStackTrace();
	}
	submitSearch(); // refresh list
    }

    /**
     * Shows edit_base_product page.
     * 
     * @param bp the selected BaseProductDTO in a row of the list
     * @return the page URL
     */
    public String showEditBaseProduct(BaseProductDTO bp) {
	sessionStorage.setSelectedProduct(bp);
	return ("/admin/edit_base_product?faces-redirect=true");
    }

	public BaseProductDTO getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(BaseProductDTO selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

}
