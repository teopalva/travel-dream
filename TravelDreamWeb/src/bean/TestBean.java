package bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import coreEJB.PackageEJBLocal;
import coreEJB.UserEJBLocal;
import dto.BuyingListItemDTO;
import dto.FlightDTO;
import dto.GiftListItemDTO;
import dto.PackageDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedProductDTO;
import dto.UserDTO;

@ManagedBean(name="Test")
@RequestScoped
public class TestBean {
	
	@EJB
	UserEJBLocal userEJB;
	
	@EJB
	PackageEJBLocal packageEJB;
	
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
		FlightDTO flight = new FlightDTO("Volo di test", "Alitalia", "LIN", "LIN", null, null);
		flight.setId(2);
		PersonalizedFlightDTO pFlight = new PersonalizedFlightDTO();
		pFlight.setFlight(flight);
		//pFlight.setDatePersonalization(datePersonalization);
		pp.add(pFlight);
		_package.setPersonalizedProducts(pp);
		packageEJB.savePackage(_package);
	}

}
