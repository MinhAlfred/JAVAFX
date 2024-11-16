package org.example.hsf301.daos;

import org.example.hsf301.pojos.KoiFarms;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class KoiFarmsDAO implements IKoiFarmsDAO {

    private SessionFactory sessionFactory = null;

    public KoiFarmsDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }

    @Override
    public void save(KoiFarms koiFarms) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(koiFarms);
            transaction.commit();
            System.out.println("KoiFarms saved successfully.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error saving koi farm: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<KoiFarms> findAll() {
        List<KoiFarms> farmsList = null;
        Session session = sessionFactory.openSession();
        try {
            farmsList = session.createQuery("from KoiFarms", KoiFarms.class).list();
        } catch (Exception e) {
            System.out.println("Error retrieving farms list: " + e.getMessage());
        } finally {
            session.close();
        }
        return farmsList;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            KoiFarms farm = session.get(KoiFarms.class, id);
            if (farm != null) {
                session.delete(farm);
                transaction.commit();
                System.out.println("Koi farm deleted successfully.");
            } else {
                System.out.println("Koi farm with ID " + id + " not found.");
            }
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error deleting koi farm: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public KoiFarms findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(KoiFarms.class, id);
        } catch (Exception e) {
            System.out.println("Error finding koi farm: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(KoiFarms koiFarms) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(koiFarms);
            transaction.commit();
            System.out.println("Koi farm updated successfully.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error updating koi farm: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<KoiFarms> findByFarmName(String farmName) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM KoiFarms WHERE farmName LIKE :farmName";
            return session.createQuery(hql, KoiFarms.class)
                    .setParameter("farmName", "%" + farmName + "%")
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding farms by name: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<KoiFarms> findActiveFarms() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM KoiFarms WHERE isActive = true";
            return session.createQuery(hql, KoiFarms.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding active farms: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }
}