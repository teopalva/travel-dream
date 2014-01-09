package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.BuyingListItemDTO;
import dto.GiftListItemDTO;
import dto.UserDTO;
import exceptions.NotPresentUserException;
import exceptions.NotValidUserException;

@Local
public interface UserEJBLocal {
	
    public UserDTO getUser(String mail) throws NotPresentUserException;
    public List<BuyingListItemDTO> getBuyingList(UserDTO userDTO) throws NotValidUserException;
    public List<GiftListItemDTO> getGiftList(UserDTO userDTO) throws NotValidUserException;
    public List<BuyingListItemDTO> getAllBuyingList();
    public void saveUser(UserDTO user);
}
