package ru.tbank.tflowers.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.tflowers.bouquet.db.BouquetEntity;
import ru.tbank.tflowers.bouquet.db.BouquetRepository;
import ru.tbank.tflowers.component.db.ComponentEntity;
import ru.tbank.tflowers.component.db.ComponentRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.tbank.tflowers.config.RedisCacheConfig.BOUQUET_CACHE;

@RestController
@RequestMapping("/serve")
public class ServiceController {

    private final BouquetRepository bouquetRepository;
    private final ComponentRepository componentRepository;

    public ServiceController(BouquetRepository bouquetRepository, ComponentRepository componentRepository) {
        this.bouquetRepository = bouquetRepository;
        this.componentRepository = componentRepository;
    }

    @PostMapping("/db/fill")
    public void fillDb() {
        List<ComponentEntity> blueLagunaComponents = new ArrayList<>(List.of(
                new ComponentEntity()
                        .setId(1L)
                        .setName("Голубая хризантема")
                        .setCount(11),
                new ComponentEntity()
                        .setId(2L)
                        .setName("Голубая упаковка")
                        .setCount(2),
                new ComponentEntity()
                        .setId(3L)
                        .setName("Голубая лента")
                        .setCount(1)
        ));
        BouquetEntity blueLaguna = new BouquetEntity()
                .setId(1L)
                .setName("Голубая лагуна")
                .setPrice(3500);
        blueLagunaComponents.forEach(blueLaguna::addComponent);

        List<ComponentEntity> redLagunaComponents = new ArrayList<>(List.of(
                new ComponentEntity()
                        .setId(4L)
                        .setName("Красная роза")
                        .setCount(11),
                new ComponentEntity()
                        .setId(5L)
                        .setName("Красная упаковка")
                        .setCount(2),
                new ComponentEntity()
                        .setId(6L)
                        .setName("Красная лента")
                        .setCount(1)
        ));
        BouquetEntity redLaguna = new BouquetEntity()
                .setId(2L)
                .setName("Красная лагуна")
                .setPrice(3550);
        redLagunaComponents.forEach(redLaguna::addComponent);

        bouquetRepository.saveAll(List.of(blueLaguna, redLaguna));
    }

    @DeleteMapping("/db/clear")
    public void clearDb() {
        componentRepository.deleteAll();
        bouquetRepository.deleteAll();
    }

    @DeleteMapping("/cache/clear")
    @CacheEvict(cacheNames = BOUQUET_CACHE, allEntries = true)
    public void clearCache() {
    }
}
