package com.example.demo.service.impl;

import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;

@Server
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Boolean checkForUserExist(String mobileNo) {
        return null;
    }
}
