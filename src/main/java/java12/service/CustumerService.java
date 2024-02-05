package java12.service;

import java12.entities.Custumer;

import java.time.LocalDate;
import java.util.List;

public interface CustumerService {

    String saveCustumer(Custumer custumer);
    public String saveCustomerWithRent(Custumer newCustomer, Long houseId, Long agencyId,
                                       LocalDate checkIn, LocalDate checkout);
    Custumer getCustumerById(Long custumerId);

    List<Custumer> getAllCustumer();

    Custumer update(Long oldCustumer, Custumer newCustumer);

    String deleteCustumer(Long custumerId);
}
