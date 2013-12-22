package dto;

abstract public class PossiblePersonalizationDTO {
	protected double price;

	public PossiblePersonalizationDTO(double price) {
		super();
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	

}
