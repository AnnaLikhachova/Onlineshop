package com.likhachova.web.controller;

import com.likhachova.model.Product;
import com.likhachova.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin(Model model, HttpServletRequest httpServletRequest) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        httpServletRequest.setAttribute("products", products);
        return "admin";
    }

    @RequestMapping(value = {"/admin/products/update/{id}"}, method = RequestMethod.GET)
    public String updateproduct(@PathVariable String id, Model model, HttpServletRequest httpServletRequest)  {
        Product product = productService.getProduct(Integer.parseInt(id));
        model.addAttribute("product", product);
        httpServletRequest.setAttribute("product", product);
        return "updateproduct";
    }

    @RequestMapping(value = {"/addproduct"}, method = RequestMethod.GET)
    public String addproduct()  {
        return "addproduct";
    }

    @RequestMapping(value = {"/admin/products/updateproduct"}, method = RequestMethod.POST)
    public String update(@RequestParam String id, @RequestParam String name, @RequestParam String price,
                         @RequestParam String description, @RequestParam String date, Model model,
                         HttpServletRequest httpServletRequest) {
        Product product = new Product(Integer.parseInt(id), name, Integer.parseInt(price), description, LocalDate.parse(date));
        productService.updateProduct(product);
        logger.debug("Product was updated");
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        httpServletRequest.setAttribute("products", products);
        return "admin";
    }

    @RequestMapping(value = {"/admin/products/delete"}, method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam String id, Model model, HttpServletRequest httpServletRequest) {
        productService.deleteProduct(Integer.parseInt(id));
        logger.debug("Product was deleted");
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        httpServletRequest.setAttribute("products", products);
        return "admin";
    }

    @RequestMapping(value = {"/admin/products/add"}, method = RequestMethod.POST)
    public String addProductToList(@RequestParam String name, @RequestParam String price,
                                   @RequestParam String description, @RequestParam String date,
                                   Model model, HttpServletRequest httpServletRequest) {
        if(name == null || price == null || description == null || date == null || name.isEmpty() || price.isEmpty() || description.isEmpty() || date.isEmpty()) {
            model.addAttribute("message", "Product was not added as one or more fields were empty");
            httpServletRequest.setAttribute("message", "Product was not added as one or more fields were empty");
            logger.debug("Product was not added as one or more fields were empty");
        }
        else {
            Product product = new Product(name, Integer.parseInt(price), description, LocalDate.parse(date));
            productService.addProduct(product);
            model.addAttribute("message", "Product was added");
            httpServletRequest.setAttribute("message", "Product was added");
            logger.debug("Product was added to list, value {}", product);
        }
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        httpServletRequest.setAttribute("products", products);
        return "admin";
    }

}
