package org.example.hsf301.repos;

import java.util.List;
import org.example.hsf301.daos.AccountDAO;
import org.example.hsf301.daos.IAccountDAO;
import org.example.hsf301.pojos.Account;


public class AccountRepository implements IAccountRepository {

	private final IAccountDAO accountDAO;

	public AccountRepository(String jpaName) {
		accountDAO = new AccountDAO(jpaName);
	}

	@Override
	public void save(Account account) {
		accountDAO.save(account);
	}

	@Override
	public List<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	public void delete(Integer id) {
		accountDAO.delete(id);
	}

	@Override
	public Account findById(Integer id) {
		return accountDAO.findById(id);
	}

	@Override
	public void update(Account account) {
		accountDAO.update(account);
	}

	@Override
	public Account login(String email, String password) {
		return accountDAO.login(email, password);
	}

	@Override
	public Account findByUserName(String username) {
		return accountDAO.findByUserName(username);
	}

	@Override
	public void signup(Account newAccount) {
		accountDAO.signup(newAccount);
	}

}
