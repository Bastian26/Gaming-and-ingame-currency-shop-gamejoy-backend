package com.gamejoy.domain.user.entities;

import com.gamejoy.config.validation.customAnnotation.*;
import com.gamejoy.domain.userIngameCurrency.entities.UserIngameCurrency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "tel_nr", nullable = false)
    private String telNr;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;
    @Column(name = "last_modified_dt", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastModified;
    @Column(name = "created_dt", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * It's a @ManToMany, but since the intermediate table has additional attributes (is more complex),
     * it is solved via a @oneToMany with intermediate table
     * **/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserIngameCurrency> ingameCurrencies;
}
