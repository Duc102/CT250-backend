package ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="order_status")
@AllArgsConstructor
@Getter
@Setter
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="status")
    private String status;

    public OrderStatus(){
        status = "New Status";
    }
}
