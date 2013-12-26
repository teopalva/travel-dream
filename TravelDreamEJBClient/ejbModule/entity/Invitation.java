package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the INVITATION database table.
 * 
 */
@Entity
@NamedQuery(name="Invitation.findAll", query="SELECT i FROM Invitation i")
public class Invitation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String hash;

	private byte accepted;

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

	public boolean getAccepted() {
		return this.accepted != 0;
	}

	public void setAccepted(boolean accepted) {
		if(accepted)
			this.accepted = new Byte("1");
		else
			this.accepted = new Byte("0");
	}

	public Package getPackage() {
		return this._package;
	}

	public User getUserInviter() {
		return userInviter;
	}

	public void setUserInviter(User userInviter) {
		this.userInviter = userInviter;
	}

	public User getUserInvited() {
		return userInvited;
	}

	public void setUserInvited(User userInvited) {
		this.userInvited = userInvited;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

}