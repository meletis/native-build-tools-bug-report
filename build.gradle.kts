plugins {
    id("java")
    id("application")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.hibernate.orm") version "6.3.1.Final" // Upgrade hibernate plugin version when upgrading spring boot
    id("io.freefair.lombok") version "8.4"
    id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "org.example"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val springCloudVersion = "2023.0.0"
val testContainersVersion = "1.19.3"
val mysqlConnectorVersion = "8.2.0"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    
    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")

    testImplementation(platform("org.testcontainers:testcontainers-bom:$testContainersVersion"))
    testImplementation("org.testcontainers:mysql:$testContainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.mysql:mysql-connector-j:$mysqlConnectorVersion")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.example.MyApplication")
}

// Configure the GraalVM native image plugin settings
graalvmNative {
    binaries.all {
        resources.autodetect()
    }

    // We need this, otherwise testcontainers suffer from this:
    // https://github.com/oracle/graalvm-reachability-metadata/pull/301
    metadataRepository {
        enabled.set(true)
    }

    toolchainDetection.set(false)
}

hibernate {
    enhancement {
        enableAssociationManagement.set(true)
    }
}