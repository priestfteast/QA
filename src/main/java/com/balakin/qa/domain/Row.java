package com.balakin.qa.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Row {

    public Row(String row) {
        String[] blocks = row.split("%");
        this.id = blocks[0].substring(blocks[0].indexOf("_")+1,blocks[0].indexOf("=")).trim();
        this.criteriaName = blocks[0].substring(0,blocks[0].indexOf("=")).trim();
        this.criteria = blocks[0].substring(blocks[0].indexOf("=")+1).trim();
        this.weightName = blocks[1].substring(0,blocks[1].indexOf("=")).trim();
        this.weight = blocks[1].substring(blocks[1].indexOf("=")+1).trim();
        System.out.println(this);
    }

    private String id;
    private String criteriaName;
    private String criteria;
    private String weightName;
    private String weight;


}
