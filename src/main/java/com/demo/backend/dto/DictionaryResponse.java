package com.demo.backend.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DictionaryResponse {

    private String name;
    private Map<String, Map<String, String>> values;

}