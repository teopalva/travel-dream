package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import coreEJB.AuthenticationEJBLocal;
import coreEJB.BuyingListItemEJBLocal;
import coreEJB.GiftListItemEJBLocal;
import coreEJB.InvitationEJBLocal;
import coreEJB.PackageEJBLocal;
import dto.BuyingListItemDTO;
import dto.GiftListItemDTO;
import dto.InvitationDTO;
import dto.PackageDTO;
import dto.UserDTO;
import exceptions.JavaMailErrorException;
import exceptions.NotAuthenticatedException;
import exceptions.NotValidBuyingListException;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidInvitationException;
import exceptions.NotValidUserException;

@ManagedBean(name = "Checkout")
@ViewScoped
public class CheckoutBean {
	private List<String> emails;
	private int price;
	private UserDTO user;
	private PackageDTO selectedPackage;

	@EJB
	private PackageEJBLocal packageEJB;

	@EJB
	private AuthenticationEJBLocal authEJB;

	@EJB
	private InvitationEJBLocal invitationEJB;

	@EJB
	private BuyingListItemEJBLocal buyingListEJB;

	@EJB
	private GiftListItemEJBLocal giftListEJB;

	@ManagedProperty("#{SessionStorage}")
	private SessionStorageBean sessionStorage;

	@PostConstruct
	public void init() {
		try {
			user = authEJB.getAuthenticatedUser();
		} catch (NotAuthenticatedException e) {
			// No problem: user area
		}
		selectedPackage = sessionStorage.getSelectedPackage();
		int numPeople = selectedPackage.getNumPeople();
		emails = new ArrayList<String>(numPeople - 1);
		for (int i = 0; i < numPeople - 1; i++) {
			emails.add("");
		}
	}

	public SessionStorageBean getSessionStorage() {
		return sessionStorage;
	}

	public void setSessionStorage(SessionStorageBean sessionStorage) {
		this.sessionStorage = sessionStorage;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public void addMail(String mail) {
		this.emails.add(mail);
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setPrice(int p) {
		this.price = p;
	}

	public int getPrice() {
		return price;
	}

	/**
	 * Button "Acquista". Does all the needed checks.
	 * 
	 * @return the payment page URL
	 */
	public String showPayment() {
		if (sessionStorage.getPreviousPage().equals("edit")) {
			PackageDTO p = new PackageDTO(selectedPackage);
			boolean gifted = false;
			BuyingListItemDTO buyingItem = new BuyingListItemDTO(p, new Date(), gifted, false, user);
			try {
				buyingListEJB.saveBuyingListItem(buyingItem);
			} catch (NotValidBuyingListException e) {
				showAlert();
				e.printStackTrace();
				return null;
			}
			return ("/user/payment?faces-redirect=true");
		} else {
			if (sessionStorage.getPreviousPage().equals("gift_user") || sessionStorage.getPreviousPage().equals("gift_friend")) {
				PackageDTO p = new PackageDTO(selectedPackage);
				boolean gifted;
				if (sessionStorage.getPreviousPage().equals("gift_friend")) {
					gifted = true;
				} else {
					gifted = false;
				}
				try {
					BuyingListItemDTO buyingItemBought = new BuyingListItemDTO(p, new Date(), false, false, user);
					buyingListEJB.saveBuyingListItem(buyingItemBought);
					if(gifted && sessionStorage.getTmpUser()!=null) {
						BuyingListItemDTO buyingItemGifted = new BuyingListItemDTO(p, new Date(), gifted, false, sessionStorage.getTmpUser());	
						buyingListEJB.saveBuyingListItem(buyingItemGifted);
					}
				} catch (NotValidBuyingListException e) {
					showAlert();
					e.printStackTrace();
					return null;
				}
				try {
					PackageDTO _package = new PackageDTO(selectedPackage);
					_package.setId(selectedPackage.getId());
					//giftListEJB.removeGiftListItem(new GiftListItemDTO(_package, user));
					giftListEJB.removeGiftListItem(_package);
				} catch (Exception e) {
					e.printStackTrace();
					//No problem
				}
				return ("/user/payment?faces-redirect=true");
			} else if (sessionStorage.getPreviousPage().equals("invitation")) {
				PackageDTO p = new PackageDTO(selectedPackage);
				boolean gifted = false;
				BuyingListItemDTO buyingItem = new BuyingListItemDTO(p, new Date(), gifted, false, user);
				try {
					buyingListEJB.saveBuyingListItem(buyingItem);
				} catch (NotValidBuyingListException e) {
					showAlert();
					e.printStackTrace();
					return null;
				}
				return ("/user/payment?faces-redirect=true");
			} else
				return null;
		}
	}

	/**
	 * Button "Aggiungi a lista regali". Does all the needed checks.
	 * 
	 * @return the gift_list page URL
	 */
	public String showGiftList() {
		PackageDTO p = new PackageDTO(selectedPackage);
		GiftListItemDTO giftItem = new GiftListItemDTO(p, user);
		try {
			giftListEJB.saveGiftListItem(giftItem);
		} catch (NotValidUserException e) {
			showAlert();
			e.printStackTrace();
			return null;
		} catch (NotValidGiftListItemException e) {
			showAlert();
			e.printStackTrace();
			return null;
		}
		return ("/user/gift_list?faces-redirect=true");
	}

	/**
	 * Button "Invita i tuoi amici". Does all the needed checks.
	 * 
	 * @return the invitation_list page URL
	 */
	public String showInvitationList() {
		for (String email : emails) {
			if (email.equals("")) {
				FacesContext.getCurrentInstance().addMessage("alertMail",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Compilare tutti i campi", "Devi invitare un numero di amici pari a quello previsto dal pacchetto."));
				return null;
			}
			UserDTO invited = new UserDTO(email, null, null, null, null);
			PackageDTO p = new PackageDTO(selectedPackage);
			try {
				invitationEJB.sendInvitation(new InvitationDTO(user, invited, p, null, false));
			} catch (NotValidInvitationException | JavaMailErrorException e) {
				FacesContext.getCurrentInstance().addMessage("alertMail",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Al momento e' impossibile inviare tutte le mail di invito. Riprova."));
				e.printStackTrace();
				return null;
			}
		}
		return ("/user/invitation_list?faces-redirect=true");
	}

	/**
	 * 
	 * @return true if the "Add to giftList" section of the checkout is accessible to the user
	 */
	public boolean isGiftActive() {
		return sessionStorage.getPreviousPage().equals("edit") ? true : false;
	}

	/**
	 * 
	 * @return true if the "Invite your friends" section of the checkout is accessible to the user
	 */
	public boolean isInviteActive() {
		return sessionStorage.getPreviousPage().equals("edit") && selectedPackage.getNumPeople() > 1 ? true : false;
	}

	private void showAlert() {
		FacesContext.getCurrentInstance().addMessage("alert", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errore", "Qualcosa e' andato storto, riprova."));
	}

}
