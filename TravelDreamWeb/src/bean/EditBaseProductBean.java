package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import coreEJB.BaseProductEJBLocal;
import dto.BaseProductDTO;
import dto.CityDTO;
import dto.ClassPersonalizationDTO;
import dto.DatePersonalizationDTO;
import dto.ExcursionDTO;
import dto.FlightDTO;
import dto.HotelDTO;
import dto.PossibleClassPersonalizationDTO;
import dto.PossibleClassPersonalizationFlightDTO;
import dto.PossibleClassPersonalizationHotelDTO;
import dto.PossibleDatePersonalizationDTO;
import dto.PossibleDatePersonalizationExcursionDTO;
import dto.PossibleDatePersonalizationFlightDTO;
import exceptions.NotValidBaseProductException;
import exceptions.NotValidPersonalizationException;
import exceptions.PersonalizationNotSupportedException;

@ManagedBean(name = "EditBaseProduct")
@ViewScoped
public class EditBaseProductBean {
	private BaseProductDTO selectedProduct = null;
	private int productType = 0;
    private List<SelectItem> companies;
    private List<SelectItem> airports;
    
    /* Properties for setting new class/date */
    private Date date = null;
    private double price = 0;
    private int duration = 0;
    private String personalization = "";
    
    
	@EJB
	private BaseProductEJBLocal bpEJB;
	
	
	public boolean isFlight() {
		if(selectedProduct == null)
			return false;
		if(selectedProduct instanceof FlightDTO) {
			return true;
		}
		return false;
	}
	
	public boolean isHotel() {
		if(selectedProduct == null)
			return false;
		if(selectedProduct instanceof HotelDTO) {
			return true;
		}
		return false;
	}
	
	public boolean isExcursion() {
		if(selectedProduct == null)
			return false;
		if(selectedProduct instanceof ExcursionDTO) {
			return true;
		}
		return false;
	}
	
	private FlightDTO getFlight() {
		if(!isFlight())
			return null;
		return (FlightDTO) selectedProduct;
	}
	
	private HotelDTO getHotel() {
		if(!isHotel())
			return null;
		return (HotelDTO) selectedProduct;
	}
	
	private ExcursionDTO getExcursion() {
		if(!isExcursion())
			return null;
		return (ExcursionDTO) selectedProduct;
	}
	
	/**
	 * @return true if no base product type is selected
	 */
	public boolean isNotSelected() {
		if(isFlight() == false && isHotel() == false && isExcursion() == false)
			return true;
		return false;
	}
	
	/**
	 * Set the base product type to FlightDTO
	 */
	public void setFlight() {
		selectedProduct = new FlightDTO();
	}
	
	/**
	 * Set the base product type to HotelDTO
	 */
	public void setHotel() {
		selectedProduct = new HotelDTO();
	}
	
	/**
	 * Set the base product type to FlightDTO
	 */
	public void setExcursion() {
		selectedProduct = new ExcursionDTO();
	}

	// ----COMMON PROPERTIES
	
	//public List<String> dropDownFilterCompany() {
	//	return bpEJB.getAllCompanies();
	//}
	
	public String getName() {
		return selectedProduct.getName();
	}

	public void setName(String name) {
		selectedProduct.setName(name);
	}

	public String getCompany() {
		return selectedProduct.getCompany();
	}

	public void setCompany(String company) {
		selectedProduct.setCompany(company);
		
	}
	
