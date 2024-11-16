
package org.example.hsf301.daos;

import org.example.hsf301.pojos.Koi;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class KoiDAO implements IKoiDAO{

    private SessionFactory sessionFactory = null;

    public KoiDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }

    // Save a new Koi record
    @Override
    public void save(Koi koi) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(koi);
            transaction.commit();
            System.out.println("Koi saved successfully.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error saving koi: " + e.getMessage());
        }
    }

    // Find all Koi records
    @Override
    public List<Koi> findAll() {
        List<Koi> koiList = null;
        Session session = sessionFactory.openSession();
        try {
            koiList = session.createQuery("from Koi", Koi.class).list();
        } catch (Exception e) {
            System.out.println("Error retrieving koi list: " + e.getMessage());
        } finally {
            session.close();
        }
        return koiList;
    }

    // Delete a Koi record by ID
    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Koi koi = session.get(Koi.class, id);
            if (koi != null) {
                session.delete(koi);
                transaction.commit();
                System.out.println("Koi deleted successfully.");
            } else {
                System.out.println("Koi with ID " + id + " not found.");
            }
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error deleting koi: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Find a Koi record by ID
    @Override
    public Koi findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Koi.class, id);
        } catch (Exception e) {
            System.out.println("Error finding koi: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    // Update an existing Koi record
    @Override
    public void update(Koi koi) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(koi);
            transaction.commit();
            System.out.println("Koi updated successfully.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error updating koi: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Find Koi records by a specific color
    @Override
    public List<Koi> findByColor(String color) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Koi WHERE color = :color";
            return session.createQuery(hql, Koi.class)
                    .setParameter("color", color)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding koi by color: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }
    @Override
    public List<Koi> findByKoiName(String koiName) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM Koi WHERE koiName = :koiName";
            return session.createQuery(hql, Koi.class)
                    .setParameter("koiName", koiName)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding koi by name: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Koi> findAllActive() {
        Session session = sessionFactory.openSession();
        List<Koi> activeKoiList = null;
        try {
            String hql = "FROM Koi WHERE isActive = true";
            activeKoiList = session.createQuery(hql, Koi.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error finding active koi: " + e.getMessage());
        } finally {
            session.close();
        }
        return activeKoiList;
    }

}
