package dto;

import entity.PersonalizedProductHotel;
import exceptions.FieldNotPresentException;

public class PersonalizedHotelDTO extends PersonalizedProductDTO {
	private HotelDTO hotel;
	private ClassPersonalizationDTO classPersonalization;
	
	public PersonalizedHotelDTO(PersonalizedProductHotel hotel) throws FieldNotPresentException {
		super();
		this.hotel = new HotelDTO(hotel.getHotel());
		this.id = hotel.getId();
		if(hotel.getClassPersonalization() != null) {
			this.classPersonalization = new ClassPersonalizationDTO(hotel.getClassPersonalization());
		}
	}

	public PersonalizedHotelDTO(HotelDTO hotel) {
		super();
		this.hotel = hotel;
		this.id = -1;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}

	public ClassPersonalizationDTO getClassPersonalization() {
		return classPersonalization;
	}

	public void setClassPersonalization(ClassPersonalizationDTO classPersonalization) {
		this.classPersonalization = classPersonalization;
	}

	@Override
	public String toString() {
		return "PersonalizedHotelDTO [hotel=" + hotel
				+ ", classPersonalization=" + classPersonalization + "]";
	}
}
