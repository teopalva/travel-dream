package coreEJB;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class PackageEJB {
	
	@PersistenceContext
	EntityManager em;

}
