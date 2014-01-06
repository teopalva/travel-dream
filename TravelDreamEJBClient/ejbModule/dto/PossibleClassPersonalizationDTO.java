package dto;

abstract public class PossibleClassPersonalizationDTO extends PossiblePersonalizationDTO {
	protected int classId;
	
	//Only for EditBaseProductBean
	private ClassPersonalizationDTO classPersonalization = null;
	private double price;

	public PossibleClassPersonalizationDTO(double price, int classId) {
		super(price);
		this.classId = classId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	public ClassPersonalizationDTO getClassPersonalization() {
		return classPersonalization;
	}

	public void setClassPersonalization(ClassPersonalizationDTO classPersonalization) {
		this.classPersonalization = classPersonalization;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "PossibleClassPersonalizationDTO [classId=" + classId + "]";
	}
}
