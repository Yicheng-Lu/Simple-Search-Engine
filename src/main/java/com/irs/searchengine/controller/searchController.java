package com.irs.searchengine.controller;

import com.irs.searchengine.dto.docInfo;
import com.irs.searchengine.ref.PriorityQueue;
import com.irs.searchengine.service.Searcher;
import com.irs.searchengine.service.SpellChecking;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class searchController {

    @RequestMapping("/searcher")
    public String getQuery(@RequestParam("querystr") String query, Model model) throws IOException {
        long startTime = System.currentTimeMillis();
        Searcher searcher = new Searcher();
        PriorityQueue<Integer,String> pq =  Searcher.occurrences(query);
        docInfo[] docInfos = searcher.queue2List(pq);
        long endTime = System.currentTimeMillis();
        long timeUsage = endTime - startTime;
        if (docInfos.length != 0){
            model.addAttribute("hasRes", true);
            model.addAttribute("resList", docInfos);
            model.addAttribute("numRes", docInfos.length);
            model.addAttribute("timeUsage_res", timeUsage);
        } else {
            model.addAttribute("hasRes", false);
            String[] altWords = SpellChecking.alternativeWord(query);
            if (altWords.length != 0) {
                model.addAttribute("alternatives", altWords);
            } else {
                String[] noAlt = {"None"};
                model.addAttribute("alternatives", noAlt);
            }
        }
        return "searchResultPage";
    }
}
