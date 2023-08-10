package com.example.demo.repository.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "iban")
    private String iban;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "first_name")
    private String firstName;
    /*@Column(name = "second_name")
    private String secondName;
    @Column(name = "third_name")
    private String thirdName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "english_first_name")
    private String englishFirstName;
    @Column(name = "english_second_name")
    private String englishSecondName;
    @Column(name = "english_third_name")
    private String englishThirdName;
    @Column(name = "english_last_name")
    private String englishLastName;
    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "grand_father_name")
    private String grandFatherName;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "date_type")
    private String dateType;
    @Column(name = "gender")
    private String gender;
    @Column(name = "kyc_status")
    private String kycStatus;
    @Column(name = "consent_status")
    private String consentStatus;
    @Column(name = "source_of_income")
    private String SourceOfIncome;*/
}
