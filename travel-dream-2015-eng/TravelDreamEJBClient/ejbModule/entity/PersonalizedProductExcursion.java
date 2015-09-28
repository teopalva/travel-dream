package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the PERSONALIZED_PRODUCT_EXCURSION database table.
 * 
 */
@Entity
@Table(name="PERSONALIZED_PRODUCT_EXCURSION")
@NamedQuery(name="PersonalizedProductExcursion.findAll", query="SELECT p FROM PersonalizedProductExcursion p")
public class PersonalizedProductExcursion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to DatePersonalization
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="DatePersonalization")
	private DatePersonalization datePersonalization;

	//bi-directional many-to-one association to Excursion
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="ExcursionId")
	private Excursion excursion;

	//bi-directional many-to-one association to Package
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PackageId")
	private Package _package;

	public PersonalizedProductExcursion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DatePersonalization getDatePersonalization() {
		return this.datePersonalization;
	}

	public void setDatePersonalization(DatePersonalization datePersonalization) {
		this.datePersonalization = datePersonalization;
	}

	public Excursion getExcursion() {
		return this.excursion;
	}

	public void setExcursion(Excursion excursion) {
		this.excursion = excursion;
	}

	public Package getPackage() {
		return this._package;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

}