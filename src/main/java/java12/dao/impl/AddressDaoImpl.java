package java12.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java12.config.PostgresConnection;
import java12.dao.AddressDao;
import java12.entities.Address;
import java12.entities.Agency;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressDaoImpl implements AddressDao {
    EntityManagerFactory entityManagerFactory = PostgresConnection.entityManagerFactory();

    @Override
    public Address getAddressById(Long addressId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address address = null;
        try {
            entityManager.getTransaction().begin();
            address = entityManager.find(Address.class, addressId);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return address;
    }

    @Override
    public List<Address> getAllAddress() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Address> address = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            address = entityManager.createQuery("select a from Address a", Address.class).getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return address;
    }

    @Override
    public Address update(Long oldAddress, Address newAddress) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Address address = null;
        try {
            entityManager.getTransaction().begin();
            address = entityManager.find(Address.class, oldAddress);
            address.setCity(newAddress.getCity());
            address.setRegion(newAddress.getRegion());
            address.setStreet(newAddress.getStreet());
            entityManager.merge(address);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return address;
    }

    @Override
    public List<Address> getAddressToAgency() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Address> addresses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            addresses = entityManager.createQuery("select a from Address a inner join Agency ag on a.id = ag.address.id").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return addresses;
    }

    @Override
    public Long getAgencyByAddressName(String cityName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long addressCount = 0L;
        try {
            entityManager.getTransaction().begin();
            List<Long> result = entityManager.createQuery(
                            "select count(a.id) from Address a inner join Agency ag on a.id = ag.address.id where a.city = :ci", Long.class)
                    .setParameter("ci", cityName)
                    .getResultList();
            if (!result.isEmpty()) {
                addressCount = result.get(0);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return addressCount;
    }

    @Override
    public Map<String, List<Agency>> groupByRegion() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Map<String, List<Agency>> map = new HashMap<>();
        List<Agency> agencies = new ArrayList<>();
        String region = null;
        Agency agency = null;
        try {
            entityManager.getTransaction().begin();
            for (Address allAddress : getAllAddress()) {
                region = allAddress.getRegion();
                agency = allAddress.getAgency();
                agencies.add(agency);
                map.put(region, agencies);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
        }
        return map;
    }
}
