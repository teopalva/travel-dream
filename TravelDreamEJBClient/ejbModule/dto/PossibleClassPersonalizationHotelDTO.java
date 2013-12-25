package dto;

public class PossibleClassPersonalizationHotelDTO extends
		PossibleClassPersonalizationDTO {
	private int hotelId;

	public PossibleClassPersonalizationHotelDTO(double price, int classId,
			int hotelId) {
		super(price, classId);
		this.hotelId = hotelId;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	@Override
	public String toString() {
		return "PossibleClassPersonalizationHotelDTO [hotelId=" + hotelId + "]";
	}
}
