package coreEJB;

import javax.ejb.Local;

@Local
public interface AuthenticationEJBLocal {
	public boolean isTDE();
	public boolean isTDC();

}