package bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="OfferingsList")
@SessionScoped
public class OfferingsListBean {
	private String departurePlace;
	private String arrivalPlace;
	private Date departureDate;
	private Date returnDate;
	private int numPeople;
	private String flightClass;
	private int hotelStars;
	private String hotelClass;
	
	public void submitSearch() {
		
	}
	
	public void dropDownFilter() {
		
	}
	
	public void showEditPackage() {
		
	}

}
