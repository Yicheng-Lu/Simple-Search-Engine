package com.irs.searchengine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
class indexController {

    // Set default homepage
    @RequestMapping("/")
    public String home() { return "home"; }

    @RequestMapping(value = "/returnHome", method = RequestMethod.GET)
    public String returnHome(HttpServletRequest request) {
        return request.getParameter("url");
    }

}
