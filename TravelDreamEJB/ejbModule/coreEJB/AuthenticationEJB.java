package coreEJB;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.UserDTO;
import entity.User;
import exceptions.NotAuthenticatedException;

/**
 * Session Bean implementation class AuthenticationEJB
 */
@Stateless
public class AuthenticationEJB implements AuthenticationEJBLocal {

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private EJBContext context;
	
    public AuthenticationEJB() {
    }
    
    public boolean isTDE() {
    	System.out.println("isTDE");
    	UserDTO user;
		try {
			user = this.getAuthenticatedUser();
		} catch (NotAuthenticatedException e) {
			return false;
		}
    	if(user == null)
    		return false;
    	if(user.getGroup().equals("TDE"))
    		return true;
    	return false;
    }
    
    public boolean isTDC() {
    	System.out.println("isTDC");
    	UserDTO user;
		try {
			user = this.getAuthenticatedUser();
		} catch (NotAuthenticatedException e) {
			return false;
		}
    	if(user == null)
    		return false;
    	if(user.getGroup().equals("TDC"))
    		return true;
    	return false;
    }
    
    private UserDTO getAuthenticatedUser() throws NotAuthenticatedException {
    	String mail = context.getCallerPrincipal().getName();
    	User user = null;
    	UserDTO userDTO = null;
    	try {
    		user = em.createQuery("SELECT u FROM User u JOIN FETCH u.groups WHERE u.mail=:mail", User.class).setParameter("mail", mail).getResultList().get(0);
    		userDTO = new UserDTO(user);
    	}catch(Exception e) {
    		System.out.println(e.toString());
    		user = null;
    	}
    	//User user = em.find(User.class, mail);
    	if(userDTO == null)
    		throw new NotAuthenticatedException();
    	return userDTO;
    }

}