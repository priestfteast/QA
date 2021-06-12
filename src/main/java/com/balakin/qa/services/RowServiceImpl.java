package com.balakin.qa.services;

import com.balakin.qa.domain.Row;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class RowServiceImpl implements RowService {

    @Override
    public List<List<Row>> getCheckListBlocks(String checkListPayload) throws IOException {
        if(checkListPayload==null) return new ArrayList<>();
        Properties properties = castToProperties(checkListPayload);
        List<List<Row>> blocks = new ArrayList<>();
        List<String> block1, block2, block3, block4, block5, block6 = new ArrayList<>();
        String[] pairs = checkListPayload.split("\n");
        block1 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_a")||pair.contains("weight_a")).collect(Collectors.toList());
        block2 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_b")||pair.contains("weight_b")).collect(Collectors.toList());
        block3 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_c")||pair.contains("weight_c")).collect(Collectors.toList());
        block4 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_d")||pair.contains("weight_d")).collect(Collectors.toList());
        block5 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_e")||pair.contains("weight_e")).collect(Collectors.toList());
        block6 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_f")||pair.contains("weight_f")).collect(Collectors.toList());
        blocks.add(uniteBlocks(block1,properties));blocks.add(uniteBlocks(block2,properties));
        blocks.add(uniteBlocks(block3,properties));blocks.add(uniteBlocks(block4,properties));
        blocks.add(uniteBlocks(block5,properties));blocks.add(uniteBlocks(block6,properties));
        return blocks;
    }

    @Override
    public List<List<Row>> getEntryBlocks(String checkListPayload, String entryPayload) throws IOException {
        if(checkListPayload==null) return new ArrayList<>();
        Properties properties = castToProperties(checkListPayload);
        List<List<Row>> blocks = new ArrayList<>();
        List<String> block1, block2, block3, block4, block5, block6 = new ArrayList<>();
        String[] pairs = entryPayload.split("\n");
        block1 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_a")||pair.contains("weight_a")).collect(Collectors.toList());
        block2 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_b")||pair.contains("weight_b")).collect(Collectors.toList());
        block3 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_c")||pair.contains("weight_c")).collect(Collectors.toList());
        block4 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_d")||pair.contains("weight_d")).collect(Collectors.toList());
        block5 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_e")||pair.contains("weight_e")).collect(Collectors.toList());
        block6 = Arrays.stream(pairs).filter(pair->pair.contains("criteria_f")).collect(Collectors.toList());
        blocks.add(uniteBlocks(block1,properties));blocks.add(uniteBlocks(block2,properties));
        blocks.add(uniteBlocks(block3,properties));blocks.add(uniteBlocks(block4,properties));
        blocks.add(uniteBlocks(block5,properties));blocks.add(uniteBlocks(block6,properties));
        return blocks;
    }

    @Override
    public List<Row> uniteBlocks(List<String> blocks, Properties properties) {
        List<Row> result = new ArrayList<>();
        for (int i = 0; i < blocks.size()-1; i++) {
            String row;
                row = blocks.get(i) + "%" + blocks.get(i + 1) + " MaxWeight=" + properties.get(blocks.get(i + 1).substring(0, blocks.get(i + 1).indexOf("=")));
            result.add(new Row(row));
            i++;
        }
        return result;
    }

    @Override
    public String getPayload(MultiValueMap<String, String> formData) {
        StringBuilder sb = new StringBuilder();
        formData.entrySet().forEach(stringListEntry -> sb.append(stringListEntry.toString()+"\n"));
        String payload = sb.toString();
        payload = payload.replaceAll("[\\[\\]]","");
        System.out.println(payload);
        return payload;
    }

    @Override
    public Properties castToProperties(String payload) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader(payload));
        return properties;
    }
}
