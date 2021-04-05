package com.example.tracker.controllers;

import com.example.tracker.models.CasesRecord;
import com.example.tracker.repositories.CasesRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CasesRecordRepository casesRecordRepository;

    @GetMapping("/")
    public String home(Model model){
        long totalCases = casesRecordRepository.findAll().stream()
                .mapToLong(CasesRecord::getConfirmed).sum();
        long newCases = casesRecordRepository.findAll().stream()
                .mapToLong(CasesRecord::getNewCases).sum();

        model.addAttribute("records", casesRecordRepository.findAll());
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("newCases", newCases);
        return "hello";
    }
}
