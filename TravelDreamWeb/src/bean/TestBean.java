package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import coreEJB.BaseProductEJBLocal;
import coreEJB.BuyingListItemEJBLocal;
import coreEJB.GiftListItemEJBLocal;
import coreEJB.InvitationEJBLocal;
import coreEJB.PackageEJBLocal;
import coreEJB.UserEJBLocal;
import dto.BaseProductDTO;
import dto.BuyingListItemDTO;
import dto.CityDTO;
import dto.ClassPersonalizationDTO;
import dto.DatePersonalizationDTO;
import dto.ExcursionDTO;
import dto.FlightDTO;
import dto.GiftListItemDTO;
import dto.HotelDTO;
import dto.InvitationDTO;
import dto.PackageDTO;
import dto.PersonalizedExcursionDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedHotelDTO;
import dto.PersonalizedProductDTO;
import dto.UserDTO;
import exceptions.NotValidBaseProductException;
import exceptions.NotValidBuyingListException;
import exceptions.NotValidGiftListItemException;
import exceptions.NotValidInvitationException;
import exceptions.NotValidPackageException;
import exceptions.NotValidUserException;

@ManagedBean(name="Test")
@RequestScoped
public class TestBean {
	
	@EJB
	UserEJBLocal userEJB;
	
	@EJB
	PackageEJBLocal packageEJB;
	
	@EJB
	BaseProductEJBLocal baseProductEJB;
	
	@EJB
	InvitationEJBLocal invitationEJB;
	
	@EJB
	BuyingListItemEJBLocal buyingListEJB;
	
	@EJB
	GiftListItemEJBLocal giftListEJB;
	
	public void testUserEJB() throws NotValidUserException {
		UserDTO user = userEJB.getUser("gianluca.91@gmail.com");
		System.out.println(user.getGroup());
		List<BuyingListItemDTO> list = userEJB.getBuyingList(user);
		System.out.println(list.size());
		List<GiftListItemDTO> list2 = userEJB.getGiftList(user);
		System.out.println(list2.size());
		list = userEJB.getAllBuyingList();
		System.out.println(list.size());
		UserDTO user2 = new UserDTO("utente@gmail.com", "utente", "Paolo", "Rossi", "TDC");
		userEJB.saveUser(user2);
	}
	
	public void testPackageEJB() {
		List<PackageDTO> list = packageEJB.getOfferingPackages();
		System.out.print(list.size());
		PackageDTO _package = new PackageDTO();
		_package.setName("Offerta test");
		_package.setNumPeople(2);
		_package.setReduction(0.1);
		List<PersonalizedProductDTO> pp = new ArrayList<PersonalizedProductDTO>();
		FlightDTO flight = new FlightDTO("Volo di test", "Alitalia", "LIN", "LIN", null, null, null);
		flight.setId(2);
		PersonalizedFlightDTO pFlight = new PersonalizedFlightDTO();
		pFlight.setFlight(flight);
		//pFlight.setDatePersonalization(datePersonalization);
		pp.add(pFlight);
		_package.setPersonalizedProducts(pp);
		try {
			packageEJB.savePackage(_package);
		} catch (NotValidPackageException e) {
			e.printStackTrace();
		}
		try {
			packageEJB.removePackage(list.get(0));
		} catch (NotValidPackageException e) {
			e.printStackTrace();
		}
	}
	
