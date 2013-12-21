package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.BuyingListItemDTO;
import dto.GiftListItemDTO;
import dto.UserDTO;

@Local
public interface UserEJBLocal {
	
    public UserDTO getUser(String mail);
    public List<BuyingListItemDTO> getBuyingList(UserDTO userDTO);
    public List<GiftListItemDTO> getGiftList(UserDTO userDTO);
    public List<BuyingListItemDTO> getAllBuyingList();
}
