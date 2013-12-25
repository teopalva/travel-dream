package coreEJB;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.BuyingListItemDTO;
import dto.GiftListItemDTO;
import dto.UserDTO;
import entity.BuyingListItem;
import entity.Group;
import entity.User;
import exceptions.FieldNotPresentException;

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
    
    public void saveUser(UserDTO userDTO) {
    	try {
	    	User user = new User();
	    	List<Group> groups = new ArrayList<Group>();
	    	Group group = new Group();
	    	if(!userDTO.getGroup().equals("TDC")) {
	    		System.err.println("User must be TDC!!");
	    		throw new Exception();
	    	}
	    	group.setId("TDC");
	    	groups.add(group);
	    	user.setFirstName(userDTO.getFirstName());
	    	user.setLastName(userDTO.getLastName());
	    	user.setMail(userDTO.getMail());
	    	user.setGroups(groups);
	    	
	    	//Calculate SHA-256 hash for the user
	    	user.setPassword(encryptPassword(userDTO.getPassword()));
	    	em.persist(user);
    	} catch(Exception e) {
    		e.printStackTrace();
    		throw new IllegalArgumentException();
    	}
    	
    }
    
    private static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-256");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
