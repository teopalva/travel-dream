package dto;

abstract public class PossibleClassPersonalizationDTO extends PossiblePersonalizationDTO {
	protected int classId;

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
	
}
