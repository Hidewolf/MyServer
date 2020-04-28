package com.example.myserver.demo.crawler;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

public class CrawlerMain {
    public static void main1(String[] args) {
        CrawlerMain m = new CrawlerMain();
        String staticurl = "http://m.qinxiaoshuo.com";
        String url = "/read/0/1545/5d77d0d856fec85e5b0ffcce.html";
        BufferedReader in = null;
        FileWriter file = null;
        try {
            file = new FileWriter("D:/overLord.txt");
            Boolean hasNext = true;
            while (hasNext) {
                if (!url.contains("?")) {
                    staticurl = "http://m.qinxiaoshuo.com" + url;
                    url = "";
                }
                hasNext = false;
                System.out.println(staticurl + url);
                URL realUrl = new URL((staticurl + url).replaceAll(" ", ""));
                URLConnection connection = realUrl.openConnection();
                connection.connect();
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains("chapter_content")) {
                        m.chapterContent(in, file);
                    } else if (line.contains("c_title")) {
                        m.chapterTitle(line, file);
                    } else if (line.contains("\">下一")) {
                        hasNext = true;
                        url = m.getNextUrl(line);
                    }
                }
            }

        } catch (Exception e) {

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                file.flush();
                file.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void chapterTitle(String line, FileWriter file) throws IOException {
        file.write(line.replaceAll("<div class=\"c_title\"><h3>", "").replaceAll("</h3></div>", "\n\r"));
        System.out.println(line.replaceAll("<div class=\"c_title\"><h3>", "").replaceAll("</h3></div>", "\n\r"));
    }

    public void chapterContent(BufferedReader in, FileWriter file) throws IOException {
        String line = in.readLine();
        file.write(line.replaceAll("<br /><br />", "\n\r"));

        // System.out.println(line.replaceAll("<br /><br />", "/n/r"));
    }

    public String getNextUrl(String line) {
        line = line.replaceAll("<a class=\"qxs_btn\" href=\"", "");
        int charE = line.indexOf("\">下");
        String url = line.substring(0, charE);
        return !url.isEmpty() ? url : null;
    }

    // public static void main(String[] args) throws UnsupportedEncodingException {
    //     System.out.println("123456".substring(0,3));
    //     Map<String, String> sortMap = new TreeMap<String, String>();
    //     sortMap.put("employeeNumber", "150420723");
    //     //sortMap.put("mobile", "13816818561");
    //     sortMap.put("bindType", "1");
    //     sortMap.put("bizCode", "TS-HR");
    //     sortMap.put("bizLoginName", "150420723");
    //     sortMap.put("bizUserType", "0");
    //     String timeStamp = System.currentTimeMillis()+"";
    //     sortMap.put("timestamp", timeStamp);
    //     sortMap.put("method", "POST");
    //     sortMap.put("nonce", "8NV7KyBgq4WiZAQkwGJ1nRCjlpaxLm3b");
	// 	StringBuilder sbParam = new StringBuilder();
	// 	for (Map.Entry<String, String> item : sortMap.entrySet()) {
	// 			sbParam.append(item.getKey());
	// 				sbParam.append(item.getValue());
    //     }
    //     System.out.println(new String(DigestUtils.md5Hex("92e3b469823a70a5"+"POST"+"updateSsoUserBindInfo"+sbParam+"92e3b469823a70a5").getBytes("UTF-8"), "UTF-8"));
    //     System.out.println(timeStamp);
    // }
}