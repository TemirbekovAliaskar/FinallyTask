package java12.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java12.config.PostgresConnection;
import java12.dao.CustumerDao;
import java12.entities.*;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustumerDaoImpl implements CustumerDao {

    EntityManagerFactory entityManagerFactory = PostgresConnection.entityManagerFactory();
    @Override
    public String saveCustumer(Custumer custumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(custumer);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        finally {
            entityManager.close();
        }return custumer.getFirstName()+"Saved!";
    }

    @Override
    public String saveCustomerWithRent(Custumer newCustomer, Long houseId, Long agencyId, LocalDate checkIn, LocalDate checkout) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Agency findAgency = entityManager.find(Agency.class, agencyId);
            House findHouse = entityManager.find(House.class, houseId);

            RentInfo rentInfo = new RentInfo();
            rentInfo.setCustumer(newCustomer);
            rentInfo.setHouse(findHouse);
            rentInfo.setAgency(findAgency);
            rentInfo.setCheckin(checkIn);
            rentInfo.setCheckOut(checkout);

            findHouse.setRentInfo(rentInfo);
            findAgency.getRentInfos().add(rentInfo);
            entityManager.persist(rentInfo);
            entityManager.persist(newCustomer);

            if (newCustomer.getRentInfos() == null) {
                newCustomer.setRentInfos(new ArrayList<>());
            }
            newCustomer.getRentInfos().add(rentInfo);

            entityManager.persist(newCustomer);
            entityManager.persist(rentInfo);
            entityManager.getTransaction().commit();
            return newCustomer.getFirstName() + " Successfully saved!!!";
        }catch (Exception e){
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Failed: "+e.getMessage();
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Custumer getCustumerById(Long custumerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Custumer custumer = null;
        try {
            entityManager.getTransaction().begin();
            custumer = entityManager.find(Custumer.class, custumerId);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return custumer;
    }

    @Override
    public List<Custumer> getAllCustumer() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Custumer> list = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            list = entityManager.createQuery("select c from Custumer c", Custumer.class).getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return list;
    }

    @Override
    public Custumer update(Long oldCustumer, Custumer newCustumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Custumer custumer = null;
        try {
            entityManager.getTransaction().begin();
            custumer = entityManager.find(Custumer.class, oldCustumer);
            custumer.setFirstName(newCustumer.getFirstName());
            custumer.setLastName(newCustumer.getLastName());
            custumer.setEmail(newCustumer.getEmail());
            custumer.setDateOfBirth(newCustumer.getDateOfBirth());
            custumer.setGender(newCustumer.getGender());
            custumer.setNationality(newCustumer.getNationality());
            custumer.setFamilyStatus(newCustumer.getFamilyStatus());
            entityManager.merge(custumer);
            entityManager.getTransaction().commit();
        }catch (HibernateException e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return custumer;
    }

    @Override
    public String deleteCustumer(Long custumerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Custumer customer = entityManager.find(Custumer.class, custumerId);

            if (customer != null && customer.getRentInfos() != null) {
                List<RentInfo> rentInfosToRemove = new ArrayList<>();

                for (RentInfo rentInfo : customer.getRentInfos()) {
                    if (rentInfo.getCheckOut() != null && rentInfo.getCheckOut().isBefore(LocalDate.now())) {
                        rentInfosToRemove.add(rentInfo);
                    }
                }
                customer.getRentInfos().removeAll(rentInfosToRemove);
            }
            if (customer != null) {
                entityManager.remove(customer);
            }
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            return "Error: " + e.getMessage();
        } finally {
            entityManager.close();
        }

        return "Success!";
    }
}
