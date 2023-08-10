package com.example.demo.controller;

import com.example.demo.config.SwaggerConfig;
import com.example.demo.constants.ResourcePaths.Card.V1;
import com.example.demo.controller.defination.OtpApi;
import com.example.demo.model.*;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.entity.Customer;
import com.example.demo.service.OtpService;
import com.example.demo.service.impl.OtpServiceImpl;
import io.swagger.annotations.Api;
//import lombok.extern.flogger.Flogger;
//import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(V1.ROOT)
@Validated
@Api(value = "/api", tags = {SwaggerConfig.ServiceTags.HELLO_WORLD_SERVICE})
@Log4j2
public class OtpController implements OtpApi {
    @Autowired
    CustomerRepository customerRepository;
    private final OtpService otpService;

    @Autowired
    public OtpController(OtpServiceImpl otpService){
        this.otpService=otpService;
    }

    @Override
    @PostMapping(value = V1.GET_OTP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OtpResponse> getOTP(@RequestBody GenerateOtpRequest request){
        log.atInfo().log(" getOTP to %s", request.getMobileNo());

       return otpService.getOTP(request);
    }

    @Override
    @PostMapping(value = V1.RESEND_OTP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OtpResponse> reSendOTP(@RequestBody ReSendOtpRequest request){
        log.atInfo().log(" reSendOTP to %s", request.getRefId());

        return otpService.reSendOTP(request);
    }

    @Override
    @PostMapping(value = V1.VALIDATE_OTP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidateOtpResponse> validateOTP(@RequestBody ValidateOtpRequest request){
        log.atInfo().log(" validateOTP to %s", request.getRefId());

        return otpService.validateOTP(request);
    }

    @PostMapping(value = V1.CUSTOMER_R, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer createCustomer(@RequestBody Customer customer) {
        log.atInfo().log(" createCustomer to %s", customer.getMobileNo());
        Customer d= customerRepository.save(customer);

        return d;
    }

}
