package store.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", schema = "ord")
@Getter @Setter @NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    
    @Column(name = "id_account", nullable = false)  
    private String idAccount;

    
    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItemModel> items = new ArrayList<>();
}
