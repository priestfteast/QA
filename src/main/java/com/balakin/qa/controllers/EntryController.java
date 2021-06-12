package com.balakin.qa.controllers;

import com.balakin.qa.domain.*;
import com.balakin.qa.services.CheckListService;
import com.balakin.qa.services.EntryService;
import com.balakin.qa.services.OperatorService;
import com.balakin.qa.services.RowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class EntryController {
    private final String ALL_ENTRIES_URL = "entries/allentries";
    private final String ENTRY_FORM_URL = "entries/entryform";
    private final CheckListService checkListService;
    private final EntryService entryService;
    private final OperatorService operatorService;
    private final RowService rowService;

    public EntryController(CheckListService checkListService, EntryService entryService, OperatorService operatorService, RowService rowService) {
        this.checkListService = checkListService;
        this.entryService = entryService;
        this.operatorService = operatorService;
        this.rowService = rowService;
    }



    @GetMapping({"/entries/{id}/update"})
    public String updateEntry(@PathVariable String id, Model model) throws IOException {
        Entry entry = entryService.findById(Long.valueOf(id));
        CheckList checkList = checkListService.findByProject(entry.getProject());
        model.addAttribute("entry", entry);
        List<String> criticalErrors = checkListService.getCritErrors(checkList.getPayload());
        String criticalError =rowService.castToProperties(entry.getPayload()).getProperty("critical_error");
        List<String> blockNames = checkListService.getBlockNames(entry.getPayload());
        List<Operator> operators = operatorService.getAllOperators();
        model.addAttribute("criterrors",criticalErrors);
        model.addAttribute("criterror",criticalError);
        model.addAttribute("operators", operators);
        model.addAttribute("blocknames", blockNames);
        if(entry.getPayload()!=null) {
            List<List<Row>> rows = entryService.getBlocks(checkList.getPayload(), entry.getPayload());
            model.addAttribute("row1", rows.get(0));
            model.addAttribute("row2", rows.get(1));
            model.addAttribute("row3", rows.get(2));
            model.addAttribute("row4", rows.get(3));
            model.addAttribute("row5", rows.get(4));
        }

        return ENTRY_FORM_URL;
    }

    @GetMapping({"/entries/new/{name}"})
    public String newEntry(@PathVariable String name, Model model) throws IOException {
        CheckList checkList = checkListService.findByProject(Project.valueOf(name));
        List<Operator> operators = operatorService.getAllOperators();
        List<String> criticalErrors = checkListService.getCritErrors(checkList.getPayload());
        String criticalError ="Отсутствуют";
        model.addAttribute("operators", operators);
        model.addAttribute("checkList", checkList);
        model.addAttribute("criterrors",criticalErrors);
        model.addAttribute("criterror",criticalError);

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

        return ENTRY_FORM_URL;
    }
    @PostMapping ({"/entries/entrylist"})
      public String showEntries(@RequestParam MultiValueMap<String, String> formData, Model model) {
        Project project = Project.valueOf(formData.getFirst("project"));
        List<Operator> operators = operatorService.getAllOperators();
        Operator operator = operatorService.findByName(formData.getFirst("operator"));
        LocalDate start_date = LocalDate.parse(formData.getFirst("start_date"));
        LocalDate end_date = LocalDate.parse(formData.getFirst("end_date"));
        List<Entry> entries = formData.getFirst("operator").equals("all") ?
                entryService.findAllByProjectAndCheckDateBetween(project, start_date, end_date) :
                entryService.findAllByProjectAndOperatorAndCheckDateBetween(Project.valueOf(formData.getFirst("project")), operator, start_date, end_date);
        model.addAttribute("start_date", start_date);
        model.addAttribute("end_date", end_date);
        model.addAttribute("operators", operators);
        model.addAttribute("operator", operator.getFullName());
        model.addAttribute("project", project.name());
        model.addAttribute("entries", entries);

        return ALL_ENTRIES_URL;
    }

    @GetMapping ({"/entries/allentries"})
    public String showEntries(Model model) {
        List<Operator> operators = operatorService.getAllOperators();
        String operator = "all";
        Project project = Project.AUCHAN;
        LocalDate start_date = LocalDate.parse(LocalDate.now().toString().substring(0, 8) + "01");
        LocalDate end_date = LocalDate.now();
        List<Entry> entries = entryService.findAllByProjectAndCheckDateBetween(project, start_date, end_date);
        model.addAttribute("start_date", start_date);
        model.addAttribute("end_date", end_date);
        model.addAttribute("operators", operators);
        model.addAttribute("operator", operator);
        model.addAttribute("project", project.name());
        model.addAttribute("entries", entries);

        return ALL_ENTRIES_URL;
    }

    @PostMapping("/entries/save")
    public String saveEntry(@RequestParam MultiValueMap<String, String> formData) throws Exception {
        System.out.println(formData);
        System.out.println("!!!");
        entryService.saveOrUpdateEntry(formData);

        return "redirect:/entries/allentries";
    }

    @GetMapping("entries/{id}/delete")
    public String deleteById(@PathVariable String id){
        entryService.deleteById(Long.valueOf(id));
        return "redirect:/entries/allentries";
    }

}
