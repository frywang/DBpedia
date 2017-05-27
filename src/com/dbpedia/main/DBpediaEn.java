package com.dbpedia.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBpediaEn {
	// 获得 category_label信息 ， 返回的是一个map {<http://zh.dbpedia.org/resource/Category:帮助文档> ： 帮助文档}
	public Map<String, String> getCategoryLabels() throws IOException {
		// 定义正则匹配项,取出的内容为两个括号中的内容，组成group
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, String> labels = new HashMap<String, String>();
		// 读取的是label文件，文件内容为(每一行为一个)：
		// <http://zh.dbpedia.org/resource/Category:帮助文档> <http://www.w3.org/2000/01/rdf-schema#label> "帮助文档"@zh <http://zh.wikipedia.org/wiki/Category:帮助文档?oldid=29665294>
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/category_labels_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			// matcher匹配中["<http://zh.dbpedia.org/resource/Category:帮助文档>", ""帮助文档"@zh"]
			if (matcher.find()) {
				labels.put(
						matcher.group(1).trim(),
						matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", "").trim());
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
		}
		reader.close();
		input.close();
		return labels;
	}
	// 获得category_label的中英文对照， 返回一个map {<http://dbpedia.org/resource/Category:Wikipedia_help> ： 帮助文档}
	public Map<String, String> getCategoryEnToZh() throws IOException {
		// 定义正则匹配项,取出的内容为两个括号中的内容，组成group
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, String> labels = new HashMap<String, String>();
		// 读取的是label文件，文件内容为(每一行为一个)：
		// <http://dbpedia.org/resource/Category:Wikipedia_help> <http://www.w3.org/2000/01/rdf-schema#label> "帮助文档"@zh <http://zh.wikipedia.org/wiki/Category:帮助文档?oldid=29665294> .
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/category_labels_en_uris_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			// matcher匹配中["<http://dbpedia.org/resource/Category:Wikipedia_help>", ""帮助文档"@zh"]
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				labels.put(
						matcher.group(1).trim(),
						matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", "").trim());
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
		}
		reader.close();
		input.close();
		return labels;
	}
	
	// 获得所有的labels信息 ， 返回的是一个map {<http://zh.dbpedia.org/resource/文學> ： 文學}
	public Map<String, String> getLabels() throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, String> labels = new HashMap<String, String>();
		// 读取的是label文件，文件内容为(每一行为一个)：
		// <http://zh.dbpedia.org/resource/文學> <http://www.w3.org/2000/01/rdf-schema#label> "文學"@zh <http://zh.wikipedia.org/wiki/文學?oldid=28847994> 
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/labels_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			// matcher匹配中["<http://zh.dbpedia.org/resource/文學>", ""文學"@zh"]
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				labels.put(
						matcher.group(1).trim(),
						matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", "").trim());
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
		}
		reader.close();
		input.close();
		return labels;
	}
	// 获得 label的中英文对照， 返回一个map {<http://dbpedia.org/resource/Literature> ： 文學}
	public Map<String, String> getEnToZh() throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, String> labels = new HashMap<String, String>();
		// 读取的是label文件，文件内容为(每一行为一个)：
		// <http://dbpedia.org/resource/Literature> <http://www.w3.org/2000/01/rdf-schema#label> "文學"@zh <http://zh.wikipedia.org/wiki/文學?oldid=28847994> 
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/labels_en_uris_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			// matcher匹配中["<http://dbpedia.org/resource/Literature>", ""文學"@zh"]
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				labels.put(
						matcher.group(1).trim(),
						matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", "").trim());
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
		}
		reader.close();
		input.close();
		return labels;
	}
	// 获得 Article的信息， 返回一个map {<http://zh.dbpedia.org/resource/文學> ： list链表}
	public Map<String,LinkedList<String>> getArticleCategories(Map<String, String> clabels,Map<String, String> cenToZh,Map<String, String> labels,Map<String, String> enToZh) throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, LinkedList<String>> classes = new HashMap<String, LinkedList<String>>();
		// <http://zh.dbpedia.org/resource/文學> <http://purl.org/dc/terms/subject> <http://zh.dbpedia.org/resource/Category:文学> <http://zh.wikipedia.org/wiki/文學?oldid=28847994#section=外部资源&relative-line=7&absolute-line=140>
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/article_categories_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			// matcher匹配中["<http://zh.dbpedia.org/resource/文學>", "<http://zh.dbpedia.org/resource/Category:文学>"]
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String key = clabels.get(matcher.group(2).trim());
				if (key == null) {
					key = cenToZh.get(matcher.group(2).trim());
				}
				String value = labels.get(matcher.group(1).trim());
				if (value == null) {
					value = enToZh.get(matcher.group(1).trim());
				}
				if (key == null) {
					LinkedList<String> list = classes.get(matcher.group(2).trim());
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(matcher.group(2).trim(), list);
					}
					if (value == null) {
						list.add(matcher.group(1).trim());
					} else {
						list.add(value);
					}
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
					if (value == null) {
						list.add(matcher.group(1).trim());
					} else {
						list.add(value);
					}
				}
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		return classes;
	}
	// 获得 infoDefinitions的信息， 返回一个map {<http://zh.dbpedia.org/resource/文學> ： list链表}
	public Map<String, String> getinfoDefinitions() throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, String> labels = new HashMap<String, String>();
		// <http://zh.dbpedia.org/property/width> <http://www.w3.org/2000/01/rdf-schema#label> "width"@zh <http://zh.wikipedia.org/wiki/历史?oldid=30302065#absolute-line=6>
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/infobox_property_definitions_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			// 限制只爬去2000的label
			if (!line.contains("<http://www.w3.org/2000/01/rdf-schema#label>")) {
				continue;
			}
			Matcher matcher = pattern.matcher(line);
			// matcher匹配中["<http://zh.dbpedia.org/property/width>", ""width"@zh"]
			if (matcher.find()) {
				labels.put(
						matcher.group(1).trim(),
						matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", "").trim());
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
		}
		reader.close();
		input.close();
		return labels;
	}
	public Map<String, LinkedList<String>> getExternalLinks(Map<String, String> labels,Map<String, String> enToZh) throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, LinkedList<String>> classes = new HashMap<String, LinkedList<String>>();
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/external_links_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String key = labels.get(matcher.group(1).trim());
				if (key == null) {
					key = enToZh.get(matcher.group(1).trim());
				}
				if (key == null) {
					LinkedList<String> list = classes.get(matcher.group(1).trim());
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(matcher.group(1).trim(), list);
					}
						list.add(matcher.group(2).replaceAll("[<> ]", "").trim());
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
						list.add(matcher.group(2).replaceAll("[<> ]", "").trim());
				}
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		return classes;
	
	}
	public Map<String, LinkedList<String>> getGeoCoordinates(Map<String, String> labels,Map<String, String> enToZh) throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>(.*)<[^>]*>");
		Map<String, LinkedList<String>> classes = new HashMap<String, LinkedList<String>>();
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/geo_coordinates_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			if (!line.contains("<http://www.georss.org/georss/point>")) {
				continue;
			}
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String key = labels.get(matcher.group(1).trim());
				if (key == null) {
					key = enToZh.get(matcher.group(1).trim());
				}
				if (key == null) {
					LinkedList<String> list = classes.get(matcher.group(1).trim());
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(matcher.group(1).trim(), list);
					}
						list.add(matcher.group(2).replaceAll("[^\"]\"", "").replaceAll("\".*", "").replace(" ", ","));
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
						list.add(matcher.group(2).replaceAll("[^\"]\"", "").replaceAll("\".*", "").replace(" ", ","));
				}
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		return classes;
	
	}
	
	public Map<String, LinkedList<String>> getLongAbstracts(Map<String, String> enToZh) throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) <[^>]*>[^\"]\"([^@]*)@.*");
		Map<String, LinkedList<String>> classes = new HashMap<String, LinkedList<String>>();
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/long_abstracts_en_uris_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String key = enToZh.get(matcher.group(1).trim());
				if (key == null) {
					key = enToZh.get(matcher.group(1).trim());
				}
				if (key == null) {
					LinkedList<String> list = classes.get(matcher.group(1).trim());
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(matcher.group(1).trim(), list);
					}
						list.add(matcher.group(2).substring(0,matcher.group(2).length() - 1).replaceAll("[ ]", "").trim());
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
						list.add(matcher.group(2).substring(0,matcher.group(2).length() - 1).replaceAll("[ ]", "").trim());
				}
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		return classes;
	
	}
	public Map<String, LinkedList<String>> getInfoboxProperties(Map<String, String> labels,Map<String, String> infoboxdefinitions) throws IOException {
		Pattern pattern = Pattern.compile("(<[^>]*>) (<[^>]*>) (\"[^\"]*\")(.*)");
		Pattern pattern1 = Pattern.compile("(<[^>]*>) (<[^>]*>) (<[^>]*>)(.*)");
		Map<String, LinkedList<String>> classes = new HashMap<String, LinkedList<String>>();
		FileReader input = new FileReader(new File(
				"D:/dbpediawiki/infobox_properties_unredirected_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
		Boolean isFind;
		while ((line = reader.readLine()) != null) {
			line = line.replace("^^", " ");
			Matcher matcher = pattern.matcher(line);
			isFind = matcher.find();
			if (!isFind) {
				matcher = pattern1.matcher(line);
				isFind = matcher.find();
			}
			if (isFind) {
				System.out.println(matcher.group(1) + "\n" + matcher.group(2) + "\n" + matcher.group(3));
				String key = labels.get(matcher.group(1).trim());
				if (key == null) {
					key = matcher.group(1).trim();
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
					String value = infoboxdefinitions.get(matcher.group(2).trim());
					if (value == null) {
						value = matcher.group(2);
					}
					String value2 = matcher.group(3);
					if (value2.contains("http://")) {
						if (labels.get(value2.trim()) != null) {
							value2 = labels.get(value2.trim());
						} else {
							value2 = value2.replaceAll("[<> ]", "");
						}
					} else {
						value2 = value2.substring(value2.indexOf("\"") + 1,value2.lastIndexOf("\"")).replace("<bl\n/>", "").replace("\n", "");
					}
					value += "=" + value2;
					list.add(value);
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
					String value = infoboxdefinitions.get(matcher.group(2).trim());
					if (value == null) {
						value = matcher.group(2);
					}
					String value2 = matcher.group(3);
					if (value2.contains("http") || value2.contains("ftp")) {
						if (labels.get(value2.trim()) != null) {
							value2 = labels.get(value2.trim());
						} else {
							value2 = value2.replaceAll("[<> ]", "");
						}
					} else {
						value2 = value2.substring(value2.indexOf("\"") + 1,value2.lastIndexOf("\"")).replace("<bl\n/>", "").replace("\n", "");
					}
					value += "=" + value2;
					list.add(value);
				}
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		return classes;
	
	}
	public static void main(String[] args) throws IOException {
		DBpediaEn dbPedia = new DBpediaEn();
		Map<String, String> clabels = dbPedia.getCategoryLabels();
		Map<String, String> cenToZh = dbPedia.getCategoryEnToZh();
		Map<String, String> labels = dbPedia.getLabels();
		Map<String, String> enToZh = dbPedia.getEnToZh();
		Map<String,String> infoboxdefinitions = dbPedia.getinfoDefinitions();
		Map<String, LinkedList<String>>	categories = dbPedia.getArticleCategories(clabels, cenToZh, labels, enToZh);
		Map<String, LinkedList<String>> externalLinks = dbPedia.getExternalLinks(labels, enToZh);
//		Map<String, LinkedList<String>>	geo = dbPedia.getGeoCoordinates(labels, enToZh);
		Map<String, LinkedList<String>> longAbstracts = dbPedia.getLongAbstracts(enToZh);
		Map<String, LinkedList<String>> infobox = dbPedia.getInfoboxProperties(labels, infoboxdefinitions);
//		FileOutputStream output = new FileOutputStream(new File("/home/lan/Desktop/dbpedia.txt"));
		String dir = "D:/dbpediawiki/data/";
		for (Entry<String,LinkedList<String>> catego : categories.entrySet()) {
			String path = dir + catego.getKey().replace("<http://zh.dbpedia.org/resource/Category::", "").replace("<http://zh.dbpedia.org/resource/Category:", "").replace(">", "").replace(" ", "") + "/";
			if (!(new File(path)).exists()) {
				new File(path).mkdirs();
			}
			LinkedList<String> cates = catego.getValue();
			for(int i = 0; i < cates.size();i++) {
				String filePath = path + cates.get(i).replace("/", "_") + ".txt";
				System.out.println(filePath);
				FileOutputStream output = new FileOutputStream(new File(filePath));
				LinkedList<String> infoList = infobox.get(cates.get(i).trim());
				System.out.println("infobox:\n");
				output.write("infobox:\n".getBytes());
				for (int j = 0; infoList != null && j < infoList.size();j++) {
					System.out.println(infoList.get(j) + "\n");
					output.write((infoList.get(j) + "\n").getBytes());
				}
				System.out.println("\n");
				output.write("\n".getBytes());
				LinkedList<String> absList = longAbstracts.get(cates.get(i));
				System.out.println("info:\n");
				output.write("info:\n".getBytes());
				for (int j = 0; absList != null && j < absList.size();j++) {
					System.out.println(absList.get(j) + "\n");
					output.write((absList.get(j) + "\n").getBytes());
				}
				System.out.println("\n");
				output.write("\n".getBytes());
				LinkedList<String> Linkss = externalLinks.get(cates.get(i));
				System.out.println("links:\n");
				output.write("LINKS:\n".getBytes());
				for (int j = 0; Linkss != null && j < Linkss.size();j++) {
					System.out.println(Linkss.get(j) + "\n");
					output.write((Linkss.get(j) + "\n").getBytes());
				}
				System.out.println("\n");
				output.write("\n".getBytes());
				output.close();
			}
		}
//		for (Entry<String, LinkedList<String>> list : classes.entrySet()) {
//			System.out.print("\n" + list.getKey() + ": ");
//			output.write((list.getKey() + ":{").getBytes());
//			LinkedList<String> l = list.getValue();
//			for (int i = 0; i < l.size();i++) {
//				System.out.println(l.get(i));
//				output.write((l.get(i) + "\n").getBytes());
//			}
//			output.write("}\n".getBytes());
//		}
//		output.close();
/**
		FileReader input = new FileReader(new File(
				"/home/lan/Documents/dbpedia/category_labels_zh.tql"));
		BufferedReader reader = new BufferedReader(input);
		String line;
//		while ((line = reader.readLine()) != null) {
//			Matcher matcher = pattern.matcher(line);
//			if (matcher.find()) {
//				clabels.put(
//						matcher.group(1).trim(),
//						matcher.group(2).replaceAll("[\" ]", "")
//								.replace("@zh", "").trim());
//				System.out.println(matcher.group(1)
//						+ "\n"
//						+ matcher.group(2).replaceAll("[\" ]", "")
//								.replace("@zh", ""));
//			}
//		}
//		reader.close();
//		input.close();
//
//		input = new FileReader(new File(
//				"/home/lan/Documents/dbpedia/category_labels_en_uris_zh.tql"));
//
//		reader = new BufferedReader(input);
//		while ((line = reader.readLine()) != null) {
//			Matcher matcher = pattern.matcher(line);
//			if (matcher.find()) {
//				cenToZh.put(
//						matcher.group(1).trim(),
//						matcher.group(2).replaceAll("[\" ]", "")
//								.replace("@zh", "").trim());
//				System.out.println(matcher.group(1)
//						+ "\n"
//						+ matcher.group(2).replaceAll("[\" ]", "")
//								.replace("@zh", ""));
//			}
//		}
		reader.close();
		input.close();

		input = new FileReader(new File(
				"/home/lan/Documents/dbpedia/labels_zh.tql"));

		reader = new BufferedReader(input);
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				labels.put(
						matcher.group(1).trim(),
						matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", "").trim());
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
		}
		reader.close();
		input.close();

		input = new FileReader(new File(
				"/home/lan/Documents/dbpedia/labels_en_uris_zh.tql"));

		reader = new BufferedReader(input);
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				enToZh.put(
						matcher.group(1).trim(),
						matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", "").trim());
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
		}
		reader.close();
		input.close();
		
		input = new FileReader(new File(
				"/home/lan/Documents/dbpedia/geo_coordinates_zh.tql"));

		reader = new BufferedReader(input);
		while ((line = reader.readLine()) != null) {
			if (!line.contains("<http://www.georss.org/georss/point>")) {
				continue;
			}
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String key = labels.get(matcher.group(1).trim());
				if (key == null) {
					key = enToZh.get(matcher.group(1).trim());
				}
				if (key == null) {
					LinkedList<String> list = classes.get(matcher.group(1).trim());
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(matcher.group(1).trim(), list);
					}
						list.add(matcher.group(2).replaceAll("[^\"]\"", "").replaceAll("\".*", "").replace(" ", ","));
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
						list.add(matcher.group(2).replaceAll("[^\"]\"", "").replaceAll("\".*", "").replace(" ", ","));
				}
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		FileOutputStream output = new FileOutputStream(new File("/home/lan/Desktop/GeoCoordinates.txt"));
		for (Entry<String, LinkedList<String>> list : classes.entrySet()) {
			System.out.print("\n" + list.getKey() + " : ");
			output.write((list.getKey() + ":").getBytes());
			LinkedList<String> l = list.getValue();
			for (int i = 0; i < l.size();i++) {
				System.out.println(l.get(i));
				output.write((l.get(i) + " ").getBytes());
			}
			output.write("\n".getBytes());
		}
		output.close();
**/		
		
/**
 external_links		
		input = new FileReader(new File(
				"/home/lan/Documents/dbpedia/external_links_zh.tql"));

		reader = new BufferedReader(input);
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String key = labels.get(matcher.group(1).trim());
				if (key == null) {
					key = enToZh.get(matcher.group(1).trim());
				}
				if (key == null) {
					LinkedList<String> list = classes.get(matcher.group(1).trim());
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(matcher.group(1).trim(), list);
					}
						list.add(matcher.group(2).replaceAll("[<> ]", "").trim());
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
						list.add(matcher.group(2).replaceAll("[<> ]", "").trim());
				}
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		FileOutputStream output = new FileOutputStream(new File("/home/lan/Desktop/externalLinks.txt"));
		for (Entry<String, LinkedList<String>> list : classes.entrySet()) {
			System.out.print("\n" + list.getKey() + " : ");
			output.write((list.getKey() + ":").getBytes());
			LinkedList<String> l = list.getValue();
			for (int i = 0; i < l.size();i++) {
				System.out.println(l.get(i));
				output.write((l.get(i) + " ").getBytes());
			}
			output.write("\n".getBytes());
		}
		output.close();
**/		
		
/**
//classes
		input = new FileReader(new File(
				"/home/lan/Documents/dbpedia/article_categories_zh.tql"));

		reader = new BufferedReader(input);
		while ((line = reader.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				String key = clabels.get(matcher.group(2).trim());
				if (key == null) {
					key = cenToZh.get(matcher.group(2).trim());
				}
				String value = labels.get(matcher.group(1).trim());
				if (value == null) {
					value = enToZh.get(matcher.group(1).trim());
				}
				if (key == null) {
					LinkedList<String> list = classes.get(matcher.group(2).trim());
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(matcher.group(2).trim(), list);
					}
					if (value == null) {
						list.add(matcher.group(1).trim());
					} else {
						list.add(value);
					}
				} else {
					LinkedList<String> list = classes.get(key);
					if (list == null) {
						list = new LinkedList<String>();
						classes.put(key, list);
					}
					if (value == null) {
						list.add(matcher.group(1).trim());
					} else {
						list.add(value);
					}
				}
				System.out.println(matcher.group(1)
						+ "\n"
						+ matcher.group(2).replaceAll("[\" ]", "")
								.replace("@zh", ""));
			}
			System.out.println(line);
		}
		reader.close();
		input.close();
		FileOutputStream output = new FileOutputStream(new File("/home/lan/Desktop/Classes.txt"));
		for (Entry<String, LinkedList<String>> list : classes.entrySet()) {
			System.out.print("\n" + list.getKey() + " : ");
			output.write((list.getKey() + ":").getBytes());
			LinkedList<String> l = list.getValue();
			for (int i = 0; i < l.size();i++) {
				System.out.println(l.get(i));
				output.write((l.get(i) + " ").getBytes());
			}
			output.write("\n".getBytes());
		}
		output.close();
		**/
	}
}
