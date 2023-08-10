package com.example.demo.controller.defination;

import com.example.demo.model.*;
import org.springframework.http.ResponseEntity;

public interface OtpApi {
    public ResponseEntity<OtpResponse> getOTP(GenerateOtpRequest request);
    public ResponseEntity<OtpResponse> reSendOTP(ReSendOtpRequest request);
    public ResponseEntity<ValidateOtpResponse> validateOTP(ValidateOtpRequest request);
}
