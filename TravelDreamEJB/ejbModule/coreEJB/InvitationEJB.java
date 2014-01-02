package coreEJB;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import entity.Invitation;
import entity.Package;
import entity.User;
import exceptions.FieldNotPresentException;
import exceptions.NotValidInvitationException;
import exceptions.NotValidPackageException;
import exceptions.NotValidUserException;

/**
 * Session Bean implementation class InvitationEJB
 */
@Stateless
public class InvitationEJB implements InvitationEJBLocal {
	
	@Resource(name = "mail/info")
	private Session mailSession;
	
	@PersistenceContext
	EntityManager em;
	
	private static int MAX_TRY = 1000;

    public InvitationEJB() {
    }
    
    /**
     * Send the invitation and insert the record in database
     * Attention: is a blocking function!!! It takes time in order to send mail (it's a Gmail fault, not mine ;-) )
     */
    public void sendInvitation(InvitationDTO invitation) throws NotValidInvitationException {
        Message msg = new MimeMessage(mailSession);
        try {
        	//Insert in DB
        	User inviter = em.find(User.class, invitation.getInviter().getMail());
        	User invited = em.find(User.class, invitation.getInvited().getMail());
        	
        	Package _package = em.find(Package.class, invitation.get_package().getId());
        	
        	if(inviter == null || invitation.getInvited().getMail() == null || _package == null)
        		throw new NotValidInvitationException();
        	
        	if(invited == null) {
        		//Create TDF account
        		invited = new User();
        		invited.setMail(invitation.getInvited().getMail());
        		em.persist(invited);
        	}
        	
        	Invitation inv = new Invitation();
        	inv.setUserInvited(invited);
        	inv.setUserInviter(inviter);
        	inv.setHash(generateUniqueHash(invited.getMail()));
        	inv.setPackage(_package);
        	inv.setAccepted(false);
        	
        	em.persist(inv);
        	
        	//Send email
        	String name = inviter.getFirstName();
        	String lastName = inviter.getLastName();
        	msg.setSubject("Invito da parte di "+name+" "+lastName+" ad unirsi ad un pacchetto");
        	msg.setRecipient(RecipientType.TO,
        			new InternetAddress(invitation.getInvited().getMail(),
        					""));
        	msg.setText("Ciao,\n"
          			+ "Sei stato invitato da "+name+" "+lastName+" ad unirti ad un viaggio sul sito TravelDream\n"
          			+ "Conferma subito la tua presenza iscrivendoti al sito oppure loggandoti con username e password\n"
          			+ "Hask del link: "+inv.getHash());
        	Transport.send(msg);
        	System.out.println("Mail sent with success to address "+invited.getMail());
        }
        catch(MessagingException me) {
          // manage exception
        	me.printStackTrace();
        	System.err.println("MessagingException");
        }
        catch(UnsupportedEncodingException uee) {
        	uee.printStackTrace();
        	System.err.println("UnsupportedEncodingException");
        }
        catch(NullPointerException e) {
        	throw new NotValidInvitationException();
        }
      }
	 
    /**
     * Return all invitations for user
     */
	 public List<InvitationDTO> getInvitations(UserDTO userDTO) throws NotValidUserException {
		 List<InvitationDTO> list = null;
		 User user = em.find(User.class, userDTO.getMail());
		 list = InvitationDTO.getInvitations(user);
		 if(list == null)
			 throw new NotValidUserException();
		 return list;
	 }
	 
	 /**
	  * Return all invitations for a package
	  */
	 public List<InvitationDTO> getInvitations(PackageDTO packageDTO) throws NotValidPackageException {
		 List<InvitationDTO> list = new ArrayList<InvitationDTO>();
		 if(packageDTO.getId() < 0)
			 throw new NotValidPackageException();
		 List<Invitation> invitationList = em.createNativeQuery("SELECT * FROM INVITATION WHERE PackageId='"+packageDTO.getId()+"'", Invitation.class).getResultList();
		 
		 for(Invitation invitation : invitationList) {
			 try {
				list.add(new InvitationDTO(invitation));
			} catch (FieldNotPresentException e) {
				e.printStackTrace();
			}
		 }
		 return list;
	 }
	 
	 /**
	  * Get the invitation corresponding to hash
	  */
	 public InvitationDTO getInvitation(String hash) {
		 Invitation invitation = em.find(Invitation.class, hash);
		 if(invitation == null)
			 return null;
		 try {
			return new InvitationDTO(invitation);
		} catch (FieldNotPresentException e) {
			return null;
		}
	 }
	 
	 private String generateUniqueHash(String mail) {
		 int count = 0;
		 Invitation invitation = null;
		 String hash = null;
		 do {
			 Date date = new Date();	//assign current time
			 hash = encryptString(mail+date.toString());
			 invitation = em.find(Invitation.class, hash);
		 } while(count < MAX_TRY && invitation != null);
		 return hash;
	 }
	 
	 private static String encryptString(String password)
	 {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-256");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
	 }

     private static String byteToHex(final byte[] hash)
     {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
     }

}
