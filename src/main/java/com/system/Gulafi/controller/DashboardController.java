package com.system.Gulafi.controller;

import com.system.Gulafi.entity.Product;
import com.system.Gulafi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {
    private final ProductService productService;
    @GetMapping("/")
    private String dashboard(Model  model){
        List<Product> products = productService.findLatestProduct();
        model.addAttribute("products",products.stream().map(product -> Product.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDescription(product.getProductDescription())
                .productImageBase64(getImageBase64(product.getProductImage()))
                .build())
        );
        return "dashboard";
    }

    @GetMapping("/list")
    public String menuByCategory(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "") String partialName) throws IOException {
        int totalProducts;
        totalProducts = productService.countAllProducts(partialName);

        int pageSize=6;

        // Calculate the total number of pages based on the page size and total products
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        if(totalPages==0){
            totalPages=1;
        }
        // Ensure the requested page is within the valid range
        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        // Calculate the starting index and ending index for the subset of products to display
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalProducts);





        List<Product> products =new ArrayList<>();
        products=productService.getSixProducts(page,partialName);


        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("products",products.stream().map(product -> Product.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDescription(product.getProductDescription())
                .productImageBase64(getImageBase64(product.getProductImage()))
                .build())
        );
        return "products";
    }

    @GetMapping("/list/{id}")
    public String getProduct(Model model, @PathVariable int id){
        Product product = productService.getProduct(id).get();
        model.addAttribute("product",Product.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDescription(product.getProductDescription())
                .productImageBase64(getImageBase64(product.getProductImage()))
                .build());

        return "product-details";
    }


    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/uploads/";
        File file = new File(filePath + fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }
}