	public List<PossibleDatePersonalizationDTO> getDatePersonalizations() {
		List<PossibleDatePersonalizationDTO> datePersonalizations = new ArrayList<PossibleDatePersonalizationDTO>();
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			for(DatePersonalizationDTO d : flight.getPossibleDatePersonalizations()) {
				PossibleDatePersonalizationFlightDTO dpf = new PossibleDatePersonalizationFlightDTO();
				dpf.setDatePersonalization(d);
				dpf.setPrice(flight.getPrices().get(d));
				datePersonalizations.add(dpf);
			}
			return datePersonalizations;
		}
		else if(getExcursion() != null) {
			ExcursionDTO excursion = getExcursion();
			for(DatePersonalizationDTO d : excursion.getPossibleDatePersonalizations()) {
				PossibleDatePersonalizationExcursionDTO dpf = new PossibleDatePersonalizationExcursionDTO();
				dpf.setDatePersonalization(d);
				dpf.setPrice(excursion.getPrices().get(d));
				datePersonalizations.add(dpf);
			}
			return datePersonalizations;
		}
		else {
			System.err.println("datePersonalizations not supported");
			throw new PersonalizationNotSupportedException();
		}
	}
	
	public List<PossibleClassPersonalizationDTO> getClassPersonalizations() {
		List<PossibleClassPersonalizationDTO> classPersonalizations = new ArrayList<PossibleClassPersonalizationDTO>();
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			for(ClassPersonalizationDTO c : flight.getPossibleClassPersonalizations()) {
				PossibleClassPersonalizationFlightDTO cpf = new PossibleClassPersonalizationFlightDTO();
				cpf.setClassPersonalization(c);
				cpf.setPrice(flight.getPrices().get(c));
				classPersonalizations.add(cpf);
			}
			return classPersonalizations;
		}
		else if(getHotel() != null) {
			HotelDTO hotel = getHotel();
			for(ClassPersonalizationDTO c : hotel.getPossibleClassPersonalizations()) {
				PossibleClassPersonalizationHotelDTO cpf = new PossibleClassPersonalizationHotelDTO();
				cpf.setClassPersonalization(c);
				cpf.setPrice(hotel.getPrices().get(c));
				classPersonalizations.add(cpf);
			}
			return classPersonalizations;
		}
		
		System.err.println("classPersonalizations not supported");
		throw new PersonalizationNotSupportedException();
	}
	
	private void addDatePersonalization(DatePersonalizationDTO datePersonalization, double price) throws NotValidPersonalizationException {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			if(!checkDuplicateDate(flight.getPossibleDatePersonalizations(), datePersonalization))
				flight.addPersonalization(datePersonalization, price);
			else
				throw new NotValidPersonalizationException();			
		}
		else if(getExcursion() != null) {
			ExcursionDTO excursion = getExcursion();
			if(!checkDuplicateDate(excursion.getPossibleDatePersonalizations(), datePersonalization))
				excursion.addPersonalization(datePersonalization, price);
			else
				throw new NotValidPersonalizationException();
		}
		else {
			System.err.println("datePersonalizations not supported");
			throw new PersonalizationNotSupportedException();
		}
	}
	
	private void addClassPersonalization(ClassPersonalizationDTO classPersonalization, double price) throws NotValidPersonalizationException  {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			if(!checkDuplicateClass(flight.getPossibleClassPersonalizations(), classPersonalization))
				flight.addPersonalization(classPersonalization, price);
			else
				throw new NotValidPersonalizationException();
		}
		else if(getHotel() != null) {
			HotelDTO hotel = getHotel();
			if(!checkDuplicateClass(hotel.getPossibleClassPersonalizations(), classPersonalization))
				hotel.addPersonalization(classPersonalization, price);
			else
				throw new NotValidPersonalizationException();
		}
		else {
			System.err.println("classPersonalizations not supported");
			throw new PersonalizationNotSupportedException();
		}
	}
	
	public void removeDatePersonalization(DatePersonalizationDTO datePersonalization) {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			flight.removePersonalization(datePersonalization);
		}
		else if(getExcursion() != null) {
			ExcursionDTO excursion = getExcursion();
			excursion.removePersonalization(datePersonalization);
		}
		else {
			System.err.println("datePersonalizations not supported");
			throw new PersonalizationNotSupportedException();
		}
	}
	
	public void removeClassPersonalization(ClassPersonalizationDTO classPersonalization) {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			flight.removePersonalization(classPersonalization);
		}
		else if(getHotel() != null) {
			HotelDTO hotel = getHotel();
			hotel.removePersonalization(classPersonalization);
		}
		else {
			System.err.println("classPersonalizations not supported");
			throw new PersonalizationNotSupportedException();
		}
	}

	// -----FLIGHT-----

	public List<String> dropDownFilterAirport() {
		return bpEJB.getAllAirports();
	}

	public String getDepartureAirport() {
		FlightDTO flight = getFlight();
		return flight.getAirportDeparture();
	}

	public void setDepartureAirport(String departureAirport) {
		FlightDTO flight = getFlight();
		flight.setAirportDeparture(departureAirport);
	
	}

	public String getArrivalAirport() {
		FlightDTO flight = getFlight();
		return flight.getAirportArrival();
	}

	public void setArrivalAirport(String arrivalAirport) {
		FlightDTO flight = getFlight();
		flight.setAirportArrival(arrivalAirport);
	}

	// -----HOTEL-----

	public int getHotelStars() {
		HotelDTO hotel = getHotel();
		return hotel.getStars();
	}

	public void setHotelStars(int hotelStars) {
		HotelDTO hotel = getHotel();
		hotel.setStars(hotelStars);
	}

	public String getHotelCityName() {
		HotelDTO hotel = getHotel();
		return hotel.getCity().getName();
	}
	
	public String getHotelCityCountry() {
		HotelDTO hotel = getHotel();
		return hotel.getCity().getCountry();
	}

	public void setHotelCityName(String name) {
		HotelDTO hotel = getHotel();
		CityDTO city = hotel.getCity();
		if(city == null) {
			hotel.setCity(new CityDTO(name, ""));
		}
		else {
			hotel.getCity().setName(name);
		}
	}
	
	public void setHotelCityCountry(String country) {
		HotelDTO hotel = getHotel();
		CityDTO city = hotel.getCity();
		if(city == null) {
			hotel.setCity(new CityDTO("", country));
		}
		else {
			hotel.getCity().setCountry(country);
		}
	}

	// -----EXCURSION-----

	public String getExcursionCityName() {
		ExcursionDTO excursion = getExcursion();
		return excursion.getCity().getName();
	}
	
	public String getExcursionCityCountry() {
		ExcursionDTO excursion = getExcursion();
		return excursion.getCity().getCountry();
	}

	public void setExcursionCityName(String name) {
		ExcursionDTO excursion = getExcursion();
		CityDTO city = excursion.getCity();
		if(city == null) {
			excursion.setCity(new CityDTO(name, ""));
		}
		else {
			excursion.getCity().setName(name);
		}
	}
	
	public void setExcursionCityCountry(String country) {
		ExcursionDTO excursion = getExcursion();
		CityDTO city = excursion.getCity();
		if(city == null) {
			excursion.setCity(new CityDTO("", country));
		}
		else {
			excursion.getCity().setCountry(country);
		}
	}

	// ----------

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

    	companies = new ArrayList<SelectItem>(bpEJB.getAllCompanies().size());
    	for(String value : bpEJB.getAllCompanies()){
    	    companies.add(new SelectItem(value));
    	}
    	
    	airports = new ArrayList<SelectItem>(bpEJB.getAllAirports().size());
    	for(String value : bpEJB.getAllAirports()){
    	    airports.add(new SelectItem(value));
    	}
		
    	
    	
    	
		if (sessionStorage.getSelectedProduct() == null) {
			selectedProduct = null;	//Not selected
		} else {
			selectedProduct = sessionStorage.getSelectedProduct();
			sessionStorage.setSelectedProduct(null);
		}
	}

	// Methods to modify/update the class attributes as the user interacts with the personalizations:

	public void addDatePersonalization() {
		//System.out.println(this.duration+"  "+this.date+" "+this.price);
		DatePersonalizationDTO datePersonalization = new DatePersonalizationDTO(this.duration, this.date);
		try {
			this.addDatePersonalization(datePersonalization, this.price);
		} catch (NotValidPersonalizationException e) {
			// TODO display error GUI
			System.err.println("Doppia personalizzazione");
			e.printStackTrace();
		}
	}

	public void addClassPersonalization() {
		//System.out.println(this.personalization+"  "+this.price);
		ClassPersonalizationDTO classPersonalization = new ClassPersonalizationDTO(this.personalization);
		try {
			this.addClassPersonalization(classPersonalization, this.price);
		} catch (NotValidPersonalizationException e) {
			// TODO display error GUI
			System.err.println("Doppia personalizzazione");
			e.printStackTrace();
		}
	}

	//public void removeDatePersonalization(Date date, int duration) {
	//	DatePersonalizationDTO datePersonalization = new DatePersonalizationDTO(duration, date);
	//	this.removeDatePersonalization(datePersonalization);
	//}

	// Method to apply to the EJBs the class attributes values when the user clicks on confirm button:

	public String confirm() {
		System.out.println(selectedProduct.toString());
		if(selectedProduct != null) {
			try {
				bpEJB.saveBaseProduct(selectedProduct);
			} catch (NotValidBaseProductException e) {
				// TODO rise error to be displayed in JSF -  display error GUI
				e.printStackTrace();
				return null;
			}
		}
		return "/admin/view_base_product?faces-redirect=true";
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
		if(this.productType == 1) setFlight();
		if(this.productType == 2) setHotel();
		if(this.productType == 3) setExcursion();
	}

	public List<SelectItem> getCompanies() {
		return companies;
	}

	public void setCompanies(List<SelectItem> companies) {
		this.companies = companies;
	}

	public List<SelectItem> getAirports() {
		return airports;
	}

	public void setAirports(List<SelectItem> airports) {
		this.airports = airports;
	}

	
	/* getter and setter for attributes of new class/date */
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getPersonalization() {
		return personalization;
	}

	public void setPersonalization(String personalization) {
		this.personalization = personalization;
	}
	
	public List<CityDTO> getAllCities() {
		return bpEJB.getAllCities();
	}
	
	public boolean checkDuplicateClass(List<ClassPersonalizationDTO> classPersonalizations, ClassPersonalizationDTO classPersonalization) {
		for(ClassPersonalizationDTO c : classPersonalizations) {
			if(c.equals(classPersonalization))
				return true;
		}
		return false;
	}
	
	public boolean checkDuplicateDate(List<DatePersonalizationDTO> datePersonalizations, DatePersonalizationDTO datePersonalization) {
		for(DatePersonalizationDTO c : datePersonalizations) {
			if(c.equals(datePersonalization))
				return true;
		}
		return false;
	}

}
