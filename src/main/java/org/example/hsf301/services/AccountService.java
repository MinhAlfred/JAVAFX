package org.example.hsf301.services;

import java.util.ArrayList;
import java.util.List;

import org.example.hsf301.enums.ERole;
import org.example.hsf301.pojos.Account;
import org.example.hsf301.repos.AccountRepository;
import org.example.hsf301.repos.IAccountRepository;

public class AccountService implements IAccountService{
	private final IAccountRepository accountRepo;

	public AccountService(String name) {
		accountRepo = new AccountRepository(name);
	}

	@Override
	public void save(Account account) {
		accountRepo.save(account);
	}

	@Override
	public List<Account> findAll() {
		return accountRepo.findAll();
	}

	@Override
	public List<Account> findAllCustomers() {
		List<Account> accounts = accountRepo.findAll();
		List<Account> customers = new ArrayList<Account>();
		for (Account account : accounts) {
			if(account.getRole()== ERole.CUSTOMER)
				customers.add(account);
		}
		return customers;
	}

	@Override
	public void delete(Integer id) {
		accountRepo.delete(id);
	}

	@Override
	public Account findById(Integer id) {
		return accountRepo.findById(id);
	}

	@Override
	public void update(Account account) {
		accountRepo.update(account);
	}

	@Override
	public Account login(String email, String password) {
		return accountRepo.login(email, password);
	}

	@Override
	public Account findByUserName(String username) {
		return accountRepo.findByUserName(username);
	}

	@Override
	public void signup(Account newAccount) {
		accountRepo.signup(newAccount);
	}


}
