package bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

import org.primefaces.event.DragDropEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import coreEJB.BaseProductEJBLocal;
import coreEJB.PackageEJBLocal;
import dto.BaseProductDTO;
import dto.ClassPersonalizationDTO;
import dto.DatePersonalizationDTO;
import dto.ExcursionDTO;
import dto.FlightDTO;
import dto.HotelDTO;
import dto.PackageDTO;
import dto.PersonalizedExcursionDTO;
import dto.PersonalizedFlightDTO;
import dto.PersonalizedHotelDTO;
import dto.PersonalizedProductDTO;
import exceptions.NotValidPackageException;

@SuppressWarnings("unused")
@ManagedBean(name = "EditPackage")
@ViewScoped
public class EditPackageBean {
    // private String warningDiscount; TODO
    private PackageDTO selectedPackage = null;
    private double totalPrice;
    private String packageName = "";
    private Integer reduction = 0;

    private String departurePlace = null;
    private String arrivalPlace = null;
    private Date departureDate = null;
    private Date returnDate = null;
    private Integer numPeople = 0;

    private List<PersonalizedHotelDTO> hotels;
    private List<PersonalizedFlightDTO> outboundFlights;
    private List<PersonalizedFlightDTO> returnFlights;
    private List<PersonalizedExcursionDTO> excursions;

    // homepage flags
    private boolean hotelSelected = true;
    private boolean flightSelected = true;
    private boolean excursionSelected = true;

    // Single objects setted on drag&drop
    private PersonalizedFlightDTO outboundFlight = new PersonalizedFlightDTO();
    private PersonalizedFlightDTO returnFlight = new PersonalizedFlightDTO();
    private PersonalizedHotelDTO hotel = new PersonalizedHotelDTO();
    private PersonalizedExcursionDTO excursion = new PersonalizedExcursionDTO();

    // Possible baseProduct that can be added - if null every baseProduct can be added
    private FlightDTO baseFlight0 = null;
    private FlightDTO baseFlight1 = null;
    private HotelDTO baseHotel = null;
    private ExcursionDTO baseExcursion = null;

    private SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

    @EJB
    private BaseProductEJBLocal baseProductEJB;

    @EJB
    private PackageEJBLocal packageEJB;

    @ManagedProperty("#{SessionStorage}")
    private SessionStorageBean sessionStorage;

    public EditPackageBean() {
	outboundFlights = new ArrayList<PersonalizedFlightDTO>();
	returnFlights = new ArrayList<PersonalizedFlightDTO>();
	hotels = new ArrayList<PersonalizedHotelDTO>();
	excursions = new ArrayList<PersonalizedExcursionDTO>();
    }

    @PostConstruct
    public void init() {
	if (sessionStorage.getSelectedPackage() == null) {
	    selectedPackage = new PackageDTO();
	    selectedPackage.setName("");
	    numPeople = 2;
	} else {
	    // Reconstruct the packageDTO structure in temporary variables
	    selectedPackage = sessionStorage.getSelectedPackage();
	    numPeople = selectedPackage.getNumPeople();
	    List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();
	    outboundFlight = new PersonalizedFlightDTO();
	    returnFlight = new PersonalizedFlightDTO();
	    hotel = new PersonalizedHotelDTO();
	    excursion = new PersonalizedExcursionDTO();
	    for (PersonalizedProductDTO p : selectedPackage.getPersonalizedProducts()) {
		if (p instanceof PersonalizedFlightDTO) {
		    flights.add((PersonalizedFlightDTO) p);
		}
		if (p instanceof PersonalizedHotelDTO) {
		    hotel = (PersonalizedHotelDTO) p;
		    baseHotel = hotel.getHotel();
		}
		if (p instanceof PersonalizedExcursionDTO) {
		    excursion = (PersonalizedExcursionDTO) p;
		    baseExcursion = excursion.getExcursion();
		}
	    }
	    if (flights.size() == 2) {
		try {
		    if (flights.get(0).getDatePersonalization().getInitialDate().before(flights.get(1).getDatePersonalization().getInitialDate())) {
			outboundFlight = flights.get(0);
			returnFlight = flights.get(1);
			baseFlight0 = flights.get(0).getFlight();
			baseFlight1 = flights.get(1).getFlight();
		    } else {
			outboundFlight = flights.get(1);
			returnFlight = flights.get(0);
			baseFlight0 = flights.get(1).getFlight();
			baseFlight1 = flights.get(0).getFlight();
		    }
		} catch (NullPointerException | IndexOutOfBoundsException e) {
		    // No problem
		}

	    }

	}
    }

