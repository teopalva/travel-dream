package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Hotel;
import entity.PossibleClassPersonalizationHotel;
import exceptions.FieldNotPresentException;

public class HotelDTO extends BaseProductDTO {
	private List<ClassPersonalizationDTO> possibleClassPersonalizations;
	private int stars;
	private CityDTO city;
	private Map<PersonalizationDTO,Double> prices;
	
	public HotelDTO() {
		possibleClassPersonalizations = new ArrayList<ClassPersonalizationDTO>();
		prices = new HashMap<PersonalizationDTO,Double>();
		this.id = -1;
	}
	
	public HotelDTO(Hotel hotel) throws FieldNotPresentException{
		this();
		try {
			this.company = hotel.getCompany().getName();
			this.name = hotel.getName();
			this.stars = stars;
			this.id = hotel.getId();
			this.city = new CityDTO(hotel.getCity());
			try {
				for(PossibleClassPersonalizationHotel cp: hotel.getPossibleClassPersonalizationHotels()) {
					ClassPersonalizationDTO c = new ClassPersonalizationDTO(cp.getClassPersonalization());
					possibleClassPersonalizations.add(c);
					prices.put(c, new Double(cp.getPrice().doubleValue()));
				}
			} catch(NullPointerException e) {}	//No problem, there are no personalization
			
		}catch(NullPointerException e) {
			throw new FieldNotPresentException();
		}	
	}
	
	public HotelDTO(String name, String company, int stars, List<ClassPersonalizationDTO> possibleClassPersonalizations, CityDTO city, HashMap<PersonalizationDTO,Double>prices) {
		this();
		this.id = -1;
		this.name = name;
		this.city = city;
		this.company = company;
		this.stars = stars;
		if(possibleClassPersonalizations != null)
			this.possibleClassPersonalizations = new ArrayList<ClassPersonalizationDTO>(possibleClassPersonalizations);
		if(prices != null)
		this.prices = new HashMap<PersonalizationDTO,Double>(prices);
	}

	public List<ClassPersonalizationDTO> getPossibleClassPersonalizations() {
		return possibleClassPersonalizations;
	}

	public void setPossibleClassPersonalizations(
			List<ClassPersonalizationDTO> possibleClassPersonalizations) {
		this.possibleClassPersonalizations = possibleClassPersonalizations;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public CityDTO getCity() {
		return city;
	}

	public void setCity(CityDTO city) {
		this.city = city;
	}

	public Map<PersonalizationDTO, Double> getPrices() {
		return prices;
	}

	public void setPrices(Map<PersonalizationDTO, Double> prices) {
		this.prices = prices;
	}
	
	public void addPersonalization(PersonalizationDTO personalization, double price) {
		if(personalization instanceof ClassPersonalizationDTO) {
			ClassPersonalizationDTO c = (ClassPersonalizationDTO)personalization;
			this.possibleClassPersonalizations.add(c);
			this.prices.put(c,new Double(price));
		}
	}
	public void removePersonalization(PersonalizationDTO personalization) {
		if(personalization instanceof ClassPersonalizationDTO) {
			ClassPersonalizationDTO c = (ClassPersonalizationDTO)personalization;
			this.possibleClassPersonalizations.remove(c);
			this.prices.remove(c);
		}
	}

	@Override
	public String toString() {
		return "HotelDTO [possibleClassPersonalizations="
				+ possibleClassPersonalizations + ", stars=" + stars
				+ ", city=" + city + ", prices=" + prices + "]";
	}
}
