package com.healthCare.Service;

import java.util.ArrayList;
import java.util.HashMap;

import com.healthCare.model.DoctorUser;

public interface DoctorManageService {
	public HashMap<String, String> RegisterUser(String firstName, String lastName, String idnum, String gender, String address, String workplace, String degree, String mobileNumber,String email, String password);
	public DoctorUser getUserDetails(String Did);
	public ArrayList<DoctorUser> getAllUsers();
	public DoctorUser deleteUser(String Did);
	public DoctorUser updateUser(String userId, String firstName, String lastName, String idnum, String gender, String address, String mobileNumber, String workplace, String degree,String email);
}

