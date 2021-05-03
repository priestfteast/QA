package com.balakin.qa.controllers;

import com.balakin.qa.services.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class IndexController {

    private final OperatorService operatorService;


    public IndexController(OperatorService operatorService) {
        this.operatorService = operatorService;

    }


    @GetMapping({"/","/index","/index.html"})
    public String showDailyStats(Model model) {
        try{
            return "index/index";
        }
        catch (Exception e){
            String error=e.getMessage()+"\n";
            e.printStackTrace();
            model.addAttribute("error",error);
            return "/index/index";
        }
    }



}
