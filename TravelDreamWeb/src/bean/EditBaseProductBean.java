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
import dto.CityDTO;
import dto.ClassPersonalizationDTO;
import dto.DatePersonalizationDTO;
import dto.ExcursionDTO;
import dto.FlightDTO;
import dto.HotelDTO;
import exceptions.NotValidBaseProductException;
import exceptions.PersonalizationNotSupportedException;

@ManagedBean(name = "EditBaseProduct")
@ViewScoped
public class EditBaseProductBean {
	private BaseProductDTO selectedProduct = null;

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
	
	public List<String> dropDownFilterCompany() {
		return bpEJB.getAllCompanies();
	}
	
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
	
	public List<DatePersonalizationDTO> getDatePersonalizations() {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			return flight.getPossibleDatePersonalizations();
		}
		if(getExcursion() != null) {
			ExcursionDTO excursion = getExcursion();
			return excursion.getPossibleDatePersonalizations();
		}
		
		System.err.println("datePersonalizations not supported");
		throw new PersonalizationNotSupportedException();
	}
	
	public List<ClassPersonalizationDTO> getClassPersonalizations() {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			return flight.getPossibleClassPersonalizations();
		}
		if(getHotel() != null) {
			HotelDTO hotel = getHotel();
			return hotel.getPossibleClassPersonalizations();
		}
		
		System.err.println("classPersonalizations not supported");
		throw new PersonalizationNotSupportedException();
	}
	
	private void addDatePersonalization(DatePersonalizationDTO datePersonalization, double price) {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			flight.addPersonalization(datePersonalization, price);
		}
		if(getExcursion() != null) {
			ExcursionDTO excursion = getExcursion();
			excursion.addPersonalization(datePersonalization, price);
		}
		
		System.err.println("datePersonalizations not supported");
		throw new PersonalizationNotSupportedException();
	}
	
	private void addClassPersonalization(ClassPersonalizationDTO classPersonalization, double price) {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			flight.addPersonalization(classPersonalization, price);
		}
		if(getHotel() != null) {
			HotelDTO hotel = getHotel();
			hotel.addPersonalization(classPersonalization, price);
		}
		
		System.err.println("classPersonalizations not supported");
		throw new PersonalizationNotSupportedException();
	}
	
	private void removeDatePersonalization(DatePersonalizationDTO datePersonalization) {
		if(getFlight() != null) {
			FlightDTO flight = getFlight();
			flight.removePersonalization(datePersonalization);
		}
		if(getExcursion() != null) {
			ExcursionDTO excursion = getExcursion();
			excursion.removePersonalization(datePersonalization);
		}
		
		System.err.println("datePersonalizations not supported");
		throw new PersonalizationNotSupportedException();
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
		flight.setAirportDeparture(arrivalAirport);
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

	// TODO non capisco il bisogno di averlo doppio...
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
			selectedProduct = null;	//Not selected
		} else {
			selectedProduct = sessionStorage.getSelectedProduct();
		}
	}

	// Methods to modify/update the class attributes as the user interacts with the personalizations:

	public void addDatePersonalization(double price, Date date, int duration) {
		DatePersonalizationDTO datePersonalization = new DatePersonalizationDTO(duration, date);
		this.addDatePersonalization(datePersonalization, price);
	}

	public void addClassPersonalization(double price, String personalization) {
		ClassPersonalizationDTO classPersonalization = new ClassPersonalizationDTO(personalization);
		this.addClassPersonalization(classPersonalization, price);
	}

	public void removeDatePersonalization(Date date, int duration) {
		DatePersonalizationDTO datePersonalization = new DatePersonalizationDTO(duration, date);
		this.removeDatePersonalization(datePersonalization);
	}

	// Method to apply to the EJBs the class attributes values when the user clicks on confirm button:

	public void confirm() {
		if(selectedProduct != null)
			try {
				bpEJB.saveBaseProduct(selectedProduct);
			} catch (NotValidBaseProductException e) {
				// TODO rise error to be displayed in JSF 
				e.printStackTrace();
			}
	}

}
