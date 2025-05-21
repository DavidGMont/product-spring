package me.davidgarmo.soundseeker.product.persistence.repository;

import me.davidgarmo.soundseeker.product.persistence.entity.BrandEntity;
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

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
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
        List<BrandEntity> brands = List.of(
                new BrandEntity(null, "Yamaha", "Innovación japonesa en cada instrumento. Yamaha " +
                        "combina tradición y tecnología para ofrecerte sonidos precisos y materiales duraderos que " +
                        "te acompañarán en cada concierto, ensayo y aventura musical. Descubre por qué los " +
                        "profesionales de todo el mundo confían en la excelencia Yamaha.", "/uploads/yamaha.svg",
                        true, null),
                new BrandEntity(null, "Fender", "El sonido que revolucionó la música moderna. Desde " +
                        "1946, Fender ha sido sinónimo de guitarras y bajos legendarios que han moldeado géneros " +
                        "enteros. Siente la magia de décadas de innovación en tus manos y déjate llevar por ese tono " +
                        "inconfundible que ha conquistado escenarios de todo el mundo.", "/uploads/fender.svg",
                        true, null),
                new BrandEntity(null, "Steinway & Sons", "La excelencia hecha piano desde 1853. Cada " +
                        "Steinway es una obra maestra artesanal, creada con pasión, precisión y los mejores " +
                        "materiales. Descubre por qué los más grandes pianistas de la historia han elegido estos " +
                        "instrumentos para expresar su arte y permitir que sus emociones fluyan a través de notas " +
                        "perfectamente equilibradas.", "/uploads/steinway-and-sons.svg", true, null)
        );
        this.brandRepository.saveAll(brands);

        List<CategoryEntity> categories = List.of(
                new CategoryEntity(null, "Baterías", "Sumérgete desde el rock estruendoso hasta el " +
                        "jazz más sutil. Cada tarola, bombo y platillo está listo para traducir tu pasión en " +
                        "pulsaciones que capturan almas. Libera tu potencial rítmico, marca el compás que hará latir " +
                        "corazones y convierte cada actuación en una experiencia inolvidable. ¡Es hora de que hagas " +
                        "retumbar el mundo con tu talento!", "/uploads/drum.svg", true, null),
                new CategoryEntity(null, "Guitarras y Cuerdas", "Explora el universo sonoro que te " +
                        "ofrecen nuestras guitarras y cuerdas en alquiler. Ya sea que anheles el dulce susurro de " +
                        "una guitarra acústica o la potencia estruendosa de un bajo eléctrico, tenemos el " +
                        "instrumento perfecto para ti. Sumérgete en la variedad, siente la emoción de probar " +
                        "diferentes modelos y encuentra tu compañero musical ideal.", "/uploads/guitars.svg",
                        true, null),
                new CategoryEntity(null, "Percusión", "Siente el pulso de la música en tus venas " +
                        "con nuestra vibrante selección de instrumentos de percusión en alquiler. Desde la " +
                        "resonancia profunda de los timbales hasta la chispeante alegría de las maracas, cada golpe " +
                        "es un eco de tu espíritu aventurero. Atrévete a ser el corazón rítmico de tu banda, " +
                        "explorando texturas y cadencias que avivan la llama de cualquier composición.",
                        "/uploads/triangle-instrument.svg", true, null)
        );
        this.categoryRepository.saveAll(categories);
    }

    @AfterEach
    void tearDown() {
        this.productRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.brandRepository.deleteAll();
        LOGGER.debug("✔ Database cleaned up.");
    }
}