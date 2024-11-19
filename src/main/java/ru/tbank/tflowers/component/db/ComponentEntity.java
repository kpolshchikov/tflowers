package ru.tbank.tflowers.component.db;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ru.tbank.tflowers.bouquet.db.BouquetEntity;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "component")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@json_id"
)
public class ComponentEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Integer count;
    @ManyToOne
    @JoinColumn(name = "bouquet_id")
    private BouquetEntity bouquet;

    public Long getId() {
        return id;
    }

    public ComponentEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComponentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public ComponentEntity setCount(Integer count) {
        this.count = count;
        return this;
    }

    public BouquetEntity getBouquet() {
        return bouquet;
    }

    public ComponentEntity setBouquet(BouquetEntity bouquet) {
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
        ComponentEntity that = (ComponentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ComponentEntity{"
                + "  id=" + id
                + '}';
    }
}
