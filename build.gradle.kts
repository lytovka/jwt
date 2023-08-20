import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.0"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("com.diffplug.spotless") version "6.20.0"
}

group = "com.lytovka"
version = "1.0-SNAPSHOT"

val javaVersion = "17"
java.setTargetCompatibility(javaVersion)
java.setSourceCompatibility(javaVersion)

// kotlin
val kotlinSerializationVersion = "1.5.1"
val kotlinCoroutinesVersion = "1.7.3"

// security
val joseVersion = "9.31"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // JWT
    implementation("com.nimbusds:nimbus-jose-jwt:$joseVersion")
}

tasks.bootBuildImage {
    environment.set(
        mapOf(
            "BP_JVM_VERSION" to "17",
            "BP_SPRING_CLOUD_BINDINGS_DISABLED" to "true",
            "BPL_SPRING_CLOUD_BINDINGS_DISABLED" to "true",
            "BPE_JAVA_TOOL_OPTIONS" to "-XX:ActiveProcessorCount=0 -XX:+UseContainerSupport -XX:+PreferContainerQuotaForCPUCount",
        ),
    )
}

// ---- Current Version ----
tasks.register("currentVersion") {
    logger.quiet("$version")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = javaVersion
    }
}

// ---- pre-commit hook ----
tasks.register("installGitHook", Copy::class) {
    from(File(rootProject.rootDir, "scripts/pre-commit.sh"))
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 509
    rename("pre-commit.sh", "pre-commit")
}

tasks.compileKotlin {
    dependsOn("installGitHook")
}

spotless {
    java {
        removeUnusedImports()
    }
    kotlin {
        ktlint()
        target("**/*.kt")
        targetExclude("**/build/**")
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }
}
