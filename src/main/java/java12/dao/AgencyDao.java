package java12.dao;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;
import java.util.Optional;

public interface AgencyDao {

    String saveAgency(Agency agency,Address address);

    Agency getAgencyById(Long agencyId);

    List<Agency> getAllAgency();

    Agency update(Long oldAgency, Agency newAgency);

    String deleteAgency(Long agencyId);

}
