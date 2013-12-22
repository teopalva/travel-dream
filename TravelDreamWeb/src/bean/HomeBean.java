package bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import coreEJB.AuthenticationEJBLocal;
import exceptions.EmptySelectionException;

@ManagedBean(name = "Home")
@ViewScoped
public class HomeBean {

	/**
	 * To be checked directly from edit_package.jsf before rendering the page
	 */
	private Boolean hotelSelected;
	private Boolean flightSelected;
	private Boolean excursionSelected;

	@EJB
	AuthenticationEJBLocal authenticationEJB;

	// -------------------------------------
	public String getHelloWorld() {
		if (authenticationEJB.isTDC())
			return "TDC";
		if (authenticationEJB.isTDE())
			return "TDE";
		return "NON AUTENTICATO";
	}

	// -------------------------------------

	// Bean properties:

	public void setHotelSelected(Boolean b) {
		hotelSelected = b;
	}

	public Boolean getHotelSelected() {
		return hotelSelected;
	}

	public void setFlightSelected(Boolean b) {
		flightSelected = b;
	}

	public Boolean getFlightSelected() {
		return flightSelected;
	}

	public void setExcursionSelected(Boolean b) {
		excursionSelected = b;
	}

	public Boolean getExcursionSelected() {
		return excursionSelected;
	}

	// Action controller methods:

	public String showEditPackage() {
		try {
			if (!flightSelected && !hotelSelected && !excursionSelected) {
				throw new EmptySelectionException();
			}
		} catch (EmptySelectionException e) {
			System.out.printf("EmptySelectionException");
			return null;
		}
		return "edit_package";
	}

	public String showOfferingsList() {

		return "offerings_list";
	}

}
