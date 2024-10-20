package ru.tbank.tflowers.bouquet.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bouquet")
public class BouquetEntity implements Serializable {
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer price;

    public Long getId() {
        return id;
    }

    public BouquetEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BouquetEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BouquetEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public BouquetEntity setPrice(Integer price) {
        this.price = price;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BouquetEntity that = (BouquetEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BouquetEntity{"
                + "  id=" + id
                + '}';
    }
}
