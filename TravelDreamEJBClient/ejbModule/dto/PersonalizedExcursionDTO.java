package dto;

import entity.PersonalizedProductExcursion;

public class PersonalizedExcursionDTO extends PersonalizedProductDTO {
	private ExcursionDTO excursion;
	private DatePersonalizationDTO datePersonalization;

	public PersonalizedExcursionDTO(PersonalizedProductExcursion excursion) {
		this.excursion = new ExcursionDTO(excursion.getExcursion());
		this.id = excursion.getId();
		if(excursion.getDatePersonalization() != null) {
			this.datePersonalization = new DatePersonalizationDTO(excursion.getDatePersonalization());
		}
	}
	
	public PersonalizedExcursionDTO(ExcursionDTO excursion) {
		super();
		this.excursion = excursion;
		this.id = -1;
	}
	
	public ExcursionDTO getExcursion() {
		return excursion;
	}
	public void setExcursion(ExcursionDTO excursion) {
		this.excursion = excursion;
	}

	public DatePersonalizationDTO getDatePersonalization() {
		return datePersonalization;
	}

	public void setDatePersonalization(DatePersonalizationDTO datePersonalization) {
		this.datePersonalization = datePersonalization;
	}

	@Override
	public String toString() {
		return "PersonalizedExcursionDTO [excursion=" + excursion
				+ ", datePersonalization=" + datePersonalization + "]";
	}
}
