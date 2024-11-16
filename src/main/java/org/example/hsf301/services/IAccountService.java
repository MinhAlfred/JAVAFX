package org.example.hsf301.services;

import java.util.List;
import org.example.hsf301.pojos.Account;


public interface IAccountService {
	void save(Account account);
	List<Account> findAll();
	List<Account> findAllCustomers();
	void delete(Integer id);
	Account findById(Integer id);
	void update(Account account);
	Account login(String email, String password);
	Account findByUserName(String username);
	void signup(Account newAccount);
}
