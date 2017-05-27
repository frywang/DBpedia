package com.work;
/*
 * test文件是要输入的动物名。结果就是生成的txt文件。
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.JSONException;

import com.dbpedia.main.DBpediaTest;

public class read_CNDBpedia {
	
	public static void main(String[] args) throws IOException, JSONException {
		File fil = new File("./test");
		FileInputStream input = new FileInputStream(fil);
		BufferedReader byteinputf = new BufferedReader(new InputStreamReader(input));
		String findBuffer = null;
		String dir = "./datas/结果/";
		while ((findBuffer = byteinputf.readLine()) != null) {
			DBpediaTest dbpedia = new DBpediaTest();
			String[] entitys = dbpedia.getEntity(findBuffer);
			String dirPath = dir + findBuffer+"/";
			if (!(new File(dirPath)).exists()) {
				new File(dirPath).mkdirs();
			}
			for (int i = 0; i < entitys.length;i++) {
			
				System.out.println(entitys[i]);
				
				FileOutputStream output = new FileOutputStream(new File(dirPath + entitys[i].replace("/", "&").replace("\\", "&") + ".txt"));
				Map<String,String> avp = dbpedia.getEntityAVP(entitys[i]);
				output.write("AVP:\n".getBytes());
				for (String key : avp.keySet()) {
					System.out.println(key + "=" +avp.get(key));
					output.write((key + "=" +avp.get(key) + "\n").getBytes());
				}
				System.out.println();

				output.close();
				System.out.println();
			}
		}
		byteinputf.close();
	}
	
}
