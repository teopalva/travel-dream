package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the INVITATION database table.
 * 
 */
@Entity
@NamedQuery(name="Invitation.findAll", query="SELECT i FROM Invitation i")
public class Invitation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String hash;

	//bi-directional many-to-one association to Package
	@ManyToOne
	@JoinColumn(name="PackageId")
	private Package _package;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="InviterUserId")
	private User userInviter;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="InvitedUserId")
	private User userInvited;

	public Invitation() {
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Package get_package() {
		return this._package;
	}

	public void set_package(Package _package) {
		this._package = _package;
	}

	public User getUserInviter() {
		return this.userInviter;
	}

	public void setUserInviter(User userInviter) {
		this.userInviter = userInviter;
	}

	public User getUserInvited() {
		return this.userInvited;
	}

	public void setUserInvited(User userInvited) {
		this.userInvited = userInvited;
	}

}