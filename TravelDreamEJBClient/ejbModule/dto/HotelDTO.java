package dto;

import java.util.ArrayList;
import java.util.List;

import entity.Hotel;
import entity.PossibleClassPersonalizationHotel;

public class HotelDTO extends BaseProductDTO {
	private List<ClassPersonalizationDTO> possibleClassPersonalizations;
	private int stars;
	
	public HotelDTO(Hotel hotel) throws FieldNotPresentException{
		try {
			this.company = hotel.getCompany().getName();
			this.name = hotel.getName();
			this.stars = stars;
			this.id = hotel.getId();
			possibleClassPersonalizations = new ArrayList<ClassPersonalizationDTO>();
			try {
				for(PossibleClassPersonalizationHotel cp: hotel.getPossibleClassPersonalizationHotels()) {
					possibleClassPersonalizations.add(new ClassPersonalizationDTO(cp.getClassPersonalization()));
				}
			} catch(NullPointerException e) {}	//No problem, there are no personalization
			
		}catch(NullPointerException e) {
			throw new FieldNotPresentException();
		}
		
		
	}
	
	public HotelDTO(String name, String company, int stars, List<ClassPersonalizationDTO> possibleClassPersonalizations) {
		super();
		this.id = -1;
		this.name = name;
		this.company = company;
		this.stars = stars;
		this.possibleClassPersonalizations = new ArrayList<ClassPersonalizationDTO>(possibleClassPersonalizations);
	}

	public List<ClassPersonalizationDTO> getPossibleClassPersonalizations() {
		return possibleClassPersonalizations;
	}

	public void setPossibleClassPersonalizations(
			List<ClassPersonalizationDTO> possibleClassPersonalizations) {
		this.possibleClassPersonalizations = possibleClassPersonalizations;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

}
