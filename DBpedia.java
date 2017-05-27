package com.dbpedia.main;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBpedia {
	public final static String DBPEDIA_URL_API_ENTITY = "http://knowledgeworks.cn:20313/cndbpedia/api/entity?mention=";
	public final static String DBPEDIA_URL_API_ENTITYAVP = "http://knowledgeworks.cn:20313/cndbpedia/api/entityAVP?entity=";
	public final static String DBPEDIA_URL_API_ENTITY_ATTRIBUTE = "http://knowledgeworks.cn:20313/cndbpedia/api/entityAttribute?entity=";
	public final static String DBPEDIA_URL_API_ENTITY_ATTRIBUTE2 = "&attribute=";
	public final static String DBPEDIA_URL_API_ENTITY_TYPE = "http://knowledgeworks.cn:20313/cndbpedia/api/entityType?entity=";
	public final static String DBPEDIA_URL_API_ENTITY_TAG= "http://knowledgeworks.cn:20313/cndbpedia/api/entityTag?entity=";
	public final static String DBPEDIA_URL_API_ENTITY_INFORMATION = "http://knowledgeworks.cn:20313/cndbpedia/api/entityInformation?entity=";
	// 获得实体
	public String[] getEntity(String entity) throws IOException, JSONException {
//		String urlPath = DBPEDIA_URL_API_ENTITY + entity;
		String urlPath = "http://knowledgeworks.cn:20313/cndbpedia/api/entity?mention="+entity;
		System.out.println(urlPath);
        String result = getStringForUrl(urlPath);
        JSONObject objg = new JSONObject(result);
        JSONArray array = objg.getJSONArray("entity");
        String[] results = new String[array.length()];
        for (int i = 0; i < array.length();i++) {
        	results[i] = array.getString(i);
        }
		return results;
	}
	// 获得实体的属性值-对
	public Map<String,String> getEntityAVP(String entity) throws IOException, JSONException {
		String urlPath = DBPEDIA_URL_API_ENTITYAVP + entity;
		String result = getStringForUrl(urlPath);
		JSONObject objg = new JSONObject(result);
        JSONArray array = objg.getJSONArray("av pair");
        Map<String,String> results = new HashMap<String, String>();
        for (int i = 0; i < array.length();i++) {
        	JSONArray obj = array.getJSONArray(i);
        	if (results.get(obj.getString(0)) != null) {
        		results.put(obj.getString(0), results.get(obj.getString(0)) + "," + obj.getString(1).replaceAll("<[^>]*>", ""));
        	} else {
        	    results.put(obj.getString(0), obj.getString(1).replaceAll("<[^>]*>", ""));
        	}
        }
		return results;
	}
	// 返回实体的摘要信息
	public  String[] getEntityInfo(String entity) throws IOException, JSONException {
		String urlPath = DBPEDIA_URL_API_ENTITY_INFORMATION + entity;
        String result = getStringForUrl(urlPath);
        JSONObject objg = new JSONObject(result);
        String array = objg.get("Information").toString();
        if (array.subSequence(0, 1).equals("[")) {
        	return null;
        }
        String[] results = objg.getString("Information").replace("|||", "\n").split("\n");
		return results;
	}
	// 返回实体的所属标签
	public  String[] getEntityTag(String entity) throws IOException, JSONException {
		String urlPath = DBPEDIA_URL_API_ENTITY_TAG + entity;
        String result = getStringForUrl(urlPath);
        JSONObject objg = new JSONObject(result);
        JSONArray array = objg.getJSONArray("Tags");
        String[] results = new String[array.length()];
        for (int i = 0; i < array.length();i++) {
        	results[i] = array.getString(i);
        }
		return results;
	}
	// 返回实体的类型
	public  String[] getEntityTypes(String entity) throws IOException, JSONException {
		String urlPath = DBPEDIA_URL_API_ENTITY_TYPE + entity;
        String result = getStringForUrl(urlPath);
        JSONObject objg = new JSONObject(result);
        JSONArray array = objg.getJSONArray("Types");
        String[] results = new String[array.length()];
        for (int i = 0; i < array.length();i++) {
        	results[i] = array.getString(i);
        }
		return results;
	}
	// 获取URL内容，并以字符串的形式返回
	private String getStringForUrl(String urlPath) throws IOException {
		// 连接URL
		URL url = new URL(urlPath);
        System.out.println(url);
        // 创建连接对象
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();// 握手  
        InputStream is = conn.getInputStream();//拿到输入流  
        
        byte[] buffer = new byte[1024];
        int readBytes = 0;
        // 建立输出流
        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream();
        // 存入输出流中
        while((readBytes = is.read(buffer)) > 0){
        	baos.write(buffer, 0, readBytes);;
        }
        is.close();
        baos.close();
        return baos.toString();
	}
	// 
	public void cope(String dir,String entity) throws IOException, JSONException {
		
		String[] entitys = getEntity(entity);
		String dirPath = dir + entity + "/";
		// 是否存在该文件，不存就创建该文件
		if (!(new File(dirPath)).exists()) {
			new File(dirPath).mkdirs();
		}
		// 由于有多个实体对象，因此循环每个实体对象，获取其相关信息
		for (int i = 0; i < 1;i++) {
			System.out.println(entitys[i]);
			// 创建实体相关内容的输出文件
			FileOutputStream output = new FileOutputStream(new File(dirPath + entitys[i].replace("/", "-") + ".txt"));
			// 获得实体的属性值-对
			Map<String,String> avp = getEntityAVP(entitys[i]);
			// 写入AVP
			output.write("AVP:\n".getBytes());
			// 写入实体的属性值-对
			for (String key : avp.keySet()) {
				System.out.println(key + "=" +avp.get(key));
				output.write((key + "=" +avp.get(key) + "\n").getBytes());
			}
			System.out.println();
			// 获得标签
			String[] tags = getEntityTag(entitys[i]);
			// 写入标签
			output.write("\nTAG:\n".getBytes());
			for (int j = 0; tags != null && j < tags.length;j++) {
				System.out.println(tags[j]);
				output.write((tags[j] + "\n").getBytes());
			}
			System.out.println();
			// 获得实体信息
			String[] info = getEntityInfo(entitys[i]);
			output.write("\nINFO:\n".getBytes());
			for (int j = 0; info != null && j < info.length;j++) {
				System.out.println(info[j]);
				output.write((info[j] + "\n").getBytes());
			}
			// 获得实体标签
			String[] types = getEntityTypes(entitys[i]);
			output.write("\nTYPES:\n".getBytes());
			for (int j = 0; types != null && j < types.length;j++) {
				System.out.println(types[j]);
				output.write((types[j] + "\n").getBytes());
			}
			output.close();
			System.out.println();
		}
	}
	public static void main(String[] args) throws IOException, JSONException {
//		System.out.println(args[0]);
//		String dir = "/home/lan/CN-dbpedia/";
//		FileInputStream input = new FileInputStream(new File(args[0]));
//		byte[] buffer = new byte[1024];
//        int readBytes = 0;
//        ByteArrayOutputStream   baos   =   new   ByteArrayOutputStream();
//        while((readBytes = input.read(buffer)) > 0){
//        	baos.write(buffer, 0, readBytes);;
//        }
//        input.close();
//        baos.close();
//        DBpedia dbpedia = new DBpedia();
//        String[] clas = baos.toString().split("\n");
//        for (int i = 0; i < clas.length;i++) {
//        	String[] item = clas[i].split(" ");
//        	String itemDir = dir + item[0].replace("/", "_") + "/";
//        	for (int j = 0; j < item.length;j++) {
//        		String[] it = item[j].split("/");
//        		for (int k = 0; k < it.length; k++) {
//        			System.out.println(it[k]);
//        			dbpedia.cope(itemDir,it[k]);
//        		}
//        	}
//        	
        
		File fil = new File("./datas/test");
		FileInputStream input = new FileInputStream(fil);
		BufferedReader byteinputf = new BufferedReader(new InputStreamReader(input));
		String findBuffer = null;
		String dir = "./datas/test";
		while ((findBuffer = byteinputf.readLine()) != null) {
			DBpediaTest dbpedia = new DBpediaTest();
			String[] entitys = dbpedia.getEntity(findBuffer);
			String dirPath = dir + findBuffer+"/";
			if (!(new File(dirPath)).exists()) {
				new File(dirPath).mkdirs();
			}
			for (int i = 0; i < entitys.length;i++) {
				if (i > 0){break;}
				System.out.println(entitys[i]);
				
				FileOutputStream output = new FileOutputStream(new File(dirPath + entitys[i].replace("/", "&").replace("\\", "&") + ".txt"));
				Map<String,String> avp = dbpedia.getEntityAVP(entitys[i]);
				output.write("AVP:\n".getBytes());
				for (String key : avp.keySet()) {
					System.out.println(key + "=" +avp.get(key));
					output.write((key + "=" +avp.get(key) + "\n").getBytes());
				}
				System.out.println();
				String[] tags = dbpedia.getEntityTag(entitys[i]);
				output.write("\nTAG:\n".getBytes());
				for (int j = 0; tags != null && j < tags.length;j++) {
					System.out.println(tags[j]);
					output.write((tags[j] + "\n").getBytes());
				}
				System.out.println();
				String[] info = dbpedia.getEntityInfo(entitys[i]);
				output.write("\nINFO:\n".getBytes());
				for (int j = 0; info != null && j < info.length;j++) {
					System.out.println(info[j]);
					output.write((info[j] + "\n").getBytes());
				}
				String[] types = dbpedia.getEntityTypes(entitys[i]);
				output.write("\nTYPES:\n".getBytes());
				for (int j = 0; types != null && j < types.length;j++) {
					System.out.println(types[j]);
					output.write((types[j] + "\n").getBytes());
				}
				output.close();
				System.out.println();

			}
		}
		byteinputf.close();
	}
	
	
}
