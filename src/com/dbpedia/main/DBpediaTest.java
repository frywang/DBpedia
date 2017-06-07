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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBpediaTest {
	public final static String DBPEDIA_URL_API_ENTITY = "http://knowledgeworks.cn:20313/cndbpedia/api/entity?mention=";
	public final static String DBPEDIA_URL_API_ENTITYAVP = "http://knowledgeworks.cn:20313/cndbpedia/api/entityAVP?entity=";
	
	// 获得实体
	public static String[] getEntity(String entity) throws IOException, JSONException {
		String urlPath = DBPEDIA_URL_API_ENTITY + entity;
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
	public static Map<String,String> getEntityAVP(String entity) throws IOException, JSONException {
		String urlPath = DBPEDIA_URL_API_ENTITYAVP + entity;
		String result = getStringForUrl(urlPath);
        Map<String,String> results = new HashMap<String, String>();
		if ((!result.contains("链接有问题"))||((!result.matches("歌曲|专辑|电影")) ) ){
			JSONObject objg = new JSONObject(result);
	        JSONArray array = objg.getJSONArray("av pair");
	        for (int i = 0; i < array.length();i++) {
	        	JSONArray obj = array.getJSONArray(i);
	        	if (results.get(obj.getString(0)) != null) {
	        		results.put(obj.getString(0), results.get(obj.getString(0)) + "," + obj.getString(1).replaceAll("<[^>]*>", ""));
	        	} else {
	        	    results.put(obj.getString(0), obj.getString(1).replaceAll("<[^>]*>", ""));
	        	}
	        }
	        //返回属性值
			return results;
			
		}else{
			return null;
		}

	}
	
	
	// 获取URL内容，并以字符串的形式返回
	private static String getStringForUrl(String urlPath) throws IOException {
		String urlPath1 = urlPath.replace(" ", "%20");
		URL url = new URL(urlPath1);
//
        System.out.println(url);

        // 创建连接对象
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();// 握手  
        try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return urlPath+"链接有问题，不能识别";
		}
	}

	
	
	public static void  writeFiles(File fil,String dir,Pattern pattern) throws IOException, JSONException {
		//读取需要写入的词表
		FileInputStream input = new FileInputStream(fil);
		BufferedReader byteinputf = new BufferedReader(new InputStreamReader(input));
		String findBuffer = null;
		
		System.out.println("瓦房额排挤哦是发改");
		
		while ((findBuffer = byteinputf.readLine()) != null) {

			// 确定要写入文件的文件夹
			String dirPath = dir + findBuffer + "/";
			// 如果该文件存在，则生成以概念命名的文件夹
			if (!(new File(dirPath)).exists()) {
				new File(dirPath).mkdirs();
			}
			// 获得概念名的消歧类型
			String[] entitys = getEntity(findBuffer);

			for (int i = 0; i < entitys.length; i++) {
				String rawfilepath = entitys[i];
				Matcher m = pattern.matcher(entitys[i]);
				if (!m.find()) {
					System.out.println(rawfilepath);
					Map<String, String> results = getEntityAVP(entitys[i]);
					if (getEntityAVP(entitys[i]) != null) {
						// 接下来准备写入文件
						File f = new File(dirPath + rawfilepath.replace("/", "&").replace("\\", "&").replace(" ", "%20")
								.replace(" ", "%20") + ".txt");
						FileOutputStream output = new FileOutputStream(f);
						// 把概念下属性写入map
						Map<String, String> avp = getEntityAVP(entitys[i]);
						// 写入文件
						output.write("AVP:\n".getBytes());
						for (String key : avp.keySet()) {
							// 将属性以键值对的形式写入
							System.out.println(key + "=" + avp.get(key));
							output.write((key + "=" + avp.get(key) + "\n").getBytes());
						}
						System.out.println();
						output.close();
						System.out.println();
					}

				}
			}


		}
		byteinputf.close();
	}

	
	// 
	public static void main(String[] args) throws IOException, JSONException {
		
		Pattern pattern = Pattern.compile("歌|专辑|电影|曲|漫画|期刊|杂志|短片|图书|小说|手游|纪录片|影视|连续剧|电视剧|日剧|韩剧|动画片|书籍|散文|美剧");
		
		
		//打开需要获取属性的词表
		File fil = new File("./test2");		
		//确定需要写入属性组的文件夹
		String dir = "./datas/结果/中国历史人物实例/";
		writeFiles(fil,dir,pattern);		

	}
	
}
