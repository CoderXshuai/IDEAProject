package com.example.demo.entity;

import lombok.Data;

/**
 * @author CoderXshuai
 * @date 2021/5/18/0018 18:34
 */
@Data
public class MyJson {
    boolean include_req = false;
    Model[] data;
}
