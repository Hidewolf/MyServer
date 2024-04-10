package com.example.myserver.demo.controller.practiseForNewTec;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class IOSJWTDemo {
    public static void main(String[] args) throws IOException, InvalidPublicKeyException {
        // params
        String identityToken = "eyJraWQiOiJXNldjT0tCIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLmRvbXlvcy5jb25uZWN0ZWQuY2hpbmEiLCJleHAiOjE3MDAyMDcwODIsImlhdCI6MTcwMDEyMDY4Miwic3ViIjoiMDAwNTEyLjI1NjdkZjU5N2I2ZjRhYjRiYzZkZmNhNGJjMjMzYTFmLjA3MzMiLCJjX2hhc2giOiJET2dfd3MzN0Y0M1NZbklHTE54Z3dBIiwiZW1haWwiOiJoZzR5MjltNDZqQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNzAwMTIwNjgyLCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.MDYIDZ9QjTjxpwYNMAm3_J1jz9zfvJzvJlO9XwOcvESybU724JB1KO7aEI45Ks5d-97XO7HenR9dyox0lipivSFjnEqgNU58JE-JRDP1MmO7YCKtjlxdHsmkx49O_sLJHT_aujk648EpiGq20gNR4XKnxK98BtWgvp7ezuokM7VGFt1Ph3q4gmoT5qm77-3JYtNwyfK93Y1yr7wwRz8zNI0Jx4TXCj1cgwS0zOZYuPm91enpiuhacCiAlUdSzCe7xMuwbpgmsitT1V8nZAk7Cv31oDinmjkygSvGws6fhIhxAXC17xg7qELbWLRGEbDoVD-D5FU8nGBWcBabSSM9ow";

        IOSJWTDemo jwtDemo = new IOSJWTDemo();
        JSONArray arr = jwtDemo.getKeys();
        List<String> identity = jwtDemo.spliteIdentityToken(identityToken);

        JSONObject keyJson = jwtDemo.getKeyByKid(arr, JSONObject.parseObject(identity.get(0)).getString("kid"));
        Jwk jwk = Jwk.fromValues(keyJson);
        PublicKey key = jwk.getPublicKey();

        jwtDemo.verifyIdentity(identityToken,identity, key);
    }

    private void verifyIdentity(String identityToken, List<String> identity, PublicKey key) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(key);
        JSONObject obj = JSONObject.parseObject(identity.get(1));
        jwtParser.requireIssuer(obj.getString("iss"));
        jwtParser.requireAudience(obj.getString("aud"));
        jwtParser.requireSubject(obj.getString("sub"));

        Jws<Claims> claim = jwtParser.parseClaimsJws(identityToken);
        if (claim!=null){
            System.out.println(claim);
            System.out.println(claim.getBody().get("auth_time"));
            String time = claim.getBody().get("auth_time").toString();
            System.out.println(Instant.ofEpochSecond(Long.parseLong(time)));
            System.out.println(Instant.ofEpochMilli(new Date().getTime()));

        }
    }

    private JSONObject getKeyByKid(JSONArray arr, String kid) {
        return (JSONObject) arr.stream().filter(key -> ((JSONObject) key).getString("kid").equals(kid)).findFirst().get();
    }

    private JSONArray getKeys() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://appleid.apple.com/auth/keys");
        HttpResponse res = client.execute(get);
        String result = EntityUtils.toString(res.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(result);

        String keys = jsonObject.getString("keys");
        JSONArray arr = JSONArray.parseArray(keys);
        return arr;
    }

    private List<String> spliteIdentityToken(String identityToken) {
        String[] parts = identityToken.split("\\.");
        List<String> res = new ArrayList<>();
        res.add(new String(Base64.getDecoder().decode(parts[0]), StandardCharsets.UTF_8));
        res.add(new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8));
        res.add(parts[2]);
        return res;
    }
}
