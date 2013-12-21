package dto;

import java.util.Date;

import entity.BuyingListItem;

public class BuyingListItemDTO {
	PackageDTO _package;
	Date date;
	boolean gifted;
	boolean paid;
	UserDTO user;
	public BuyingListItemDTO(BuyingListItem item) throws FieldNotPresentException {
		super();
		try {
			this._package = new PackageDTO(item.get_package());
			this.date = item.getDate();
			this.gifted = item.getGifted()!=0;
			this.paid = item.getPaid()!=0;
			this.user = new UserDTO(item.getUser());
		}catch (NullPointerException e) {
			throw new FieldNotPresentException();
		}
	}
	
	public BuyingListItemDTO(PackageDTO _package, Date date, boolean gifted,
			boolean paid, UserDTO user) {
		super();
		this._package = _package;
		this.date = date;
		this.gifted = gifted;
		this.paid = paid;
		this.user = user;
	}
	public PackageDTO get_package() {
		return _package;
	}
	public void set_package(PackageDTO _package) {
		this._package = _package;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isGifted() {
		return gifted;
	}
	public void setGifted(boolean gifted) {
		this.gifted = gifted;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	
	

}
