package ru.tbank.tflowers.bouquet.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import ru.tbank.tflowers.component.db.ComponentEntity;
import ru.tbank.tflowers.store.db.StoreEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bouquet")
public class BouquetEntity implements Serializable {
    @Id
    private Long id;
    private String name;
    private Integer price;
    @Column(name = "last_updated")
    private transient LocalDateTime lastUpdated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bouquet", fetch = FetchType.EAGER)
    private List<ComponentEntity> components = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bouquet")
    private List<StoreEntity> stores = new ArrayList<>();

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

    public Integer getPrice() {
        return price;
    }

    public BouquetEntity setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public List<ComponentEntity> getComponents() {
        return components;
    }

    public BouquetEntity setComponents(List<ComponentEntity> components) {
        this.components = components;
        return this;
    }

    public void addComponent(ComponentEntity component) {
        components.add(component);
        component.setBouquet(this);
    }

    public void removeComponent(ComponentEntity component) {
        components.remove(component);
        component.setBouquet(null);
    }

    public List<StoreEntity> getStores() {
        return stores;
    }

    public BouquetEntity setStores(List<StoreEntity> stores) {
        this.stores = stores;
        return this;
    }

    public void addStore(StoreEntity store) {
        stores.add(store);
        store.setBouquet(this);
    }

    public void removeComponent(StoreEntity store) {
        stores.remove(store);
        store.setBouquet(null);
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public BouquetEntity setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
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
