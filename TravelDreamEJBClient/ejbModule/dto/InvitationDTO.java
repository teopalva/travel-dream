package dto;

public class InvitationDTO {
	UserDTO inviter;
	UserDTO invited;
	PackageDTO _package;
	String hash;
	boolean accepted;
	
	public InvitationDTO() {
		hash = null;
		inviter = null;
		invited = null;
	}
	
	public InvitationDTO(UserDTO inviter, UserDTO invited, PackageDTO _package,
			String hash, boolean accepted) {
		super();
		this.inviter = inviter;
		this.invited = invited;
		this._package = _package;
		this.hash = hash;
		this.accepted = accepted;
	}

	public UserDTO getInviter() {
		return inviter;
	}

	public void setInviter(UserDTO inviter) {
		this.inviter = inviter;
	}

	public UserDTO getInvited() {
		return invited;
	}

	public void setInvited(UserDTO invited) {
		this.invited = invited;
	}

	public PackageDTO get_package() {
		return _package;
	}

	public void set_package(PackageDTO _package) {
		this._package = _package;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public String toString() {
		return "InvitationDTO [inviter=" + inviter + ", invited=" + invited
				+ ", _package=" + _package + ", hash=" + hash + ", accepted="
				+ accepted + "]";
	}
}
