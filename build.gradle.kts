plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.javaguru.jdmk15"
version = "0.0.1-SNAPSHOT"
description = "spring-boot-mvc"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}
val mapstructVersion = "1.6.3"

dependencies {
    // Это даст вам Tomcat и все возможности REST API
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Без этого аннотация @Valid и проверки (@NotNull, @Size) работать не будут
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // Для автоматической перезагрузки при изменении кода
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // --- MAPSTRUCT ---
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

    // --- LOMBOK ---
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // --- ВАЖНО: СВЯЗКА LOMBOK + MAPSTRUCT ---
    // Эта зависимость гарантирует, что MapStruct увидит поля, сгенерированные Lombok
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // Он содержит JUnit, Mockito, AssertJ и поддержку @SpringBootTest + @AutoConfigureMockMvc
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // JUnit лаунчер
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
