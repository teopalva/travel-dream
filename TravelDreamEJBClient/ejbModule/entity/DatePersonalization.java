package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DATE_PERSONALIZATION database table.
 * 
 */
@Entity
@Table(name="DATE_PERSONALIZATION")
@NamedQuery(name="DatePersonalization.findAll", query="SELECT d FROM DatePersonalization d")
public class DatePersonalization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date date;

	private int duration;

	//bi-directional many-to-many association to Excursion
	@ManyToMany
	@JoinTable(
		name="POSSIBLE_DATE_PERSONALIZATION_EXCURSION"
		, joinColumns={
			@JoinColumn(name="DatePersonalizationId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ExcursionId")
			}
		)
	private List<Excursion> excursions;

	//bi-directional many-to-many association to Flight
	@ManyToMany
	@JoinTable(
		name="POSSIBLE_DATE_PERSONALIZATION_FLIGHT"
		, joinColumns={
			@JoinColumn(name="DatePersonalizationId")
			}
		, inverseJoinColumns={
			@JoinColumn(name="FlightId")
			}
		)
	private List<Flight> flights;

	//bi-directional many-to-one association to PersonalizedProductExcursion
	@OneToMany(mappedBy="datePersonalization")
	private List<PersonalizedProductExcursion> personalizedProductExcursions;

	//bi-directional many-to-one association to PersonalizedProductFlight
	@OneToMany(mappedBy="datePersonalization")
	private List<PersonalizedProductFlight> personalizedProductFlights;

	//bi-directional many-to-one association to PossibleDatePersonalizationExcursion
	@OneToMany(mappedBy="datePersonalization")
	private List<PossibleDatePersonalizationExcursion> possibleDatePersonalizationExcursions;

	//bi-directional many-to-one association to PossibleDatePersonalizationFlight
	@OneToMany(mappedBy="datePersonalization")
	private List<PossibleDatePersonalizationFlight> possibleDatePersonalizationFlights;

	public DatePersonalization() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDuration() {
		return this.duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<Excursion> getExcursions() {
		return this.excursions;
	}

	public void setExcursions(List<Excursion> excursions) {
		this.excursions = excursions;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public List<PersonalizedProductExcursion> getPersonalizedProductExcursions() {
		return this.personalizedProductExcursions;
	}

	public void setPersonalizedProductExcursions(List<PersonalizedProductExcursion> personalizedProductExcursions) {
		this.personalizedProductExcursions = personalizedProductExcursions;
	}

	public PersonalizedProductExcursion addPersonalizedProductExcursion(PersonalizedProductExcursion personalizedProductExcursion) {
		getPersonalizedProductExcursions().add(personalizedProductExcursion);
		personalizedProductExcursion.setDatePersonalization(this);

		return personalizedProductExcursion;
	}

	public PersonalizedProductExcursion removePersonalizedProductExcursion(PersonalizedProductExcursion personalizedProductExcursion) {
		getPersonalizedProductExcursions().remove(personalizedProductExcursion);
		personalizedProductExcursion.setDatePersonalization(null);

		return personalizedProductExcursion;
	}

	public List<PersonalizedProductFlight> getPersonalizedProductFlights() {
		return this.personalizedProductFlights;
	}

	public void setPersonalizedProductFlights(List<PersonalizedProductFlight> personalizedProductFlights) {
		this.personalizedProductFlights = personalizedProductFlights;
	}

	public PersonalizedProductFlight addPersonalizedProductFlight(PersonalizedProductFlight personalizedProductFlight) {
		getPersonalizedProductFlights().add(personalizedProductFlight);
		personalizedProductFlight.setDatePersonalization(this);

		return personalizedProductFlight;
	}

	public PersonalizedProductFlight removePersonalizedProductFlight(PersonalizedProductFlight personalizedProductFlight) {
		getPersonalizedProductFlights().remove(personalizedProductFlight);
		personalizedProductFlight.setDatePersonalization(null);

		return personalizedProductFlight;
	}

	public List<PossibleDatePersonalizationExcursion> getPossibleDatePersonalizationExcursions() {
		return this.possibleDatePersonalizationExcursions;
	}

	public void setPossibleDatePersonalizationExcursions(List<PossibleDatePersonalizationExcursion> possibleDatePersonalizationExcursions) {
		this.possibleDatePersonalizationExcursions = possibleDatePersonalizationExcursions;
	}

	public PossibleDatePersonalizationExcursion addPossibleDatePersonalizationExcursion(PossibleDatePersonalizationExcursion possibleDatePersonalizationExcursion) {
		getPossibleDatePersonalizationExcursions().add(possibleDatePersonalizationExcursion);
		possibleDatePersonalizationExcursion.setDatePersonalization(this);

		return possibleDatePersonalizationExcursion;
	}

	public PossibleDatePersonalizationExcursion removePossibleDatePersonalizationExcursion(PossibleDatePersonalizationExcursion possibleDatePersonalizationExcursion) {
		getPossibleDatePersonalizationExcursions().remove(possibleDatePersonalizationExcursion);
		possibleDatePersonalizationExcursion.setDatePersonalization(null);

		return possibleDatePersonalizationExcursion;
	}

	public List<PossibleDatePersonalizationFlight> getPossibleDatePersonalizationFlights() {
		return this.possibleDatePersonalizationFlights;
	}

	public void setPossibleDatePersonalizationFlights(List<PossibleDatePersonalizationFlight> possibleDatePersonalizationFlights) {
		this.possibleDatePersonalizationFlights = possibleDatePersonalizationFlights;
	}

	public PossibleDatePersonalizationFlight addPossibleDatePersonalizationFlight(PossibleDatePersonalizationFlight possibleDatePersonalizationFlight) {
		getPossibleDatePersonalizationFlights().add(possibleDatePersonalizationFlight);
		possibleDatePersonalizationFlight.setDatePersonalization(this);

		return possibleDatePersonalizationFlight;
	}

	public PossibleDatePersonalizationFlight removePossibleDatePersonalizationFlight(PossibleDatePersonalizationFlight possibleDatePersonalizationFlight) {
		getPossibleDatePersonalizationFlights().remove(possibleDatePersonalizationFlight);
		possibleDatePersonalizationFlight.setDatePersonalization(null);

		return possibleDatePersonalizationFlight;
	}

}