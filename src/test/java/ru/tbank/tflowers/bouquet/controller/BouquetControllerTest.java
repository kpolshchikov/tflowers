package ru.tbank.tflowers.bouquet.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.tbank.tflowers.bouquet.db.BouquetEntity;
import ru.tbank.tflowers.bouquet.db.BouquetRepository;
import ru.tbank.tflowers.bouquet.service.BouquetCacheService;
import ru.tbank.tflowers.component.db.ComponentEntity;
import ru.tbank.tflowers.component.db.ComponentRepository;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = "spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.xml"
)
@AutoConfigureMockMvc
class BouquetControllerTest {

    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER =
            new PostgreSQLContainer<>("postgres:15-alpine");

    private static final GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>("redis:7.4.0-alpine")
                    .withExposedPorts(6379);

    @BeforeAll
    static void beforeAll() {
        POSTGRES_CONTAINER.start();
        REDIS_CONTAINER.start();
    }

    @AfterAll
    static void afterAll() {
        POSTGRES_CONTAINER.stop();
        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BouquetRepository bouquetRepository;
    @Autowired
    private ComponentRepository componentRepository;
    @SpyBean
    private BouquetCacheService bouquetCacheService;

    @BeforeEach
    void setUp() {
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

    @AfterEach
    void tearDown() {
        componentRepository.deleteAll();
        bouquetRepository.deleteAll();
    }

    @Test
    void cacheTest() throws Exception {
        // act
        System.out.println("-----------------------------------------------------------------------");
        String firstRequestBody = mockMvc.perform(MockMvcRequestBuilders.get("/bouquets"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("-----------------------------------------------------------------------");
        String secondRequestBody = mockMvc.perform(MockMvcRequestBuilders.get("/bouquets"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("-----------------------------------------------------------------------");
        // assert
        assertThat(firstRequestBody)
                .isEqualTo(secondRequestBody)
                .isEqualTo("[{\"name\":\"Голубая лагуна\",\"description\":\"В состав этого букета входит:"
                        + "\\n  - Голубая хризантема x 11;"
                        + "\\n  - Голубая упаковка x 2;"
                        + "\\n  - Голубая лента x 1;\\n\",\"price\":3500},"
                        + "{\"name\":\"Красная лагуна\",\"description\":\"В состав этого букета входит:"
                        + "\\n  - Красная роза x 11;"
                        + "\\n  - Красная упаковка x 2;"
                        + "\\n  - Красная лента x 1;\\n\",\"price\":3550}]");
        verify(bouquetCacheService, Mockito.times(1)).getBouquets();
    }
}
