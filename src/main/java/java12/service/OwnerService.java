package java12.service;

import java12.entities.House;
import java12.entities.Owner;

import java.util.List;

public interface OwnerService {
    String saveOwner(Owner owner);
    String saveOwner(Owner owner, List<House> house);

    Owner getOwnerById(Long ownerId);

    List<Owner> getAllOwner();

    Owner update(Long oldOwner, Owner newOwner);

    String deleteOwner(Long ownerId);
    String asSignOwnerToAgency(Long ownerId,Long agencyId);
    List<Owner> getAllOwnerToAgencyID(Long agencyId);
}
