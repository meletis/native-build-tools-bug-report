plugins {
    id("java")
    id("application")
    id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // Test containers dependencies
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.19.1"))
    testImplementation("org.testcontainers:mysql:1.19.1")
    testImplementation("org.testcontainers:junit-jupiter:1.19.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")

    testRuntimeOnly("com.mysql:mysql-connector-j:8.0.33")
}

tasks.test {
    useJUnitPlatform()
}

// Conditionally apply the GraalVM native image plugin
gradle.taskGraph.whenReady {
    if (gradle.taskGraph.hasTask(":nativeCompile") or gradle.taskGraph.hasTask(":nativeTest")) {
        apply(plugin = "org.graalvm.buildtools.native")

        application {
            mainClass.set("com.example.TestApp")
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
    }
}
