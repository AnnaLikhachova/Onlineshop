package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import com.likhachova.util.PageGenerator;
import com.likhachova.util.CookieUtil;
import com.likhachova.web.security.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CookieUtil cookieUtil;

    @RequestMapping(value = {"/allproducts"}, method = RequestMethod.GET)
    public void allproducts(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        List<Product> products = productService.getAllProducts();
        pageVariables.put("products", products);
        response.getWriter().print(PageGenerator.getInstance().getPage("allproducts.ftl", pageVariables));
    }

    @RequestMapping(value = {"/allproducts/search"}, method = RequestMethod.GET)
    @ResponseBody
    public String search(@RequestParam String search) {
        Map<String, Object> pageVariables = new HashMap<>();
        List<Product> products = productService.getProductsByDescription(search);
        pageVariables.put("products", products);
        String page = PageGenerator.getInstance().getPage("allproducts.ftl", pageVariables);
        return page;
    }

    @RequestMapping(value = {"/allproducts/addtocart/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String addtocart(@PathVariable String id, HttpServletRequest httpServletRequest) {
        Map<String, Object> pageVariables = new HashMap<>();
        Product product = productService.getProduct(Integer.parseInt(id));
        Session session = cookieUtil.getUserSessionFromRequest(httpServletRequest);
        Map<Product, Integer> cart;
        if(session != null) {
            cart = session.getCart();
            if(cart != null) {
                if(cart.containsKey(product)) {
                    int quantity = cart.get(product);
                    cart.put(product, ++quantity);
                }
                else {
                    cart.put(product, 1);
                }
            }
            else {
                cart = new HashMap<>();
                cart.put(product, 1);
            }
            session.setCart(cart);
            httpServletRequest.setAttribute("session", session);
            List<Product> products = productService.getAllProducts();
            pageVariables.put("products", products);
            return PageGenerator.getInstance().getPage("allproducts.ftl", pageVariables);
        }
        else {
            pageVariables.put("message", "You are not authorized to add product to the cart. Please log in.");
            return PageGenerator.getInstance().getPage("error.ftl", pageVariables);
        }
    }
}
