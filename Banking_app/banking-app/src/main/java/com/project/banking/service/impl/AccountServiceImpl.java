package com.project.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.banking.dto.AccountDto;
import com.project.banking.entity.Account;
import com.project.banking.mapper.AccountMapper;
import com.project.banking.repository.AccountRepository;
import com.project.banking.service.AccountService;


@Service
public class AccountServiceImpl  implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	

	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	} 


	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepository.save(account);
		return  AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doent exist"));
		return AccountMapper.mapToAccountDto(account);
	}


	@Override
	public AccountDto deposit(Long id, Double amount) {
		Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doent exist"));
		double total = account.getBalance()+ amount;
		account.setBalance(total);
		Account SavedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(SavedAccount);
	}


	@Override
	public AccountDto withdraw(Long id, Double amount) {
		Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doent exist"));
		
		if(account.getBalance()< amount) {
			throw new RuntimeException("Insufficient Balance ");
		}
		
		double total = account.getBalance()- amount;
		account.setBalance(total);
		Account SavedAccount = accountRepository.save(account);


		return AccountMapper.mapToAccountDto(SavedAccount);
	}


	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map((account)-> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
		 
	}


	@Override
	public void deleteAccount(Long id) {
		
		Account account = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account doent exist"));
		
		accountRepository.deleteById(id);

	}
	


	

}
