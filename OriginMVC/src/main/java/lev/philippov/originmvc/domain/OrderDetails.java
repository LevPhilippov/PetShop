package lev.philippov.originmvc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetails  implements Serializable {

    private static final long serialVersionUID = -5461296058176582750L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 25, message = "Too short name! First name must be between 2 and 25 simbols!")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false)
    @Pattern(regexp = "\\S*@\\S*\\.\\w*(\\.\\w*)?", message = "Please enter correct email!")
    private String email;

    @Column(name = "phone")
//    @Size(min = 11,max = 20, message = "Phone number hould be between 11 and 20 digits!")
    private String phone;

    @OneToOne(mappedBy = "orderDetails")
    private Order order;



}
