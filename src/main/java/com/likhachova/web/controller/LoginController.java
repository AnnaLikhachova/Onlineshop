package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import com.likhachova.service.SecurityService;
import com.likhachova.service.UserService;
import com.likhachova.util.CookieUtil;
import com.likhachova.util.PageGenerator;
import com.likhachova.web.security.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class LoginController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CookieUtil cookieUtil;

    @RequestMapping(value = { "/login"}, method = RequestMethod.GET)
    public void login(HttpServletResponse response) throws IOException {
        response.getWriter().print(PageGenerator.getInstance().getPage("login.ftl", null));
    }

    @RequestMapping(value = { "/login"}, method = RequestMethod.POST)
    public void login(@RequestParam String login, @RequestParam String password,  HttpServletResponse response, HttpServletRequest httpServletRequest) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String token = securityService.login(login, password);
        if( token != null){
            Session session = securityService.getSession(token);
            httpServletRequest.setAttribute("session", session);
            List<Product> products = productService.getAllProducts();
            pageVariables.put("products", products);
            response.setContentType("text/html;charset=utf-8");
            if(securityService.isUserAdmin(login,password)){
                response.addCookie(new Cookie(token, "admin-token"));
                response.getWriter().println(PageGenerator.getInstance().getPage("admin.ftl", pageVariables));
            } else{
                response.addCookie(new Cookie(token, "user-token"));
                response.getWriter().println(PageGenerator.getInstance().getPage("allproducts.ftl", pageVariables));
            }
        } else {
            pageVariables.put("message","No user with such login. Try again.");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(PageGenerator.getInstance().getPage("login.ftl", pageVariables));
        }
    }

    @RequestMapping(value = { "/logout"}, method = RequestMethod.GET)
    @ResponseBody
    public String logout(HttpServletRequest request,  HttpServletResponse response) {
      request.setAttribute("session", null);
      cookieUtil.eraseCookie(request,response);
      return PageGenerator.getInstance().getPage("login.ftl", null);
    }

}
