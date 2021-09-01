package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import com.likhachova.service.SecurityService;
import com.likhachova.util.CookieUtil;
import com.likhachova.web.security.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CookieUtil cookieUtil;

    @Value( "${cookies.expire}" )
    private String cookieExpireDate;

    @RequestMapping(value = { "/login"}, method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = { "/login"}, method = RequestMethod.POST)
    public String login(Model model, @RequestParam String login, @RequestParam String password, HttpServletResponse response, HttpServletRequest httpServletRequest) throws IOException {
        String token = securityService.login(login, password);
        if( token != null){
            Session session = securityService.getSession(token); // TODO: add roles to session
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            httpServletRequest.setAttribute("products", products);
            if(securityService.isUserAdmin(login,password)){
                Cookie adminCookie = new Cookie(token, "admin-token");
                adminCookie.setMaxAge(Integer.parseInt(cookieExpireDate));
                response.addCookie(adminCookie);
                return "admin";
            } else{
                Cookie userCookie = new Cookie(token, "user-token");
                userCookie.setMaxAge(Integer.parseInt(cookieExpireDate));
                response.addCookie(userCookie);
                return "allproducts";
            }
        } else {
            model.addAttribute("message","No user with such login. Try again.");
            httpServletRequest.setAttribute("message","No user with such login. Try again.");
            logger.debug("No user with such username");
            return "login";
        }
    }

    @RequestMapping(value = { "/logout"}, method = RequestMethod.GET)
    public String logout(HttpServletRequest request,  HttpServletResponse response) {
      cookieUtil.eraseCookie(request,response);
      Session session = cookieUtil.getUserSessionByCookie(request);
      if(session != null){
          securityService.logout(session.getSessionId());
      }
      return "login";
    }

}
