package java12.service.impl;

import java12.dao.CustumerDao;
import java12.dao.impl.CustumerDaoImpl;
import java12.entities.Custumer;
import java12.service.CustumerService;

import java.time.LocalDate;
import java.util.List;

public class CustumerImpl implements CustumerService {
    CustumerDao custumerDao = new CustumerDaoImpl();
    @Override
    public String saveCustumer(Custumer custumer) {
        return custumerDao.saveCustumer(custumer);
    }

    @Override
    public String saveCustomerWithRent(Custumer newCustomer, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkout) {
        return custumerDao.saveCustomerWithRent(newCustomer, houseId, agencyId, checkIn, checkout);
    }

    @Override
    public Custumer getCustumerById(Long custumerId) {
        return custumerDao.getCustumerById(custumerId);
    }

    @Override
    public List<Custumer> getAllCustumer() {
        return custumerDao.getAllCustumer();
    }

    @Override
    public Custumer update(Long oldCustumer, Custumer newCustumer) {
        return custumerDao.update(oldCustumer, newCustumer);
    }

    @Override
    public String deleteCustumer(Long custumerId) {
        return custumerDao.deleteCustumer(custumerId);
    }
}
