package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.OtpInfo;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpInfo, String> {

        Optional<OtpInfo> findByUserIdentity(String mobileNo);


}
