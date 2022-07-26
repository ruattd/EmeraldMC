plugins {
    java
    kotlin("jvm").version("1.6.0")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "org.eu.maxelbk"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.18.2-R0.1-SNAPSHOT")
}

val targetJavaVersion = JavaVersion.VERSION_17
java {
    sourceCompatibility = targetJavaVersion
    targetCompatibility = targetJavaVersion
}

tasks {
    withType<JavaCompile>().configureEach {
        if (targetJavaVersion >= JavaVersion.VERSION_1_10 || JavaVersion.current().isJava10Compatible) {
            options.release.set(targetJavaVersion.toString().toInt())
        }
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = targetJavaVersion.toString()
        }
        sourceCompatibility = targetJavaVersion.toString()
        targetCompatibility = targetJavaVersion.toString()
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
