package java12.service.impl;

import java12.dao.AddressDao;
import java12.dao.AgencyDao;
import java12.dao.impl.AddressDaoImpl;
import java12.dao.impl.AgencyDaoImpl;
import java12.entities.Address;
import java12.entities.Agency;
import java12.service.AgencyService;

import java.util.List;

public class AgencyImpl implements AgencyService {

    AgencyDao agencyDao = new AgencyDaoImpl();
    AddressDao addressDao = new AddressDaoImpl();


    @Override
    public String saveAgency(Agency agency,Address address) {
        for (Address allAddress : addressDao.getAllAddress()) {
            if (allAddress.getStreet().equalsIgnoreCase(address.getStreet())){
                return "myndai adres uje bar";
            }
        }
        if (agency.getPhoneNumber().startsWith("+996") && agency.getPhoneNumber().length() >= 13) {
            return agencyDao.saveAgency(agency, address);
        }else return "tel nomer tuura jaz";
    }

    @Override
    public Agency getAgencyById(Long agencyId) {
        return agencyDao.getAgencyById(agencyId);
    }

    @Override
    public List<Agency> getAllAgency() {
        return agencyDao.getAllAgency();
    }

    @Override
    public Agency update(Long oldAgency, Agency newAgency) {
        return agencyDao.update(oldAgency, newAgency);
    }

    @Override
    public String deleteAgency(Long agencyId) {
        return agencyDao.deleteAgency(agencyId);
    }
}
