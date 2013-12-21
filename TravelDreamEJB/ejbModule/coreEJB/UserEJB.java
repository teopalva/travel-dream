package coreEJB;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.BuyingListItemDTO;
import dto.FieldNotPresentException;
import dto.GiftListItemDTO;
import dto.UserDTO;
import entity.BuyingListItem;
import entity.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
public class UserEJB implements UserEJBLocal {
	
	@PersistenceContext
	EntityManager em;

    /**
     * Default constructor. 
     */
    public UserEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public UserDTO getUser(String mail) {
    	if(mail == null)
    		throw new IllegalArgumentException();
    	User user = em.find(User.class, mail);
    	try {
			return new UserDTO(user);
		} catch (FieldNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    	
    }
    
    public List<BuyingListItemDTO> getBuyingList(UserDTO userDTO){
    	List<BuyingListItemDTO> list = new ArrayList<BuyingListItemDTO>();
    	User user = em.find(User.class, userDTO.getMail());
    	if(user == null)
    		throw new IllegalArgumentException();
    	for(BuyingListItem item : user.getBuyingLists()) {
    		try {
				list.add(new BuyingListItemDTO(item));
			} catch (FieldNotPresentException e) {
				e.printStackTrace();
			}
    	}
    	return list;
    }
    public List<GiftListItemDTO> getGiftList(UserDTO userDTO){
    	List<GiftListItemDTO> list = new ArrayList<GiftListItemDTO>();
    	User user = em.find(User.class, userDTO.getMail());
    	if(user == null)
    		throw new IllegalArgumentException();
    	try {
			list = GiftListItemDTO.getGiftList(user);
		} catch (FieldNotPresentException e) {
			e.printStackTrace();
		}
    	return list;
    	
    }
    public List<BuyingListItemDTO> getAllBuyingList(){
    	List<BuyingListItemDTO> list = new ArrayList<BuyingListItemDTO>();
    	List<BuyingListItem> l =  em.createQuery("SELECT b FROM BuyingListItem b").getResultList();
    	for(BuyingListItem item : l) {
    		try {
				list.add(new BuyingListItemDTO(item));
			} catch (FieldNotPresentException e) {
				e.printStackTrace();
			}
    	}
    	return list;
    }

}
