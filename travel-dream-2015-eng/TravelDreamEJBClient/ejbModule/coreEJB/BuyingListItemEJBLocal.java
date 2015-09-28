package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.BuyingListItemDTO;
import dto.UserDTO;
import exceptions.NotValidBuyingListException;
import exceptions.NotValidUserException;

@Local
public interface BuyingListItemEJBLocal {
    public void saveBuyingListItem(BuyingListItemDTO buyingListItemDTO) throws NotValidBuyingListException;

    public void setPaid(BuyingListItemDTO itemDTO) throws NotValidBuyingListException;
    
    public void removeBuyingListItem(BuyingListItemDTO buyingListItemDTO) throws NotValidBuyingListException;

    public List<BuyingListItemDTO> getAllBuyingListItem();
    
    public List<BuyingListItemDTO> getBuyingListItem(UserDTO userDTO) throws NotValidUserException;

    public BuyingListItemDTO getTmpBuyingListItem();

    public void setTmpBuyingListItem(BuyingListItemDTO tmpBuyingListItem);

}
