package com.example.demo.controller;

import com.example.demo.entity.Model;
import com.example.demo.service.AuthService;
import com.example.demo.service.EvaluationService;
import com.example.demo.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author CoderXshuai
 * @date 2021/5/17/0017 17:13
 */
@Controller
@RequestMapping("/eva")
@CrossOrigin
public class EvaluationController {
    @Autowired
    EvaluationService evaluationService;
    @Autowired
    AuthService authService;
    @Autowired
    RequestService requestService;

    @RequestMapping(value = "/{type}")
    @ResponseBody
    public int uploadOneFile(HttpServletRequest request, @PathVariable String type) throws IOException {
        //读取一个文件
        MultipartHttpServletRequest mreq = null;
        if (request instanceof MultipartHttpServletRequest) {
            mreq = (MultipartHttpServletRequest) request;
        }
        assert mreq != null;
        MultipartFile mf = mreq.getFile("file");
        assert mf != null;
        List<Model> modelList = evaluationService.readFile(mf.getInputStream());
        String access_token = authService.getAuth();
        int score = requestService.requestModel(access_token, modelList, type);
        return score;
    }
}
