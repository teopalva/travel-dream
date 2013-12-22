package dto;

import entity.PersonalizedProductFlight;

public class PersonalizedFlightDTO extends PersonalizedProductDTO {
    private FlightDTO flight;
    private DatePersonalizationDTO date;

    public PersonalizedFlightDTO() {
    }

    public PersonalizedFlightDTO(PersonalizedProductFlight flight) throws FieldNotPresentException {
	this.flight = new FlightDTO(flight.getFlight());
    }

    public PersonalizedFlightDTO(FlightDTO flight, DatePersonalizationDTO date) {
	super();
	this.flight = flight;
	this.date = date;
    }

    public FlightDTO getFlight() {
	return flight;
    }

    public void setFlight(FlightDTO flight) {
	this.flight = flight;
    }

    public DatePersonalizationDTO getDatePersonalizatonFlight() {
	return date;
    }

}
