# SoundSeeker Product Service

Este proyecto implementa un microservicio de gestión de productos para la plataforma SoundSeeker, una tienda de
instrumentos musicales, utilizando Spring Boot.

## 🎯 Objetivo

Permitir la gestión completa de productos (instrumentos musicales) con operaciones CRUD (Crear, Leer, Actualizar,
Eliminar) a través de una API REST.

## 📋 Características

- API RESTful con Spring Boot
- Persistencia con Spring Data JPA y base de datos H2 en memoria
- Validaciones de datos con Jakarta Validation
- Seguridad básica con Spring Security
- Mapeo de entidades y DTOs con MapStruct
- Manejo centralizado de excepciones
- Pruebas unitarias

## 📽 Demo

Vídeo de funcionamiento:

https://github.com/user-attachments/assets/3d364240-edc2-4ef1-a628-701d2df3879f

## 🧱 Estructura del proyecto

El proyecto está organizado en paquetes según su responsabilidad:

```
src/main/java/me/davidgarmo/soundseeker/product/
 ├── persistence/               # Persistencia de datos
 │    ├── entity/               # Entidades JPA
 │    ├── mapper/               # MapStruct mappers
 │    └── repository/           # Repositorios JPA
 ├── service/                   # Lógica de negocio y validaciones
 │    ├── dto/                  # Objetos de transferencia de datos
 │    ├── exception/            # Excepciones personalizadas y manejo global
 │    └── impl/                 # Implementaciones de servicios
 └── web/                       # Controladores REST
 │    ├── config/               # Configuraciones de la aplicación
 │    └── controller/           # Controladores REST
 └── ProductApplication.java    # Clase principal Spring Boot
```

## 🛠️ Tecnologías utilizadas

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security
- H2 Database
- MapStruct
- Jakarta Validation
- Gradle

## ⚙️ Instalación y ejecución

### Requisitos previos

- Java 21 o superior
- Gradle 8.x

### Pasos para ejecutar

1. Clonar el repositorio
   ```bash
   git clone https://github.com/DavidGMont/product-spring.git
   ```
2. Acceder al directorio del proyecto
   ```bash
    cd product-spring
    ```
3. Compilar y ejecutar pruebas
   ```bash
   ./gradlew clean test
    ```
4. Ejecutar la aplicación
   ```bash
   ./gradlew bootRun
    ```

La API estará disponible en `http://localhost:8080`.

## 📈 Funcionalidades implementadas

### Operaciones CRUD

- **CREATE:** Crear un nuevo producto (POST)
- **READ:** Obtener producto por ID o listar todos (GET)
- **UPDATE:** Actualizar información de un producto (PUT)
- **DELETE:** Eliminar un producto (DELETE)

### Validaciones

- Campos obligatorios (nombre, descripción, marca, etc.)
- Restricciones de longitud y valores válidos (precio > 0)

## 🧪 Tests

Incluye pruebas unitarias y de integración para los repositorios:

   ```bash
   ./gradlew clean test
   ```

## 📚 Referencias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [MapStruct](https://mapstruct.org/)
- [H2 Database](https://h2database.com/html/main.html)

## 👥 Autores

- [David García](https://github.com/DavidGMont)
