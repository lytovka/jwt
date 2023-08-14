import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.0"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("com.diffplug.spotless") version "6.20.0"
}

group = "com.lytovka"
version = "1.0-SNAPSHOT"

val javaVersion = "17"
java.setTargetCompatibility(javaVersion)
java.setSourceCompatibility(javaVersion)

// kotlin
val kotlinSerializationVersion = "1.5.1"

// security
val joseVersion = "9.31"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("com.nimbusds:nimbus-jose-jwt:$joseVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = javaVersion
    }
}

spotless {
    java {
        removeUnusedImports()
    }
    kotlin {
        target("**/*.kt")
        ktlint()
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }
}
