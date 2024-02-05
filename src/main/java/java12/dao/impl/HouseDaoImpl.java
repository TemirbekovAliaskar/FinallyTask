package java12.dao.impl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java12.config.PostgresConnection;
import java12.dao.HouseDao;
import java12.entities.House;
import java12.entities.Owner;
import java12.entities.RentInfo;
import org.hibernate.HibernateException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HouseDaoImpl implements HouseDao {
    EntityManagerFactory entityManagerFactory = PostgresConnection.entityManagerFactory();

    @Override
    public String saveHouse(House house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(house.getAddress());
            entityManager.persist(house);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return "Success!";
    }

    @Override
    public String saveHouse(Owner owner, List<House> house) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            for (House houses : house) {
                RentInfo rentInfo = new RentInfo();
                houses.setRentInfo(rentInfo);
                rentInfo.setHouse(houses);

                entityManager.persist(houses.getAddress());
                entityManager.persist(rentInfo);
                entityManager.persist(houses);
            }
            owner.setHouses(house);
            entityManager.persist(owner);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
        return null;
    }

    @Override
    public House getHouseById(Long houseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        House house = null;
        try {
            entityManager.getTransaction().begin();
            house = entityManager.find(House.class, houseId);
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return house;
    }

    @Override
    public List<House> getAllHouse() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h", House.class).getResultList();
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
        return houses;
    }

    @Override
    public House update(Long oldHouse, House newHouse) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        House house = null;
        try {
            entityManager.getTransaction().begin();
            house = entityManager.find(House.class, oldHouse);
            house.setHouseType(newHouse.getHouseType());
            house.setPrice(newHouse.getPrice());
            house.setRating(newHouse.getRating());
            house.setDescription(newHouse.getDescription());
            house.setRoom(newHouse.getRoom());
            house.setFurniture(newHouse.isFurniture());
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return house;
    }

    @Override
    public String deleteHouse(Long houseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            House house = entityManager.find(House.class, houseId);
            if (house != null) {
                RentInfo rentInfo = house.getRentInfo();
                if (rentInfo != null) {
                    LocalDate currentDate = LocalDate.now();
                    LocalDate moveOutDate = rentInfo.getCheckOut();
                    if (moveOutDate != null && moveOutDate.isBefore(currentDate)) {
                        entityManager.remove(rentInfo);
                        entityManager.remove(house);
                    } else {
                        return "Нельзя удалить дом, так как арендный срок еще не истек.";
                    }
                } else {
                    entityManager.remove(house);
                }
                entityManager.getTransaction().commit();
            } else {
                return "Дом с ID " + houseId + " не найден.";
            }
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            entityManager.close();
        }
        return null;
    }

    @Override
    public List<House> getAgencyHouses(Long agencyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h inner join RentInfo  r on h.id = r.house.id inner join Agency  ag on r.agency.id = ag.id where ag.id = ?1").setParameter(1, agencyId).getResultList();
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public List<House> getOwnerHouses(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h inner join Owner  o on h.owner.id = o.id where o.id = ?1").setParameter(1,id).getResultList();
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }



    @Override
    public List<House> getRegion(String region) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();
        try {
            entityManager.getTransaction().begin();
            houses = entityManager.createQuery("select h from House h where h.address.region = ?1", House.class).setParameter(1, region).getResultList();
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return houses;
    }

    @Override
    public List<House> getCheck(LocalDate checkin, LocalDate checkout) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<House> houses = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();
            String jpql = "select h from House h " +
                    "where h.rentInfo.checkin between :checkin and :checkout " +
                    "or h.rentInfo.checkOut between :checkin and :checkout";

            houses = entityManager.createQuery(jpql, House.class)
                    .setParameter("checkin", checkin)
                    .setParameter("checkout", checkout)
                    .getResultList();

            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }

        return houses;
    }

}
