package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import com.likhachova.util.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = { "/admin"}, method = RequestMethod.GET)
    public void admin(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        List<Product> products = productService.getAllProducts();
        pageVariables.put("products", products);
        response.getWriter().print(PageGenerator.getInstance().getPage("admin.ftl", pageVariables));
    }

    @RequestMapping(value = { "/products/update/{id}"}, method = RequestMethod.GET)
    public void updateproduct(@PathVariable String id, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        Product product = productService.getProduct(Integer.parseInt(id));
        pageVariables.put("product", product);
        response.getWriter().print(PageGenerator.getInstance().getPage("updateproduct.ftl", pageVariables));
    }

    @RequestMapping(value = { "/addproduct"}, method = RequestMethod.GET)
    public void addproduct(HttpServletResponse response) throws IOException {
        response.getWriter().print(PageGenerator.getInstance().getPage("addproduct.ftl", null));
    }

    @RequestMapping(value = { "/products/updateproduct"}, method = RequestMethod.POST)
    public void update(@RequestParam String id, @RequestParam String name, @RequestParam String price, @RequestParam String description, @RequestParam String date, HttpServletResponse response) throws IOException {
        Product product = new Product(Integer.parseInt(id),name,Integer.parseInt(price),description, LocalDate.parse(date));
        productService.updateProduct(product);
        Map<String, Object> pageVariables = new HashMap<>();
        List<Product> products = productService.getAllProducts();
        pageVariables.put("products", products);
        response.getWriter().print(PageGenerator.getInstance().getPage("admin.ftl", pageVariables));
    }

    @RequestMapping(value = { "/products/delete"}, method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam String id) {
        productService.deleteProduct(Integer.parseInt(id));
        Map<String, Object> pageVariables = new HashMap<>();
        List<Product> products = productService.getAllProducts();
        pageVariables.put("products", products);
        return PageGenerator.getInstance().getPage("admin.ftl", pageVariables);
    }

    @RequestMapping(value = { "/products/add"}, method = RequestMethod.POST)
    public void addProductToList(@RequestParam String name, @RequestParam String price, @RequestParam String description, @RequestParam String date, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        if(name == null || price == null || description == null || date == null || name.isEmpty() || price.isEmpty() ||  description.isEmpty() || date.isEmpty()) {
            pageVariables.put("message", "Product was not added as one or more fields were empty");
        }
        else {
            Product product = new Product(name,Integer.parseInt(price),description, LocalDate.parse(date));
            productService.addProduct(product);
            pageVariables.put("message", "Product was added");
        }
        List<Product> products = productService.getAllProducts();
        pageVariables.put("products", products);
        response.getWriter().print(PageGenerator.getInstance().getPage("admin.ftl", pageVariables));
    }

}
