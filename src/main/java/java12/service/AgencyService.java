package java12.service;

import java12.entities.Address;
import java12.entities.Agency;

import java.util.List;

public interface AgencyService {

    String saveAgency(Agency agency,Address address);

    Agency getAgencyById(Long agencyId);

    List<Agency> getAllAgency();

    Agency update(Long oldAgency, Agency newAgency);

    String deleteAgency(Long agencyId);
}
