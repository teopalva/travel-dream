package dto;

import java.util.ArrayList;
import java.util.List;

import entity.Package;
import entity.PersonalizedProductExcursion;
import entity.PersonalizedProductFlight;
import entity.PersonalizedProductHotel;

public class PackageDTO {
    private long imageId;
    private int numPeople;
    private String name;
    private double reduction;
    private int id;

    List<PersonalizedProductDTO> personalizedProducts;

    public PackageDTO() {
	this.id = -1;
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

    public PackageDTO(long imageId, int numPeople, String name, double reduction, List<PersonalizedProductDTO> personalizedProducts) {
	super();
	this.id = -1;
	this.imageId = imageId;
	this.numPeople = numPeople;
	this.name = name;
	this.reduction = reduction;
	this.personalizedProducts = personalizedProducts;
    }

    public long getImageId() {
	return imageId;
    }

    public void setImageId(long imageId) {
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

    public void addPersonalizedProduct(PersonalizedProductDTO product) {
	personalizedProducts.add(product);
    }

    /**
     * Check if the package is valid or not
     */
    public boolean isValid() {
	// TODO: implement the check
	return false;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @Override
    public PackageDTO clone() {
	PackageDTO p = new PackageDTO();
	p = this.clone();
	return p;
    }
}
