package com.example.demo.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.entity.Model;
import com.example.demo.entity.MyJson;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CoderXshuai
 * @date 2021/5/18/0018 17:54
 */
@Service
public class RequestService {
    public int requestModel(String access_token, List<Model> modelList, String type) throws IOException {
        int score;
        List<Integer> res = new ArrayList<>();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/table_infer/" + type + "?access_token=" + access_token;
        try {
            for (int i = 0; i < modelList.size(); i = i + modelList.size() / 5) {
                MyJson myJson = new MyJson();
                myJson.setData(new Model[]{modelList.get(i)});
                String str = JSONUtil.toJsonStr(myJson);
                RequestBody body = RequestBody.create(mediaType, str);
                Request request = new Request.Builder()
                        .url(url)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                JSONObject object = JSONUtil.parseObj(response.body().string());
                res.add(object.getJSONArray("batch_result").getJSONObject(0).getInt("score"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            score = (int) Math.round(res.stream().mapToInt(Integer::intValue).average().getAsDouble());
        }
        return score;
    }
}
