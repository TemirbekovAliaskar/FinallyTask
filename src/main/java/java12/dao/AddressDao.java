package java12.dao;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Map;

public interface AddressDao {

    Address getAddressById(Long addressId);

    List<Address> getAllAddress();

    Address update(Long oldAddress, Address newAddress);

    List<Address> getAddressToAgency();
    Long getAgencyByAddressName(String cityName);
    Map<String,List<Agency>> groupByRegion();
}