    // Bean properties:

    public void setSelectedPackage(PackageDTO p) {
	selectedPackage = p;
    }

    public PackageDTO getSelectedPackage() {
	return selectedPackage;
    }

    public SessionStorageBean getSessionStorage() {
	return sessionStorage;
    }

    public void setSessionStorage(SessionStorageBean sessionStorage) {
	this.sessionStorage = sessionStorage;
    }

    public void setDeparturePlace(String p) {
	departurePlace = p;
    }

    public String getDeparturePlace() {
	return departurePlace;
    }

    public void setArrivalPlace(String p) {
	arrivalPlace = p;
    }

    public String getArrivalPlace() {
	return arrivalPlace;
    }

    public void setDepartureDate(Date d) {
	departureDate = d;
    }

    public Date getDepartureDate() {
	return departureDate;
    }

    public void setReturnDate(Date d) {
	returnDate = d;
    }

    public Date getReturnDate() {
	return returnDate;
    }

    public void setNumPeople(Integer n) {
	numPeople = n;
	if (selectedPackage != null)
	    selectedPackage.setNumPeople(n);
    }

    public Integer getNumPeople() {
	return numPeople;
    }

    public String getPackageName() {
	return packageName;
    }

    public void setPackageName(String packageName) {
	this.packageName = packageName;
    }

    public Integer getReduction() {
	return reduction;
    }

    public void setReduction(Integer reduction) {
	this.reduction = reduction;
    }

    public void setTotalPrice(double n) {
	totalPrice = n;
    }

    public double getTotalPrice() {
	return totalPrice;
    }

    public List<PersonalizedHotelDTO> getHotels() {
	return hotels;
    }

    public List<PersonalizedFlightDTO> getOutboundFlights() {
	return outboundFlights;
    }

    public List<PersonalizedFlightDTO> getReturnFlights() {
	return returnFlights;
    }

    public List<PersonalizedExcursionDTO> getExcursions() {
	return excursions;
    }

    public boolean isHotelSelected() {
	return hotelSelected;
    }

    public void setHotelSelected(boolean hotelSelected) {
	this.hotelSelected = hotelSelected;
    }

    public boolean isFlightSelected() {
	return flightSelected;
    }

    public void setFlightSelected(boolean flightSelected) {
	this.flightSelected = flightSelected;
    }

    public boolean isExcursionSelected() {
	return excursionSelected;
    }

    public void setExcursionSelected(boolean excursionSelected) {
	this.excursionSelected = excursionSelected;
    }

    // Bean methods:

    /**
     * Refreshes the lists of base products. Do a new getHotels() with Ajax to get the refreshed list. Requires that all search fields are non-empty!
     */
    public void submitSearch() {
	refreshLists();
    }

