package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import coreEJB.BaseProductEJBLocal;
import coreEJB.PackageEJBLocal;
import coreEJB.UserEJBLocal;
import dto.BuyingListItemDTO;
import dto.ClassPersonalizationDTO;
import dto.DatePersonalizationDTO;
import dto.FlightDTO;
import dto.GiftListItemDTO;
import dto.PackageDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedProductDTO;
import dto.UserDTO;
import exceptions.NotValidBaseProductException;
import exceptions.NotValidPackageException;

@ManagedBean(name="Test")
@RequestScoped
public class TestBean {
	
	@EJB
	UserEJBLocal userEJB;
	
	@EJB
	PackageEJBLocal packageEJB;
	
	@EJB
	BaseProductEJBLocal baseProductEJB;
	
	public void testUserEJB() {
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
		
		HotelDTO hotel = new HotelDTO()
	}

}
