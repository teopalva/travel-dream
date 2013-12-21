package bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import coreEJB.UserEJBLocal;
import dto.BuyingListItemDTO;
import dto.GiftListItemDTO;
import dto.UserDTO;

@ManagedBean(name="Test")
@RequestScoped
public class TestBean {
	
	@EJB
	UserEJBLocal userEJB;
	
	public void testUserEJB() {
		UserDTO user = userEJB.getUser("gianluca.91@gmail.com");
		System.out.println(user.getGroup());
		List<BuyingListItemDTO> list = userEJB.getBuyingList(user);
		System.out.println(list.size());
		List<GiftListItemDTO> list2 = userEJB.getGiftList(user);
		System.out.println(list2.size());
		list = userEJB.getAllBuyingList();
		System.out.println(list.size());
	}

}
