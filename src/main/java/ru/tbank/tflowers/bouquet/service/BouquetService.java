package ru.tbank.tflowers.bouquet.service;

import org.springframework.stereotype.Service;
import ru.tbank.tflowers.bouquet.Bouquet;
import ru.tbank.tflowers.bouquet.db.BouquetRepository;
import ru.tbank.tflowers.store.db.StoreEntity;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BouquetService {

    private final BouquetCacheService bouquetCacheService;
    private final BouquetRepository bouquetRepository;

    public BouquetService(BouquetCacheService bouquetCacheService, BouquetRepository bouquetRepository) {
        this.bouquetCacheService = bouquetCacheService;
        this.bouquetRepository = bouquetRepository;
    }

    public List<Bouquet> getBouquets() {
        return bouquetCacheService.getBouquetsForToday().stream().map(bouquetEntity -> {
                    StringBuilder description = new StringBuilder("В состав этого букета входит:\n");
                    bouquetEntity.getComponents().forEach(component ->
                            description.append(
                                    String.format("  - %s x %d;%n", component.getName(), component.getCount()))
                    );
                    return new Bouquet(
                            bouquetEntity.getName(),
                            description.toString(),
                            bouquetEntity.getPrice()
                    );
                }
        ).toList();
    }

    public List<String> getStores(Long bouquetId) {
        return bouquetRepository.findById(bouquetId)
                .orElseThrow(() -> new NoSuchElementException(String.format("Букет с id=%d, не найден", bouquetId)))
                .getStores().stream()
                .map(StoreEntity::getAddress)
                .toList();
    }
}
