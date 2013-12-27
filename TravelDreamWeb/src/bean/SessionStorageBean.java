package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dto.PackageDTO;

@ManagedBean(name = "SessionStorage")
@SessionScoped
public class SessionStorageBean {

    /**
     * The package selected by the user for editing
     */
    private PackageDTO selectedPackage;
    private String previousPage = null;

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    public void setSelectedPackage(PackageDTO selectedPackage) {
	this.selectedPackage = selectedPackage;
    }

    public String getPreviousPage() {
	return previousPage;
    }

    /**
     * Possible pages: "gift", "invitation", "edit"
     */
    public void setPreviousPage(String page) {
	previousPage = page;
    }

}
