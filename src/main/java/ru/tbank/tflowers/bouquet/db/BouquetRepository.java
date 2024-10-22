package ru.tbank.tflowers.bouquet.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BouquetRepository extends JpaRepository<BouquetEntity, Long> {
}
