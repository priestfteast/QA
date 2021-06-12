package com.balakin.qa.services;

import com.balakin.qa.domain.Row;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public interface RowService {
    List<List<Row>> getCheckListBlocks(String checkListPayload) throws IOException;
    List<List<Row>> getEntryBlocks(String checkListPayload, String entryPayload) throws IOException;
    List<Row> uniteBlocks(List<String> blocks, Properties properties);
    String getPayload(MultiValueMap<String, String> formData);
    Properties castToProperties(String payload) throws IOException;
}
