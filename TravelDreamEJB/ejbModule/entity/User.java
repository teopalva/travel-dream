package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USER database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String mail;

	private String firstName;

	private String lastName;

	private String password;

	//bi-directional many-to-one association to BuyingList
	@OneToMany(mappedBy="user")
	private List<BuyingList> buyingLists;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="userInviter")
	private List<Invitation> invitationsInviter;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="userInvited")
	private List<Invitation> invitationsInvited;

	//bi-directional many-to-many association to Package
	@ManyToMany
	@JoinTable(
		name="GIFT_LIST"
		, joinColumns={
			@JoinColumn(name="UserIdGiftList")
			}
		, inverseJoinColumns={
			@JoinColumn(name="PackageId")
			}
		)
	private List<Package> packages;

	public User() {
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<BuyingList> getBuyingLists() {
		return this.buyingLists;
	}

	public void setBuyingLists(List<BuyingList> buyingLists) {
		this.buyingLists = buyingLists;
	}

	public BuyingList addBuyingList(BuyingList buyingList) {
		getBuyingLists().add(buyingList);
		buyingList.setUser(this);

		return buyingList;
	}

	public BuyingList removeBuyingList(BuyingList buyingList) {
		getBuyingLists().remove(buyingList);
		buyingList.setUser(null);

		return buyingList;
	}

	public List<Invitation> getInvitationsInviter() {
		return this.invitationsInviter;
	}

	public void setInvitationsInviter(List<Invitation> invitationsInviter) {
		this.invitationsInviter = invitationsInviter;
	}

	public Invitation addInvitationsInviter(Invitation invitationsInviter) {
		getInvitationsInviter().add(invitationsInviter);
		invitationsInviter.setUserInviter(this);

		return invitationsInviter;
	}

	public Invitation removeInvitationsInviter(Invitation invitationsInviter) {
		getInvitationsInviter().remove(invitationsInviter);
		invitationsInviter.setUserInviter(null);

		return invitationsInviter;
	}

	public List<Invitation> getInvitationsInvited() {
		return this.invitationsInvited;
	}

	public void setInvitationsInvited(List<Invitation> invitationsInvited) {
		this.invitationsInvited = invitationsInvited;
	}

	public Invitation addInvitationsInvited(Invitation invitationsInvited) {
		getInvitationsInvited().add(invitationsInvited);
		invitationsInvited.setUserInvited(this);

		return invitationsInvited;
	}

	public Invitation removeInvitationsInvited(Invitation invitationsInvited) {
		getInvitationsInvited().remove(invitationsInvited);
		invitationsInvited.setUserInvited(null);

		return invitationsInvited;
	}

	public List<Package> getPackages() {
		return this.packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

}