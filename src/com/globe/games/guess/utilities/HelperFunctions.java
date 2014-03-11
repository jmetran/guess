package com.globe.games.guess.utilities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HelperFunctions {

	private ObjectMapper mapper = new ObjectMapper();
	
	public HelperFunctions(){
	}
	
	public String objectToString(Object _obj){
		String retVal = "";
		try{
			mapper = new ObjectMapper();
			retVal = mapper.writeValueAsString(_obj);			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return retVal;
	}
	
	public Object stringToObject(String _str, Class<?> _obj){
		Object retVal = "";
		try{
			mapper = new ObjectMapper();
			retVal = mapper.readValue(_str, _obj);		
		}catch (Exception e) {
			// TODO: handle exception
		}
		return retVal;
	}
	
	public List<String> jsonArrayToArrayList(String _str){
		List<String> retVal = new ArrayList<String>();		
		try {
			JSONArray jsonArray = new JSONArray(_str);
			int len = jsonArray.length();
			   for (int i=0;i<len;i++){ 
				   retVal.add(jsonArray.get(i).toString());
			   } 				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return retVal;
	}
	
	
}
