package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.Package;
import entity.PersonalizedProductExcursion;
import entity.PersonalizedProductFlight;
import entity.PersonalizedProductHotel;
import exceptions.FieldNotPresentException;

public class PackageDTO {
    private int imageId;
    private int numPeople;
    private String name;
    private double reduction;
    private int id;
    private byte[] imageData;
    
    private List<InvitationDTO> invitations; //only for invitationListBean purposes

    List<PersonalizedProductDTO> personalizedProducts;
    
    public PackageDTO() {
    	this.id = -1;
    	this.personalizedProducts = new ArrayList<PersonalizedProductDTO>();
    	imageData = null;
    }

    public PackageDTO(Package _package) throws FieldNotPresentException {
    	try {
    		if (_package.getImage() != null)
    			this.imageId = _package.getImage().getId();
    		else
    			this.imageId = -1;
    		this.numPeople = _package.getNumPeople();
    		this.name = _package.getName();
    		this.reduction = _package.getReduction();
    		this.personalizedProducts = new ArrayList<PersonalizedProductDTO>();
    		this.id = _package.getId();
    		try {
    			for (PersonalizedProductFlight flight : _package.getPersonalizedProductFlights()) {
    				personalizedProducts.add(new PersonalizedFlightDTO(flight));
    			}
    		} catch (NullPointerException e) {
    		} // No problem
    		try {
    			for (PersonalizedProductExcursion excursion : _package.getPersonalizedProductExcursions()) {
    				personalizedProducts.add(new PersonalizedExcursionDTO(excursion));
    			}
    		} catch (NullPointerException e) {
    		} // No problem
    		try {
    			for (PersonalizedProductHotel hotel : _package.getPersonalizedProductHotels()) {
    				personalizedProducts.add(new PersonalizedHotelDTO(hotel));
    			}
    		} catch (NullPointerException e) {
    		} // No problem
    	} catch (Exception e) {
    		throw new FieldNotPresentException();
    	}
    }

    public PackageDTO(int imageId, int numPeople, String name, double reduction, List<PersonalizedProductDTO> personalizedProducts) {
    	super();
    	this.id = -1;
    	this.imageId = imageId;
    	this.numPeople = numPeople;
    	this.name = name;
    	this.reduction = reduction;
    	if(personalizedProducts != null) {
    		this.personalizedProducts = new ArrayList<PersonalizedProductDTO>();
    		//this.personalizedProducts = new ArrayList<PersonalizedProductDTO>(personalizedProducts);
    		for(PersonalizedProductDTO p : personalizedProducts) {
    			this.personalizedProducts.add(p.clone());
    		}
    	}
    	else
    		this.personalizedProducts = new ArrayList<PersonalizedProductDTO>();
    }
    
    /**
     * Make a deep copy of the packageDTO, useful in order to duplicate package.
     * Initialize the package without the id, full compatible with savePackage (meaning: create new package) in PackageEJB
     * @param packageDTO the package that must be (deep) copied
     */
    public PackageDTO(PackageDTO packageDTO) {
		this(packageDTO.imageId, packageDTO.numPeople, packageDTO.name, packageDTO.reduction, null);
		for(PersonalizedProductDTO p : packageDTO.personalizedProducts) {
			if(p instanceof PersonalizedFlightDTO) {
				this.addPersonalizedProduct(new PersonalizedFlightDTO((PersonalizedFlightDTO)p));
			}
			if(p instanceof PersonalizedExcursionDTO) {
				this.addPersonalizedProduct(new PersonalizedExcursionDTO((PersonalizedExcursionDTO)p));
			}
			if(p instanceof PersonalizedHotelDTO) {
				this.addPersonalizedProduct(new PersonalizedHotelDTO((PersonalizedHotelDTO)p));
			}
		}
	}
    
    public static List<PackageDTO> getAllPackagesFromInvitation(List<InvitationDTO> invitations) {
    	List<PackageDTO> list = new ArrayList<PackageDTO>();
    	for(InvitationDTO invitation : invitations) {
    		list.add(invitation.get_package());
    	}
    	return list;
    }
    
    public static List<PackageDTO> getAllPackagesFromBuying(List<BuyingListItemDTO> buyingList) {
    	List<PackageDTO> list = new ArrayList<PackageDTO>();
    	for(BuyingListItemDTO item : buyingList) {
    		list.add(item.get_package());
    	}
    	return list;
    }
    
