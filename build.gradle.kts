plugins {
    kotlin("jvm") version "2.0.21"
}

group = "dev.limelier"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core:6.0.0.M1")
}

tasks.test {
    useJUnitPlatform()
}

java.setTargetCompatibility(22)

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_22
    }
}