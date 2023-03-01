plugins {
	java
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "org.ziaho"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	// implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springframework.boot:spring-boot-starter-freemarker")
	runtimeOnly("com.h2database:h2")

	// swagger
//	implementation("io.springfox:springfox-boot-starter:3.0.0")
//	implementation("io.springfox:springfox-swagger-ui:3.0.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.0.2")

	implementation("org.springframework.boot:spring-boot-starter-validation")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
