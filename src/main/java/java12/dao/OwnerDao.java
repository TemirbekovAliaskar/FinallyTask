package java12.dao;

import java12.entities.Address;
import java12.entities.Agency;
import java12.entities.House;
import java12.entities.Owner;

import java.util.List;

public interface OwnerDao {

    String saveOwner(Owner owner);
    String saveOwner(Owner owner, List<House> house);

    Owner getOwnerById(Long ownerId);

    List<Owner> getAllOwner();

    Owner update(Long oldOwner, Owner newOwner);

    String deleteOwner(Long ownerId);
    String asSignOwnerToAgency(Long ownerId,Long agencyId);
    List<Owner> getAllOwnerToAgencyID(Long agencyId);
    List<Owner> getOwnerAge();


}
