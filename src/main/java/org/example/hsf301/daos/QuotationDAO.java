package org.example.hsf301.daos;

import org.example.hsf301.pojos.Quotations;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class QuotationDAO implements  IQuotationDAO{

    private SessionFactory sessionFactory = null;
    private Configuration configuration = null;

    public QuotationDAO(String persistenceName) {
        configuration = new Configuration();
        configuration.configure(persistenceName);
        configuration.addAnnotatedClass(Quotations.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public void save(Quotations quotation) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(quotation);
            transaction.commit();
            System.out.println("Quotation saved successfully");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Quotations> findAll() {
        List<Quotations> quotations = null;
        Session session = sessionFactory.openSession();
        try {
            quotations = session.createQuery("from Quotations", Quotations.class).list();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
        return quotations;
    }

    public Quotations findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Quotations.class, id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    public void update(Quotations quotation) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(quotation);
            transaction.commit();
            System.out.println("Quotation updated successfully");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Quotations quotation = session.get(Quotations.class, id);
            if (quotation != null) {
                session.delete(quotation);
                transaction.commit();
                System.out.println("Quotation deleted successfully");
            } else {
                System.out.println("Quotation not found");
            }
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}