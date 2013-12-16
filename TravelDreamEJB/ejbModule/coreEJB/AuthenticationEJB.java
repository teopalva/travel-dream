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
    	User user = this.getAuthenticatedUser();
    	if(user.getGroups().contains("TDE"))
    		return true;
    	return false;
    }
    
    public boolean isTDC() {
    	User user = this.getAuthenticatedUser();
    	if(user.getGroups().contains("TDC"))
    		return true;
    	return false;
    }
    
    private User getAuthenticatedUser() {
    	String mail = context.getCallerPrincipal().getName();
    	User user = em.find(User.class, mail);
    	return user;
    }

}
