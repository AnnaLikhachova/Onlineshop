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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CookieUtil cookieUtil;

    @RequestMapping(value = {"/allproducts"}, method = RequestMethod.GET)
    public String allproducts(Model model,  HttpServletRequest httpServletRequest) throws IOException {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        httpServletRequest.setAttribute("products", products);
        return "allproducts";
    }

    @RequestMapping(value = {"/user/allproducts/search"}, method = RequestMethod.GET)
    public String search(@RequestParam String search, Model model,  HttpServletRequest httpServletRequest) {
        List<Product> products = productService.getProductsByDescription(search);
        model.addAttribute("products", products);
        httpServletRequest.setAttribute("products", products);
        return "allproducts";
    }

    @RequestMapping(value = {"/user/allproducts/addtocart/{id}"}, method = RequestMethod.GET)
    public String addtocart(@PathVariable String id, HttpServletRequest httpServletRequest, Model model) {
        Product product = productService.getProduct(Integer.parseInt(id));
        Session session = cookieUtil.getUserSessionByCookie(httpServletRequest);
        Map<Product, Integer> cart;
        if(session != null) {
            cart = session.getCart();
            if(cart != null) {
                if(cart.containsKey(product)) {
                    int quantity = cart.get(product);
                    cart.put(product, ++quantity);
                    logger.debug("Product {} was added to the cart", product.getName());
                }
                else {
                    cart.put(product, 1);
                    logger.debug("Product {} was added to the cart", product.getName());
                }
            }
            else {
                cart = new HashMap<>();
                cart.put(product, 1);
                logger.debug("Product {} was added to the cart", product.getName());
            }
            session.setCart(cart);
            httpServletRequest.setAttribute("session", session);
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            httpServletRequest.setAttribute("products", products);
            return "allproducts";
        }
        else {
            model.addAttribute("message", "You are not authorized to add product to the cart. Please log in.");
            return "allproducts";
        }
    }
}
