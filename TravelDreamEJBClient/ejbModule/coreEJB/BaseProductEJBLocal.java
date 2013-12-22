package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.BaseProductDTO;
import dto.FlightDTO;
import dto.PossiblePersonalizationDTO;
import entity.Airport;
import entity.Company;
import entity.Flight;
import exceptions.NotValidBaseProductException;

@Local
public interface BaseProductEJBLocal {
	
    public void saveBaseProduct(BaseProductDTO baseProduct)  throws NotValidBaseProductException;
    public void removeBaseProduct(BaseProductDTO baseProduct)  throws NotValidBaseProductException;
    public void savePersonlization(PossiblePersonalizationDTO possiblePersonalization);
    public void removePersonalization(PossiblePersonalizationDTO possiblePersonalization);
    public List <BaseProductDTO> getAllPersonalization();
    public List <BaseProductDTO> getAllBaseProducts();
}
