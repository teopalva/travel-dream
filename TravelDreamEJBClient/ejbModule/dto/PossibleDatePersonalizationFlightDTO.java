package dto;

public class PossibleDatePersonalizationFlightDTO extends
		PossibleDatePersonalizationDTO {
	private int flightId;

	public PossibleDatePersonalizationFlightDTO(double price, int dateId,
			int flightId) {
		super(price, dateId);
		this.flightId = flightId;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	@Override
	public String toString() {
		return "PossibleDatePersonalizationFlightDTO [flightId=" + flightId
				+ "]";
	}
}
