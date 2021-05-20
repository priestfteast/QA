package com.balakin.qa.controllers;

import com.balakin.qa.domain.CheckList;
import com.balakin.qa.domain.Project;
import com.balakin.qa.domain.Row;
import com.balakin.qa.services.CheckListService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;


@Controller

public class CheckListController {
    private final String CheckList_URL = "checklist/checklistform";
    private final CheckListService checkListService;

    public CheckListController(CheckListService checkListService) {
        this.checkListService = checkListService;
    }

    @GetMapping({"/checklist/{name}"})
    public String viewCheckList(@PathVariable String name, Model model) throws IOException {
        CheckList checkList = checkListService.findByProject(Project.valueOf(name));
        model.addAttribute("checkList", checkList);
        model.addAttribute("checklists", checkListService.getAllCheckLists());

        List<String> blockNames = checkListService.getBlockNames(checkList.getPayload());
        model.addAttribute("blocknames", blockNames);
        if(checkList.getPayload()!=null) {
            List<List<Row>> rows = checkListService.getBlocks(checkList.getPayload());
            model.addAttribute("row1", rows.get(0));
            model.addAttribute("row2", rows.get(1));
            model.addAttribute("row3", rows.get(2));
            model.addAttribute("row4", rows.get(3));
            model.addAttribute("row5", rows.get(4));
        }

        return CheckList_URL;
    }


    @PostMapping("/checklist/save")
    public String saveCheckList(@RequestParam MultiValueMap<String, String> formData) throws Exception {
        System.out.println(formData);
        checkListService.saveOrUpdateCheckList(formData);
        System.out.println(formData.getFirst("project"));
        return "redirect:"+formData.getFirst("project");
    }



}
