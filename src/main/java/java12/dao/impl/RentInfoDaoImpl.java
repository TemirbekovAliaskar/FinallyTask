package java12.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.PostgresConnection;
import java12.dao.RentInfoDao;
import java12.entities.Agency;
import java12.entities.RentInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentInfoDaoImpl implements RentInfoDao {
    EntityManagerFactory entityManagerFactory = PostgresConnection.entityManagerFactory();
    @Override
    public List<RentInfo> getInfoCheckouts(LocalDate date1, LocalDate date2) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<RentInfo> rentInfos = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            rentInfos = entityManager.createQuery("""
                select r from RentInfo r where r.checkOut between :date1 and :date2
                """, RentInfo.class)
                    .setParameter("date1", date1)
                    .setParameter("date2", date2).getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return rentInfos;
    }

    @Override
    public Long countHousesNow(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long countOfHouses = null;
        try {
            entityManager.getTransaction().begin();
            Agency foundAgency = entityManager.find(Agency.class, agencyId);
            countOfHouses = entityManager.createQuery("""
                SELECT COUNT(h) FROM House h 
                                    WHERE h.rentInfo.checkout > CURRENT_DATE AND h.rentInfo.agency.id = :agencyId
                                     """, Long.class).setParameter("agencyId", agencyId).getSingleResult();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive())
                System.out.println(e.getMessage());
        }
        return countOfHouses;
    }
}
