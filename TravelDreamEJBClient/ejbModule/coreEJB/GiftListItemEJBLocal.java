package coreEJB;

import javax.ejb.Local;

import dto.GiftListItemDTO;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidUserException;

@Local
public interface GiftListItemEJBLocal {
    public void saveGiftListItem(GiftListItemDTO giftListItemDTO) throws NotValidUserException, NotValidGiftListItemException;

    public void removeGiftListItem(GiftListItemDTO giftListItemDTO) throws NotValidUserException, NotValidGiftListItemException;
}
