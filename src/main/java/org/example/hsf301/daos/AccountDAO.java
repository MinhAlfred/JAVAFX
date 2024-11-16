package org.example.hsf301.daos;

import org.example.hsf301.pojos.Account;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class AccountDAO implements IAccountDAO {

	private SessionFactory sessionFactory = null;
	private Configuration cf = null;

	public AccountDAO(String persistanceName) {
		cf = new Configuration();
		cf = cf.configure(persistanceName);
		sessionFactory = cf.buildSessionFactory();
	}

	@Override
	public void save(Account account) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(account);
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
	public List<Account> findAll() {
		List<Account> Accounts = null;
		Session session = sessionFactory.openSession();
		try {
			Accounts = session.createQuery("from Account", Account.class).list();
		} catch (Exception e) {
			System.out.println("Erorr: " + e.getMessage());
		}
        return Accounts;
	}

	@Override
	public void delete(Integer id) {
		Session session = sessionFactory.openSession();
		Transaction t = session.getTransaction();
		try {
			t.begin();
			Account account = session.get(Account.class, id);
			session.delete(account);
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
	public Account findById(Integer id) {
		Session session = sessionFactory.openSession();
		try {
			return session.get(Account.class, id);
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Account account) {
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(account);
			t.commit();
			System.out.println("Update Success");
		} catch (Exception e) {
			t.rollback();
			System.out.println("Error " + e.getMessage());
		} finally {
//			sessionFactory.close();
			session.close();
		}

	}

	@Override
	public List<Account> findByPassword(String password) {
		Session session = sessionFactory.openSession();
		try {
			String hql = "FROM Account WHERE password = :password";
			return session.createQuery(hql, Account.class)
				.setParameter("password", password)
				.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public Account findByUserName(String username) {
		Session session = sessionFactory.openSession();
		try {
			String hql = "FROM Account WHERE username = :username";
			return session.createQuery(hql, Account.class)
				.setParameter("username", username)
				.uniqueResult();
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public Account login(String email, String password) {
		Session session = sessionFactory.openSession();
		try {
			// Assuming password is already hashed before calling this method
			String hql = "FROM Account WHERE username = :email AND password = :password";

			Account account = session.createQuery(hql, Account.class)
				.setParameter("email", email)
				.setParameter("password", password)
				.uniqueResult();  // Retrieve a single result

			// If no account is found, return null
			if (account == null) {
				System.out.println("Invalid email or password.");
			}
			return account;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public void signup(Account newAccount) {
		// Check if the email is already in use
		Account existingAccount = findByUserName(newAccount.getUsername());
		if (existingAccount != null) {
			throw new IllegalArgumentException("Username already in use.");
		}

		// Save the new account
		save(newAccount);
	}

}
