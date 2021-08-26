package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import com.likhachova.service.SecurityService;
import com.likhachova.service.UserService;
import com.likhachova.util.CookieUtil;
import com.likhachova.util.PageGenerator;
import com.likhachova.web.security.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CookieUtil cookieUtil;

    @Value( "${cookies.expire.date}" )
    private String cookieExpireDate;

    @RequestMapping(value = { "/login"}, method = RequestMethod.GET)
    public void login(HttpServletResponse response) throws IOException {
        response.getWriter().print(PageGenerator.getInstance().getPage("login.ftl", null));
    }

    @RequestMapping(value = { "/login"}, method = RequestMethod.POST)
    public void login(@RequestParam String login, @RequestParam String password,  HttpServletResponse response, HttpServletRequest httpServletRequest) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String token = securityService.login(login, password);
        if( token != null){
            Session session = securityService.getSession(token); // TODO: add roles to session
            List<Product> products = productService.getAllProducts();
            pageVariables.put("products", products);
            response.setContentType("text/html;charset=utf-8");
            if(securityService.isUserAdmin(login,password)){
                Cookie adminCookie = new Cookie(token, "admin-token");
                adminCookie.setMaxAge(Integer.parseInt(cookieExpireDate));
                response.addCookie(adminCookie);
                response.getWriter().println(PageGenerator.getInstance().getPage("admin.ftl", pageVariables));
            } else{
                Cookie userCookie = new Cookie(token, "user-token");
                userCookie.setMaxAge(Integer.parseInt(cookieExpireDate));
                response.addCookie(userCookie);
                response.getWriter().println(PageGenerator.getInstance().getPage("allproducts.ftl", pageVariables));
            }
        } else {
            pageVariables.put("message","No user with such login. Try again.");
            logger.debug("No user with such username");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(PageGenerator.getInstance().getPage("login.ftl", pageVariables));
        }
    }

    @RequestMapping(value = { "/logout"}, method = RequestMethod.GET)
    @ResponseBody
    public String logout(HttpServletRequest request,  HttpServletResponse response) {
      cookieUtil.eraseCookie(request,response);
      Session session = cookieUtil.getUserSessionFromRequest(request);
      if(session != null){
          securityService.logout(session.getSessionId());
      }
      return PageGenerator.getInstance().getPage("login.ftl", null);
    }

}
