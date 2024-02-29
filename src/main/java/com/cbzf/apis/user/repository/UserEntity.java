package com.cbzf.apis.user.repository;

import com.cbzf.apis.dostawca.repository.temporarysupplier.TemporarySupplierEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "role")
    private String role;
    @Column(name = "password")
    private String password;
    @Column(name = "date_added")
    private LocalDateTime dateAdded;
    @Column(name = "is_approved")
    private Boolean isApproved;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TemporarySupplierEntity temporarySupplier;

    @PrePersist
    public void onCreate() {
        dateAdded = LocalDateTime.now();
        isApproved = "Dostawca".equals(role) ? false : null;
    }

    @PreUpdate
    public void onUpdate() {
        if (this.idUser == null & "Dostawca".equals(this.role)) {
            this.isApproved = false;
        }
    }
}
