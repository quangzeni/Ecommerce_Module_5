package ra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String userName;
    @Column(name = "email", columnDefinition = "varchar(255)",unique = true,nullable = false)
    private String email;
    @Column(name ="fullname",columnDefinition = "varchar(100)", nullable = false)
    private String fullName;
    @Column(name = "user_status", columnDefinition = "boolean default true")
    private boolean status;
    @Column(name = "password",columnDefinition = "varchar(255)", nullable = false)
    private String password;
    @Column(columnDefinition = "varchar(15)", unique = true)
    private String avatar;
    @Pattern(regexp = "(03|05|07|08|09|01)[2|6|8|9]+[0-9]{7}", message = "Phone not correct")
    private String phone;
    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String address;
    @Column(name = "created_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @CreationTimestamp
    private Date updatedAt;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> listRoles;
}
