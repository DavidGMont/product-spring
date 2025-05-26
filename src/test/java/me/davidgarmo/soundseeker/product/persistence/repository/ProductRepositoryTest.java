package me.davidgarmo.soundseeker.product.persistence.repository;

import jakarta.validation.ConstraintViolationException;
import me.davidgarmo.soundseeker.product.persistence.entity.BrandEntity;
import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import me.davidgarmo.soundseeker.product.persistence.entity.ProductEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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

        List<ProductEntity> products = List.of(
                new ProductEntity(null, "Violín 4/4 Sólido Yamaha HXTQ09FRO Natural",
                        "El Violín Yamaha es un instrumento hecho de madera solida, diseñado para estudiantes " +
                                "de nivel académico medio o iniciación para estudios formales.",
                        499.99, true, "/uploads/1744052240330.webp",
                        this.brandRepository.findById(1L).orElseThrow(), this.categoryRepository.findById(2L).orElseThrow()),
                new ProductEntity(null, "Piano de Cola Steinway & Sons Modelo D GRD Hamb Ebony",
                        "El Piano de Cola Steinway & Sons Modelo D GRD Hamb Ebony es un instrumento de " +
                                "prestigio mundial, conocido por su sonido excepcional y su diseño elegante. " +
                                "Ideal para pianistas profesionales y amantes de la música.",
                        19999.99, true, "/uploads/1744051954836.webp",
                        this.brandRepository.findById(3L).orElseThrow(), this.categoryRepository.findById(3L).orElseThrow()),
                new ProductEntity(null, "Batería Accent Drive 5PC 22\" Yamaha LC19511 Negra",
                        "La Batería Accent Drive 5PC 22\" Yamaha LC19511 Negra es un kit completo de batería " +
                                "ideal para principiantes y músicos intermedios. Ofrece un sonido potente y " +
                                "una construcción duradera, perfecta para cualquier estilo musical.",
                        799.99, true, "/uploads/1747796927376.webp",
                        this.brandRepository.findById(1L).orElseThrow(), this.categoryRepository.findById(1L).orElseThrow())
        );
        this.productRepository.saveAll(products);
    }

    @AfterEach
    void tearDown() {
        this.productRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.brandRepository.deleteAll();
        LOGGER.debug("✔ Database cleaned up.");
    }

    @Test
    @Order(0)
    void givenACompleteProduct_whenSaved_thenItShouldPersistInTheDatabase() {
        BrandEntity brand = this.brandRepository.findById(2L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(2L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "Guitarra Eléctrica Fender Squier Surf Pearl",
                "La Serie Contemporánea Jazzmaster HH de Squier luce la apariencia de un Jazzmaster clásico, " +
                        "reforzado con pastillas de cerámica activas para satisfacer las necesidades de alto " +
                        "rendimiento de los sonidos modernos.",
                499.99, true, "/uploads/1744051954836.webp", brand, category);
        ProductEntity savedProduct = this.productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull().isEqualTo(4L);
        assertThat(savedProduct)
                .extracting("name", "description", "price", "available", "thumbnail")
                .containsExactly("Guitarra Eléctrica Fender Squier Surf Pearl",
                        "La Serie Contemporánea Jazzmaster HH de Squier luce la apariencia de un Jazzmaster clásico, " +
                                "reforzado con pastillas de cerámica activas para satisfacer las necesidades de alto " +
                                "rendimiento de los sonidos modernos.",
                        499.99, true, "/uploads/1744051954836.webp");
        assertThat(savedProduct)
                .extracting("name", "description", "price", "available", "thumbnail")
                .containsExactly(product.getName(), product.getDescription(), product.getPrice(),
                        product.getAvailable(), product.getThumbnail());

        assertThat(savedProduct.getBrand()).extracting("id", "name", "description", "thumbnail", "available")
                .containsExactly(brand.getId(), brand.getName(), brand.getDescription(), brand.getThumbnail(),
                        brand.getAvailable());
        assertThat(savedProduct.getCategory()).extracting("id", "name", "description", "thumbnail", "available")
                .containsExactly(category.getId(), category.getName(), category.getDescription(),
                        category.getThumbnail(), category.getAvailable());
        LOGGER.info("✔ Product matched the expected values.");
    }

    @Test
    void givenANullName_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(2L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(2L).orElseThrow();
        ProductEntity product = new ProductEntity(null, null, "Descripción del producto", 199.99, true,
                "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Product name cannot be null or empty.");
        LOGGER.info("✔ Attempt to save product with null name threw expected exception.");
    }

    @Test
    void givenAnEmptyName_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(2L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(2L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "", "Descripción del producto", 199.99, true,
                "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Product name cannot be null or empty.");
        LOGGER.info("✔ Attempt to save product with empty name threw expected exception.");
    }

    @Test
    void givenANameWithMoreThan60Characters_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(2L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(2L).orElseThrow();
        String longName = "Guitarra Eléctrica Fender Squier Surf Pearl con un nombre excesivamente largo " +
                "que supera los sesenta caracteres permitidos.";
        ProductEntity product = new ProductEntity(null, longName, "Descripción del producto", 199.99, true,
                "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Product name cannot exceed 60 characters.");
        LOGGER.info("✔ Attempt to save product with name exceeding 60 characters threw expected exception.");
    }

    @Test
    void givenANullDescription_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(1L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(1L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "Batería Accent Drive 5PC 22\" Yamaha LC19511 Verde", null,
                199.99, true, "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Product description cannot be null or empty.");
        LOGGER.info("✔ Attempt to save product with null description threw expected exception.");
    }

    @Test
    void givenAnEmptyDescription_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(1L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(1L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "Batería Accent Drive 5PC 22\" Yamaha LC19511 Verde", "",
                199.99, true, "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Product description cannot be null or empty.");
        LOGGER.info("✔ Attempt to save product with empty description threw expected exception.");
    }

    @Test
    void givenADescriptionWithMoreThan1000Characters_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(1L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(1L).orElseThrow();
        String longDescription = "Descripción del producto ".repeat(70);
        ProductEntity product = new ProductEntity(null, "Batería Accent Drive 5PC 22\" Yamaha LC19511 Verde",
                longDescription, 199.99, true, "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Product description cannot exceed 1000 characters.");
        LOGGER.info("✔ Attempt to save product with description exceeding 1000 characters threw expected exception.");
    }

    @Test
    void givenANullPrice_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(3L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(3L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "Piano Steinway & Sons Modelo E Hamb Ebony",
                "Descripción del producto", null, true, "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Price cannot be null.");
        LOGGER.info("✔ Attempt to save product with null price threw expected exception.");
    }

    @Test
    void givenANegativePrice_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(3L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(3L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "Piano Steinway & Sons Modelo E Hamb Ebony",
                "Descripción del producto", -199.99, true, "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Price must be positive and greater than zero.");
        LOGGER.info("✔ Attempt to save product with negative price threw expected exception.");
    }

    @Test
    void givenAZeroPrice_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(3L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(3L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "Piano Steinway & Sons Modelo E Hamb Ebony",
                "Descripción del producto", 0.0, true, "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Price must be positive and greater than zero.");
        LOGGER.info("✔ Attempt to save product with zero price threw expected exception.");
    }

    @Test
    void givenANullAvailable_whenSaved_thenItShouldThrowException() {
        BrandEntity brand = this.brandRepository.findById(1L).orElseThrow();
        CategoryEntity category = this.categoryRepository.findById(1L).orElseThrow();
        ProductEntity product = new ProductEntity(null, "Batería Accent Drive 5PC 22\" Yamaha LC19511 Morada",
                "Descripción del producto", 199.99, null, "/uploads/1744051954836.webp", brand, category);

        assertThatCode(() -> this.productRepository.save(product))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Product availability cannot be null.");
        LOGGER.info("✔ Attempt to save product with null availability threw expected exception.");
    }

    @Test
    @Order(1)
    void givenAnExistingProductId_whenFoundById_thenItShouldReturnTheProduct() {
        ProductEntity product = this.productRepository.findById(1L).orElseThrow();

        assertThat(product.getId()).isNotNull().isEqualTo(1L);
        assertThat(product)
                .extracting("name", "description", "price", "available", "thumbnail")
                .containsExactly("Violín 4/4 Sólido Yamaha HXTQ09FRO Natural",
                        "El Violín Yamaha es un instrumento hecho de madera solida, diseñado para estudiantes " +
                                "de nivel académico medio o iniciación para estudios formales.",
                        499.99, true, "/uploads/1744052240330.webp");
        assertThat(product.getBrand().getId()).isNotNull().isEqualTo(1L);
        assertThat(product.getCategory().getId()).isNotNull().isEqualTo(2L);
        LOGGER.info("✔ Product with ID 1 was found in the database and matched the expected values.");
    }

    @Test
    @Order(2)
    void givenAnExistingProductName_whenExistsByName_thenItShouldReturnTrue() {
        String name = "Piano de Cola Steinway & Sons Modelo D GRD Hamb Ebony";
        boolean exists = this.productRepository.existsByNameIgnoreCase(name);

        assertThat(exists).isTrue();
        LOGGER.info("✔ Product with name '{}' exists in the database.", name);
    }

    @Test
    @Order(3)
    void givenAnExistingProductNameAndId_whenExistsByNameAndIdNot_thenItShouldReturnTrue() {
        String name = "Piano de Cola Steinway & Sons Modelo D GRD Hamb Ebony";
        Long id = 2L;
        boolean exists = this.productRepository.existsByNameIgnoreCaseAndIdNot(name, id);

        assertThat(exists).isFalse();
        LOGGER.info("✔ Product with name '{}' and ID '{}' does not exist in the database.", name, id);
    }
}