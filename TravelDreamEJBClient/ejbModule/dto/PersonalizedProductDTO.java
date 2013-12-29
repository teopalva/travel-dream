package dto;

public abstract class PersonalizedProductDTO {
	protected int id;
	protected int dropIndex;	//to save the temporary index of the item in edit_package


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public abstract double getPrice();

	public int getDropIndex() {
	    return dropIndex;
	}

	public void setDropIndex(int dropIndex) {
	    this.dropIndex = dropIndex;
	}

	@Override
	public String toString() {
		return "PersonalizedProductDTO [id=" + id + "]";
	}
	
}
