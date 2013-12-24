package coreEJB;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import dto.BaseProductDTO;
import dto.CityDTO;
import dto.FlightDTO;
import dto.PossiblePersonalizationDTO;
import entity.Airport;
import entity.City;
import entity.Company;
import entity.Flight;
import exceptions.NotValidBaseProductException;

@Local
public interface BaseProductEJBLocal {
	
    public void saveBaseProduct(BaseProductDTO baseProduct)  throws NotValidBaseProductException;
    public void removeBaseProduct(BaseProductDTO baseProduct)  throws NotValidBaseProductException;
    public List<CityDTO> getAllCities();
    public List<String> getAllCompanies();
    public List<String> getAllAirposrts();
    public List <BaseProductDTO> getAllPersonalization();
    public List <BaseProductDTO> getAllBaseProducts();
}
