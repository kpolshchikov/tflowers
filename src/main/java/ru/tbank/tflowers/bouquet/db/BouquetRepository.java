package ru.tbank.tflowers.bouquet.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reactor.util.annotation.NonNull;

import java.util.List;

public interface BouquetRepository extends JpaRepository<BouquetEntity, Long> {

    @NonNull
    @Override
    @Query("SELECT b FROM BouquetEntity b LEFT JOIN FETCH b.components")
    List<BouquetEntity> findAll();
}
