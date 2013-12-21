package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dto.PackageDTO;

@ManagedBean(name="EditPackage")
@ViewScoped
public class EditPackageBean {
	private String warningDiscount;
	private PackageDTO packageDto;	//TODO "package" invalid
	
	public void showCheckout() {
		
	}
}
