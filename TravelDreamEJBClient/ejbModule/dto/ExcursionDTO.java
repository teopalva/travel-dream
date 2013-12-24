package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Excursion;
import entity.PossibleDatePersonalizationExcursion;

public class ExcursionDTO extends BaseProductDTO {
	
	private CityDTO city;
	
	private List<DatePersonalizationDTO> possibleDatePersonalizations;
	
	private Map<PersonalizationDTO,Double> prices;
	
	public ExcursionDTO() {
		prices = new HashMap<PersonalizationDTO,Double>();
		possibleDatePersonalizations = new ArrayList<DatePersonalizationDTO>();
		this.id = -1;
	}
	
	public ExcursionDTO(Excursion excursion) {
		this();
		this.city = new CityDTO(excursion.getCity());
		this.id = excursion.getId();
		try {
			for(PossibleDatePersonalizationExcursion cp: excursion.getPossibleDatePersonalizationExcursions()) {
				DatePersonalizationDTO d = new DatePersonalizationDTO(cp.getDatePersonalization());
				possibleDatePersonalizations.add(d);
				prices.put(d, new Double(cp.getPrice().doubleValue()));
			}
		} catch(NullPointerException e) {}	//No problem, there are no personalization
	}
	public ExcursionDTO(String name, String company, List<DatePersonalizationDTO> possibleDatePersonalization, CityDTO city, Map<PersonalizationDTO,Double> prices) {
		this();
		this.name = name;
		this.city = city;
		this.company = company;
		if(possibleDatePersonalization != null)
			this.possibleDatePersonalizations = new ArrayList<DatePersonalizationDTO>(possibleDatePersonalization);
		if(prices != null)
			this.prices = new HashMap<PersonalizationDTO,Double>(prices);
	}
	public CityDTO getCity() {
		return city;
	}
	public void setCity(CityDTO city) {
		this.city = city;
	}
	public List<DatePersonalizationDTO> getPossibleDatePersonalizations() {
		return possibleDatePersonalizations;
	}
	public void setPossibleDatePersonalizations(
			List<DatePersonalizationDTO> possibleDatePersonalizations) {
		this.possibleDatePersonalizations = possibleDatePersonalizations;
	}
	public Map<PersonalizationDTO, Double> getPrices() {
		return prices;
	}
	public void setPrices(Map<PersonalizationDTO, Double> prices) {
		this.prices = prices;
	}
	
	public void addPersonalization(PersonalizationDTO personalization, double price) {
		if(personalization instanceof DatePersonalizationDTO) {
			DatePersonalizationDTO d = (DatePersonalizationDTO)personalization;
			this.possibleDatePersonalizations.add(d);
			this.prices.put(d,new Double(price));
		}
	}
	public void removePersonalization(PersonalizationDTO personalization) {
		if(personalization instanceof DatePersonalizationDTO) {
			DatePersonalizationDTO d = (DatePersonalizationDTO)personalization;
			this.possibleDatePersonalizations.remove(d);
			this.prices.remove(d);
		}
	}
	
	
	
}
