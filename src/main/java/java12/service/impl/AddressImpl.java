package java12.service.impl;

import java12.dao.AddressDao;
import java12.dao.impl.AddressDaoImpl;
import java12.entities.Address;
import java12.entities.Agency;
import java12.service.AddressService;

import java.util.List;
import java.util.Map;

public class AddressImpl implements AddressService {
    AddressDao addressDao = new AddressDaoImpl();
    @Override
    public Address getAddressById(Long addressId) {
        return addressDao.getAddressById(addressId);
    }
    @Override
    public List<Address> getAllAddress() {
        return addressDao.getAllAddress();
    }
    @Override
    public Address update(Long oldAddress, Address newAddress) {
        return addressDao.update(oldAddress, newAddress);
    }

    @Override
    public List<Address> getAddressToAgency() {
        return addressDao.getAddressToAgency();
    }

    @Override
    public Long getAgencyByAddressName(String cityName) {
        return addressDao.getAgencyByAddressName(cityName);
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        return addressDao.groupByRegion();
    }
}
