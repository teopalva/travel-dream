package coreEJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.BuyingListItemDTO;
import dto.FieldNotPresentException;
import dto.GiftListItemDTO;
import dto.UserDTO;
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
    /*
    public List<BuyingListItemDTO> getBuyingList(UserDTO userDTO){
    	List<BuyingListItemDTO> list = new ArrayList<BuyingListItemDTO>
    }
    public List<GiftListItemDTO> getGiftList(UserDTO userDTO){
    	
    }
    public List<BuyingListItemDTO> getAllBuyingList(){
    	
    }
    */

}
