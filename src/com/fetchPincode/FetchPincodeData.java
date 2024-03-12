package com.fetchPincode;

import java.util.Scanner;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchPincodeData {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		
		// Input pincode
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter pincode : ");
		String pincode = sc.next();
		
		if(pincode.length() > 6) {
			System.out.println("Please enter valid 6 digit pincode");
			return;
		}
		
		try {
			// API URL with Query param 'pincode'
			String apiURL = "https://api.data.gov.in/resource/5c2f62fe-5afa-4119-a499-fec9d604d5bd?api-key=579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b&format=json&filters%5Bpincode%5D=" + pincode;
			URL url = new URL(apiURL);
			
			//Http connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			// Read the response and convert to String
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine = reader.readLine();
			StringBuilder response = new StringBuilder();
			
			while(inputLine != null) {
				response.append(inputLine);
				inputLine = reader.readLine();
			}
			reader.close();
			
			// Converting the response to JSON format
			JSONObject jsonResponse = new JSONObject(response.toString());
			//System.out.print(jsonResponse);
			
			// Getting required key from JSON object 
			JSONArray recordsArray = jsonResponse.getJSONArray("records");
			
			if(recordsArray.length() == 0) {
				System.out.println("No records found");
				return;
			}
			
			//Getting the data from the array of JSON objects
			// if there is requirement of the all the data iterate the array and get the data
			JSONObject dataObject = recordsArray.getJSONObject(0);
			
			String district = dataObject.getString("district");
			String state = dataObject.getString("statename");
			String area = dataObject.getString("officename");
			
			System.out.println("Area: "+area);
			System.out.println("District : "+ district);
			System.out.println("State : "+ state);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
