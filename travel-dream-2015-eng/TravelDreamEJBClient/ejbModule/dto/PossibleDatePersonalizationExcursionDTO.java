package dto;

public class PossibleDatePersonalizationExcursionDTO extends
		PossibleDatePersonalizationDTO {
	private int excursionId;

	public PossibleDatePersonalizationExcursionDTO(double price, int dateId,
			int excursionId) {
		super(price, dateId);
		this.excursionId = excursionId;
	}

	public PossibleDatePersonalizationExcursionDTO() {
		super(0, 0);
	}

	public int getExcursionId() {
		return excursionId;
	}

	public void setExcursionId(int excursionId) {
		this.excursionId = excursionId;
	}

	@Override
	public String toString() {
		return "PossibleDatePersonalizationExcursionDTO [excursionId="
				+ excursionId + "]";
	}
}
