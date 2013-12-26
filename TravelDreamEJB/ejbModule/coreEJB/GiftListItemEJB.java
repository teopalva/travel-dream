package coreEJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.GiftListItemDTO;
import exceptions.FieldNotPresentException;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidUserException;
import entity.User;
import entity.Package;

/**
 * Session Bean implementation class GiftListItemEJB
 */
@Stateless
public class GiftListItemEJB implements GiftListItemEJBLocal {
	
	@PersistenceContext
	EntityManager em;

    public GiftListItemEJB() {
    }
    
    public void saveGiftListItem(GiftListItemDTO giftListItemDTO) throws NotValidUserException, NotValidGiftListItemException {
    	try {
    		String mail = giftListItemDTO.getUser().getMail();
    		User user = em.find(User.class, mail);
    		Package _package = em.find(Package.class, giftListItemDTO.get_package().getId());
    		if(user == null)
    			throw new NotValidUserException();
    		if(_package == null)
    			throw new NotValidGiftListItemException();
    		user.getGiftPackages().add(_package);
    		em.merge(user);
    	}catch(NullPointerException e) {
    		throw new NotValidGiftListItemException();
    	}
    }
    
    public void removeGiftListItem(GiftListItemDTO giftListItemDTO) throws NotValidUserException, NotValidGiftListItemException{
    	try {
    		String mail = giftListItemDTO.getUser().getMail();
    		User user = em.find(User.class, mail);
    		Package _package = em.find(Package.class, giftListItemDTO.get_package().getId());
    		if(user == null)
    			throw new NotValidUserException();
    		if(_package == null)
    			throw new NotValidGiftListItemException();
    		if(!user.getGiftPackages().contains(_package))
    			throw new NotValidGiftListItemException();
    		user.getGiftPackages().remove(_package);
    		em.merge(user);
    	}catch(NullPointerException e) {
    		throw new NotValidGiftListItemException();
    	}
    }
}
