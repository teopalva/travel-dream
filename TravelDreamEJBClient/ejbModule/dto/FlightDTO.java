package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Flight;
import entity.PossibleClassPersonalizationFlight;
import entity.PossibleDatePersonalizationFlight;

public class FlightDTO extends BaseProductDTO {
	private String airportArrival;
	private String airportDeparture;
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
	
}
