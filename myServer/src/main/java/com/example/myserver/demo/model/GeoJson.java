package com.example.myserver.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class GeoJson {
    private String type;
    private String id;
    private GeoJson.Properties properties;
    private GeoJson.Geometry geometry;

    @Data
    public static class Properties {
        private String name;
        private List<Integer> cp;
        private int childNum;
    }

    @Data
    public static class Geometry {
        private String type;
        private List<Object> coordinates;
    }
}
