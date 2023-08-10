package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Constants;
import com.example.demo.model.GenerateOtpRequest;
import com.example.demo.model.OtpResponse;
import com.example.demo.model.ReSendOtpRequest;
import com.example.demo.model.ValidateOtpRequest;
import com.example.demo.model.ValidateOtpResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.entity.OtpInfo;
import com.example.demo.service.OtpService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OtpServiceImpl implements OtpService {

    @Autowired
    OtpRepository otpRepository;
    @Autowired
    CustomerRepository customerRepository;



    @Override
    public ResponseEntity getOTP(GenerateOtpRequest request) {
        log.atInfo().log(" getOTP method to %s", request.getMobileNo());

        try {
            // Check user Existence in DB
            Boolean userExist = customerRepository.findByMobileNo(request.getMobileNo()).isPresent();
            if (userExist) {
                Integer otp = generateOTP();
                String refId = getUniqueId(request.getMobileNo());

                OtpInfo entity = new OtpInfo();
                entity.setRefId(refId);
                entity.setUserIdentity(request.getMobileNo());
                entity.setOtpNumber(otp);
                entity.setGeneratedTime(LocalDateTime.now());
                entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
                entity.setAttemptsLeft(3);
                otpRepository.save(entity);

                OtpResponse response = new OtpResponse();
                response.setRefId(refId);
                response.setOtpNumber(otp);
                response.setAttemptsLeft(3);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                OtpResponse response = new OtpResponse();
                response.setStatus(Boolean.FALSE);
                response.setMessage(Constants.MOBILE_DOES_NOT_EXIST);
                return ResponseEntity.status(HttpStatus.FOUND).body(response);
            }
        }catch(Exception e){
            OtpResponse response = new OtpResponse();
            response.setStatus(Boolean.FALSE);
            response.setMessage(Constants.SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity reSendOTP(ReSendOtpRequest request) {


        log.atInfo().log(" reSendOTP Service to %s", request.getRefId());
        try{

            Optional<OtpInfo> entity =otpRepository.findById(request.getRefId());
            if(entity.isPresent()) {
                if(entity.get().getAttemptsLeft()==0){
                    OtpResponse response = new OtpResponse();
                    response.setStatus(Boolean.FALSE);
                    response.setMessage(Constants.MAX_FAILED_ATTEMPS);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                }else {
                    Integer otp=generateOTP();
                    Integer attemptsLeft=entity.get().getAttemptsLeft() - 1;
                    entity.get().setOtpNumber(otp);
                    entity.get().setGeneratedTime(LocalDateTime.now());
                    entity.get().setExpiryTime(LocalDateTime.now().plusMinutes(Constants.OTP_EXPIRED_TIME));
                    entity.get().setAttemptsLeft(attemptsLeft);
                    otpRepository.save(entity.get());
                    OtpResponse response = new OtpResponse();
                    response.setRefId(request.getRefId());
                    response.setOtpNumber(otp);
                    response.setAttemptsLeft(attemptsLeft);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }else{
                OtpResponse response = new OtpResponse();
                response.setStatus(Boolean.FALSE);
                response.setMessage(Constants.REF_ID_DOES_NOT_EXIST);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        }catch(Exception e){
            OtpResponse response = new OtpResponse();
            response.setStatus(Boolean.FALSE);
            response.setMessage(Constants.SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity validateOTP(ValidateOtpRequest request) {
        log.atInfo().log(" validateOTP Service to %s", request.getRefId());

        try {
            Optional<OtpInfo> entity = otpRepository.findById(request.getRefId());
            if (entity.isPresent()) {
                int requestOtp = (request.getOtpNumber());
                if (entity.get().getOtpNumber() == requestOtp && LocalDateTime.now().isBefore(entity.get().getExpiryTime())) {
                    ValidateOtpResponse response = new ValidateOtpResponse();
                    response.setStatus(Boolean.TRUE);
                    response.setMessage(Constants.OTP_SUCCESS);
                    entity.get().setConfirmedTime(LocalDateTime.now());
                    otpRepository.save(entity.get());
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }else{
                    ValidateOtpResponse response = new ValidateOtpResponse();
                    response.setStatus(Boolean.FALSE);
                    response.setMessage(Constants.OTP_NOT_FOUND);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            }else {
                ValidateOtpResponse response = new ValidateOtpResponse();
                response.setStatus(Boolean.FALSE);
                response.setMessage(Constants.REF_ID_DOES_NOT_EXIST);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ValidateOtpResponse response = new ValidateOtpResponse();
            response.setStatus(Boolean.FALSE);
            response.setMessage(Constants.SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


    private Integer generateOTP(){
        Random random = new Random();
        int OTP =100000 +  random.nextInt(900000);
        return OTP;
    }


    public String getUniqueId(String phoneNo){
        Long someNumber = Long.parseLong(phoneNo);
        Long lastFive = someNumber % 100000;
        String uniqueNo=System.currentTimeMillis()+""+lastFive;
        return uniqueNo;
    }

    /*@Override
    public ResponseEntity validateOTP(ValidateOtpRequest request) {
        try {
            //get Otp and time from DB
            ValidateOtpResponse response = new ValidateOtpResponse();
            Optional<OtpEntity> entity =otpRepository.findById(request.getRefId());
            if (entity.isPresent()) {
                // Validate Otp and Expiry time
                LocalDateTime expiredAt = entity.get().getExpiryTime();
                if (entity.get().getConfirmedAt() == null) {
                    if (!expiredAt.isBefore(LocalDateTime.now())) {
                        setConfirmedAt(request.getOtpNumber());
                        return ResponseEntity.status(HttpStatus.OK).body(Constants.OTP_SUCCESS);
                    }else{
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.OTP_EXPIRED);
                    }
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.MOBILE_ALREADY_CONFIRMED);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.REF_ID_DOES_NOT_EXIST);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.SERVER_ERROR);
        }
        // return response

    }*/


}