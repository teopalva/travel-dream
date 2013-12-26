package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.GiftListItemDTO;
import dto.PackageDTO;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidUserException;
import exceptions.NotValidPackageException;

@Local
public interface GiftListItemEJBLocal {
	public void saveGiftListItem(GiftListItemDTO giftListItemDTO) throws NotValidUserException, NotValidGiftListItemException;
    public void removeGiftListItem(GiftListItemDTO giftListItemDTO) throws NotValidUserException, NotValidGiftListItemException;
}
