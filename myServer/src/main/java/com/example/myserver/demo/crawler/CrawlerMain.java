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
    public static void main(String[] args) {
        CrawlerMain m = new CrawlerMain();
        String staticurl = "https://www.yodu.org";
        String url = "/book/15732/1994366.html";
        BufferedReader in = null;
        FileWriter file = null;
        try {
            file = new FileWriter("C:/Ts/download/overLord.txt",true);
            Boolean hasNext = true;
            while (hasNext) {
                if (!url.contains("?")) {
                    staticurl = "https://www.yodu.org" + url;
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
                    if (line.contains("<p>")) {
                        m.chapterContent(line, file);
                    } else if (line.contains("<h1>")) {
                        m.chapterTitle(line, file);
                    } else if (line.contains("t1602_1") && !line.contains("window.location.href")) {
                        hasNext = true;
                        url = m.getNextUrl(line);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        String l = line.replaceAll("<h1>", "").replaceAll("</h1>", "\n\r");
        file.write(l);
        System.out.println(l);
    }

    public void chapterContent(String line, FileWriter file) throws IOException {
        if (line.contains("（本章未完）")) {
            file.write(line.replaceAll("<p>", "").substring(0, line.indexOf("</p") - 3) + "\n\r");
            return;
        } else if (line.contains("class=\"bar\"")) {
            file.write(line.replaceAll("</p>", "").substring(line.indexOf("<p>")+3) + "\n\r");
            return;
        }
        file.write(line.replaceAll("<p>", "").replaceAll("</p>", "\n\r"));

        // System.out.println(line.replaceAll("<br /><br />", "/n/r"));
    }

    public String getNextUrl(String line) {
        String url = line.substring(line.indexOf("t1602_1:'")+9,line.indexOf("',t1602_index"));
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