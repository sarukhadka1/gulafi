package com.system.Gulafi.service.impl;

import com.system.Gulafi.dto.ProductDto;
import com.system.Gulafi.entity.Product;
import com.system.Gulafi.repo.ProductRepo;
import com.system.Gulafi.service.ProductService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Override
    public void saveProduct(ProductDto productDto) throws IOException {
        Product product = productRepo.findById(productDto.getId()).orElse(new Product());
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductDescription(productDto.getProductDescription());

        if (productDto.getProductImage() != null) {
            // Generate a unique image file name using user email and item name
            String imageName = productDto.getProductName() + "_" + System.currentTimeMillis();
            System.out.println(imageName);
            String originalFilename = productDto.getProductImage().getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // Save the original image file
            Path originalFilePath = Paths.get(uploadDirectory, imageName + fileExtension);
            Files.write(originalFilePath, productDto.getProductImage().getBytes());

            // Resize the image
            int desiredWidth = 300;  // Set your desired width
            int desiredHeight = 200; // Set your desired height

            // Provide the path to the output resized image
            String resizedImagePath = uploadDirectory + "/" + imageName + "_resized" + fileExtension;

            // Resize the image using Thumbnails library
            Thumbnails.of(originalFilePath.toFile())
                    .size(desiredWidth, desiredHeight)
                    .outputFormat(fileExtension.substring(1)) // Remove the dot (.) from the file extension
                    .toFile(resizedImagePath);

            // Update the item with the resized image path
            product.setProductImage(imageName + "_resized" + fileExtension);
        }
        productRepo.save(product);

    }

    @Override
    public List<Product> findLatestProduct() {
        return productRepo.findLatestProduct();
    }

    @Override
    public int countAllProducts(String partialName) {
        return productRepo.countAllProducts(partialName);
    }

    @Override
    public List<Product> getSixProducts(int page, String partialName) {
        int offSet = (page - 1) * 6;
        return productRepo.getSixProducts(offSet, partialName);
    }

    @Override
    public Optional<Product> getProduct(int id) {
        return productRepo.findById(id);
    }
}
