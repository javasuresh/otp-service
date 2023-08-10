package com.example.demo.repository;

import com.example.demo.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {


    Optional<Customer> findByMobileNo(String mobileNo);
}
