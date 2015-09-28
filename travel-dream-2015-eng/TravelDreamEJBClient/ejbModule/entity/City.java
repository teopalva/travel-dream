package entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the CITY database table.
 * 
 */
@Entity
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	
	private String country;

	//bi-directional many-to-one association to Airport
	@OneToMany(mappedBy="city")
	private List<Airport> airports;
	
	//bi-directional many-to-one association to Excursion
	@OneToMany(mappedBy="city")
	private List<Excursion> excursions;

	//bi-directional many-to-one association to Hotel
	@OneToMany(mappedBy="city")
	private List<Hotel> hotels;

	public City() {
	}
	
	public City(String name, String country) {
		this.name = name;
		this.country = country;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Airport> getAirports() {
		return this.airports;
	}

	public void setAirports(List<Airport> airports) {
		this.airports = airports;
	}

	public Airport addAirport(Airport airport) {
		getAirports().add(airport);
		airport.setCity(this);

		return airport;
	}

	public Airport removeAirport(Airport airport) {
		getAirports().remove(airport);
		airport.setCity(null);

		return airport;
	}

}