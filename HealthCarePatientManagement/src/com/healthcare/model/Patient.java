package com.healthcare.model;

import com.google.gson.JsonObject;

public class Patient extends User {

	int patientId;
	int hospitalId;
	String patientCondition;

	public Patient() {

	}

	public Patient(int patientId, int hospitalId, String patientCondition) {
		super();
		this.patientId = patientId;
		this.hospitalId = hospitalId;
		this.patientCondition = patientCondition;
	}

	public JsonObject createPatientJsonObject(int patientId, int hospitalId, String patientCondition) {
		JsonObject patient = new JsonObject();
		patient.addProperty("patientId", patientId);
		patient.addProperty("hospitalId", hospitalId);
		patient.addProperty("patientCondition", patientCondition);
		return patient;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getPatientCondition() {
		return patientCondition;
	}

	public void setPatientCondition(String patientCondition) {
		this.patientCondition = patientCondition;
	}

}
