package java12.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.PostgresConnection;
import java12.dao.OwnerDao;
import java12.entities.Agency;
import java12.entities.House;
import java12.entities.Owner;
import java12.entities.RentInfo;
import org.hibernate.HibernateException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class OwnerDaoImpl implements OwnerDao {
    EntityManagerFactory entityManagerFactory = PostgresConnection.entityManagerFactory();
    @Override
    public String saveOwner(Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
       try {
           entityManager.getTransaction().begin();
           LocalDate currentDate = LocalDate.now();
           LocalDate dateOfBirth = owner.getDateOfBirth();
           Period age = Period.between(dateOfBirth,currentDate);

           if (age.getYears() >= 18){
               entityManager.persist(owner);
               entityManager.getTransaction().commit();
               return "Success!";
           }else return "Error !";
       }catch (HibernateException e){
           if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
           System.out.println(e.getMessage());
       }finally {
           entityManager.close();
       }
       return "Success!";
    }

    @Override
    public String saveOwner(Owner owner, List<House> house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            LocalDate current = LocalDate.now();
            LocalDate date = owner.getDateOfBirth();
            Period age = Period.between(date,current);

            if (age.getYears() >= 18){
                for (House house1 : house) {
                    house1.setOwner(owner);
                    entityManager.persist(house1);
                }
                entityManager.persist(owner);
                entityManager.getTransaction().commit();
                return "Success !";
            }else return "Not 18 age !";
        }catch (HibernateException e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return null;
    }
    @Override
    public Owner getOwnerById(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner owner = null;
        try {
            entityManager.getTransaction().begin();
            owner = entityManager.find(Owner.class,ownerId);
            entityManager.getTransaction().commit();
        }catch (HibernateException e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return owner;
    }

    @Override
    public List<Owner> getAllOwner() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            owners = entityManager.createQuery("select o from Owner o", Owner.class).getResultList();
            entityManager.getTransaction().commit();
        }catch (HibernateException e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
        return owners;
    }

    @Override
    public Owner update(Long oldOwner, Owner newOwner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner owner = null;
        try {
            entityManager.getTransaction().begin();
            owner = entityManager.find(Owner.class,oldOwner);
            owner.setFirstName(newOwner.getFirstName());
            owner.setLastName(newOwner.getLastName());
            owner.setEmail(newOwner.getEmail());
            owner.setDateOfBirth(newOwner.getDateOfBirth());
            owner.setGender(newOwner.getGender());
            entityManager.merge(owner);
            entityManager.getTransaction().commit();

        }catch (HibernateException e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return owner;
    }

    @Override
    public String deleteOwner(Long ownerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Owner owner = entityManager.find(Owner.class, ownerId);
            if (owner != null) {
                List<House> houses = owner.getHouses();
                if (houses != null && !houses.isEmpty()) {
                    for (House house : houses) {
                        if (house.getRentInfo() != null && house.getRentInfo().getCheckOut().isBefore(LocalDate.now())) {
                            RentInfo rentInfo = house.getRentInfo();
                            entityManager.remove(rentInfo);
                        }
                        entityManager.remove(house);
                    }
                }
                entityManager.remove(owner);
                entityManager.getTransaction().commit();
                return "Owner deleted !";
            } else {
                return "Owner with ID " + ownerId + " not found!";
            }
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
            return "Failed to delete owner!";
        } finally {
            entityManager.close();
        }
    }

    @Override
    public String asSignOwnerToAgency(Long ownerId, Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner owner = null;
        Agency agency = null;
        try {
            entityManager.getTransaction().begin();
            owner = entityManager.find(Owner.class, ownerId);
            agency = entityManager.find(Agency.class, agencyId);

            if (owner != null && agency != null) {
                owner.getAgencies().add(agency);
                agency.getOwners().add(owner);

                entityManager.getTransaction().commit();

                return "Success! Owner assigned to Agency.";
            } else {
                return "Owner or Agency not found!";
            }
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return null;
    }

    @Override
    public List<Owner>  getAllOwnerToAgencyID(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            Agency agency = entityManager.find(Agency.class, agencyId);

            if (agency != null) {
                owners = agency.getOwners(); // Получение списка владельцев из агентства
            }
            entityManager.getTransaction().commit();

        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return owners;
    }

    @Override
    public List<Owner> getOwnerAge() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners = new ArrayList<>();
        try {

            entityManager.getTransaction().begin();
            owners = entityManager.createQuery("select o.dateOfBirth,o.firstName from Owner o", Owner.class).getResultList();
            entityManager.getTransaction().commit();

        }catch (HibernateException e){
            if (entityManager.getTransaction().isActive())entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return owners;
    }
}
