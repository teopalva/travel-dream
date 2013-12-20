package coreEJB;

import javax.ejb.Local;

import dto.UserDTO;

@Local
public interface UserEJBLocal {
	public boolean authenticateUser(UserDTO user);
}
