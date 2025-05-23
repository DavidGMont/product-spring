package me.davidgarmo.soundseeker.product.persistence.repository;

import me.davidgarmo.soundseeker.product.persistence.entity.BrandEntity;
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
class BrandRepositoryTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private BrandRepository brandRepository;

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
        BrandEntity brand = new BrandEntity(null, "Fender", "El sonido que revolucionó la música moderna. Desde " +
                "1946, Fender ha sido sinónimo de guitarras y bajos legendarios que han moldeado géneros " +
                "enteros. Siente la magia de décadas de innovación en tus manos y déjate llevar por ese tono " +
                "inconfundible que ha conquistado escenarios de todo el mundo.", "/uploads/fender.svg",
                true, null);
        this.brandRepository.save(brand);
    }

    @AfterEach
    void tearDown() {
        this.brandRepository.deleteAll();
        LOGGER.debug("✔ Database cleaned up.");
    }

    @Test
    @Order(0)
    void givenACompleteBrand_whenSaved_thenItShouldPersistInTheDatabase() {
        BrandEntity brand = new BrandEntity(null, "Yamaha", "Innovación japonesa en cada instrumento. " +
                "Yamaha combina tradición y tecnología para ofrecerte sonidos precisos y materiales duraderos que " +
                "te acompañarán en cada concierto, ensayo y aventura musical. Descubre por qué los " +
                "profesionales de todo el mundo confían en la excelencia Yamaha.", "/uploads/yamaha.svg",
                true, null);
        BrandEntity savedBrand = this.brandRepository.save(brand);

        assertThat(savedBrand.getId()).isNotNull().isEqualTo(2L);
        assertThat(savedBrand).extracting("name", "description", "thumbnail", "available")
                .containsExactly("Yamaha", "Innovación japonesa en cada instrumento. Yamaha combina tradición y " +
                        "tecnología para ofrecerte sonidos precisos y materiales duraderos que te acompañarán en cada " +
                        "concierto, ensayo y aventura musical. Descubre por qué los profesionales de todo el mundo " +
                        "confían en la excelencia Yamaha.", "/uploads/yamaha.svg", true);
        assertThat(savedBrand).extracting("name", "description", "thumbnail", "available")
                .containsExactly(brand.getName(), brand.getDescription(), brand.getThumbnail(), brand.getAvailable());
        LOGGER.info("✔ Brand matched the expected values.");
    }
}