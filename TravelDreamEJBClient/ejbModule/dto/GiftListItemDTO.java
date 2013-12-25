package dto;

import java.util.ArrayList;
import java.util.List;

import entity.User;
import entity.Package;
import exceptions.FieldNotPresentException;

public class GiftListItemDTO {
	PackageDTO _package;
	UserDTO user;
	public static List<GiftListItemDTO> getGiftList(User user) throws FieldNotPresentException {
		List<GiftListItemDTO> list = new ArrayList<GiftListItemDTO>();
		try {
			for(Package _package : user.getGiftPackages()) {
				PackageDTO packageDTO = new PackageDTO(_package);
				list.add(new GiftListItemDTO(packageDTO, new UserDTO(user)));
			}
		} catch(NullPointerException e) {
			throw new FieldNotPresentException();
		}
		return list;
	}
	public GiftListItemDTO(PackageDTO _package, UserDTO user) {
		super();
		this._package = _package;
		this.user = user;
	}
	public PackageDTO get_package() {
		return _package;
	}
	public void set_package(PackageDTO _package) {
		this._package = _package;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "GiftListItemDTO [_package=" + _package + ", user=" + user + "]";
	}
}
