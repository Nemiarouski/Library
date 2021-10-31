package com.example.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Main")
public class MainController {

    @GetMapping("/")
    @ApiOperation("Приветсвие на стартовой странице")
    public String hello() {
        return "Hello";
    }
}