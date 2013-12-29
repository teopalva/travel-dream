package coreEJB;

import javax.ejb.Local;

import dto.UserDTO;
import exceptions.NotAuthenticatedException;

@Local
public interface AuthenticationEJBLocal {
    public boolean isTDE();

    public boolean isTDC();

    public UserDTO getAuthenticatedUser() throws NotAuthenticatedException;

}
