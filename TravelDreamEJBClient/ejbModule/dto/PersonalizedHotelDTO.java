package dto;

import entity.PersonalizedProductHotel;

public class PersonalizedHotelDTO extends PersonalizedProductDTO {
	private HotelDTO hotel;
	
	public PersonalizedHotelDTO(PersonalizedProductHotel hotel) throws FieldNotPresentException {
		super();
		this.hotel = new HotelDTO(hotel.getHotel());
	}

	public PersonalizedHotelDTO(HotelDTO hotel) {
		super();
		this.hotel = hotel;
	}

	public HotelDTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelDTO hotel) {
		this.hotel = hotel;
	}
	
	

}
