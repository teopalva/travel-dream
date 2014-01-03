package bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import coreEJB.BaseProductEJBLocal;
import dto.BaseProductDTO;
import dto.DatePersonalizationDTO;
import dto.PossibleDatePersonalizationDTO;

@ManagedBean(name = "EditBaseProduct")
@ViewScoped
public class EditBaseProductBean {
    private boolean flight;
    private boolean hotel;
    private boolean excursion;
    private BaseProductDTO selectedProduct;

    @EJB
    private BaseProductEJBLocal bpEJB;

    // -----FLIGHT-----
    private String name;
    private String company;
    private String departureAirport;
    private String cityDeparture; // TODO serve?
    private String arrivalAirport;
    private String cityArrival;
    private List<DatePersonalizationDTO> flightDates;
    private String flightClass;

    public List<String> dropDownFilterCompany() {
	return bpEJB.getAllCompanies();
    }

    public List<String> dropDownFilterAirport() {
	return bpEJB.getAllAirports();
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

    public String getDepartureAirport() {
	return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
	this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
	return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
	this.arrivalAirport = arrivalAirport;
    }

    public List<DatePersonalizationDTO> getFlightDate() {
	return flightDates;
    }

    public void setFlightDate(List<DatePersonalizationDTO> flightDate) {
	this.flightDates = flightDate;
    }

    public String getFlightClass() {
	return flightClass;
    }

    public void setFlightClass(String flightClass) {
	this.flightClass = flightClass;
    }

    // -----HOTEL-----
    // name
    // company
    private int hotelStars;
    private String hotelCity;
    private String hotelClass;

    public int getHotelStars() {
	return hotelStars;
    }

    public void setHotelStars(int hotelStars) {
	this.hotelStars = hotelStars;
    }

    public String getHotelCity() {
	return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
	this.hotelCity = hotelCity;
    }

    public String getHotelClass() {
	return hotelClass;
    }

    public void setHotelClass(String hotelClass) {
	this.hotelClass = hotelClass;
    }

    // -----EXCURSION-----
    // name
    // company
    private String excursionCity;
    private DatePersonalizationDTO escursionDate;

    public String getExcursionCity() {
	return excursionCity;
    }

    public void setExcursionCity(String excursionCity) {
	this.excursionCity = excursionCity;
    }

    public DatePersonalizationDTO getEscursionDate() {
	return escursionDate;
    }

    public void setEscursionDate(DatePersonalizationDTO escursionDate) {
	this.escursionDate = escursionDate;
    }

    // ----------

    @EJB
    private BaseProductEJBLocal baseProductEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    @PostConstruct
    public void init() {
	if (sessionStorage.getSelectedProduct() == null) {
	    selectedProduct = new BaseProductDTO();
	} else {
	    selectedProduct = sessionStorage.getSelectedProduct();
	}
    }

    /**
     * Button "+ partenza". Call this method on form confirm.
     */
    public void addDatePersonalization(double price, Date date, int duration) {
//	flightDates.add(new PossibleDatePersonalizationDTO());	TODO
    }
    
    /**
     * Button "+ classe". Call this method on form confirm.
     */
    public void addClassPersonalization() {
	// TODO
    }

    /**
     * Button "x" delete on personalization. Call this method on form confirm.
     */
    public void removePersonalization() {
	// TODO
    }

    /**
     * Button "Conferma". Confirms and executes the operations on the edited product.
     */
    public void confirm() {
	// TODO
    }

}
