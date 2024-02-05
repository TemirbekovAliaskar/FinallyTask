package java12.service.impl;

import java12.dao.HouseDao;
import java12.dao.impl.HouseDaoImpl;
import java12.entities.House;
import java12.entities.Owner;
import java12.service.HouseService;

import java.time.LocalDate;
import java.util.List;

public class HouseImpl implements HouseService {

    HouseDao houseDao = new HouseDaoImpl();
    @Override
    public String saveHouse(House house) {
        return houseDao.saveHouse(house);
    }

    @Override
    public String saveHouse(Owner owner, List<House> house) {
        return houseDao.saveHouse(owner, house);
    }

    @Override
    public House getHouseById(Long houseId) {
        return houseDao.getHouseById(houseId);
    }

    @Override
    public List<House> getAllHouse() {
        return houseDao.getAllHouse();
    }

    @Override
    public House update(Long oldHouse, House newHouse) {
        return houseDao.update(oldHouse, newHouse);
    }
    @Override
    public String deleteHouse(Long houseId) {
        return houseDao.deleteHouse(houseId);
    }
    @Override
    public List<House> getRegion(String region) {
        return houseDao.getRegion(region);
    }

    @Override
    public List<House> getAgencyHouses(Long agencyId) {
        return houseDao.getAgencyHouses(agencyId);
    }

    @Override
    public List<House> getOwnerHouses(Long id) {
       return houseDao.getOwnerHouses(id);
    }

    @Override
    public List<House> getCheck(LocalDate checkin, LocalDate checkout) {
        return houseDao.getCheck(checkin,checkout);
    }


}
