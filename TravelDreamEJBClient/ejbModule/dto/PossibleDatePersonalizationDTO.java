package dto;

abstract public class PossibleDatePersonalizationDTO extends PossiblePersonalizationDTO {
	protected int dateId;

	public PossibleDatePersonalizationDTO(double price, int dateId) {
		super(price);
		this.dateId = dateId;
	}

	public int getDateId() {
		return dateId;
	}

	public void setDateId(int dateId) {
		this.dateId = dateId;
	}

	@Override
	public String toString() {
		return "PossibleDatePersonalizationDTO [dateId=" + dateId + "]";
	}
}
