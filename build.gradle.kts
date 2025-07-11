plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.anthony"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	runtimeOnly("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok:1.18.32")
	annotationProcessor("org.projectlombok:lombok:1.18.32")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
