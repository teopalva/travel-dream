package dto;

abstract public class PossibleDatePersonalizationDTO extends PossiblePersonalizationDTO {
	protected int dateId;
	
	//Only for EditBaseProductBean
	private DatePersonalizationDTO datePersonalization = null;
	private double price;

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

	public DatePersonalizationDTO getDatePersonalization() {
		return datePersonalization;
	}

	public void setDatePersonalization(DatePersonalizationDTO datePersonalization) {
		this.datePersonalization = datePersonalization;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "PossibleDatePersonalizationDTO [dateId=" + dateId + "]";
	}
}