    public static List<PackageDTO> getAllPackagesFromGift(List<GiftListItemDTO> giftList) {
    	List<PackageDTO> list = new ArrayList<PackageDTO>();
    	for(GiftListItemDTO item : giftList) {
    		list.add(item.get_package());
    	}
    	return list;
    } 

    public int getImageId() {
    	return imageId;
    }

    public void setImageId(int imageId) {
    	this.imageId = imageId;
    }

    public int getNumPeople() {
    	return numPeople;
    }

    public void setNumPeople(int numPeople) {
    	this.numPeople = numPeople;
    }

    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }

    public double getReduction() {
    	return reduction;
    }

    public void setReduction(double reduction) {
    	this.reduction = reduction;
    }

    public List<PersonalizedProductDTO> getPersonalizedProducts() {
    	return personalizedProducts;
    }

    public void setPersonalizedProducts(List<PersonalizedProductDTO> personalizedProducts) {
    	this.personalizedProducts = personalizedProducts;
    }

    public List<InvitationDTO> getInvitations() {
	return invitations;
    }

    public void setInvitations(List<InvitationDTO> invitations) {
	this.invitations = invitations;
    }

    public void addPersonalizedProduct(PersonalizedProductDTO product) {
    	personalizedProducts.add(product);
    }

    public int getId() {
    	return id;
    }

    public void setId(int id) {
    	this.id = id;
    }
    
    public double getPrice() {
    	double price = 0;
    	for(PersonalizedProductDTO p : personalizedProducts) {
    		
    		if(p instanceof PersonalizedHotelDTO) {
    			//Calculate number of days
    			Date dateCheckIn = dateDeparture();
    			Date dateCheckOut = dateReturn();
    			if(dateCheckIn != null && dateCheckOut != null)
    				price += p.getPrice() * daysBetween(dateCheckOut, dateCheckIn);
    		} else {
    			price += p.getPrice();
    		}
    	}
    	price *= this.numPeople;
    	price -= price*this.reduction;
    	
    	//Price can't be negative
    	if(price<0) {
    		System.err.println("Negative price for PackageDTO: "+this.toString());
    		price = 0;
    	}
    	return price;
    }
    
    private long daysBetween(Date date1, Date date2) {
    	return Math.abs( (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24) );
    }
    
    /**
     * @return the date of the departure (departure flight)
     * 		The package must be isValidForTDC()
     * 		This function is not very precise. Please use the one in PackageEJB
     */
    private Date dateDeparture() {    	
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();

    	for(PersonalizedProductDTO p : getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
    		}
    	}
    	
    	if(flights.size() != 2)
    		return null;
    	
    	try {
	    	Date date1 = flights.get(0).getDatePersonalization().getInitialDate();
	    	Date date2 = flights.get(1).getDatePersonalization().getInitialDate();
	    	if(date1.before(date2))
	    		return flights.get(0).getDatePersonalization().getFinalDate();
	    	else
	    		return flights.get(1).getDatePersonalization().getFinalDate();
    	} catch (NullPointerException e) {
    		return null;
    	}
    }
    
    /**
     * @return the date of the return (return flight)
     * 		The package must be isValidForTDC()
     * 		This function is not very precise. Please use the one in PackageEJB
     */
    private Date dateReturn() {
    	List<PersonalizedFlightDTO> flights = new ArrayList<PersonalizedFlightDTO>();

    	for(PersonalizedProductDTO p : getPersonalizedProducts()) {
    		if(p instanceof PersonalizedFlightDTO) {
    			flights.add((PersonalizedFlightDTO)p);
    		}
    	}
    	
    	if(flights.size() != 2)
    		return null;
    	
    	try {	
	    	Date date1 = flights.get(0).getDatePersonalization().getInitialDate();
	    	Date date2 = flights.get(1).getDatePersonalization().getInitialDate();
	    	if(date1.before(date2))
	    		return flights.get(1).getDatePersonalization().getInitialDate();
	    	else
	    		return flights.get(0).getDatePersonalization().getInitialDate();
    	} catch(NullPointerException e) {
    		return null;
    	}
    }

    @Override
    public PackageDTO clone() {
    	PackageDTO p = new PackageDTO(imageId, numPeople, name, reduction, personalizedProducts);
    	return p;
    }

	@Override
	public String toString() {
		return "PackageDTO [imageId=" + imageId + ", numPeople=" + numPeople
				+ ", name=" + name + ", reduction=" + reduction + ", id=" + id
				+ ", personalizedProducts=" + personalizedProducts + "]";
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	
}
