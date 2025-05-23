package me.davidgarmo.soundseeker.product.persistence.repository;

import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @Order(0)
    void givenACompleteCategory_whenSaved_thenItShouldPersistInTheDatabase() {
        CategoryEntity category = new CategoryEntity(null, "Guitarras y Cuerdas", "Explora el universo sonoro que te " +
                "ofrecen nuestras guitarras y cuerdas en alquiler. Ya sea que anheles el dulce susurro de " +
                "una guitarra acústica o la potencia estruendosa de un bajo eléctrico, tenemos el " +
                "instrumento perfecto para ti. Sumérgete en la variedad, siente la emoción de probar " +
                "diferentes modelos y encuentra tu compañero musical ideal.", "/uploads/guitars.svg",
                true, null);
        CategoryEntity savedCategory = this.categoryRepository.save(category);

        assertThat(savedCategory.getId()).isNotNull().isEqualTo(2L);
        assertThat(savedCategory)
                .extracting("name", "description", "thumbnail", "available")
                .containsExactly("Guitarras y Cuerdas", "Explora el universo sonoro que te ofrecen nuestras " +
                                "guitarras y cuerdas en alquiler. Ya sea que anheles el dulce susurro de una guitarra " +
                                "acústica o la potencia estruendosa de un bajo eléctrico, tenemos el instrumento " +
                                "perfecto para ti. Sumérgete en la variedad, siente la emoción de probar " +
                                "diferentes modelos y encuentra tu compañero musical ideal.", "/uploads/guitars.svg",
                        true);

        assertThat(savedCategory)
                .extracting("name", "description", "thumbnail", "available")
                .containsExactly(category.getName(), category.getDescription(), category.getThumbnail(),
                        category.getAvailable());
        LOGGER.info("✔ Category matched the expected values.");
    }

    @Test
    @Order(1)
    void givenAnExistingCategoryId_whenFoundById_thenItShouldReturnTheCategory() {
        CategoryEntity category = this.categoryRepository.findById(1L).orElseThrow();

        assertThat(category.getId()).isNotNull().isEqualTo(1L);
        assertThat(category)
                .extracting("name", "description", "thumbnail", "available")
                .containsExactly("Baterías", "Sumérgete desde el rock estruendoso hasta el jazz más sutil. " +
                        "Cada tarola, bombo y platillo está listo para traducir tu pasión en pulsaciones " +
                        "que capturan almas. Libera tu potencial rítmico, marca el compás que hará latir " +
                        "corazones y convierte cada actuación en una experiencia inolvidable. ¡Es hora de " +
                        "que hagas retumbar el mundo con tu talento!", "/uploads/drum.svg", true);
        LOGGER.info("✔ Category with ID 1 was found in the database and matched the expected values.");
    }
}