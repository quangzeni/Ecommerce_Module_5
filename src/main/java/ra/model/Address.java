package ra.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "full_address", columnDefinition = "varchar(255)")
    private String fullAddress;
    @Column(name = "phone", columnDefinition = "varchar(15)")
    private String phone;
    @Column(name = "receive_name", columnDefinition = "varchar(50)")
    private String receiveName;
}
