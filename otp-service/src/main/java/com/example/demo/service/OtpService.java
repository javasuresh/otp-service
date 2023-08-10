package com.example.demo.service;

import com.example.demo.model.GenerateOtpRequest;
import com.example.demo.model.OtpResponse;
import com.example.demo.model.ReSendOtpRequest;
import com.example.demo.model.ValidateOtpRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface OtpService {

    //public OtpResponse getOTP(GenerateOtpRequest request);
    //public OtpResponse reSendOTP(ReSendOtpRequest request);
    //public String validateOTP(ValidateOtpRequest request);
    public ResponseEntity getOTP(GenerateOtpRequest request);
    public ResponseEntity reSendOTP(ReSendOtpRequest request);
    public ResponseEntity validateOTP(ValidateOtpRequest request);

}
