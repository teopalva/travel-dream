package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


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
	private List<Invitation> invites;

	//bi-directional many-to-one association to Invitation
	@OneToMany(mappedBy="userInvited")
	private List<Invitation> inviteds;

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

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="USER_GROUP"
		, joinColumns={
			@JoinColumn(name="UserMail")
			}
		, inverseJoinColumns={
			@JoinColumn(name="GroupId")
			}
		)
	private List<Group> groups;

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

	public List<Invitation> getInvites() {
		return this.invites;
	}

	public void setInvites(List<Invitation> invites) {
		this.invites = invites;
	}

	public Invitation addInvite(Invitation invite) {
		getInvites().add(invite);
		invite.setUserInviter(this);

		return invite;
	}

	public Invitation removeInvite(Invitation invite) {
		getInvites().remove(invite);
		invite.setUserInviter(null);

		return invite;
	}

	public List<Invitation> getInviteds() {
		return this.inviteds;
	}

	public void setInviteds(List<Invitation> inviteds) {
		this.inviteds = inviteds;
	}

	public Invitation addInvited(Invitation invited) {
		getInviteds().add(invited);
		invited.setUserInvited(this);

		return invited;
	}

	public Invitation removeInvited(Invitation invited) {
		getInviteds().remove(invited);
		invited.setUserInvited(null);

		return invited;
	}

	public List<Package> getPackages() {
		return this.packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}