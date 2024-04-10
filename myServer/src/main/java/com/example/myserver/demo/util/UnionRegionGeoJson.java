package com.example.myserver.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.myserver.demo.model.GeoJson;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnionRegionGeoJson {
    public static void test(String[] args) throws IOException {
        String jsonStr = getJsonStr("D:\\Ts\\Code\\myServerProject\\myServer\\src\\main\\resources\\Json\\geoJson.json");

        String regionListStr = getJsonStr("D:\\Ts\\Code\\myServerProject\\myServer\\src\\main\\resources\\Json\\cityList.json");
        List<Map<String, Object>> regionList = (List<Map<String, Object>>) JSON.parse(regionListStr);
        for (int i = 0; i < regionList.size(); i++) {
            Map jsonMap = (Map) JSON.parse(jsonStr);
            UnionRegionGeoJson.UnionCityLine(jsonMap, regionList.get(i).get("regionNo").toString(), (List<String>) regionList.get(i).get("cityList"));
        }
        //System.out.println("success");
    }

    private static void UnionMap(List<GeoJson> jsonList) throws IOException {
        List<String> cityNames = new ArrayList<>();
        cityNames.add("曲靖市");
        cityNames.add("遵义市");
        cityNames.add("铜仁市");
        cityNames.add("昭通市");
        cityNames.add("安顺市");
        cityNames.add("红河哈尼族彝族自治州");
        cityNames.add("怒江傈僳族自治州");
        cityNames.add("六盘水市");
        cityNames.add("西双版纳傣族自治州");
        cityNames.add("毕节市");
        cityNames.add("玉溪市");
        cityNames.add("黔西南布依族苗族自治州");
        cityNames.add("大理白族自治州");
        cityNames.add("黔南布依族苗族自治州");
        cityNames.add("文山壮族苗族自治州");
        cityNames.add("黔东南苗族侗族自治州");
        cityNames.add("贵阳市");
        cityNames.add("普洱市");
        cityNames.add("丽江市");
        cityNames.add("保山市");
        cityNames.add("迪庆藏族自治州");
        cityNames.add("昆明市");
        cityNames.add("楚雄彝族自治州");
        cityNames.add("临沧市");
        cityNames.add("德宏傣族景颇族自治州");

        List<Object> polygons = new ArrayList<>();

        for (int i = 0; i < cityNames.size(); i++) {
            for (int j = 0; j < jsonList.size(); j++) {
                if (cityNames.get(i).equals(jsonList.get(j).getProperties().getName())) {
                    if (jsonList.get(j).getGeometry().getType().equals("Polygon")) {
                        polygons.add(jsonList.get(j).getGeometry().getCoordinates());
                    } else {
                        polygons.addAll(jsonList.get(j).getGeometry().getCoordinates());
                    }
                    break;
                }
            }
        }
        File f = new File("D:\\a.json");
        FileOutputStream fos1 = new FileOutputStream(f);
        OutputStreamWriter dos1 = new OutputStreamWriter(fos1);
        dos1.write("{\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"properties\": {},\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"MultiPolygon\",\n" +
                "        \"coordinates\":" + polygons.toString() + "}}");
        dos1.close();
        //System.out.println(polygons.toString());
    }

    private static void UnionCity(Map jsonList, String regionNo, List<String> cityNames) throws IOException {

        List<Map<String, Map>> list = (List<Map<String, Map>>) jsonList.get("features");

        removeNotInList(list, cityNames);
        File f = new File("D:\\geoJson" + regionNo + ".json");
        FileOutputStream fos1 = new FileOutputStream(f);
        OutputStreamWriter dos1 = new OutputStreamWriter(fos1);
        dos1.write(JSON.toJSONString(jsonList));
        dos1.close();
        //System.out.println(jsonList.toString());
    }

    private static void UnionCityLine(Map jsonList, String regionNo, List<String> cityNames) throws IOException {
        List<List<List<String>>> lineList = new ArrayList<>();
        List<Map<String, Map>> list = (List<Map<String, Map>>) jsonList.get("features");
        removeNotInList(list, cityNames);
        for (int i = 0; i < list.size(); i++) {
            List<List> l = (List<List>) list.get(i).get("geometry").get("coordinates");
            collectLine(lineList, l);
        }
        removeSameLine(lineList, 0);
        List<List<List<String>>> res = new ArrayList<>();
        orderLine(res, lineList);
        System.out.println(res);
        File f = new File("D:\\geoJson" + regionNo + "2.json");
        FileOutputStream fos1 = new FileOutputStream(f);
        OutputStreamWriter dos1 = new OutputStreamWriter(fos1);
        dos1.write(JSON.toJSONString(res));
        dos1.close();
    }

    private static void orderLine(List<List<List<String>>> res, List<List<List<String>>> lineList) {
        List<List<String>> shape = new ArrayList<>();
        shape.add(lineList.get(0).get(0));
        shape.add(lineList.get(0).get(1));
        lineList.remove(0);
        getNextPoint(shape, lineList);
        res.add(shape);
        if (lineList.size() > 0) {
            orderLine(res, lineList);
        }
    }

    private static void getNextPoint(List<List<String>> shape, List<List<List<String>>> lineList) {
        List<String> thisPoint = shape.get(shape.size()-1);
        for (int i = 0; i < lineList.size(); i++) {
            List<List<String>> line = lineList.get(i);
            if(isSamePoint(thisPoint,line.get(0))){
                shape.add(line.get(1));
                lineList.remove(i);
                getNextPoint(shape, lineList);
                break;
            }else if(isSamePoint(thisPoint,line.get(1))){
                shape.add(line.get(0));
                lineList.remove(i);
                getNextPoint(shape, lineList);
                break;
            }
        }
    }

    private static String getJsonStr(String filePath) {

        //File jsonFile = new File("D:\\Ts\\Code\\myServerProject\\myServer\\src\\main\\resources\\Json\\geoJson.json");
        File jsonFile = new File(filePath);
        String jsonStr = "";

        try {
            //File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    private static void removeNotInList(List<Map<String, Map>> list, List<String> cityNames) {
        for (int i = 0; i < list.size(); i++) {
            boolean inList = false;
            for (int j = 0; j < cityNames.size(); j++) {
                if (cityNames.get(j).equals(list.get(i).get("properties").get("name"))) {
                    inList = true;
                    break;
                }
            }
            if (!inList) {
                list.remove(i);
                i--;
            }
        }
    }

    private static void collectLine(List<List<List<String>>> lineList, List<List> li) {
        if (li.size() == 1) {
            List<List> l = (List<List>) li.get(0);
            if (l.get(0).size() == 2) {
                for (int i = 0; i < l.size() - 1; i++) {
                    List<List<String>> line = new ArrayList<>();
                    line.add(l.get(i));
                    line.add(l.get(i + 1));
                    lineList.add(line);
                }
            } else {
                collectLine(lineList, l);
            }
        } else {
            for (int i = 0; i < li.size(); i++) {

            }
        }

    }

    private static void removeSameLine(List<List<List<String>>> lineList, int index) {
        List<List<String>> line = lineList.get(index);
        boolean hasSame = false;
        for (int i = index + 1; i < lineList.size(); i++) {
            if (isSameLine(line, lineList.get(i))) {
                lineList.remove(i);
                i--;
                hasSame = true;
            }
        }
        if (hasSame) {
            lineList.remove(index);
            index--;
        }
        index++;
        if (index < lineList.size()) {
            removeSameLine(lineList, index);
        }
    }

    private static boolean isSameLine(List<List<String>> l1, List<List<String>> l2) {
            BigDecimal sx1 = new BigDecimal(String.valueOf(l1.get(0).get(0)));
            BigDecimal sy1 = new BigDecimal(String.valueOf(l1.get(0).get(1)));
            BigDecimal ex1 = new BigDecimal(String.valueOf(l1.get(1).get(0)));
            BigDecimal ey1 = new BigDecimal(String.valueOf(l1.get(1).get(1)));

            BigDecimal sx2 = new BigDecimal(String.valueOf(l2.get(0).get(0)));
            BigDecimal sy2 = new BigDecimal(String.valueOf(l2.get(0).get(1)));
            BigDecimal ex2 = new BigDecimal(String.valueOf(l2.get(1).get(0)));
            BigDecimal ey2 = new BigDecimal(String.valueOf(l2.get(1).get(1)));
            if (sx1.compareTo(sx2) == 0 && sy1.compareTo(sy2) == 0 && ex1.compareTo(ex2) == 0 && ey1.compareTo(ey2) == 0) {
                return true;
            } else if (sx1.compareTo(ex2) == 0 && sy1.compareTo(ey2) == 0 && ex1.compareTo(sx2) == 0 && ey1.compareTo(sy2) == 0) {
                return true;
            } else {
                return false;
            }
    }
    private static boolean isSamePoint(List<String> p1, List<String> p2) {
            BigDecimal x1 = new BigDecimal(String.valueOf(p1.get(0)));
            BigDecimal y1 = new BigDecimal(String.valueOf(p1.get(0)));

            BigDecimal x2 = new BigDecimal(String.valueOf(p2.get(0)));
            BigDecimal y2 = new BigDecimal(String.valueOf(p2.get(0)));

            if(x1.compareTo(x2) == 0 && y1.compareTo(y2) == 0){
                return true;
            }else{
                return false;
            }
    }
}
