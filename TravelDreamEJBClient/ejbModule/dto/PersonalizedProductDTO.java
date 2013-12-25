package dto;

public class PersonalizedProductDTO {
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PersonalizedProductDTO [id=" + id + "]";
	}
}
