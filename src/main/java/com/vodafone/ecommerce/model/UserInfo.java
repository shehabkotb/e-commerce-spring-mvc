package com.vodafone.ecommerce.model;

import com.vodafone.ecommerce.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
//    @JoinTable(name = "user_roles" , joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
//    ,inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
//    private List<Role> roles=new ArrayList<>();
    private Role role;
}
