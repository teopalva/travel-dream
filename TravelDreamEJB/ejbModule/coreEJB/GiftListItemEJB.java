package coreEJB;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.GiftListItemDTO;
import dto.PackageDTO;
import dto.UserDTO;
import entity.Package;
import entity.User;
import exceptions.FieldNotPresentException;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidPackageException;
import exceptions.NotValidUserException;

/**
 * Session Bean implementation class GiftListItemEJB
 */
@Stateless
public class GiftListItemEJB implements GiftListItemEJBLocal {
	
	@PersistenceContext
	EntityManager em;
	
	@EJB
	PackageEJB packageEJB;

    public GiftListItemEJB() {
    }
    
    public void saveGiftListItem(GiftListItemDTO giftListItemDTO) throws NotValidUserException, NotValidGiftListItemException {
    	try {
    		String mail = giftListItemDTO.getUser().getMail();
    		User user = em.find(User.class, mail);
    		Package _package = em.find(Package.class, giftListItemDTO.get_package().getId());
    		if(user == null)
    			throw new NotValidUserException();
    		if(_package == null) {
        		try {
    				packageEJB.savePackage(giftListItemDTO.get_package());
    				_package = em.find(Package.class, giftListItemDTO.get_package().getId());
    			} catch (NotValidPackageException e) {
    				e.printStackTrace();
    				throw new NotValidGiftListItemException();
    			}
        	}
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
    
    public void removeGiftListItem(PackageDTO packageDTO) throws NotValidPackageException {
    	List<User> users = em.createNativeQuery("SELECT * FROM USER U, GIFT_LIST_ITEM I WHERE U.Mail = I.UserIdGiftList AND I.PackageId = '"+packageDTO.getId()+"'", User.class).getResultList();
    	for(User user : users) {
    		List<Package> removePackages = new ArrayList<Package>();
    		for(Package p: user.getGiftPackages()) {
    			if(p.getId() == packageDTO.getId())
    				removePackages.add(p);
    			
    		}
    		user.getGiftPackages().removeAll(removePackages);
    		em.merge(user);
    	}
    }
    
    public List<GiftListItemDTO> getGiftListItem(UserDTO userDTO) throws NotValidUserException {
		List<GiftListItemDTO> list = null;
    	
		if(userDTO == null || userDTO.getMail() == null)
			throw new NotValidUserException();
		
    	//Extract from DB
    	User user = em.find(User.class, userDTO.getMail());
    	if(user == null)
    		throw new NotValidUserException();
    	
    	try {
			list = GiftListItemDTO.getGiftList(user);
		} catch (FieldNotPresentException e) {
			throw new NotValidUserException();
		}
    	
    	if(list == null)
    		throw new NotValidUserException();
    	
    	return list;
	}
}
