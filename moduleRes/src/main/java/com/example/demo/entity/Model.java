package com.example.demo.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * @author CoderXshuai
 * @date 2021/5/17/0017 17:14
 */
@Data
public class Model {
    private long microsecond;
    private double acSX;
    private double acSY;
    private double acSZ;
    private double accX;
    private double accY;
    private double accZ;
    private double angX;
    private double angY;
    private double angZ;
}
