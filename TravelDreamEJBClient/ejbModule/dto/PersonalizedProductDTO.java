package dto;

public abstract class PersonalizedProductDTO {
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public abstract double getPrice();

	@Override
	public String toString() {
		return "PersonalizedProductDTO [id=" + id + "]";
	}
	
}
