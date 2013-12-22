package dto;

import java.util.ArrayList;
import java.util.List;

import entity.Flight;
import entity.PossibleClassPersonalizationFlight;
import entity.PossibleDatePersonalizationFlight;

public class FlightDTO extends BaseProductDTO {
	private String airportArrival;
	private String airportDeparture;
	private List<ClassPersonalizationDTO> possibleClassPersonalizations;
	private List<DatePersonalizationDTO> possibleDatePersonalizations;
	
	public FlightDTO() {}
	
	public FlightDTO(Flight flight) throws FieldNotPresentException{
		try {
			this.airportArrival = flight.getAirportArrival().getId();
			this.airportDeparture = flight.getAirportDeparture().getId();
			this.name = flight.getName();
			this.company = flight.getCompany().getName();
			possibleClassPersonalizations = new ArrayList<ClassPersonalizationDTO>();
			possibleDatePersonalizations = new ArrayList<DatePersonalizationDTO>();
			try {
				for(PossibleClassPersonalizationFlight cp: flight.getPossibleClassPersonalizationFlights()) {
					possibleClassPersonalizations.add(new ClassPersonalizationDTO(cp.getClassPersonalization()));
				}
			} catch(NullPointerException e) {}	//No problem, there are no personalizations
			try {
				for(PossibleDatePersonalizationFlight cp: flight.getPossibleDatePersonalizationFlights()) {
					possibleDatePersonalizations.add(new DatePersonalizationDTO(cp.getDatePersonalization()));
				}
			} catch(NullPointerException e) {}	//No problem, there are no personalizations
		} catch(Exception e) {
			throw new FieldNotPresentException();
		}
	}
	
	public FlightDTO(String name, String company,String airportArrival, String airportDeparture,
			List<ClassPersonalizationDTO> possibleClassPersonalizations,
			List<DatePersonalizationDTO> possibleDatePersonalizations) {
		super();
		this.name = name;
		this.company = company;
		this.airportArrival = airportArrival;
		this.airportDeparture = airportDeparture;
		this.possibleClassPersonalizations = new ArrayList(possibleClassPersonalizations);
		this.possibleDatePersonalizations = new ArrayList(possibleDatePersonalizations);
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
}
