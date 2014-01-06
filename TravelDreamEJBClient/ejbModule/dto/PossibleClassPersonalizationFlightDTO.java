package dto;

public class PossibleClassPersonalizationFlightDTO extends
		PossibleClassPersonalizationDTO {
	private int flightId;

	public PossibleClassPersonalizationFlightDTO(double price, int classId,
			int flightId) {
		super(price, classId);
		this.flightId = flightId;
	}

	public PossibleClassPersonalizationFlightDTO() {
		super(0, 0);
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	@Override
	public String toString() {
		return "PossibleClassPersonalizationFlightDTO [flightId=" + flightId
				+ "]";
	}
}
