package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Flight;
import entity.PossibleClassPersonalizationFlight;
import entity.PossibleDatePersonalizationFlight;
import exceptions.FieldNotPresentException;

public class FlightDTO extends BaseProductDTO {
	private String airportArrival;
	private String airportDeparture;
	private CityDTO cityArrival;
	private CityDTO cityDeparture;
	private List<ClassPersonalizationDTO> possibleClassPersonalizations;
	private List<DatePersonalizationDTO> possibleDatePersonalizations;
	private Map<PersonalizationDTO,Double> prices;
	
	public FlightDTO() {
		possibleClassPersonalizations = new ArrayList<ClassPersonalizationDTO>();
		possibleDatePersonalizations = new ArrayList<DatePersonalizationDTO>();
		prices = new HashMap<PersonalizationDTO,Double>();
		this.id = -1;
	}
	
	public FlightDTO(Flight flight) throws FieldNotPresentException{
		this();
		try {
			
			this.airportArrival = flight.getAirportArrival().getId();
			this.airportDeparture = flight.getAirportDeparture().getId();
			this.cityArrival = new CityDTO(flight.getAirportArrival().getCity());
			this.cityDeparture = new CityDTO(flight.getAirportDeparture().getCity());
			this.name = flight.getName();
			this.company = flight.getCompany().getName();
			this.id = flight.getId();
			
			try {
				for(PossibleClassPersonalizationFlight cp: flight.getPossibleClassPersonalizationFlights()) {
					ClassPersonalizationDTO c = new ClassPersonalizationDTO(cp.getClassPersonalization());
					possibleClassPersonalizations.add(c);
					prices.put(c, new Double(cp.getPrice().doubleValue()));
				}
			} catch(NullPointerException e) {}	//No problem, there are no personalizations
			try {
				for(PossibleDatePersonalizationFlight cp: flight.getPossibleDatePersonalizationFlights()) {
					DatePersonalizationDTO d = new DatePersonalizationDTO(cp.getDatePersonalization());
					possibleDatePersonalizations.add(d);
					prices.put(d, new Double(cp.getPrice().doubleValue()));
				}
			} catch(NullPointerException e) {}	//No problem, there are no personalizations
		} catch(Exception e) {
			throw new FieldNotPresentException();
		}
	}
	
	public FlightDTO(String name, String company,String airportArrival, String airportDeparture,
			List<ClassPersonalizationDTO> possibleClassPersonalizations,
			List<DatePersonalizationDTO> possibleDatePersonalizations,
			Map<PersonalizationDTO,Double> prices) {
		this();
		this.id = -1;
		this.name = name;
		this.company = company;
		this.airportArrival = airportArrival;
		this.airportDeparture = airportDeparture;
		if(prices != null)
			this.prices = new HashMap<PersonalizationDTO,Double>(prices);
		if(possibleClassPersonalizations != null)
			this.possibleClassPersonalizations = new ArrayList<ClassPersonalizationDTO>(possibleClassPersonalizations);
		if(possibleDatePersonalizations != null)
			this.possibleDatePersonalizations = new ArrayList<DatePersonalizationDTO>(possibleDatePersonalizations);
	}
	public String getAirportArrival() {
		return airportArrival;
	}
	public void setAirportArrival(String airportArrival) {
		this.airportArrival = airportArrival;
	}
	public String getAirportDeparture() {
		return airportDeparture;
	}
	public void setAirportDeparture(String airportDeparture) {
		this.airportDeparture = airportDeparture;
	}
	public List<ClassPersonalizationDTO> getPossibleClassPersonalizations() {
		return possibleClassPersonalizations;
	}
	public void setPossibleClassPersonalizations(
			List<ClassPersonalizationDTO> possibleClassPersonalizations) {
		this.possibleClassPersonalizations = possibleClassPersonalizations;
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
	
	public CityDTO getCityArrival() {
		return cityArrival;
	}

	public void setCityArrival(CityDTO cityArrival) {
		this.cityArrival = cityArrival;
	}

	public CityDTO getCityDeparture() {
		return cityDeparture;
	}

	public void setCityDeparture(CityDTO cityDeparture) {
		this.cityDeparture = cityDeparture;
	}

	public void addPersonalization(PersonalizationDTO personalization, double price) {
		if(personalization instanceof DatePersonalizationDTO) {
			DatePersonalizationDTO d = (DatePersonalizationDTO)personalization;
			this.possibleDatePersonalizations.add(d);
			this.prices.put(d,new Double(price));
		}
		if(personalization instanceof ClassPersonalizationDTO) {
			ClassPersonalizationDTO c = (ClassPersonalizationDTO)personalization;
			this.possibleClassPersonalizations.add(c);
			this.prices.put(c,new Double(price));
		}
	}
	public void removePersonalization(PersonalizationDTO personalization) {
		if(personalization instanceof DatePersonalizationDTO) {
			DatePersonalizationDTO d = (DatePersonalizationDTO)personalization;
			this.possibleDatePersonalizations.remove(d);
			this.prices.remove(d);
		}
		if(personalization instanceof ClassPersonalizationDTO) {
			ClassPersonalizationDTO c = (ClassPersonalizationDTO)personalization;
			this.possibleClassPersonalizations.remove(c);
			this.prices.remove(c);
		}
	}
	
	@Override
	public String toString() {
		return "FlightDTO [airportArrival=" + airportArrival
				+ ", airportDeparture=" + airportDeparture + ", cityArrival="
				+ cityArrival + ", cityDeparture=" + cityDeparture
				+ ", possibleClassPersonalizations="
				+ possibleClassPersonalizations
				+ ", possibleDatePersonalizations="
				+ possibleDatePersonalizations + ", prices=" + prices
				+ ", name=" + name + ", company=" + company + ", id=" + id
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((airportArrival == null) ? 0 : airportArrival.hashCode());
		result = prime
				* result
				+ ((airportDeparture == null) ? 0 : airportDeparture.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		FlightDTO other = (FlightDTO) obj;
		if (airportArrival == null) {
			if (other.airportArrival != null)
				return false;
		} else if (!airportArrival.equals(other.airportArrival))
			return false;
		if (airportDeparture == null) {
			if (other.airportDeparture != null)
				return false;
		} else if (!airportDeparture.equals(other.airportDeparture))
			return false;
		return true;
	}
}
