package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BaseProductEJBLocal;
import dto.BaseProductDTO;
import dto.PackageDTO;

@ManagedBean(name = "ViewBaseProduct")
@ViewScoped
public class ViewBaseProductBean {
    private String name = ""; // text-field
    private String company = "null"; // dropdown

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

    public List<BaseProductDTO> submitSearch() {
	List<BaseProductDTO> products;
	products = bpEJB.getAllBaseProducts();
	return searchFilter(products);
    }

    private List<BaseProductDTO> searchFilter(List<BaseProductDTO> products) {
	List<BaseProductDTO> filteredProducts = new ArrayList<BaseProductDTO>();
	for (BaseProductDTO bp : products) {
	    if (nameCheck(bp) && companyCheck(bp)) {
		filteredProducts.add(bp);
	    }
	}
	return filteredProducts;
    }

    private boolean nameCheck(BaseProductDTO bp) {
	return (name.equals("") || bp.getName().equals(name)) ? true : false;
    }

    private boolean companyCheck(BaseProductDTO bp) {
	return (company.equals("null") || bp.getCompany().equals(company)) ? true : false;
    }

    public List<String> dropDownFilterCompany() {
	return bpEJB.getAllCompanies();
    }

    public void deleteBaseProduct(BaseProductDTO bp) {
	// TODO
    }

    public String showEditBaseProduct(BaseProductDTO bp) {
	sessionStorage.setSelectedProduct(bp);
	return ("/admin/edit_base_product?faces-redirect=true");
    }

}
