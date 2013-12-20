package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PACKAGE database table.
 * 
 */
@Entity
@NamedQuery(name="Package.findAll", query="SELECT p FROM Package p")
public class Package implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	private int numPeople;

	private double reduction;

	//bi-directional many-to-one association to BuyingList
	@OneToMany(mappedBy="_package")
	private List<BuyingListItem> buyingListItems;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="_package")
	private List<Invitation> invitations;

	//bi-directional many-to-one association to Image
	@ManyToOne
	@JoinColumn(name="ImageId")
	private Image image;

	//bi-directional many-to-one association to PersonalizedProductExcursion
	@OneToMany(mappedBy="_package")
	private List<PersonalizedProductExcursion> personalizedProductExcursions;

	//bi-directional many-to-one association to PersonalizedProductFlight
	@OneToMany(mappedBy="_package")
	private List<PersonalizedProductFlight> personalizedProductFlights;

	//bi-directional many-to-one association to PersonalizedProductHotel
	@OneToMany(mappedBy="_package")
	private List<PersonalizedProductHotel> personalizedProductHotels;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="giftPackages")
	private List<User> users;

	public Package() {
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

	public int getNumPeople() {
		return this.numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public double getReduction() {
		return this.reduction;
	}

	public void setReduction(double reduction) {
		this.reduction = reduction;
	}

	public List<BuyingListItem> getBuyingListItems() {
		return this.buyingListItems;
	}

	public void setBuyingLists(List<BuyingListItem> buyingLists) {
		this.buyingListItems = buyingLists;
	}

	public BuyingListItem addBuyingListItem(BuyingListItem buyingList) {
		getBuyingListItems().add(buyingList);
		buyingList.set_package(this);

		return buyingList;
	}

	public BuyingListItem removeBuyingList(BuyingListItem buyingList) {
		getBuyingListItems().remove(buyingList);
		buyingList.set_package(null);

		return buyingList;
	}

	public List<Invitation> getInvitations() {
		return this.invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	public Invitation addInvitation(Invitation invitation) {
		getInvitations().add(invitation);
		invitation.setPackage(this);

		return invitation;
	}

	public Invitation removeInvitation(Invitation invitation) {
		getInvitations().remove(invitation);
		invitation.setPackage(null);

		return invitation;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<PersonalizedProductExcursion> getPersonalizedProductExcursions() {
		return this.personalizedProductExcursions;
	}

	public void setPersonalizedProductExcursions(List<PersonalizedProductExcursion> personalizedProductExcursions) {
		this.personalizedProductExcursions = personalizedProductExcursions;
	}

	public PersonalizedProductExcursion addPersonalizedProductExcursion(PersonalizedProductExcursion personalizedProductExcursion) {
		getPersonalizedProductExcursions().add(personalizedProductExcursion);
		personalizedProductExcursion.setPackage(this);

		return personalizedProductExcursion;
	}

	public PersonalizedProductExcursion removePersonalizedProductExcursion(PersonalizedProductExcursion personalizedProductExcursion) {
		getPersonalizedProductExcursions().remove(personalizedProductExcursion);
		personalizedProductExcursion.setPackage(null);

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
		personalizedProductFlight.setPackage(this);

		return personalizedProductFlight;
	}

	public PersonalizedProductFlight removePersonalizedProductFlight(PersonalizedProductFlight personalizedProductFlight) {
		getPersonalizedProductFlights().remove(personalizedProductFlight);
		personalizedProductFlight.setPackage(null);

		return personalizedProductFlight;
	}

	public List<PersonalizedProductHotel> getPersonalizedProductHotels() {
		return this.personalizedProductHotels;
	}

	public void setPersonalizedProductHotels(List<PersonalizedProductHotel> personalizedProductHotels) {
		this.personalizedProductHotels = personalizedProductHotels;
	}

	public PersonalizedProductHotel addPersonalizedProductHotel(PersonalizedProductHotel personalizedProductHotel) {
		getPersonalizedProductHotels().add(personalizedProductHotel);
		personalizedProductHotel.set_package(this);

		return personalizedProductHotel;
	}

	public PersonalizedProductHotel removePersonalizedProductHotel(PersonalizedProductHotel personalizedProductHotel) {
		getPersonalizedProductHotels().remove(personalizedProductHotel);
		personalizedProductHotel.set_package(null);

		return personalizedProductHotel;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}