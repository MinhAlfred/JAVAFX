package org.example.hsf301.daos;

import java.util.ArrayList;
import java.util.List;
import org.example.hsf301.pojos.Tours;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TourDAO  implements  ITourDAO{

    private SessionFactory sessionFactory = null;
    private Configuration cf = null;

    public TourDAO(String persistanceName) {
        cf = new Configuration();
        cf = cf.configure(persistanceName);
        sessionFactory = cf.buildSessionFactory();
    }

    @Override
    public void save(Tours tours) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(tours);
            t.commit();
            System.out.println("Successfully saved");
        } catch (Exception e) {
            t.rollback();
            System.out.println("Error " + e.getMessage());
            // TODO: handle exception
        } finally {
//			sessionFactory.close();
            session.close();
        }
    }

    @Override
    public List<Tours> findAll() {
        List<Tours> tours = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            tours = session.createQuery("from Tours ", Tours.class).list();
        } catch (Exception e) {
            System.out.println("Erorr: " + e.getMessage());
        }
        return tours;
    }

    @Override
    public List<Tours> findByTourName(String tourName) {
        List<Tours> tours = null;
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Tours WHERE tourName like :tourNameHql";
            tours = session.createQuery(hql, Tours.class)
                    .setParameter("tourNameHql", "%" + tourName + "%")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
        return tours;
    }

    @Override
    public List<Tours> findByTourActive() {
        List<Tours> tours = null;
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Tours WHERE status = :status";
            tours = session.createQuery(hql, Tours.class)
                    .setParameter("status", "active")
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
        return tours;
    }


    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction t = session.getTransaction();
        try {
            t.begin();
            Tours tour = session.get(Tours.class, id);
            session.delete(tour);
            t.commit();
            System.out.println("Successfully Delete");
        } catch (Exception e) {
            t.rollback();
            System.out.println("Error " + e.getMessage());
        } finally {
//			sessionFactory.close();
            session.close();
        }
    }

    @Override
    public Tours findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Tours.class, id);
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Tours tours) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(tours);
            t.commit();
            System.out.println("Update successfully");
        } catch (Exception e) {
            // TODO: handle exception
            t.rollback();
            System.out.println("Error "+e.getMessage());
        } finally {
            //sessionFactory.close();
            session.close();
        }
    }

    public static void main(String[] args) {

        for (Tours tours : new TourDAO("hibernate.cfg.xml").findAll()) {
            System.out.println(tours);
        }

    }


}
