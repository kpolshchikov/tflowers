package ru.tbank.tflowers.store.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ru.tbank.tflowers.bouquet.db.BouquetEntity;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "store")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)
public class StoreEntity implements Serializable {
    @Id
    private Long id;
    private String address;
    @ManyToOne
    @JoinColumn(name = "bouquet_id")
    private BouquetEntity bouquet;

    public Long getId() {
        return id;
    }

    public StoreEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public StoreEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public BouquetEntity getBouquet() {
        return bouquet;
    }

    public StoreEntity setBouquet(BouquetEntity bouquet) {
        this.bouquet = bouquet;
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
        StoreEntity that = (StoreEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StoreEntity{"
                + "  id=" + id
                + '}';
    }
}
