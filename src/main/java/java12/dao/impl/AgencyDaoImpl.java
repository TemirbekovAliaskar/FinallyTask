package java12.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.PostgresConnection;
import java12.dao.AgencyDao;
import java12.entities.Address;
import java12.entities.Agency;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgencyDaoImpl implements AgencyDao {
    EntityManagerFactory entityManagerFactory = PostgresConnection.entityManagerFactory();

    @Override
    public String saveAgency(Agency agency,Address address) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            agency.setAddress(address);
            address.setAgency(agency);
            entityManager.persist(agency);

            entityManager.persist(address);
            entityManager.getTransaction().commit();
        }catch (HibernateException e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        entityManager.close();
        return agency.getName() + "Saved !";
    }

    @Override
    public Agency getAgencyById(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Agency agency = null;

        try {
            entityManager.getTransaction().begin();
            agency = entityManager.find(Agency.class, agencyId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return agency;
    }

    @Override
    public List<Agency> getAllAgency() {
        List<Agency> resultList = new ArrayList<>();
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            resultList = entityManager.createQuery("select a from Agency a", Agency.class).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    @Override
    public Agency update(Long oldAgency, Agency newAgency) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Agency agency = entityManager.find(Agency.class, oldAgency);
        agency.setName(newAgency.getName());
        agency.setPhoneNumber(newAgency.getPhoneNumber());
        entityManager.merge(agency);
        entityManager.getTransaction().commit();
        entityManager.close();
        return agency;
    }

    @Override
    public String deleteAgency(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Agency agency = entityManager.find(Agency.class, agencyId);
        entityManager.remove(agency);
        entityManager.getTransaction().commit();
        entityManager.close();
        return agency.getName()+ "Deleted !";
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.startsWith("996") && phoneNumber.length() >= 13;
    }
}
