package com.elite.tools.imgtools.business;

import com.elite.tools.imgtools.HttpVisitor;
import com.elite.tools.imgtools.Request;
import com.elite.tools.imgtools.RequestFuture;
import com.elite.tools.imgtools.RequestQueue;
import com.elite.tools.imgtools.request.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by wjc133
 * Date: 2016/1/22
 * Time: 1:16
 */
public class TestMain {
    public static void main(String[] args) {
        String url = "http://apis.baidu.com/apistore/weatherservice/citylist?cityname=北京";
        RequestQueue queue = HttpVisitor.newRequestQueue();
        ServerResult<List<City>> result = null;
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, future, future) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", "e5cba1557b738ff5e717f7558850ae8f");
                return headers;
            }
        };
        queue.add(request);
        try {
            JSONObject response = future.get();
            result = parseResponse(response);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    private static ServerResult<List<City>> parseResponse(JSONObject response) throws JSONException {
        ServerResult<List<City>> result = new ServerResult<>();
        result.setCode(response.getInt("errNum"));
        result.setMessage(response.getString("errMsg"));
        if ("success".equals(result.getMessage())) {
            JSONArray retData = response.getJSONArray("retData");
            List<City> cities = new ArrayList<>();
            for (int i = 0; i < retData.length(); i++) {
                JSONObject object = retData.getJSONObject(i);
                City city = parseCity(object);
                cities.add(city);
            }
            result.setData(cities);
        }
        return result;
    }

    private static City parseCity(JSONObject object) throws JSONException {
        City city = new City();
        city.setProvince_cn(object.getString("province_cn"));
        city.setDistrict_cn(object.getString("district_cn"));
        city.setName_cn(object.getString("name_cn"));
        city.setName_en(object.getString("name_en"));
        city.setArea_id(object.getString("area_id"));
        return city;
    }
}
