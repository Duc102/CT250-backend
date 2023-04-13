package ecommerce.controller;


import ecommerce.models.Product;
import ecommerce.models.ProductCategory;
import ecommerce.models.ProductItem;
import ecommerce.models.VariationOption;
import ecommerce.services.ProductService;
import ecommerce.services.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("administrator/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private VariationService variationService;


    @PostMapping("/new")
    public Product createNewProduct(@RequestBody Product newProduct){
        return productService.createNewProduct(newProduct);
    }

    @PostMapping("/new/{productId}/productItem")
    public List<ProductItem> createNewProductItem(@PathVariable long productId, @RequestBody List<ProductItem> newProductItem){
        return productService.createNewProductItem(productId, newProductItem);
    }

    @PostMapping("/new/productItem/productImages")
    public List<ProductItem> updateProductImagesOfNewProductItems(@RequestBody List<ProductItem> productItems){
        return productService.updateAfterCreateNewProductItems(productItems);
    }



    /**
     * Get all product items
     * @return
     */
    @GetMapping("/productItem/all")
    public List<ProductItem> getAllProductItem(){
        return productService.findAllProductItems();
    }

    /**
     * Get a product of a product item
     * @param id product of the product item
     * @return A product
     */
    @GetMapping("/productItem/{id}/info")
    public Product getProductByProductItemId(@PathVariable Long id){
        return productService.findProductByProductItemId(id);
    }


    /**
     * Get product items by category with configuration
     * @param id Category id
     * @param conditions List of conditions
     * @return List of product items
     */
    @PostMapping("category/{id}/conditions")
    public List<ProductItem> getProductItemsByCategoryWithConfiguration(@PathVariable long id, @RequestBody List<Long> conditions){
        return productService.findProductItemsByCategoryWithConfiguration(id, conditions);
    }

    /**
     * Get all product items in a category
     * @param id Category id
     * @return List of product items in the category
     */
    @GetMapping("/productItem/category/{id}")
    public List<ProductItem> getProductItemsByCategoryId(@PathVariable long id){
        return productService.findProductItemByCategoryId(id);
    }

    @PostMapping("product/{id}")
    public void updateProduct(@PathVariable long id, @RequestBody Product product){
        productService.updateProduct(id, product);
    }

    @PutMapping("productItem/quickUpdate/{id}")
    public void quickUpdateProductItem (@PathVariable long id, @RequestBody ProductItem newProductItem){
        ProductItem oldProductItem = productService.findProductItemById(id);
        oldProductItem.setPrice(newProductItem.getPrice());
        oldProductItem.setQtyInStock(newProductItem.getQtyInStock());
        productService.update(oldProductItem);
    }

    /**
     * Update the product item detail.
     * @param id
     * @param updatedProductItem
     */
    @PutMapping("productItemDetail/update/{id}")
    public void updateProductItem(@PathVariable long id, @RequestBody ProductItem updatedProductItem){
        productService.updateDetail(id, updatedProductItem);

    }

    @DeleteMapping("productItem/quickDelete/{id}")
    public boolean quickDeleteProductItem(@PathVariable long id){
        return productService.deleteProductItemById(id);
    }

    @GetMapping("productItemsDetail/{id}")
    public ProductItem getProductItemsDetail(@PathVariable Long id){
        return productService.findProductItemDetailById(id);
    }

    @PostMapping("otherConfigurations/product/{id}")
    public Map<Long, List<VariationOption>> getOtherConfigurationsOfProduct(@PathVariable long id, @RequestBody List<Long> conditions){
        return productService.findOtherConfigurationsOfProduct(id, conditions);
    }

    @PostMapping("/productItems/product/{id}")
    public List<ProductItem> getProductItemsByProductIdWithConfiguration(@PathVariable long id, @RequestBody List<Long> conditions){
        List<ProductItem> productItems = productService.findProductItemsByProductIdWithConfiguration(id, conditions);
        return productService.findProductItemsByProductIdWithConfiguration(id, conditions);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.findProductById(id);
    }







    /* ==============RAW=============== */

    @PostMapping("/conditions")
    public List<Product> getProductByConfiguration(@RequestBody List<Long> conditions){
        return productService.findProductByConfiguration(conditions);
    }

    @GetMapping("category/{id}")
    public List<Product> getProductByCategoryId(@PathVariable long id){
        return productService.findProductsByCategoryId(id);
    }

    @GetMapping("category/all")
    public List<Product> getAllProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("search/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return productService.findProductByName(name);
    }
}
