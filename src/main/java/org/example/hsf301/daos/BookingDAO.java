package org.example.hsf301.daos;

import org.example.hsf301.pojos.BookingKoiDetail;
import org.example.hsf301.pojos.Bookings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BookingDAO implements IBookingDAO {

    private final SessionFactory sessionFactory;
    private Configuration configuration;

    public BookingDAO(String name) {
        configuration = new Configuration();
        configuration = configuration.configure(name);
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public List<Bookings> findAll() {
        List<Bookings> bookings = null;
        Session session = sessionFactory.openSession();
        try {
            bookings = session.createQuery("from Bookings", Bookings.class).list();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error" + e.getMessage());
        } finally {
            //sessionFactory.close();
            session.close();
        }
        return bookings;
    }

    @Override
    public void save(Bookings booking) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(booking);
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
    public void delete(Long bookingID) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            Bookings booking = session.get(Bookings.class, bookingID);
            session.delete(booking);
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
    public Bookings findById(Long bookingID) {
        // TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        try {
            return session.get(Bookings.class, bookingID);
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Bookings booking) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(booking);
            t.commit();
            System.out.println("Update successfully");
        } catch (Exception e) {
            // TODO: handle exception
            t.rollback();
            System.out.println("Error " + e.getMessage());
        } finally {
            //sessionFactory.close();
            session.close();
        }
    }

    @Override
    public List<Bookings> findByAccountID(String accountID) {
        List<Bookings> bookings = null;
        Session session = sessionFactory.openSession();
        try {
            bookings = session.createQuery("from Bookings where account.id = :accountID",
                                           Bookings.class).setParameter("accountID", accountID)
                .list();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return bookings;
    }

    @Override
    public List<BookingKoiDetail> findByBookingID(Long bookingID) {
        List<BookingKoiDetail> bookingKoiDetails = null;
        Session session = sessionFactory.openSession();
        try {
            bookingKoiDetails = session.createQuery(
                    "from BookingKoiDetail where booking.id = :bookingID",
                    BookingKoiDetail.class).setParameter("bookingID", bookingID)
                .list();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return bookingKoiDetails;
    }

    public static void main(String[] args) {

        for (Bookings booking : new BookingDAO("hibernate.cfg.xml").findByAccountID("minh1")) {
            System.out.println(booking);
        }

    }

}
