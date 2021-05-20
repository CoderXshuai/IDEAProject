package com.example.demo.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.demo.entity.Model;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CoderXshuai
 * @date 2021/5/17/0017 17:26
 */
@Service
public class EvaluationService {
    public List<Model> readFile(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        reader.readLine();
        String item;
        boolean flag = false;
        LocalDateTime start = null;
        List<Model> modelList = new ArrayList<>();
        while ((item = reader.readLine()) != null) {
            String[] strings = item.split("\t");
            Model model = new Model();
            if (!flag) {
                start = LocalDateTimeUtil.parse(strings[3], " yyyy-MM-dd HH:mm:ss.SSS");
                flag = true;
            }
            LocalDateTime time = LocalDateTimeUtil.parse(strings[3], " yyyy-MM-dd HH:mm:ss.SSS");
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
        return modelList;
    }
}
