package ecommerce.repository;

import ecommerce.models.ProductConfiguration;
import ecommerce.models.ProductItem;
import ecommerce.models.Variation;
import ecommerce.models.VariationOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductItemRepositoryTest {
    @Autowired ProductItemRepository productItemRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private VariationOptionRepository variationOptionRepository;

    @Test
    public void getAllProductItem() {
        List<ProductItem> productItems = productItemRepository.findAll();
        productItems.stream().forEach(productItem -> {
            System.out.println(productItem.getProduct().getName());
            System.out.println(productItem.getPrice());
            productItem.getProductConfigurations().forEach(pc -> System.out.println(pc.getVariationOption().getValue()));
        });
    }

    @Test
    public void getProductByConfiguration(){
        List<ProductItem> productItems = productItemRepository.selectAllProductItemAndFetchConfig();
        productItems.stream().forEach(productItem -> System.out.println(productItem.getId()));
        List<Long> conditions = new ArrayList<>();
        conditions.add(26L);
        conditions.add(39L);
        List<ProductItem> result = new ArrayList<>();
        for(int i = 0; i < productItems.size(); i++) {
            int count = 0;
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();
            for (int j = 0; j < pc.size() && count != -1; j++) {
                Long id = pc.get(j).getVariationOption().getId();
                if (conditions.contains(id)) count++;
                else count = -1;
            }
            if (count != -1 && count == conditions.size()) result.add(productItems.get(i));
        }
        result.stream().forEach(productItem -> System.out.println(productItem.getId()));
    }

    @Test
    public void sortConfiguration(){
        List<ProductItem> productItems = productItemRepository.selectAllProductItems();
        for(int i = 0; i < productItems.size(); i++) {
            List<ProductConfiguration> pc = productItems.get(i).getProductConfigurations();

            Collections.sort(pc, (pc1, pc2)->pc1.getVariationOption().getVariation().getName().compareToIgnoreCase(pc2.getVariationOption().getVariation().getName()));
            for(int j= 0; j<pc.size(); j++){
                System.out.println(pc.get(j).getVariationOption().getValue());
            }
            System.out.println();
        }
    }

    /**
     * get all variation options and variations can have of a product.
     */
    @Test
    public void findAllVarianceOfProductByProductId(){
        List<ProductItem> productItems = productItemRepository.selectAllProductItemsAndFetchConfigurationByProductId(6L);
        Map<Long, List<VariationOption>> varAndVarOp = new HashMap<>();

        productItems.forEach((productItem -> {
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

        for(Long key: varAndVarOp.keySet()){
            List<VariationOption> values = varAndVarOp.get(key);
            System.out.print("Key: " + key + ", Value: [");
            values.forEach(value -> {System.out.print(" " +value.getValue());});
            System.out.println("]");
        }
    }

    @Test
    public void findVarianceOfProductCanHaveByProductIdAndConditions(){
        List<Long> conditions = new ArrayList<>(Arrays.asList(48L));
        List<ProductItem> productItems = productItemRepository.selectAllProductItemsAndFetchConfigurationByProductId(5L);
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

        for(Long key: varAndVarOp.keySet()){
            List<VariationOption> values = varAndVarOp.get(key);
            System.out.print("Key: " + key + ", Value: [");
            values.forEach(value -> {System.out.print(" " +value.getValue());});
            System.out.println("]");
        }
    }

    @Test
    public void checkInsert(){
        ProductItem item = productItemRepository.selectProductItemAndFetchConfigById(20L);
        VariationOption variationOption = variationOptionRepository.findById(50L).get();
        System.out.println(item.getPrice());
        System.out.println(variationOption.getValue());

        ProductConfiguration proConf = new ProductConfiguration();
        proConf.setProductItem(item);
        proConf.setVariationOption(variationOption);
        item.getProductConfigurations().add(proConf);
        productItemRepository.save(item);

    }

    @Test
    public void xyz(){
        ProductItem item = productItemRepository.selectProductItemAndFetchConfigById(17L);
        ProductItem itemWithImages = productItemRepository.selectProductItemAndFetchImages(item);
        System.out.println(itemWithImages.getProductImages().size());
    }
}