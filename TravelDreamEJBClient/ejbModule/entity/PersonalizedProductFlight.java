package entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the PERSONALIZED_PRODUCT_FLIGHT database table.
 * 
 */
@Entity
@Table(name="PERSONALIZED_PRODUCT_FLIGHT")
@NamedQuery(name="PersonalizedProductFlight.findAll", query="SELECT p FROM PersonalizedProductFlight p")
public class PersonalizedProductFlight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to ClassPersonalization
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="ClassPersonalization")
	private ClassPersonalization classPersonalization;

	//bi-directional many-to-one association to DatePersonalization
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="DatePersonalization")
	private DatePersonalization datePersonalization;

	//bi-directional many-to-one association to Flight
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="FlightId")
	private Flight flight;

	//bi-directional many-to-one association to Package
	@ManyToOne
	@JoinColumn(name="PackageId")
	private Package _package;

	public PersonalizedProductFlight() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ClassPersonalization getClassPersonalization() {
		return this.classPersonalization;
	}

	public void setClassPersonalization(ClassPersonalization classPersonalization) {
		this.classPersonalization = classPersonalization;
	}

	public DatePersonalization getDatePersonalization() {
		return this.datePersonalization;
	}

	public void setDatePersonalization(DatePersonalization datePersonalization) {
		this.datePersonalization = datePersonalization;
	}

	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Package getPackage() {
		return this._package;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

}