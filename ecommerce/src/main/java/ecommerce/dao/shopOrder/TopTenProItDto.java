package ecommerce.dao.shopOrder;

import ecommerce.models.Product;
import ecommerce.models.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TopTenProItDto {
    private Product product;
    private ProductItem productItem;
    private int count;
}
