package com.marlan.springbootsstore.entities;

import com.marlan.springbootsstore.dto.BootDTO;
import com.marlan.springbootsstore.enums.BootType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BOOTS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Boot {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="MATERIAL")
    private String material;

    @Column(name="TYPE")
    @Enumerated(EnumType.STRING)
    private BootType type;

    @Column(name="SIZE")
    private Float size;

    @Column(name="QUANTITY")
    private Integer quantity;

    public Boot(BootDTO bootDTO) {
        this.material = bootDTO.getMaterial();
        this.type = bootDTO.getType();
        this.size = bootDTO.getSize();
        this.quantity = bootDTO.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Boot boot = (Boot) o;
        return id != null && Objects.equals(id, boot.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
