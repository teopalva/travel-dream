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
 * The persistent class for the HOTEL database table.
 * 
 */
@Entity
@NamedQuery(name="Hotel.findAll", query="SELECT h FROM Hotel h")
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	
	private int stars;
	
	//bi-directional many-to-one association to City
		@ManyToOne
		@JoinColumn(name="City")
		private City city;

	//bi-directional many-to-many association to ClassPersonalization
	@ManyToMany(mappedBy="hotels", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ClassPersonalization> classPersonalizations;

	//bi-directional many-to-one association to Company
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="Company")
	private Company company;

	//bi-directional many-to-one association to PersonalizedProductHotel
	@OneToMany(mappedBy="hotel")
	private List<PersonalizedProductHotel> personalizedProductHotels;

	//bi-directional many-to-one association to PossibleClassPersonalizationHotel
	@OneToMany(mappedBy="hotel", cascade = CascadeType.ALL)
	private List<PossibleClassPersonalizationHotel> possibleClassPersonalizationHotels;

	public Hotel() {
		classPersonalizations = new ArrayList<ClassPersonalization>();
		personalizedProductHotels = new ArrayList<PersonalizedProductHotel>();
		possibleClassPersonalizationHotels = new ArrayList<PossibleClassPersonalizationHotel>();
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

	public List<ClassPersonalization> getClassPersonalizations() {
		return this.classPersonalizations;
	}

	public void setClassPersonalizations(List<ClassPersonalization> classPersonalizations) {
		this.classPersonalizations = classPersonalizations;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<PersonalizedProductHotel> getPersonalizedProductHotels() {
		return this.personalizedProductHotels;
	}

	public void setPersonalizedProductHotels(List<PersonalizedProductHotel> personalizedProductHotels) {
		this.personalizedProductHotels = personalizedProductHotels;
	}

	public PersonalizedProductHotel addPersonalizedProductHotel(PersonalizedProductHotel personalizedProductHotel) {
		getPersonalizedProductHotels().add(personalizedProductHotel);
		personalizedProductHotel.setHotel(this);

		return personalizedProductHotel;
	}

	public PersonalizedProductHotel removePersonalizedProductHotel(PersonalizedProductHotel personalizedProductHotel) {
		getPersonalizedProductHotels().remove(personalizedProductHotel);
		personalizedProductHotel.setHotel(null);

		return personalizedProductHotel;
	}

	public List<PossibleClassPersonalizationHotel> getPossibleClassPersonalizationHotels() {
		return this.possibleClassPersonalizationHotels;
	}

	public void setPossibleClassPersonalizationHotels(List<PossibleClassPersonalizationHotel> possibleClassPersonalizationHotels) {
		this.possibleClassPersonalizationHotels = possibleClassPersonalizationHotels;
	}

	public PossibleClassPersonalizationHotel addPossibleClassPersonalizationHotel(PossibleClassPersonalizationHotel possibleClassPersonalizationHotel) {
		getPossibleClassPersonalizationHotels().add(possibleClassPersonalizationHotel);
		possibleClassPersonalizationHotel.setHotel(this);

		return possibleClassPersonalizationHotel;
	}

	public PossibleClassPersonalizationHotel removePossibleClassPersonalizationHotel(PossibleClassPersonalizationHotel possibleClassPersonalizationHotel) {
		getPossibleClassPersonalizationHotels().remove(possibleClassPersonalizationHotel);
		possibleClassPersonalizationHotel.setHotel(null);

		return possibleClassPersonalizationHotel;
	}
	
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	public int getStars() {
		return this.stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

}