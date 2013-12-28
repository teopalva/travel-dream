package dto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import coreEJB.BaseProductEJBLocal;
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

    public PackageDTO(int imageId, int numPeople, String name, double reduction, List<PersonalizedProductDTO> personalizedProducts) {
    	super();
    	this.id = -1;
    	this.imageId = imageId;
    	this.numPeople = numPeople;
    	this.name = name;
    	this.reduction = reduction;
    	if(personalizedProducts != null)
    		this.personalizedProducts = new ArrayList<PersonalizedProductDTO>(personalizedProducts);
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
    		price += p.getPrice();
    	}
    	return price;
    }

    @Override
    public PackageDTO clone() {
    	PackageDTO p = new PackageDTO();
    	p = this.clone();
    	return p;
    }

	@Override
	public String toString() {
		return "PackageDTO [imageId=" + imageId + ", numPeople=" + numPeople
				+ ", name=" + name + ", reduction=" + reduction + ", id=" + id
				+ ", personalizedProducts=" + personalizedProducts + "]";
	}
}
