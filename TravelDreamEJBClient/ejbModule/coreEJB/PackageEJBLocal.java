package coreEJB;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import dto.CityDTO;
import dto.PackageDTO;
import exceptions.NotValidPackageException;

@Local
public interface PackageEJBLocal {
    public List<PackageDTO> getOfferingPackages();

    public void savePackage(PackageDTO _package) throws NotValidPackageException;

    public void removePackage(PackageDTO _package) throws NotValidPackageException;

    // PackageDTO helper functions
    public boolean isValidForOffering(PackageDTO packageDTO);

    public boolean isValidForTDC(PackageDTO packageDTO);

    public Date dateDeparture(PackageDTO packageDTO);

    public Date dateReturn(PackageDTO packageDTO);

    public CityDTO cityArrival(PackageDTO packageDTO);

    public CityDTO cityReturn(PackageDTO packageDTO);

    // Getters and setters
    public PackageDTO getTmpPackage();

    public void setTmpPackage(PackageDTO tmpPackage);

    public boolean isSelectedFlight();

    public void setSelectedFlight(boolean selectedFlight);

    public boolean isSelectedHotel();

    public void setSelectedHotel(boolean selectedHotel);

    public boolean isSelectedExcursion();

    public void setSelectedExcursion(boolean selectedExcursion);

    public byte[] getPackageImage(PackageDTO packageDTO) throws NotValidPackageException;
}
