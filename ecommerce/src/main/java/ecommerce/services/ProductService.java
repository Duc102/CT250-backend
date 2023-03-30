package ecommerce.services;

import ecommerce.Const.Disk;
import ecommerce.models.*;
import ecommerce.repository.ProductCategoryRepository;
import ecommerce.repository.ProductItemRepository;
import ecommerce.repository.ProductRepository;
import ecommerce.uploadFile.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private VariationService variationService;

    @Autowired
    private ProductCategoryService categoryService;

    public Product createNewProduct(Product newProduct){
        ProductCategory category = categoryService.findCategoryById(newProduct.getProductCategory().getId());
        Product product = new Product();
        product.setName(newProduct.getName());
        product.setProductCategory(category);
        Product pro = productRepository.save(product);
//        File dir = new File(Disk.source+"/frontend/ecommerce/public/Images/Products/"+pro.getId());
        File dir = new File(FileUtils.getRootLocation()+"frontend/ecommerce/public/Images/Products/"+pro.getId());
        if(!dir.exists()) {
            dir.mkdir();
//            File imgDir = new File(Disk.source+"/frontend/ecommerce/public/Images/Products/"+pro.getId()+"/Images");
            File imgDir = new File(FileUtils.getRootLocation()+"frontend/ecommerce/public/Images/Products/"+pro.getId()+"/Images");
            imgDir.mkdir();
//            FileUtils.copyAllFileInDirectory(Disk.source+"/frontend/ecommerce/public/Images/Products/0/Images",
//                    Disk.source+"/frontend/ecommerce/public/Images/Products/"+pro.getId()+"/Images");
            FileUtils.copyAllFileInDirectory(FileUtils.getRootLocation()+"frontend/ecommerce/public/Images/Products/0/Images",
                    FileUtils.getRootLocation()+"frontend/ecommerce/public/Images/Products/"+pro.getId()+"/Images");
        } else {
            System.out.println("Product directory existed");
        }
//        dir = new File(Disk.source+"/Store/Products/Descriptions/"+pro.getId());
        dir = new File(FileUtils.getRootLocation()+"Store/Products/Descriptions/"+pro.getId());
        if(!dir.exists()) {
            dir.mkdir();
        } else {
            System.out.println("Product directory existed");
        }
        return pro;
    }

    public List<ProductItem> createNewProductItem(Long productId, List<ProductItem> productItems){
        Product product = productRepository.findById(productId).get();
        List<ProductItem> newProductItems = new ArrayList<>();
        productItems.forEach(productItem -> {
            ProductItem newProductItem = new ProductItem();
            newProductItem.setProduct(product);
            newProductItem.setPrice(productItem.getPrice());
            newProductItem.setSku(productItem.getSku());
            newProductItem.setQtyInStock(productItem.getQtyInStock());
//            Save a product item.
            ProductItem pi = productItemRepository.save(newProductItem);
//            File dir = new File("D:/B1906657/NL/code/fullstack/frontend/ecommerce/public/Images/Products/"+productId+"/"+String.valueOf(pi.getId()));
            File dir = new File(FileUtils.getRootLocation()+"frontend/ecommerce/public/Images/Products/"+productId+"/"+String.valueOf(pi.getId()));
            if(!dir.exists()){
                dir.mkdir();
            } else {
                System.out.println("Product item directory existed");
            }
            newProductItems.add(pi);
        });
        return newProductItems;
    }

    public List<ProductItem> updateAfterCreateNewProductItems(List<ProductItem> productItems){
        List<ProductItem> result = new ArrayList<>();
        productItems.forEach(productItem -> {
            ProductItem newProduct = updateDetail(productItem.getId(), productItem);
            result.add(newProduct);
        });
        return result;
    }

    public Product findProductById(long id){
        return productRepository.findById(id).get();
    }

    public ProductItem findProductItemById(long id){
        return productItemRepository.findById(id).get();
    }

    /**
     * get a product from a product item by id of the product item.
     * @param id The product item id
     * @return The product of the product item
     */
    public Product findProductByProductItemId(long id){
        return productItemRepository.selectProductByProductItemId(id);
    }

    /***
     *
     * @param id id of category that we want to find products
     * @return Products list, include products of children categories
     */
    public List<Product> findProductsByCategoryId(long id){
        List<Integer> familyTree = productCategoryRepository.selectFamilyTreeByCategoryId(id);
        List<Product> products = new ArrayList<>();
        familyTree.stream().forEach((children)->{
            List<Product> products1 = productRepository.selectProductByCategoryId(children);
            products.addAll(products1);
        });
        return products;
    }

    /**
     * Find all product items
     * Usage: One the product page when it is initializing or the admin chooses all product items
     * @return
     */
    public List<ProductItem> findAllProductItems(){
        List<ProductItem> productItems = productItemRepository.selectAllProductItems();
        for(int i=0; i< productItems.size(); i++) {
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();
            Collections.sort(pc, (pc1, pc2) -> pc1.getVariationOption().getVariation().getName().compareToIgnoreCase(pc2.getVariationOption().getVariation().getName()));
        }
        return productItems;
    }

    public List<Variation> findVariationAlongCategory(Long id){
        return variationService.findVariationAlongCategory(id);
    }

    public List<VariationOption> findVariationOptionOfVariation(Long id){
        return variationService.findVariationOptionOfVariation(id);
    }

    /**
     * Find all product items belong to a category (includes children categories)
     * Usage: On the product page when the admin chooses a category without configuration
     * @param categoryId Category id
     * @return List of product items
     */
    public List<ProductItem> findProductItemByCategoryId(Long categoryId){
//      Select all products along the category includes its children categories
        List<Product> products = findProductsByCategoryId(categoryId);
        List<ProductItem> productItems = new ArrayList<>();
//      Select all product items of the product and fetch configuration of product items
        for(int i =0; i< products.size(); i++) {
            List<ProductItem> productItem = productItemRepository.selectAllProductItemsAndFetchConfigurationByProductId(products.get(i).getId());
            productItems.addAll(productItem);
        }
//      Sort configuration of a product item
        for(int i=0; i< productItems.size(); i++) {
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();
            Collections.sort(pc, (pc1, pc2) -> pc1.getVariationOption().getVariation().getName().compareToIgnoreCase(pc2.getVariationOption().getVariation().getName()));
        }
        return productItems;
    }

    /**
     * Find all product items that belong to the category (includes children category) and satisfy the configuration
     * Usage: On the product page when the admin chooses a category with configuration
     * @param categoryId Product category id
     * @param conditions List of conditions
     * @return List of product items
     */
    public List<ProductItem> findProductItemsByCategoryWithConfiguration(Long categoryId, List<Long> conditions){
//      Select all products along the category includes its children categories
        List<Product> products = findProductsByCategoryId(categoryId);

//      Select all product items of the product and fetch configuration of product items
        List<ProductItem> productItems = new ArrayList<>();
        for(int i = 0; i< products.size(); i++) {
            List<ProductItem> productItemsConfig = productItemRepository.selectAllProductItemsAndFetchConfigurationByProductId(products.get(i).getId());
            productItems.addAll(productItemsConfig);
        }
//      Select the product item that is passed to conditions
        List<ProductItem> result = new ArrayList<>();
        for(int i = 0; i < productItems.size(); i++) {
            int count = 0; // Then number of conditions to be satisfied
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();
            for (int j = 0; j < pc.size() && count != -1; j++) {
                Long id = pc.get(j).getVariationOption().getId();
                if (conditions.contains(id)) count++;
            }
            if (count == conditions.size()) {
//              Sort configuration of a product item
                Collections.sort(pc, (pc1, pc2)->pc1.getVariationOption().getVariation().getName().compareToIgnoreCase(pc2.getVariationOption().getVariation().getName()));
                result.add(productItems.get(i));
            }
        }
        return result;
    }

    public List<ProductItem> findProductItemsPassedConditions(List<ProductItem> productItems, List<Long> conditions){
        List<ProductItem> result = new ArrayList<>();
        for(int i = 0; i < productItems.size(); i++) {
            int count = 0; // Then number of conditions to be satisfied
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();
            for (int j = 0; j < pc.size() && count != -1; j++) {
                Long id = pc.get(j).getVariationOption().getId();
                if (conditions.contains(id)) count++;
            }
            if (count == conditions.size()) {
//              Sort configuration of a product item
                Collections.sort(pc, (pc1, pc2)->pc1.getVariationOption().getVariation().getName().compareToIgnoreCase(pc2.getVariationOption().getVariation().getName()));
                result.add(productItems.get(i));
            }
        }
        return result;
    }

    /**
     * Find all product items belong product item and passed conditions.
     * @param productId
     * @param conditions
     * @return
     */
    public List<ProductItem> findProductItemsByProductIdWithConfiguration(long productId, List<Long> conditions){
        List<ProductItem> productItems = productItemRepository.selectAllProductItemsByProductId(productId);
        List<ProductItem> result = findProductItemsPassedConditions(productItems, conditions);
        return result;
    }

    /**
     * Select a product item by id and fetch its configuration
     * @param id
     * @return
     */
    public ProductItem findProductItemDetailById(long id){
        return productItemRepository.selectProductItemAndFetchConfigById(id);
    }

    /**
     * Update product by id
     * @param id
     * @param product
     */
    public void updateProduct(long id, Product product){
        Product oldProduct = findProductById(id);
        oldProduct.setName(product.getName());
        productRepository.save(oldProduct);
    }


    /**
     * Quick update a product item.
     * @param productItem
     * @return
     */
    public ProductItem update(ProductItem productItem) {
        return productItemRepository.save(productItem);
    }

    /**
     *Update a product item detail
     * @param id
     * @param productItem
     * @return
     */
    public ProductItem updateDetail(Long id, ProductItem productItem){
//        ProductItem oldProductItem = findProductItemDetailById(id);
        ProductItem oldProductItem = productItemRepository.findById(productItem.getId()).get();
//       update price
        oldProductItem.setPrice(productItem.getPrice());
//        update sku
        oldProductItem.setSku(productItem.getSku());
//        update quantity in stock
        oldProductItem.setQtyInStock(productItem.getQtyInStock());
//        Check changed configurations
        AtomicBoolean haveChangedConfig = new AtomicBoolean(false);
        List<ProductConfiguration> proConfig = oldProductItem.getProductConfigurations();

//        Create a new configuration.
        List<ProductConfiguration> newConfig = new ArrayList<>();
        productItem.getProductConfigurations().forEach(pc->{
            if(pc.getId() == 0) haveChangedConfig.set(true);
//            Set new configurations.
            ProductConfiguration conf = new ProductConfiguration();
            conf.setProductItem(oldProductItem);
            conf.setVariationOption(pc.getVariationOption());
            newConfig.add(conf);
        });
//        if config have changed, clear old config and set new config
        if(haveChangedConfig.get()){
            proConfig.clear();
            proConfig.addAll(newConfig);
        }

        List<ProductImage> imagesWillBeSaved = getImagesWillBeSave(oldProductItem, productItem.getProductImages(), oldProductItem.getProductImages());
        List<ProductImage> proImages = oldProductItem.getProductImages();
        if(haveChangedImages(imagesWillBeSaved, proImages)){
            proImages.clear();
            proImages.addAll(imagesWillBeSaved);
            FileUtils.deleteProductItemImagesFileIfNotNeed(imagesWillBeSaved);
        };
        ProductItem newProductItem = productItemRepository.save(oldProductItem);
        return newProductItem;
    }

    public List<ProductImage> getImagesWillBeSave(ProductItem productItem, List<ProductImage> newImages , List<ProductImage> existedImage){
        List<ProductImage> savedImages= new ArrayList<>();
        for (ProductImage productImage : newImages) {
            if(existedImage.contains(productImage)){
                savedImages.add(productImage);
            } else {
                ProductImage completelyNew = new ProductImage();
                completelyNew.setUrl(productImage.getUrl());
                completelyNew.setProductItem(productItem);
                savedImages.add(completelyNew);
            }
        }
        return savedImages;
    }

    public boolean haveChangedImages(List<ProductImage> newImagesList, List<ProductImage> oldImagesList){
        if(newImagesList.size() == oldImagesList.size()){
            for(ProductImage img : oldImagesList){
                if(!newImagesList.contains(img))
                    return true;
            }
        } else {
            return true;
        }
        return false;
    }


    /**
     * Quick delete a product by id.
     * @param id
     */
    public boolean deleteProductItemById(long id) {
        Product product = productItemRepository.selectProductByProductItemId(id);
        productItemRepository.deleteById(id);
        FileUtils.deleteDirectory("/frontend/ecommerce/public/Images/Products/"+product.getId()+"/"+id);
        Product after = productRepository.findById(product.getId()).get();
        int haveProItem = after.getProductItems().size();
        if(haveProItem == 0){
            FileUtils.deleteDirectory("/frontend/ecommerce/public/Images/Products/"+after.getId());
            FileUtils.deleteDirectory("/Store/Products/Descriptions/"+after.getId());
        }
        return true;
    }


    /**
     * Find others configuration of a product with a given configuration.
     * @param productId
     * @param conditions
     * @return
     */
    public Map<Long, List<VariationOption>> findOtherConfigurationsOfProduct(Long productId, List<Long> conditions){
//        List<Long> conditions = new ArrayList<>(Arrays.asList(48L));
        List<ProductItem> productItems = productItemRepository.selectAllProductItemsAndFetchConfigurationByProductId(productId);
        Map<Long, List<VariationOption>> varAndVarOp = new HashMap<>();

        List<ProductItem> result = new ArrayList<>();
        for(int i = 0; i < productItems.size(); i++) {
            int count = 0; // Then number of conditions to be satisfied
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();
            for (int j = 0; j < pc.size() && count != -1; j++) {
                Long id = pc.get(j).getVariationOption().getId();
                if (conditions.contains(id)) count++;
            }
            if (count == conditions.size()) {
//              Sort configuration of a product item
                Collections.sort(pc, (pc1, pc2)->pc1.getVariationOption().getVariation().getName().compareToIgnoreCase(pc2.getVariationOption().getVariation().getName()));
                result.add(productItems.get(i));
            }
        }

        result.forEach((productItem -> {
            productItem.getProductConfigurations().forEach(productConfiguration -> {
                VariationOption varOp = productConfiguration.getVariationOption();
                Long key = varOp.getVariation().getId();

                List<VariationOption> mapVarOps;
                if(varAndVarOp.containsKey(key)){
                    mapVarOps = varAndVarOp.get(key);

                } else {
                    mapVarOps = new ArrayList<>();
                }

                List<String> valuesExisted = new ArrayList<>();
                mapVarOps.forEach(v -> {
                    valuesExisted.add(v.getValue());
                });

                if(!valuesExisted.contains(varOp.getValue())){
                    mapVarOps.add(varOp);
                    varAndVarOp.put(key, mapVarOps);
                }
            });
        }));

        return varAndVarOp;
    }



    /*============RAW=============================================================*/


    public List<Product> findProductByConfiguration(List<Long> conditions){
        List<ProductItem> productItems = productItemRepository.selectAllProductItemAndFetchConfig();
        List<Product> result = new ArrayList<>();
        for(int i = 0; i < productItems.size(); i++) {
            int count = 0;
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();
            for (int j = 0; j < pc.size(); j++) {
                Long id = pc.get(j).getVariationOption().getId();
                if (conditions.contains(id)) count++;
            }

            if (count == conditions.size()) result.add(productItems.get(i).getProduct());
        }
        result.stream().forEach(product -> System.out.println(product.getName()));
        return result;
    }

    public List<Product> findProductsFetchItemByCategoryId(long id){
        List<Integer> familyTree = productCategoryRepository.selectFamilyTreeByCategoryId(id);
        List<Product> products = new ArrayList<>();
        familyTree.stream().forEach((children)->{
            List<Product> products1 = productRepository.selectProductsFetchItemByCategoryId(children);
            products.addAll(products1);
        });
        return products;
    }

    public List<Product> findAllProducts(){
        List<ProductCategory> productCategories =productCategoryRepository.selectCategoryZeroLevel();
        List<Product> products = new ArrayList<>();
        productCategories.forEach((productCategory -> {
            products.addAll(findProductsByCategoryId(productCategory.getId()));
        }));
        return products;
    }
}
