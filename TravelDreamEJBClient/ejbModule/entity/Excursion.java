package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the EXCURSION database table.
 * 
 */
@Entity
@NamedQuery(name="Excursion.findAll", query="SELECT e FROM Excursion e")
public class Excursion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	
	//bi-directional many-to-one association to City
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="City")
	private City city;

	//bi-directional many-to-many association to DatePersonalization
	@ManyToMany(mappedBy="excursions", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<DatePersonalization> datePersonalizations;

	//bi-directional many-to-one association to Company
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="Company")
	private Company company;

	//bi-directional many-to-one association to PersonalizedProductExcursion
	@OneToMany(mappedBy="excursion")
	private List<PersonalizedProductExcursion> personalizedProductExcursions;

	//bi-directional many-to-one association to PossibleDatePersonalizationExcursion
	@OneToMany(mappedBy="excursion", cascade = CascadeType.ALL)
	private List<PossibleDatePersonalizationExcursion> possibleDatePersonalizationExcursions;

	public Excursion() {
		datePersonalizations = new ArrayList<DatePersonalization>();
		personalizedProductExcursions = new ArrayList<PersonalizedProductExcursion>();
		possibleDatePersonalizationExcursions = new ArrayList<PossibleDatePersonalizationExcursion>();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DatePersonalization> getDatePersonalizations() {
		return this.datePersonalizations;
	}

	public void setDatePersonalizations(List<DatePersonalization> datePersonalizations) {
		this.datePersonalizations = datePersonalizations;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<PersonalizedProductExcursion> getPersonalizedProductExcursions() {
		return this.personalizedProductExcursions;
	}

	public void setPersonalizedProductExcursions(List<PersonalizedProductExcursion> personalizedProductExcursions) {
		this.personalizedProductExcursions = personalizedProductExcursions;
	}

	public PersonalizedProductExcursion addPersonalizedProductExcursion(PersonalizedProductExcursion personalizedProductExcursion) {
		getPersonalizedProductExcursions().add(personalizedProductExcursion);
		personalizedProductExcursion.setExcursion(this);

		return personalizedProductExcursion;
	}

	public PersonalizedProductExcursion removePersonalizedProductExcursion(PersonalizedProductExcursion personalizedProductExcursion) {
		getPersonalizedProductExcursions().remove(personalizedProductExcursion);
		personalizedProductExcursion.setExcursion(null);

		return personalizedProductExcursion;
	}

	public List<PossibleDatePersonalizationExcursion> getPossibleDatePersonalizationExcursions() {
		return this.possibleDatePersonalizationExcursions;
	}

	public void setPossibleDatePersonalizationExcursions(List<PossibleDatePersonalizationExcursion> possibleDatePersonalizationExcursions) {
		this.possibleDatePersonalizationExcursions = possibleDatePersonalizationExcursions;
	}

	public PossibleDatePersonalizationExcursion addPossibleDatePersonalizationExcursion(PossibleDatePersonalizationExcursion possibleDatePersonalizationExcursion) {
		getPossibleDatePersonalizationExcursions().add(possibleDatePersonalizationExcursion);
		possibleDatePersonalizationExcursion.setExcursion(this);

		return possibleDatePersonalizationExcursion;
	}

	public PossibleDatePersonalizationExcursion removePossibleDatePersonalizationExcursion(PossibleDatePersonalizationExcursion possibleDatePersonalizationExcursion) {
		getPossibleDatePersonalizationExcursions().remove(possibleDatePersonalizationExcursion);
		possibleDatePersonalizationExcursion.setExcursion(null);

		return possibleDatePersonalizationExcursion;
	}
	
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}