package com.irs.searchengine.controller;

import com.irs.searchengine.service.Crawler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class crawlController {

    @RequestMapping("/crawler")
    public String getURL(@RequestParam("urlstr") String url, Model model){
        Long startTime = System.currentTimeMillis();
        Crawler crawler = new Crawler(url);
        Long endTime = System.currentTimeMillis();
        String[] urlList = crawler.getURLList();
        Long time = endTime - startTime;
        model.addAttribute("urlList", urlList);
        model.addAttribute("numURLs", urlList.length);
        model.addAttribute("timeUsage_url", time);
        return "crawlerResultPage";
    }
}
