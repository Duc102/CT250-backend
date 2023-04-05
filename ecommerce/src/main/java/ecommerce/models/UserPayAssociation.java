package ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user_payment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPayAssociation {
    @EmbeddedId
    private UserPayEmbeddedId id;

    @Column(name="is_default")
    private boolean isDefault;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false, referencedColumnName = "id")
    private SiteUser siteUser;

    @ManyToOne
    @JoinColumn(name="payment_id", insertable = false, updatable = false, referencedColumnName = "id")
    private Payment payment;
}
