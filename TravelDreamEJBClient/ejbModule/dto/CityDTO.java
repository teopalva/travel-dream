package dto;

import entity.City;

public class CityDTO {
	String name;
	String country;
	
	public CityDTO(City city) {
		super();
		this.name = city.getName();
		this.country = city.getCountry();
	}
	public CityDTO(String name, String country) {
		super();
		this.name = name;
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "CityDTO [name=" + name + ", country=" + country + "]";
	}

}
