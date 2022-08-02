plugins {
    val kotlinVersion = "1.6.21"
    java
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
//    kotlin("kapt") version kotlinVersion
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.eu.maxelbk"
version = "1.0"

repositories {
    mavenCentral()

    // PaperMC API
    maven {
        name = "papermc-repo"
        setUrl("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        setUrl("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    // PaperMC API
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    // kts support
    implementation(kotlin("script-runtime"))
    implementation(kotlin("script-util"))
    implementation(kotlin("compiler-embeddable"))
    implementation(kotlin("scripting-compiler-embeddable"))
    implementation(kotlin("scripting-dependencies"))

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0-RC")

}

val targetJavaVersion = JavaVersion.VERSION_17
val targetJavaVersionString = targetJavaVersion.toString()

java {
    sourceCompatibility = targetJavaVersion
    targetCompatibility = targetJavaVersion
}

tasks {
    withType<JavaCompile>().configureEach {
        if (targetJavaVersion >= JavaVersion.VERSION_1_10 || JavaVersion.current().isJava10Compatible) {
            options.release.set(targetJavaVersionString.toInt())
        }
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = targetJavaVersionString
        }
        sourceCompatibility = targetJavaVersionString
        targetCompatibility = targetJavaVersionString
    }
    shadowJar {
        archiveClassifier.set("")
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
}

//processResources {
//    val props = [version: version]
//    inputs.properties(props)
//    filteringCharset("UTF-8")
//    filesMatching("plugin.yml") {
//        expand(props)
//    }
//}
