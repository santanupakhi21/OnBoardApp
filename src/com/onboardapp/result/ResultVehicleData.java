package com.onboardapp.result;

import java.io.Serializable;
import java.util.ArrayList;

import com.onboardapp.model.VehicleData;

@SuppressWarnings("serial")
public class ResultVehicleData extends Result implements Serializable{
	public ArrayList<VehicleData> ListVehicle=new ArrayList<VehicleData>();

}
