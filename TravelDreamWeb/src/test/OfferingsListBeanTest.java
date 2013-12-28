package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bean.OfferingsListBean;
import dto.DatePersonalizationDTO;
import dto.FlightDTO;
import dto.HotelDTO;
import dto.PackageDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedHotelDTO;
import dto.PersonalizedProductDTO;

public class OfferingsListBeanTest {
    List<PackageDTO> list = new ArrayList<PackageDTO>();
    List<PackageDTO> result = new ArrayList<PackageDTO>();

    /**
     * Adds fake packages to offeringsList
     */
    @SuppressWarnings("deprecation")
    @Before
    public void searchFilterTestBefore() {
	// Package1
	List<PersonalizedProductDTO> pp1 = new ArrayList<PersonalizedProductDTO>();

	FlightDTO flight1 = new FlightDTO();
	flight1.setName("Volo AmericanAirlines MI-CH");
	flight1.setCompany("AmericanAirlines");
	flight1.setAirportDeparture("Milano");
	flight1.setAirportArrival("Chicago");
	PersonalizedFlightDTO pflight1 = new PersonalizedFlightDTO();
	pflight1.setFlight(flight1);
	pflight1.setDatePersonalization(new DatePersonalizationDTO(10080, new Date(2013, 04, 10))); // 1 week duration

	HotelDTO hotel1 = new HotelDTO();
	hotel1.setName("Hotel Hilton Chicago");
	hotel1.setCompany("Hilton");
	hotel1.setStars(5);
	PersonalizedHotelDTO photel1 = new PersonalizedHotelDTO(hotel1);

	pp1.add(pflight1);
	pp1.add(photel1);
	PackageDTO package1 = new PackageDTO(0, 2, "Pacchetto1", 20, pp1);

	// Package2
	List<PersonalizedProductDTO> pp2 = new ArrayList<PersonalizedProductDTO>();

	FlightDTO flight2 = new FlightDTO();
	flight2.setName("Volo Alitalia RO-CH");
	flight2.setCompany("Alitalia");
	flight2.setAirportDeparture("Roma");
	flight2.setAirportArrival("Chicago");
	PersonalizedFlightDTO pflight2 = new PersonalizedFlightDTO();
	pflight2.setFlight(flight2);
	pflight2.setDatePersonalization(new DatePersonalizationDTO(10080, new Date(2013, 06, 22))); // 1 week duration

	HotelDTO hotel2 = new HotelDTO();
	hotel2.setName("Hotel HolidayInn Chicago");
	hotel2.setCompany("HolidayInn");
	hotel2.setStars(3);
	PersonalizedHotelDTO photel2 = new PersonalizedHotelDTO(hotel1);

	pp2.add(pflight2);
	pp2.add(photel2);
	PackageDTO package2 = new PackageDTO(0, 4, "Pacchetto2", 2.5, pp2);

	list.add(package1);
	list.add(package2);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void searchFilterTest() {
	OfferingsListBean bean = new OfferingsListBean();
	bean.setDeparturePlace("Roma");
	bean.setArrivalPlace(null);
	bean.setDepartureDate(new Date(2013, 06, 22));
	bean.setReturnDate(new Date(2013, 06, 29));
	bean.setNumPeople(0);
	// ------------------------------
	bean.setFlightClass(null);
	bean.setHotelStars(null);
	bean.setHotelClass(null);

	result = bean.searchFilter(list);
	for (PackageDTO p : result) {
	    System.out.printf(p.getName() + "\n"); // mostra i pacchetti filtrati in catalogo
	}
    }

}
