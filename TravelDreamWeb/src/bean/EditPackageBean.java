package bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import dto.PackageDTO;

@ManagedBean(name = "EditPackage")
@ViewScoped
public class EditPackageBean {
    private String warningDiscount;
    private PackageDTO packageDto;

    // @ManagedProperty(value = "#{OfferingsListBean.selectedPackage}")
    // private PackageDTO selectedPackage;

    @ManagedProperty("#{OfferingsListBean.selectedPackageString}")
    private Integer selectedPackageString; // TODO pass the entire package

    @PostConstruct
    public void init() {
	packageDto = new PackageDTO();
	this.packageDto.setId(selectedPackageString);
    }

    public void setPackageDto(PackageDTO p) {
	packageDto = p;
    }

    public PackageDTO getPackageDto() {
	return packageDto;
    }

    public Integer getselectedPackageString() {
	return selectedPackageString;
    }

    public void setselectedPackageString(Integer s) {
	selectedPackageString = s;
    }

    public void showCheckout() {

    }
}