	public void testBaseProductEJB() {
		FlightDTO flight = new FlightDTO("Volo di test 2","Alitalia","MXP","MXP",null,null,null);
		ClassPersonalizationDTO cp = new ClassPersonalizationDTO("Business con vodka");
		flight.getPossibleClassPersonalizations().add(cp);
		flight.getPrices().put(cp, new Double(10));
		DatePersonalizationDTO dp = new DatePersonalizationDTO(100, new Date(114,0,13));
		dp.setId(36);
		flight.getPossibleDatePersonalizations().add(dp);
		flight.getPrices().put(dp, new Double(20));
		try {
			baseProductEJB.saveBaseProduct(flight);
		} catch (NotValidBaseProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HotelDTO hotel = new HotelDTO("Hilton London","Hilton",5,null,new CityDTO("London","England"),null);
		ClassPersonalizationDTO cp2 = new ClassPersonalizationDTO("Camera di lusso");
		hotel.addPersonalization(cp2, 30.2);
		try {
			baseProductEJB.saveBaseProduct(hotel);
		} catch (NotValidBaseProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExcursionDTO excursion = new ExcursionDTO("Escursione di test","Alitalia",null,new CityDTO("Mantova","IT"),null);
		DatePersonalizationDTO dp2 = new DatePersonalizationDTO(100, new Date(114,0,15));
		excursion.addPersonalization(dp2, 50);
		try {
			baseProductEJB.saveBaseProduct(excursion);
		} catch (NotValidBaseProductException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void testDeleteBaseProductEJB() {
		//Delete all base products
			List<BaseProductDTO> baseProducts = baseProductEJB.getAllBaseProducts();
			for(BaseProductDTO b : baseProducts) {
				try {
					baseProductEJB.removeBaseProduct(b);
				} catch (NotValidBaseProductException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	public void testInvitationEJB() {
		InvitationDTO invitation = new InvitationDTO();
		UserDTO user = new UserDTO();
		UserDTO inviter = new UserDTO();
		PackageDTO _package = new PackageDTO();
		_package.setId(2);
		inviter.setMail("gianluca.91@gmail.com");
		user.setMail("gianluca.91@gmail.com");
		invitation.setInvited(user);
		invitation.setInviter(inviter);
		invitation.set_package(_package);
		try {
			invitationEJB.sendInvitation(invitation);
		} catch (NotValidInvitationException e) {
			e.printStackTrace();
		}
	}
	
	public void testBuyingListEJB() {
		UserDTO user = new UserDTO();
		user.setMail("gianluca.91@gmail.com");
		PackageDTO _package = packageEJB.getOfferingPackages().get(0);
		BuyingListItemDTO item = new BuyingListItemDTO(_package, new Date(115,1,1), false, false, user);
		try {
			buyingListEJB.saveBuyingListItem(item);
		} catch (NotValidBuyingListException e) {
			e.printStackTrace();
		}
		
		item.setPaid(true);
		item.setGifted(true);
		
		try {
			buyingListEJB.saveBuyingListItem(item);
		} catch (NotValidBuyingListException e) {
			e.printStackTrace();
		}
	}
	
	public void testDeleteBuyingListEJB() {
		UserDTO user = userEJB.getUser("gianluca.91@gmail.com");
		PackageDTO _package = null;
		try {
			_package = buyingListEJB.getBuyingListItem(user).get(0).get_package();
		} catch (NotValidUserException e1) {
			e1.printStackTrace();
		}
		BuyingListItemDTO item = new BuyingListItemDTO(_package, new Date(115,1,1), false, false, user);
		try {
			buyingListEJB.removeBuyingListItem(item);
		} catch (NotValidBuyingListException e) {
			e.printStackTrace();
		}
	}
	
	public void testPrice() {
		List<PackageDTO> list = packageEJB.getOfferingPackages();
		System.out.print(list.size());
		PackageDTO _package = new PackageDTO();
		_package.setName("Offerta test");
		_package.setNumPeople(2);
		_package.setReduction(0.1);
		List<PersonalizedProductDTO> pp = new ArrayList<PersonalizedProductDTO>();
		List<BaseProductDTO> baseProducts = baseProductEJB.getAllBaseProducts();
		for(BaseProductDTO b : baseProducts) {
			if(b instanceof HotelDTO) {
				PersonalizedHotelDTO ph = new PersonalizedHotelDTO((HotelDTO) b);
				ph.setClassPersonalization(ph.getHotel().getPossibleClassPersonalizations().get(0));
				pp.add(ph);
				
			}
			if(b instanceof FlightDTO) {
				PersonalizedFlightDTO pf = new PersonalizedFlightDTO((FlightDTO) b);
				pf.setClassPersonalization(pf.getFlight().getPossibleClassPersonalizations().get(0));
				pp.add(pf);
			}
			if(b instanceof ExcursionDTO) {
				PersonalizedExcursionDTO pe = new PersonalizedExcursionDTO((ExcursionDTO) b);
				pe.setDatePersonalization(pe.getExcursion().getPossibleDatePersonalizations().get(0));
				pp.add(pe);
			}
		}
		_package.setPersonalizedProducts(pp);
		System.out.println("Prezzo pacchetto: "+_package.getPrice());
	}
	
	public void testGiftListEJB() {
		UserDTO user = userEJB.getUser("gianluca.91@gmail.com");
		PackageDTO _package = packageEJB.getOfferingPackages().get(0);
		GiftListItemDTO item = new GiftListItemDTO(_package, user);
		try {
			giftListEJB.saveGiftListItem(item);
		} catch (NotValidUserException | NotValidGiftListItemException e) {
			e.printStackTrace();
		}
		try {
			giftListEJB.removeGiftListItem(item);
		} catch (NotValidUserException | NotValidGiftListItemException e) {
			e.printStackTrace();
		}
	}
	
	public void testBuyPackageDeepCopy() {
		PackageDTO _package = packageEJB.getOfferingPackages().get(0);
		UserDTO user = userEJB.getUser("gianluca.91@gmail.com");
		PackageDTO boughtPackage = new PackageDTO(_package);
		BuyingListItemDTO item = new BuyingListItemDTO(boughtPackage,new Date(114,1,1),false,false,user);
		try {
			buyingListEJB.saveBuyingListItem(item);
		} catch (NotValidBuyingListException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testIsValidPackage() {
		List<PackageDTO> packages = packageEJB.getOfferingPackages();
		for(PackageDTO _package : packages)
			if(packageEJB.isValidForOffering(_package)) {
				System.out.println("Valid for offering");
				System.out.println(packageEJB.dateDeparture(_package));
				System.out.println(packageEJB.dateReturn(_package));
				System.out.println(packageEJB.cityArrival(_package));
				System.out.println(packageEJB.cityReturn(_package));
			}
			else
				System.err.println(":-( NOT Valid for offerings");
	}
	
	public void testGetInvitations() {
		PackageDTO packageDTO = new PackageDTO();
		packageDTO.setId(9);
		List<InvitationDTO> invitations = null;
		try {
			invitations = invitationEJB.getInvitations(packageDTO);
		} catch (NotValidPackageException e) {
			e.printStackTrace();
		}
		System.out.println(invitations.size());
	}	

}
