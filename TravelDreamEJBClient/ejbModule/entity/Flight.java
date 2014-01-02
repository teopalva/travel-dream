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
 * The persistent class for the FLIGHT database table.
 * 
 */
@Entity
@NamedQuery(name="Flight.findAll", query="SELECT f FROM Flight f")
public class Flight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	//bi-directional many-to-many association to ClassPersonalization
	@ManyToMany(mappedBy="flights", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ClassPersonalization> classPersonalizations;

	//bi-directional many-to-many association to DatePersonalization
	@ManyToMany(mappedBy="flights", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<DatePersonalization> datePersonalizations;

	//bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name="Arrival")
	private Airport airportArrival;

	//bi-directional many-to-one association to Airport
	@ManyToOne
	@JoinColumn(name="Departure")
	private Airport airportDeparture;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="Company")
	private Company company;

	//bi-directional many-to-one association to PersonalizedProductFlight
	@OneToMany(mappedBy="flight")
	private List<PersonalizedProductFlight> personalizedProductFlights;

	//bi-directional many-to-one association to PossibleClassPersonalizationFlight
	@OneToMany(mappedBy="flight", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<PossibleClassPersonalizationFlight> possibleClassPersonalizationFlights;

	//bi-directional many-to-one association to PossibleDatePersonalizationFlight
	@OneToMany(mappedBy="flight", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<PossibleDatePersonalizationFlight> possibleDatePersonalizationFlights;

	public Flight() {
		classPersonalizations = new ArrayList<ClassPersonalization>();
		datePersonalizations = new ArrayList<DatePersonalization>();
		personalizedProductFlights = new ArrayList<PersonalizedProductFlight>();
		possibleClassPersonalizationFlights = new ArrayList<PossibleClassPersonalizationFlight>();
		possibleDatePersonalizationFlights = new ArrayList<PossibleDatePersonalizationFlight>();
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

	public List<DatePersonalization> getDatePersonalizations() {
		return this.datePersonalizations;
	}

	public void setDatePersonalizations(List<DatePersonalization> datePersonalizations) {
		this.datePersonalizations = datePersonalizations;
	}

	public Airport getAirportArrival() {
		return this.airportArrival;
	}

	public void setAirportArrival(Airport airportArrival) {
		this.airportArrival = airportArrival;
	}

	public Airport getAirportDeparture() {
		return this.airportDeparture;
	}

	public void setAirportDeparture(Airport airportDeparture) {
		this.airportDeparture = airportDeparture;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<PersonalizedProductFlight> getPersonalizedProductFlights() {
		return this.personalizedProductFlights;
	}

	public void setPersonalizedProductFlights(List<PersonalizedProductFlight> personalizedProductFlights) {
		this.personalizedProductFlights = personalizedProductFlights;
	}

	public PersonalizedProductFlight addPersonalizedProductFlight(PersonalizedProductFlight personalizedProductFlight) {
		getPersonalizedProductFlights().add(personalizedProductFlight);
		personalizedProductFlight.setFlight(this);

		return personalizedProductFlight;
	}

	public PersonalizedProductFlight removePersonalizedProductFlight(PersonalizedProductFlight personalizedProductFlight) {
		getPersonalizedProductFlights().remove(personalizedProductFlight);
		personalizedProductFlight.setFlight(null);

		return personalizedProductFlight;
	}

	public List<PossibleClassPersonalizationFlight> getPossibleClassPersonalizationFlights() {
		return this.possibleClassPersonalizationFlights;
	}

	public void setPossibleClassPersonalizationFlights(List<PossibleClassPersonalizationFlight> possibleClassPersonalizationFlights) {
		this.possibleClassPersonalizationFlights = possibleClassPersonalizationFlights;
	}

	public PossibleClassPersonalizationFlight addPossibleClassPersonalizationFlight(PossibleClassPersonalizationFlight possibleClassPersonalizationFlight) {
		getPossibleClassPersonalizationFlights().add(possibleClassPersonalizationFlight);
		possibleClassPersonalizationFlight.setFlight(this);

		return possibleClassPersonalizationFlight;
	}

	public PossibleClassPersonalizationFlight removePossibleClassPersonalizationFlight(PossibleClassPersonalizationFlight possibleClassPersonalizationFlight) {
		getPossibleClassPersonalizationFlights().remove(possibleClassPersonalizationFlight);
		possibleClassPersonalizationFlight.setFlight(null);

		return possibleClassPersonalizationFlight;
	}

	public List<PossibleDatePersonalizationFlight> getPossibleDatePersonalizationFlights() {
		return this.possibleDatePersonalizationFlights;
	}

	public void setPossibleDatePersonalizationFlights(List<PossibleDatePersonalizationFlight> possibleDatePersonalizationFlights) {
		this.possibleDatePersonalizationFlights = possibleDatePersonalizationFlights;
	}

	public PossibleDatePersonalizationFlight addPossibleDatePersonalizationFlight(PossibleDatePersonalizationFlight possibleDatePersonalizationFlight) {
		getPossibleDatePersonalizationFlights().add(possibleDatePersonalizationFlight);
		possibleDatePersonalizationFlight.setFlight(this);

		return possibleDatePersonalizationFlight;
	}

	public PossibleDatePersonalizationFlight removePossibleDatePersonalizationFlight(PossibleDatePersonalizationFlight possibleDatePersonalizationFlight) {
		getPossibleDatePersonalizationFlights().remove(possibleDatePersonalizationFlight);
		possibleDatePersonalizationFlight.setFlight(null);

		return possibleDatePersonalizationFlight;
	}

}