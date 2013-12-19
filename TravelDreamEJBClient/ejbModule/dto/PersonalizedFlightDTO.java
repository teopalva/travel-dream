package dto;

import entity.PersonalizedProductFlight;


public class PersonalizedFlightDTO extends PersonalizedProductDTO {
	private FlightDTO flight;
	
	public PersonalizedFlightDTO(PersonalizedProductFlight flight) throws FieldNotPresentException {
		this.flight = new FlightDTO(flight.getFlight());
	}

	public PersonalizedFlightDTO(FlightDTO flight) {
		super();
		this.flight = flight;
	}

	public FlightDTO getFlight() {
		return flight;
	}

	public void setFlight(FlightDTO flight) {
		this.flight = flight;
	}
	
	
}
