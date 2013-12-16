package coreEJB;

import javax.ejb.Local;

import DTO.UserDTO;

@Local
public interface UserEJBLocal {
	public boolean authenticateUser(UserDTO user);
}
