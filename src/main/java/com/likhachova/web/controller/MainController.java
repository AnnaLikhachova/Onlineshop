package com.likhachova.web.controller;

import com.likhachova.util.ImageReader;
import com.likhachova.util.PageGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping(value = { "/", "/main" }, method = RequestMethod.GET)
    public void login(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String mainImage = ImageReader.getImage("templates/images/products.jpg");
        pageVariables.put("mainImage", mainImage);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(PageGenerator.getInstance().getPage("main.ftl", pageVariables));
    }
}
