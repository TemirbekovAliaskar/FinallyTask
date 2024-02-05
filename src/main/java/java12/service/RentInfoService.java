package java12.service;

import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.List;

public interface RentInfoService {
    List<RentInfo> getInfoCheckouts(LocalDate date1, LocalDate date2);
    Long countHousesNow(Long agencyId);
}
