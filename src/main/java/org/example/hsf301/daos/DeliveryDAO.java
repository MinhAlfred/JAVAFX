package org.example.hsf301.daos;

import org.example.hsf301.pojos.Delivery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DeliveryDAO implements IDeliveryDAO{
    private final SessionFactory sessionFactory;
    private Configuration configuration;

    public DeliveryDAO(String name) {
        configuration = new Configuration();
        configuration = configuration.configure(name);
        sessionFactory = configuration.buildSessionFactory();
    }
    @Override
    public List<Delivery> getAll() {
        List<Delivery> students = null;
        Session session = sessionFactory.openSession();
        try {
            students = session.createQuery("from Delivery", Delivery.class).list();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error" + e.getMessage());
        } finally {
            //sessionFactory.close();
            session.close();
        }
        return students;
    }

    @Override
    public void save(Delivery student) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(student);
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
    public void delete(Long studentID) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            Delivery student = session.get(Delivery.class, studentID);
            session.delete(student);
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
    public Delivery findById(Long studentID) {
        // TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        try {
            return session.get(Delivery.class, studentID);
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Delivery student) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(student);
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
