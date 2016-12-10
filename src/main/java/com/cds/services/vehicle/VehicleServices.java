package com.cds.services.vehicle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class VehicleServices {
	public VehicleServices() { }
	
	public String callAPIAssignDoor(
			int sizeInDoor, int sizeOutDoor, 
			int sizeInVehicle, int sizeOutVehicle,
			int[] arrIDC, int[] arrODC, int[] arrIVD, int[] arrOVD,
			int[][] costs, int [][] trips)
	{
		String json = "{";
		json += "\"inDoorNum\": " + sizeInDoor + ",";
		json += "\"outDoorNum\": " + sizeOutDoor + ",";
		json += "\"inVehicleNum\": " + sizeInVehicle + ",";
		json += "\"outVehicleNum\": " + sizeOutVehicle + ",";
		json += "\"costs\": [";
		for(int i=0; i<sizeInDoor; i++){
			for(int j=0; j<sizeOutDoor; j++){
				json +="{\"fromDoor\": " + i + ", \"toDoor\": " + j + ", \"cost\": " + costs[i][j] + "}";
				if (i != sizeInDoor - 1 || j != sizeOutDoor -1)
					json += ",";
			}
		}
		json += "],";
		json += "\"trips\": [";
		for(int i=0; i<sizeInVehicle; i++){
			for(int j=0; j<sizeOutVehicle; j++){
				json +="{\"fromVehicle\": " + i + ", \"toVehicle\": " + j + ", \"time\": " + trips[i][j] + "}";
				if (i != sizeInVehicle - 1 || j != sizeOutVehicle -1)
					json += ",";
			}
		}
		json += "],";
		json +="\"inDoorCapacities\": [" ;
		for(int i=0; i<arrIDC.length; i++){
			json += arrIDC[i];
			if (i < arrIDC.length - 1)
				json += ",";
		}
		json +="],";
		json +="\"outDoorCapacities\": [" ;
		for(int i=0; i<arrODC.length; i++){
			json += arrODC[i];
			if (i < arrODC.length - 1)
				json += ",";
		}
		json +="],";
		json +="\"inVehicleDemands\": [" ;
		for(int i=0; i<arrIVD.length; i++){
			json += arrIVD[i];
			if (i < arrIVD.length - 1)
				json += ",";
		}
		json +="],";
		json +="\"outVehicleDemands\": [" ;
		for(int i=0; i<arrOVD.length; i++){
			json += arrOVD[i];
			if (i < arrOVD.length - 1)
				json += ",";
		}
		json +="]";
		json +="}";
		System.out.println(json);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost("http://202.191.57.103:8080/ezRoutingAPI/solve-cross-docking-system");
//			HttpPost request = new HttpPost("http://localhost:9090/CrossDockingAPI/solve-cross-docking-system");
			StringEntity params = new StringEntity(json);
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			InputStream in = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(in));
			
			String str = buf.readLine();
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public String callAPIAssignDoor(String json){
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost request = new HttpPost("http://202.191.57.103:8080/ezRoutingAPI/solve-cross-docking-system");
			StringEntity params = new StringEntity(json);
			request.addHeader("content-type", "application/json");
			request.addHeader("Accept", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			InputStream in = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(in));
			
			String str = buf.readLine();
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
