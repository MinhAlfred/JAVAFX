package org.example.hsf301.daos;

import org.example.hsf301.pojos.BookingTourDetail;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class BookingTourDetailDAO implements IBookingTourDetailDAO {
    private final SessionFactory sessionFactory;
    private Configuration configuration;
    public BookingTourDetailDAO(String name) {
        configuration = new Configuration();
        configuration = configuration.configure(name);
        sessionFactory = configuration.buildSessionFactory();
    }
    @Override
    public List<BookingTourDetail> getAll() {
        List<BookingTourDetail> bookingTourDetails = null;
        Session session = sessionFactory.openSession();
        try {
            bookingTourDetails = session.createQuery("from BookingTourDetail ", BookingTourDetail.class).list();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error" + e.getMessage());
        } finally {
            //sessionFactory.close();
            session.close();
        }
        return bookingTourDetails;
    }

    @Override
    public void save(BookingTourDetail bookingTourDetail) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(bookingTourDetail);
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
    public List<BookingTourDetail> findByBookingId(Long bookingId) {
        List<BookingTourDetail> bookingTourDetails = new ArrayList<BookingTourDetail>();
        Session session = sessionFactory.openSession();
        Transaction t =null;
        try {
            t = session.beginTransaction();
            Query<BookingTourDetail> query = session.createQuery(
                    "FROM BookingTourDetail d "
                            + "JOIN FETCH d.booking "
                            + "JOIN FETCH d.tour "
                            + "WHERE d.booking.id = :bookingId", BookingTourDetail.class);
            query.setParameter("bookingId", bookingId);
            bookingTourDetails = query.getResultList();
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            e.printStackTrace();
        }
        return bookingTourDetails;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            BookingTourDetail bookingTourDetail = session.get(BookingTourDetail.class, id);
            session.delete(bookingTourDetail);
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
    public BookingTourDetail findById(Long id) {
        // TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        try {
            return session.get(BookingTourDetail.class, id);
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(BookingTourDetail bookingTourDetail) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(bookingTourDetail);
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
