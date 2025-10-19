package store.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item", schema = "ord")
@Getter @Setter @NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class OrderItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderModel order;

    @Column(nullable = false)
    private String idProduct;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productUnit;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal total;
}
