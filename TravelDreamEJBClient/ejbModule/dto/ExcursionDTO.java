package dto;

import java.util.ArrayList;
import java.util.List;

import entity.Excursion;
import entity.PossibleDatePersonalizationExcursion;

public class ExcursionDTO extends BaseProductDTO {
	
	private List<DatePersonalizationDTO> possibleDatePersonalizations;
	
	public ExcursionDTO(Excursion excursion) {
		possibleDatePersonalizations = new ArrayList<DatePersonalizationDTO>();
		this.id = excursion.getId();
		try {
			for(PossibleDatePersonalizationExcursion cp: excursion.getPossibleDatePersonalizationExcursions()) {
				possibleDatePersonalizations.add(new DatePersonalizationDTO(cp.getDatePersonalization()));
			}
		} catch(NullPointerException e) {}	//No problem, there are no personalization
	}
	public ExcursionDTO(String name, String company, List<DatePersonalizationDTO> possibleDatePersonalization) {
		this.name = name;
		this.company = company;
		this.possibleDatePersonalizations = new ArrayList<DatePersonalizationDTO>(possibleDatePersonalization);
	}

}
