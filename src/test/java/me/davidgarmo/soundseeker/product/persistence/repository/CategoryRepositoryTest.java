package me.davidgarmo.soundseeker.product.persistence.repository;

import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryRepositoryTest {
    private final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryRepository categoryRepository;

    @DynamicPropertySource
    static void setUpDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:product-test");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "");
        registry.add("spring.jpa.defer-datasource-initialization", () -> false);
        registry.add("spring.sql.init.mode", () -> "never");
        registry.add("spring.jpa.show-sql", () -> false);
    }

    @BeforeEach
    void setUp() {
        CategoryEntity category = new CategoryEntity(null, "Baterías", "Sumérgete desde el rock estruendoso hasta el " +
                "jazz más sutil. Cada tarola, bombo y platillo está listo para traducir tu pasión en " +
                "pulsaciones que capturan almas. Libera tu potencial rítmico, marca el compás que hará latir " +
                "corazones y convierte cada actuación en una experiencia inolvidable. ¡Es hora de que hagas " +
                "retumbar el mundo con tu talento!", "/uploads/drum.svg", true, null);
        this.categoryRepository.save(category);
    }

    @AfterEach
    void tearDown() {
        this.categoryRepository.deleteAll();
        LOGGER.debug("✔ Database cleaned up.");
    }
}