package com.example.demo.service.api;

import com.example.demo.dto.DictionaryResponse;

import java.util.Dictionary;

public interface DictionaryService {

    Dictionary getDictionaryByName(String dictionaryName);

    DictionaryResponse getDictionaryResponseByName(String name);

    int getExtIdByDictionary(String dictionaryName, String key);

    int getExtIdByDictionary(Dictionary dictionary, String key);

    String getKeyByExtId(String dictionaryName, int extId);

    String getKeyByExtId(Dictionary dictionary, int extId);
}
