//package com.foodie_union.network;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class JsonTools {
//
//	public JsonTools() {
//		// TODO Auto-generated constructor stub
//	}
//public static Person getPerson(String key,String jsonString){
//	Person person=new Person();
//	try{
//		JSONObject jsonObject =new JSONObject(jsonString);
//		JSONObject personObject=jsonObject.getJSONObject("person");
//		person.setAddress(personObject.getString("address"));
//		person.setId(personObject.getInt("id"));
//		person.setName(personObject.getString("name"));
//		
//		
//	}
//	catch(Exception e){}
//	return person;
//	}
// public static List<Person> getPersons(String key,String jsonString){
//	List<Person> list=new ArrayList<Person>();
//	 try{
//		 JSONObject jsonObject=new JSONObject(jsonString);
//		 JSONArray jsonArray=jsonObject.getJSONArray(key);
//		 for(int i=0;i<jsonArray.length();i++){
//			 JSONObject jsonObject2=jsonArray.getJSONObject(i);
//			 Person person=new Person();
//			 person.setAddress(jsonObject2.getString("address"));
//			 person.setId(jsonObject2.getInt("id"));
//				person.setName(jsonObject2.getString("name"));
//				
//				list.add(person);
//			 
//		 }
//	 }catch(Exception e){
//		 
//	 }
//	return list;
//	 
// } 
// public static List<String> getList(String key,String jsonString){
//	 List<String> list=new ArrayList<String>();
//	 try{
//		 JSONObject jsonObject =new JSONObject(jsonString);
//		 JSONArray jsonArray=jsonObject.getJSONArray(key);
//		 for(int i=0;i<jsonArray.length();i++){
//			 String msg=jsonArray.getString(i);
//			 list.add(msg);
//			 
//		 }
//	 }catch(Exception e){}
//	return list;
//	 
// }
// public static List<Map<String,Object>> listKeyMaps(String key,String jsonString){
//	 List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
//	 try{
//		 JSONObject jsonObject =new JSONObject(jsonString);
//		 JSONArray jsonArray=jsonObject.getJSONArray(key);
//		 for(int i=0;i<jsonArray.length();i++){
//			 JSONObject jsonObject2=jsonArray.getJSONObject(i);
//			 Map<String,Object> map=new HashMap<String,Object>();
//			 Iterator<String> iterator=jsonObject2.keys();
//			 while(iterator.hasNext()){
//				 String json_key=iterator.next();
//				 Object json_value=jsonObject2.get(json_key);
//				 if(json_value==null){
//					 json_value="";
//				 }
//				 map.put(json_key, json_value);
//				 
//			 }
//			 list.add(map);
//			 //String msg=jsonArray.getString(i);
//			 //list.add(msg);
//			 
//		 }
//	 }catch(Exception e){}
//	return list;
//	 
// }
//}
