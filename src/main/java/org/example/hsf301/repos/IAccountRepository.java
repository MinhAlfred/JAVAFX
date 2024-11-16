package org.example.hsf301.repos;

import java.util.List;
import org.example.hsf301.pojos.Account;

public interface IAccountRepository {

	void save(Account account);
	List<Account> findAll();
	void delete(Integer id);
	Account findById(Integer id);
	void update(Account account);
	Account login(String email, String password);
	Account findByUserName(String username);
	void signup(Account newAccount);
}
