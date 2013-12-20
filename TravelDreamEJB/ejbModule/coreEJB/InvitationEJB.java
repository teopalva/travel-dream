package coreEJB;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InvitationEJB {
	
	@PersistenceContext
	EntityManager em;

}
