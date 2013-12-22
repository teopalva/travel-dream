package dto;

public class PossibleClassPersonalizationFlightDTO extends
		PossibleClassPersonalizationDTO {
	private int flightId;

	public PossibleClassPersonalizationFlightDTO(double price, int classId,
			int flightId) {
		super(price, classId);
		this.flightId = flightId;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}
	
	

}
