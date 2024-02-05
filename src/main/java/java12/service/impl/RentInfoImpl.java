package java12.service.impl;

import java12.dao.RentInfoDao;
import java12.dao.impl.RentInfoDaoImpl;
import java12.entities.RentInfo;
import java12.service.RentInfoService;

import java.time.LocalDate;
import java.util.List;

public class RentInfoImpl implements RentInfoService {
    RentInfoDao rentInfoDao = new RentInfoDaoImpl();
    @Override
    public List<RentInfo> getInfoCheckouts(LocalDate date1, LocalDate date2) {
        return rentInfoDao.getInfoCheckouts(date1,date2);
    }

    @Override
    public Long countHousesNow(Long agencyId) {
        return rentInfoDao.countHousesNow(agencyId);
    }
}
