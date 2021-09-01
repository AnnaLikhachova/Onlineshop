package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import com.likhachova.util.CookieUtil;
import com.likhachova.web.security.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CookieUtil cookieUtil;

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String admin(HttpServletRequest httpServletRequest, Model model)  {
        Session session = cookieUtil.getUserSessionByCookie(httpServletRequest);
        if(session != null) {
            Map<Product, Integer> cart = session.getCart();
            if(cart != null) {
                model.addAttribute("cart", cart);
            }

        } else {
            model.addAttribute("message", "You are not authorized to add product to the cart. Please log in.");
            logger.debug("User is not authorized to see to the cart.");
            return "error";
        }
        return "cart";
    }

    @RequestMapping(value = {"/user/cart/increase"}, method = RequestMethod.POST)
    public String increase(@RequestParam String id, HttpServletRequest httpServletRequest, Model model) {
        Product product = productService.getProduct(Integer.parseInt(id));
        Session session = cookieUtil.getUserSessionByCookie(httpServletRequest);
        if(session != null) {
            Map<Product, Integer> cart = session.getCart();
            if(cart != null) {
                if(product != null) {
                    if(cart.containsKey(product)) {
                        int quantity = cart.get(product);
                        cart.put(product, ++quantity);
                        session.setCart(cart);
                        httpServletRequest.setAttribute("session", session);
                        model.addAttribute("cart", cart);
                        logger.debug("Product {} quantity was increased in the cart", product.getName());
                    }
                }
            }
        }
        else {
            model.addAttribute("message", "You are not authorized to increase product in the cart. Please log in.");
            logger.debug("User is not authorized to increase product in the cart");
            return "error";
        }
        return "cart";
    }

    @RequestMapping(value = {"/user/cart/decrease"}, method = RequestMethod.POST)
    public String decrease(@RequestParam String id, HttpServletRequest httpServletRequest, Model model) {
        Product product = productService.getProduct(Integer.parseInt(id));
        Session session = cookieUtil.getUserSessionByCookie(httpServletRequest);
        if(session != null) {
            Map<Product, Integer> cart = session.getCart();
            if(cart != null) {
                if(product != null) {
                    if(cart.containsKey(product)) {
                        int quantity = cart.get(product);
                        if(quantity > 1) {
                            cart.put(product, --quantity);
                            logger.debug("Product {} quantity was decreased in the cart", product.getName());
                        }
                        else {
                            cart.remove(product);
                            logger.debug("Product {} was removed from the cart", product.getName());
                        }
                        session.setCart(cart);
                        httpServletRequest.setAttribute("session", session);
                        model.addAttribute("cart", cart);
                    }
                }
            }
            return "cart";
        }
        else {
            model.addAttribute("message", "You are not authorized to decrease product in the cart. Please log in.");
            logger.debug("User is not authorized to decrease product in the cart");
            return "error";

        }
    }
}


