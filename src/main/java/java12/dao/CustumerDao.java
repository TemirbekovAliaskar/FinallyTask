package java12.dao;

import java12.entities.Address;
import java12.entities.Agency;
import java12.entities.Custumer;

import java.time.LocalDate;
import java.util.List;

public interface CustumerDao {

    String saveCustumer(Custumer custumer);
    public String saveCustomerWithRent(Custumer newCustomer, Long houseId, Long agencyId,
                                       LocalDate checkIn, LocalDate checkout);
    Custumer getCustumerById(Long custumerId);

    List<Custumer> getAllCustumer();

    Custumer update(Long oldCustumer, Custumer newCustumer);

    String deleteCustumer(Long custumerId);
}
