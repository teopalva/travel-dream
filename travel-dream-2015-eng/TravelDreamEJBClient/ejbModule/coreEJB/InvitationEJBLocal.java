package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.JavaMailErrorException;
import exceptions.NotValidInvitationException;
import exceptions.NotValidPackageException;
import exceptions.NotValidUserException;

@Local
public interface InvitationEJBLocal {
	 public void sendInvitation(InvitationDTO invitation) throws NotValidInvitationException, JavaMailErrorException;
	 public List<InvitationDTO> getInvitations(UserDTO userDTO) throws NotValidUserException;
	 public List<InvitationDTO> getInvitations(PackageDTO packageDTO) throws NotValidPackageException;
	 public InvitationDTO getInvitation(String hash);
	 public void acceptInvitation(String hash) throws NotValidInvitationException;
}