    /**
     * Refreshes the lists of base products based on the selected criteria.
     */
    private void refreshLists() {
	hotels.clear();
	outboundFlights.clear();
	returnFlights.clear();
	excursions.clear();
	List<BaseProductDTO> list = baseProductEJB.getAllPersonalizations();
	int i = 0;
	int j = 0;
	int k = 0;
	int z = 0;
	try {
	    for (BaseProductDTO bp : list) {
		if (bp instanceof HotelDTO) {
		    if (arrivalPlace.equalsIgnoreCase(((HotelDTO) bp).getCity().getName())) {
			for (ClassPersonalizationDTO c : ((HotelDTO) bp).getPossibleClassPersonalizations()) {
			    PersonalizedHotelDTO h = new PersonalizedHotelDTO((HotelDTO) bp);
			    h.setClassPersonalization(c);
			    h.setDropIndex(i);
			    i++;
			    hotels.add(h);
			}
		    }
		}
		if (bp instanceof FlightDTO) {
		    if ((departurePlace.equalsIgnoreCase(((FlightDTO) bp).getCityDeparture().getName())) && arrivalPlace.equalsIgnoreCase(((FlightDTO) bp).getCityArrival().getName())) {
			for (DatePersonalizationDTO d : ((FlightDTO) bp).getPossibleDatePersonalizations()) {
			    if (fmt.format(departureDate).equals(fmt.format(d.getInitialDate()))) {
				for (ClassPersonalizationDTO c : ((FlightDTO) bp).getPossibleClassPersonalizations()) {
				    PersonalizedFlightDTO f = new PersonalizedFlightDTO((FlightDTO) bp);
				    f.setDatePersonalization(d);
				    f.setClassPersonalization(c);
				    f.setDropIndex(j);
				    j++;
				    outboundFlights.add(f);
				}
			    }
			}
		    } else {
			if ((departurePlace.equalsIgnoreCase(((FlightDTO) bp).getCityArrival().getName())) && arrivalPlace.equalsIgnoreCase(((FlightDTO) bp).getCityDeparture().getName())) {
			    for (DatePersonalizationDTO d : ((FlightDTO) bp).getPossibleDatePersonalizations()) {
				if (fmt.format(returnDate).equals(fmt.format(d.getInitialDate()))) {
				    for (ClassPersonalizationDTO c : ((FlightDTO) bp).getPossibleClassPersonalizations()) {
					PersonalizedFlightDTO f = new PersonalizedFlightDTO((FlightDTO) bp);
					f.setDatePersonalization(d);
					f.setClassPersonalization(c);
					f.setDropIndex(k);
					k++;
					returnFlights.add(f);
				    }
				}
			    }
			}
		    }
		}
		if (bp instanceof ExcursionDTO) {
		    if (arrivalPlace.equalsIgnoreCase(((ExcursionDTO) bp).getCity().getName())) {
			for (DatePersonalizationDTO d : ((ExcursionDTO) bp).getPossibleDatePersonalizations()) {
			    if (departureDate.before(d.getInitialDate()) && returnDate.after(d.getFinalDate())) {
				PersonalizedExcursionDTO e = new PersonalizedExcursionDTO((ExcursionDTO) bp);
				e.setDatePersonalization(d);
				e.setDropIndex(z);
				z++;
				excursions.add(e);
			    }
			}
		    }
		}
	    }
	} catch (NullPointerException e) {
	    System.err.printf("Devi riempire tutti i campi!");
	}
    }

    /**
     * Recalculates the total cost of the current package.
     */
    public double calculatePrice() {
	double p = 0;
	try {
	    p = selectedPackage.getPrice();
	} catch (NullPointerException e) {
	    System.err.print("Empty Package is free!");
	}
	return p;
    }

    /**
     * Synchronize the products with PackageDTO
     */
    private void updatePackage(int category, PersonalizedProductDTO p) {
	switch (category) {
	    case 0: // Outbound flight
		selectedPackage.getPersonalizedProducts().remove(outboundFlight); // Remove old
		outboundFlight = (PersonalizedFlightDTO) p; // Update
		break;
	    case 1: // Inbound flight
		selectedPackage.getPersonalizedProducts().remove(returnFlight);
		returnFlight = (PersonalizedFlightDTO) p;
		break;
	    case 2: // Hotel
		selectedPackage.getPersonalizedProducts().remove(hotel);
		hotel = (PersonalizedHotelDTO) p;
		break;
	    case 3: // Excursion
		selectedPackage.getPersonalizedProducts().remove(excursion);
		excursion = (PersonalizedExcursionDTO) p;
		break;
	}
	selectedPackage.addPersonalizedProduct(p); // Add new
	// selectedPackage.getPersonalizedProducts().set(category, p);
    }

