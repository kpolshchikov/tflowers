package ru.tbank.tflowers.bouquet.db;

import org.springframework.data.repository.ListCrudRepository;

public interface BouquetRepository extends ListCrudRepository<BouquetEntity, Long> {
}
