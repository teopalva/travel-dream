package entity;

import java.io.Serializable;

import javax.persistence.*;

import entity.ClassPersonalization;
import entity.Company;
import entity.PersonalizedProductHotel;
import entity.PossibleClassPersonalizationHotel;

import java.util.List;


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

	//bi-directional many-to-many association to ClassPersonalization
	@ManyToMany(mappedBy="hotels")
	private List<ClassPersonalization> classPersonalizations;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="Company")
	private Company company;

	//bi-directional many-to-one association to PersonalizedProductHotel
	@OneToMany(mappedBy="hotel")
	private List<PersonalizedProductHotel> personalizedProductHotels;

	//bi-directional many-to-one association to PossibleClassPersonalizationHotel
	@OneToMany(mappedBy="hotel")
	private List<PossibleClassPersonalizationHotel> possibleClassPersonalizationHotels;

	public Hotel() {
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

}