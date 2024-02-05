package java12.service.impl;

import java12.dao.OwnerDao;
import java12.dao.impl.OwnerDaoImpl;
import java12.entities.House;
import java12.entities.Owner;
import java12.service.OwnerService;

import java.util.List;

public class OwnerImpl implements OwnerService {
    OwnerDao ownerDao = new OwnerDaoImpl();
    @Override
    public String saveOwner(Owner owner) {
        return ownerDao.saveOwner(owner);
    }

    @Override
    public String saveOwner(Owner owner, List<House> house) {
        return ownerDao.saveOwner(owner,house);
    }

    @Override
    public Owner getOwnerById(Long ownerId) {
        return ownerDao.getOwnerById(ownerId);
    }

    @Override
    public List<Owner> getAllOwner() {
        return ownerDao.getAllOwner();
    }

    @Override
    public Owner update(Long oldOwner, Owner newOwner) {
        return ownerDao.update(oldOwner, newOwner);
    }

    @Override
    public String deleteOwner(Long ownerId) {
        return ownerDao.deleteOwner(ownerId);
    }

    @Override
    public String asSignOwnerToAgency(Long ownerId, Long agencyId) {
        return ownerDao.asSignOwnerToAgency(ownerId, agencyId);
    }

    @Override
    public List<Owner> getAllOwnerToAgencyID(Long agencyId) {
        return ownerDao.getAllOwnerToAgencyID(agencyId);
    }
}
