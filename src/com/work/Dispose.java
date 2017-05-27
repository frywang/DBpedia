package com.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Dispose {
public static void main(String[] args) throws IOException {
	File dir = new File("./datas\\结果");
	File[] path = dir.listFiles();
	LinkedList<File> fileList = new LinkedList<File>(); 
	for (int i = 0 ; i < path.length;i++) {
		if (path[i].isDirectory()) {
			File[] filePath = path[i].listFiles();
			for (int j = 0 ; j < filePath.length;j++) {
				if (filePath[j].isDirectory()) {
					System.out.println("error");
				} else {
					fileList.add(filePath[j]);
				}
			}
		} else {
			fileList.add(path[i]);
		}
	}
	for (int i = 0 ; i < fileList.size();i++) {
		System.out.println(fileList.get(i).getPath());
		System.out.println("cs:"+fileList.get(i).getPath().replace("\\结果", "\\结果_t"));
		System.out.println(fileList.get(i).getPath().replace("\\结果", "\\结果_new"));
		File findFile = new File(fileList.get(i).getPath().replace("\\结果", "\\结果"));
		File newFile = new File(fileList.get(i).getPath().replace("\\结果", "\\结果_new"));
		File outputDir = new File(newFile.getParent());
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
		FileOutputStream output = new FileOutputStream(newFile);
		
		FileInputStream input = new FileInputStream(fileList.get(i));
		FileInputStream inputf = new FileInputStream(findFile);
		BufferedReader byteinputf = new BufferedReader(new InputStreamReader(inputf));
		BufferedReader byteinput = new BufferedReader(new InputStreamReader(input));
		Map<String,String> find = new HashMap<String, String>();
		String findBuffer = null;
		while ((findBuffer = byteinputf.readLine()) != null) {
			if (findBuffer.contains("=")) {
				String[] temp = findBuffer.split("=");
				find.put(temp[0], temp[1]);
			}
		}
		findBuffer = null;
		while ((findBuffer = byteinput.readLine()) != null) {
			if (findBuffer.contains("=")) {
				String[] temp = findBuffer.split("=");
				String value = find.get(temp[0]);
				if (value != null) {
				String[] values = value.split(",");
				String[] v = temp[1].split(",");
				output.write((temp[0] + "=" + v[0]).getBytes());
				for (int k = 1; k < v.length;k++) {
					output.write(("," + v[k]).getBytes());
				}
				for (int k = 0; k < values.length;k++) {
					int l = 0;
					for (l = 0; l < v.length;l++) {
						if (values[k].trim().equals(v[l].trim())) {
							break;
						}
					}
					if (l >= v.length) {
						output.write(("," + values[k]).getBytes());
					}
				}
				output.write(("\n").getBytes());
			} else {
				output.write((findBuffer + "\n").getBytes());
			}
			} else {
				output.write((findBuffer + "\n").getBytes());
			}
		}
		byteinput.close();
		byteinputf.close();
		output.close();
	}
	System.out.println(fileList.size());
}
}