    /**
     * Function called by the jsf when a drag and drop happens String is draggedId type _category_id Example: for excursion (category = 3) and with id
     * 11 the string is _3_11 for hotels (category = 2) and with id 23 string is _2_23.
     */
    public void onDrop(DragDropEvent ddEvent) {

	String draggedId = ddEvent.getDragId();
	String[] parts = draggedId.split("_");

	int category = Integer.parseInt(parts[1]);
	int index = Integer.parseInt(parts[2]);

	System.out.println("Categoria: " + category);
	System.out.println("Indice: " + index);

	switch (category) {
	    case 0:
		updatePackage(category, outboundFlights.get(index));
		break;
	    case 1:
		updatePackage(category, returnFlights.get(index));
		break;
	    case 2:
		updatePackage(category, hotels.get(index));
		break;
	    case 3:
		updatePackage(category, excursions.get(index));
		break;
	    default: // do nothing
	}
    }

    /**
     * 
     * @return the checkout page URL
     */
    public String showCheckout() {
	// if (packageEJB.isValidForTDC(selectedPackage)) {
	if (!packageName.equals("")) {
	    selectedPackage.setName(packageName);
	}
	if (numPeople != 0) {
	    selectedPackage.setNumPeople(numPeople);
	}
	sessionStorage.setSelectedPackage(selectedPackage);
	sessionStorage.setPreviousPage("edit");
	return "/user/checkout?faces-redirect=true";
	// } else {
	// return null;
	// }
    }

    /**
     * Saves the package edited by TDE, then redirects.
     * 
     * @return admin panel URL
     */
    public String savePackageTDE() {
	if (packageEJB.isValidForOffering(selectedPackage)) {
	    if (!packageName.equals("")) {
		selectedPackage.setName(packageName);
	    }
	    if (numPeople != 0) {
		selectedPackage.setNumPeople(numPeople);
	    }
	    selectedPackage.setReduction(reduction);
	    try {
		packageEJB.savePackage(selectedPackage);
	    } catch (NotValidPackageException e) {
		System.err.printf("NotValidPackageException");
		e.printStackTrace();
	    }
	    return "/admin/index?faces-redirect=true";
	} else {
	    return null;
	}
    }

    /**
     * Checks the consistency of the current package.
     * 
     * @return boolean
     */
    public boolean isNotValidForTDC() {
	return !packageEJB.isValidForTDC(selectedPackage);
    }

    // Getters and setters for drag&drop
    public PersonalizedFlightDTO getOutboundFlight() {
	return outboundFlight;
    }

    public void setOutboundFlight(PersonalizedFlightDTO outboundFlight) {
	this.outboundFlight = outboundFlight;
    }

    public PersonalizedHotelDTO getHotel() {
	return hotel;
    }

    public void setHotel(PersonalizedHotelDTO hotel) {
	this.hotel = hotel;
    }

    public PersonalizedExcursionDTO getExcursion() {
	return excursion;
    }

    public void setExcursion(PersonalizedExcursionDTO excursion) {
	this.excursion = excursion;
    }

    public void setOutboundFlights(List<PersonalizedFlightDTO> outboundFlights) {
	this.outboundFlights = outboundFlights;
    }

    public PersonalizedFlightDTO getReturnFlight() {
	return returnFlight;
    }

    public void setReturnFlight(PersonalizedFlightDTO returnFlight) {
	this.returnFlight = returnFlight;
    }

    public byte[] getImage() {
	return selectedPackage.getImageData();
    }

    public void handleImageUpload(FileUploadEvent event) {
	FacesMessage msg = new FacesMessage("Immagine caricata", event.getFile().getFileName() + " e' stata aggiunta");
	FacesContext.getCurrentInstance().addMessage(null, msg);
	try {
	    selectedPackage.setImageData(this.getFileContents(event.getFile().getInputstream()));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private byte[] getFileContents(InputStream in) {
	byte[] bytes = null;
	try {
	    // write the inputStream to a FileOutputStream
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    int read = 0;
	    bytes = new byte[1024];

	    while ((read = in.read(bytes)) != -1) {
		bos.write(bytes, 0, read);
	    }
	    bytes = bos.toByteArray();
	    in.close();
	    in = null;
	    bos.flush();
	    bos.close();
	    bos = null;
	} catch (IOException e) {
	    System.out.println(e.getMessage());
	}
	return bytes;
    }

}
