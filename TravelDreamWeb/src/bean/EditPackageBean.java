package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import dto.PackageDTO;

@ManagedBean(name = "EditPackage")
@ViewScoped
public class EditPackageBean {
    private String warningDiscount;
    private PackageDTO packageDto;

//    @ManagedProperty("#{OfferingsList.selectedPackage}")
//    private PackageDTO selectedPackage;	// TODO use this

    @ManagedProperty("#{OfferingsList.selectedPackageString}")
    private String selectedPackageString; 

    public EditPackageBean() {
	packageDto = new PackageDTO();
    }

    @PostConstruct
    public void init() {
	this.packageDto.setName(selectedPackageString);
    }

    public void setPackageDto(PackageDTO p) {
	packageDto = p;
    }

    public PackageDTO getPackageDto() {
	return packageDto;
    }

    public String getselectedPackageString() {
	return selectedPackageString;
    }

    public void setselectedPackageString(String s) {
	selectedPackageString = s;
    }

    public void showCheckout() {

    }
}
