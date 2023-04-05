package ecommerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPayEmbeddedId implements Serializable {
    @Column(name="user_id")
    private long userId;
    @Column(name="payment_id")
    private long paymentId;
}
