package com.example.demo.model;

public class Constants {
	
	//send OTP
	public static final String REQUEST_SEND_OTP_RECEIVED_MOBILE="Your OTP successfully send to your Mobile number : ";
	public static final String MOBILE_DOES_NOT_EXIST="Mobile number  does not exist!";
	public static final String MOBILE_ALREADY_EXIST="Mobile number  already exist!";
	public static final String REF_ID_DOES_NOT_EXIST="Ref id does not exist!";
	public static final String   MAX_FAILED_ATTEMPS ="Your are not able to send OTP due to 3 failed attempts.It will be try after 24 hours.";
	public static final String OTP_SUCCESS="OTP successfully confirm. You can now log in with the new credentials.";
	public static final String SERVER_ERROR = "Server error please try again!";
	public static final int OTP_EXPIRED_TIME=5;
	
	//validate OTP
	public static final String CONFORM_PASSWOR_CHANGE = "OTP successfully confirm. You can now log in with the new credentials.";
	public static final String OTP_EXPIRED = "OTP expired";
	public static final String MOBILE_ALREADY_CONFIRMED = "Mobile already confirmed";
	public static final String OTP_NOT_FOUND = "OTP not found";
	
	

}
