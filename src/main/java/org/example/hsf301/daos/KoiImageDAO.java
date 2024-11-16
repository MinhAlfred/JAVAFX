package org.example.hsf301.daos;

import org.example.hsf301.pojos.KoiImage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * @author KoiImageDAO
 */
public class KoiImageDAO implements IKoiImageDAO{

    private SessionFactory sessionFactory = null;

    public KoiImageDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }
    @Override
    public void save(KoiImage koiImage) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(koiImage);
            transaction.commit();
            System.out.println("KoiImage saved successfully");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error saving KoiImage: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            KoiImage koiImage = session.get(KoiImage.class, id);
            if (koiImage != null) {
                session.delete(koiImage);
                transaction.commit();
                System.out.println("KoiImage deleted successfully");
            } else {
                System.out.println("KoiImage not found with id: " + id);
            }
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error deleting KoiImage: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteByKois(Long koiId) {

    }

    @Override
    public List<KoiImage> findByKoiId(Long koiId) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM KoiImage k WHERE k.kois.id = :koiId";
            return session.createQuery(hql, KoiImage.class)
                    .setParameter("koiId", koiId)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding KoiImage by koi ID: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }
}