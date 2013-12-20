package dto;

import entity.Excursion;
import entity.PersonalizedProductExcursion;

public class PersonalizedExcursionDTO extends PersonalizedProductDTO {
	private ExcursionDTO excursion;

	public PersonalizedExcursionDTO(PersonalizedProductExcursion excursion) {
		this.excursion = new ExcursionDTO(excursion.getExcursion());
	}
	
	public PersonalizedExcursionDTO(ExcursionDTO excursion) {
		super();
		this.excursion = excursion;
	}
	
	public ExcursionDTO getExcursion() {
		return excursion;
	}
	public void setExcursion(ExcursionDTO excursion) {
		this.excursion = excursion;
	}
}
