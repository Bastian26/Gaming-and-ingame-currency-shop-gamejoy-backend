package com.gamejoy.domain.userIngameCurrency.entities;

import com.gamejoy.domain.ingamecurrency.entities.IngameCurrency;
import com.gamejoy.domain.userManagement.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * @ManyToMany intermediate table for User and IngameCurrency Entities
 */
@Data
@Entity
@Table(name = "user_ingame_currency")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIngameCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ingame_currency_id")
    private IngameCurrency ingameCurrency;

    @Column(name = "amount")
    private int amount;

    @UpdateTimestamp
    @Column(name = "last_modified_dt")
    private LocalDateTime lastModified;
}
