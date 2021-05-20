package com.example.demo;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.entity.Model;
import com.example.demo.entity.MyJson;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    public static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            //返回结果示例
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result.toString());
            String access_token = jsonObject.getStr("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.print("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
    @Test
    void contextLoads() {
        List<Model> modelList = new ArrayList<>();
        FileReader fileReader = new FileReader("com/example/demo/test.txt");
        List<String> list = fileReader.readLines();
        LocalDateTime start = null;
        for (String item : list) {
            if (list.indexOf(item) == 0) {
                continue;
            }
            String[] strings = item.split("\t");
            Model model = new Model();
            if (list.indexOf(item) == 1) {
                start = LocalDateTimeUtil.parse(strings[3], " yyyy-MM-dd HH:mm:ss.SSS");
            }
            LocalDateTime time = LocalDateTimeUtil.parse(strings[3], " yyyy-MM-dd HH:mm:ss.SSS");
            assert start != null;
            Duration between = LocalDateTimeUtil.between(start, time);
            model.setMicrosecond(between.toNanos() / 1000000);
            model.setAcSX(Double.parseDouble(strings[4]));
            model.setAcSY(Double.parseDouble(strings[5]));
            model.setAcSZ(Double.parseDouble(strings[6]));
            model.setAccX(Double.parseDouble(strings[7]));
            model.setAccY(Double.parseDouble(strings[8]));
            model.setAccZ(Double.parseDouble(strings[9]));
            model.setAngX(Double.parseDouble(strings[10]));
            model.setAngY(Double.parseDouble(strings[11]));
            model.setAngZ(Double.parseDouble(strings[12]));
            modelList.add(model);
        }
//        JSONObject object = JSONUtil.parseObj(modelList.get(0));
//        JSONArray array = JSONUtil.parseArray(modelList);
        System.out.println(JSONUtil.toJsonStr(modelList.get(0)));
//        return modelList;
    }


    String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "QsRBlDtmwUmQTQKGadfHKPq2";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "nc7cS66gzGqCtMR7tHlXmMIwpg3wTlbd";
        return getAuth(clientId, clientSecret);
    }

//    @Test
//    void requestModel() throws IOException {
//        String access_token = getAuth();
//        List<Model> modelList = contextLoads();
//        List<Integer> res = new ArrayList<>();
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/table_infer/cepingju?access_token=" + access_token;
//        try {
//            for (int i = 0; i < modelList.size(); i = i + modelList.size() / 10) {
//                MyJson myJson = new MyJson();
//                myJson.setData(new Model[]{modelList.get(i)});
//                String str = JSONUtil.toJsonStr(myJson);
//                RequestBody body = RequestBody.create(mediaType, str);
//                Request request = new Request.Builder()
//                        .url(url)
//                        .method("POST", body)
//                        .addHeader("Content-Type", "application/json")
//                        .build();
//                Response response = client.newCall(request).execute();
//                JSONObject object = JSONUtil.parseObj(response.body().string());
//                res.add(object.getJSONArray("batch_result").getJSONObject(0).getInt("score"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println((int) res.stream().mapToInt(Integer::intValue).average().getAsDouble());
//        }
//    }
}
