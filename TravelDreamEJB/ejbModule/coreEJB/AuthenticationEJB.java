package coreEJB;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.User;

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
        // TODO Auto-generated constructor stub
    }
    
    public boolean isTDE() {
    	System.out.println("isTDE");
    	User user = this.getAuthenticatedUser();
    	if(user == null)
    		return false;
    	if(user.getGroups().get(0).getId().equals("TDE"))
    		return true;
    	return false;
    }
    
    public boolean isTDC() {
    	System.out.println("isTDC");
    	User user = this.getAuthenticatedUser();
    	if(user == null)
    		return false;
    	if(user.getGroups().get(0).getId().equals("TDC"))
    		return true;
    	return false;
    }
    
    private User getAuthenticatedUser() {
    	String mail = context.getCallerPrincipal().getName();
    	User user = null;
    	try {
    		user = em.createQuery("SELECT u FROM User u JOIN FETCH u.groups WHERE u.mail=:mail", User.class).setParameter("mail", mail).getResultList().get(0);
    	}catch(Exception e) {
    		System.out.println(e.toString());
    		user = null;
    	}
    	//User user = em.find(User.class, mail);
    	return user;
    }

}
