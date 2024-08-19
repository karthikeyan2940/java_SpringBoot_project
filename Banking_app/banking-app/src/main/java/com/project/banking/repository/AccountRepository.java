package com.project.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account , Long > {
	

}
