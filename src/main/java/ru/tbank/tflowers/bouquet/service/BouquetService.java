package ru.tbank.tflowers.bouquet.service;

import org.springframework.stereotype.Service;
import ru.tbank.tflowers.bouquet.Bouquet;

import java.util.List;

@Service
public class BouquetService {

    private final BouquetCacheService bouquetCacheService;

    public BouquetService(BouquetCacheService bouquetCacheService) {
        this.bouquetCacheService = bouquetCacheService;
    }

    public List<Bouquet> getBouquets() {
        return bouquetCacheService.getBouquets().stream().map(bouquetEntity -> {
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
}
