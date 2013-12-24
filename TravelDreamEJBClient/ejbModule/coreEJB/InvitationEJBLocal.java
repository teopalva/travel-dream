package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.InvitationDTO;
import dto.UserDTO;
import exceptions.NotValidInvitationException;

@Local
public interface InvitationEJBLocal {
	 public void sendInvitation(InvitationDTO invitation) throws NotValidInvitationException;
	 public List<InvitationDTO> getAllInvitation(UserDTO user);
}
