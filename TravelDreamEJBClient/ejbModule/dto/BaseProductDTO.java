package dto;

public class BaseProductDTO {
	protected String name;
	protected String company;
	protected int id;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BaseProductDTO [name=" + name + ", company=" + company
				+ ", id=" + id + "]";
	}
	
}
