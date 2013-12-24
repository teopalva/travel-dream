package entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the POSSIBLE_DATE_PERSONALIZATION_FLIGHT database table.
 * 
 */
@Entity
@EntityListeners({PossibleDatePersonalizationFlightListener.class})
@Table(name="POSSIBLE_DATE_PERSONALIZATION_FLIGHT")
@NamedQuery(name="PossibleDatePersonalizationFlight.findAll", query="SELECT p FROM PossibleDatePersonalizationFlight p")
public class PossibleDatePersonalizationFlight implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PossibleDatePersonalizationFlightPK id;

	private BigDecimal price;

	//bi-directional many-to-one association to DatePersonalization
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="DatePersonalizationId", nullable=false)
	private DatePersonalization datePersonalization;

	//bi-directional many-to-one association to Flight
	@ManyToOne
	@JoinColumn(name="FlightId", nullable=false)
	private Flight flight;

	public PossibleDatePersonalizationFlight() {
	}

	public PossibleDatePersonalizationFlightPK getId() {
		return this.id;
	}

	public void setId(PossibleDatePersonalizationFlightPK id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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
	
}