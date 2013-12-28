package coreEJB;

import java.util.List;

import javax.ejb.Local;

import dto.BaseProductDTO;
import dto.CityDTO;
import exceptions.NotValidBaseProductException;

@Local
public interface BaseProductEJBLocal {
	
    public void saveBaseProduct(BaseProductDTO baseProduct)  throws NotValidBaseProductException;
    public void removeBaseProduct(BaseProductDTO baseProduct)  throws NotValidBaseProductException;
    public List<CityDTO> getAllCities();
    public List<String> getAllCompanies();
    public List<String> getAllAirports();
    public List <BaseProductDTO> getAllPersonalizations();
    public List <BaseProductDTO> getAllBaseProducts();
    public CityDTO getCity(String airport);
}
