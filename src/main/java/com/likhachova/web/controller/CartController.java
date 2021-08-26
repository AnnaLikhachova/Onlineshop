package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import com.likhachova.util.CookieUtil;
import com.likhachova.util.PageGenerator;
import com.likhachova.web.security.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CookieUtil cookieUtil;

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public void admin(HttpServletResponse response, HttpServletRequest httpServletRequest) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        Session session = cookieUtil.getUserSessionFromRequest(httpServletRequest);
        if(session != null) {
            Map<Product, Integer> cart = session.getCart();
            if(cart != null) {
                pageVariables.put("cart", cart);
                response.getWriter().print(PageGenerator.getInstance().getPage("cart.ftl", pageVariables));
            }
        } else {
            pageVariables.put("message", "You are not authorized to add product to the cart. Please log in.");
            logger.debug("User is not authorized to see to the cart.");
            response.getWriter().print(PageGenerator.getInstance().getPage("error.ftl", pageVariables));
        }

    }

    @RequestMapping(value = {"/cart/increase"}, method = RequestMethod.POST)
    @ResponseBody
    public String increase(@RequestParam String id, HttpServletRequest httpServletRequest) {
        Map<String, Object> pageVariables = new HashMap<>();
        Product product = productService.getProduct(Integer.parseInt(id));
        Session session = cookieUtil.getUserSessionFromRequest(httpServletRequest);
        if(session != null) {
            Map<Product, Integer> cart = session.getCart();
            if(cart != null) {
                if(product != null) {
                    if(cart.containsKey(product)) {
                        int quantity = cart.get(product);
                        cart.put(product, ++quantity);
                        session.setCart(cart);
                        httpServletRequest.setAttribute("session", session);
                        pageVariables.put("cart", cart);
                    }
                }
            }
            return PageGenerator.getInstance().getPage("cart.ftl", pageVariables);
        }
        else {
            pageVariables.put("message", "You are not authorized to increase product in the cart. Please log in.");
            logger.debug("User is not authorized to increase product in the cart");
            return PageGenerator.getInstance().getPage("error.ftl", pageVariables);
        }
    }

    @RequestMapping(value = {"/cart/decrease"}, method = RequestMethod.POST)
    @ResponseBody
    public String decrease(@RequestParam String id, HttpServletRequest httpServletRequest) {
        Map<String, Object> pageVariables = new HashMap<>();
        Product product = productService.getProduct(Integer.parseInt(id));
        Session session = cookieUtil.getUserSessionFromRequest(httpServletRequest);
        if(session != null) {
            Map<Product, Integer> cart = session.getCart();
            if(cart != null) {
                if(product != null) {
                    if(cart.containsKey(product)) {
                        int quantity = cart.get(product);
                        if(quantity > 1) {
                            cart.put(product, --quantity);
                        }
                        else {
                            cart.remove(product);
                        }
                        session.setCart(cart);
                        httpServletRequest.setAttribute("session", session);
                        pageVariables.put("cart", cart);
                    }
                }
            }
            return PageGenerator.getInstance().getPage("cart.ftl", pageVariables);
        }
        else {
            pageVariables.put("message", "You are not authorized to decrease product in the cart. Please log in.");
            logger.debug("User is not authorized to decrease product in the cart");
            return PageGenerator.getInstance().getPage("error.ftl", pageVariables);
        }
    }
}


