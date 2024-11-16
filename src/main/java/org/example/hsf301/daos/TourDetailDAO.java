package org.example.hsf301.daos;

import org.example.hsf301.pojos.TourDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TourDetailDAO implements ITourDetailDAO {
    private final SessionFactory sessionFactory;
    private Configuration configuration;
    public TourDetailDAO(String name) {
        configuration = new Configuration();
        configuration = configuration.configure(name);
        sessionFactory = configuration.buildSessionFactory();
    }
    @Override
    public void save(TourDetail tourDetail) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(tourDetail);
            t.commit();
            System.out.println("Successfully saved");
        } catch (Exception e) {
            // TODO: handle exception
            t.rollback();
            System.out.println("Error" + e.getMessage());
        } finally {
            //	sessionFactory.close();
            session.close();
        }


    }

    @Override
    public List<TourDetail> findAll() {
        List<TourDetail> tourDetails = null;
        Session session = sessionFactory.openSession();
        try {
            tourDetails = session.createQuery("from TourDetail ", TourDetail.class).list();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error" + e.getMessage());
        } finally {
            //sessionFactory.close();
            session.close();
        }
        return tourDetails;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            TourDetail tourDetail = session.get(TourDetail.class, id);
            session.delete(tourDetail);
            t.commit();
            System.out.println("Delete saved");
        } catch (Exception e) {
            // TODO: handle exception
            t.rollback();
            throw e;
        } finally {
//			sessionFactory.close();
            session.close();
        }
    }

    @Override
    public TourDetail findById(Long id) {
        // TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        try {
            return session.get(TourDetail.class, id);
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(TourDetail tourDetail) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(tourDetail);
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
}
