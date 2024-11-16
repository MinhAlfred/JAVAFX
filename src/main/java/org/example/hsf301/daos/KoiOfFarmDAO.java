package org.example.hsf301.daos;

import org.example.hsf301.pojos.KoiOfFarm;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class KoiOfFarmDAO implements IKoiOfFarmDAO {

    private SessionFactory sessionFactory = null;

    public KoiOfFarmDAO(String persistenceName) {
        Configuration cf = new Configuration();
        cf = cf.configure(persistenceName);
        sessionFactory = cf.buildSessionFactory();
    }

    @Override
    public void save(KoiOfFarm koiOfFarm) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(koiOfFarm);
            transaction.commit();
            System.out.println("KoiOfFarm saved successfully.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error saving KoiOfFarm: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<KoiOfFarm> findAll() {
        List<KoiOfFarm> koiOfFarmList = null;
        Session session = sessionFactory.openSession();
        try {
            koiOfFarmList = session.createQuery("from KoiOfFarm", KoiOfFarm.class).list();
        } catch (Exception e) {
            System.out.println("Error retrieving KoiOfFarm list: " + e.getMessage());
        } finally {
            session.close();
        }
        return koiOfFarmList;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            KoiOfFarm koiOfFarm = session.get(KoiOfFarm.class, id);
            if (koiOfFarm != null) {
                session.delete(koiOfFarm);
                transaction.commit();
                System.out.println("KoiOfFarm deleted successfully.");
            } else {
                System.out.println("KoiOfFarm with ID " + id + " not found.");
            }
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error deleting KoiOfFarm: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public KoiOfFarm findById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(KoiOfFarm.class, id);
        } catch (Exception e) {
            System.out.println("Error finding KoiOfFarm: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(KoiOfFarm koiOfFarm) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(koiOfFarm);
            transaction.commit();
            System.out.println("KoiOfFarm updated successfully.");
        } catch (Exception e) {
            transaction.rollback();
            System.out.println("Error updating KoiOfFarm: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public List<KoiOfFarm> findByFarmId(Long farmId) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM KoiOfFarm k WHERE k.koiFarms.id = :farmId";
            return session.createQuery(hql, KoiOfFarm.class)
                    .setParameter("farmId", farmId)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding KoiOfFarm by farm ID: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<KoiOfFarm> findByKoiId(Long koiId) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM KoiOfFarm k WHERE k.koi.id = :koiId";
            return session.createQuery(hql, KoiOfFarm.class)
                    .setParameter("koiId", koiId)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding KoiOfFarm by koi ID: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<KoiOfFarm> findAvailable() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM KoiOfFarm k WHERE k.available = true AND k.quantity > 0";
            return session.createQuery(hql, KoiOfFarm.class)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error finding available KoiOfFarm: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public KoiOfFarm findByFarmIdAndKoiId(Long farmId, Long koiId) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM KoiOfFarm kf WHERE kf.koiFarms.id = :farmId AND kf.koi.id = :koiId";
            return session.createQuery(hql, KoiOfFarm.class)
                    .setParameter("farmId", farmId)
                    .setParameter("koiId", koiId)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error finding KoiOfFarm by farm ID and koi ID: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

}