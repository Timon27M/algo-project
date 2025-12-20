plugins {
    id("java")
    id("java-library")  // Дополнительный плагин для библиотек
}

group = "apt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(files("libs/heroes_task_lib-1.0-SNAPSHOT.jar"))
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java/")
            include("**/*.java")  // Все java файлы в любых подпапках
        }
    }
}

tasks.test {
    useJUnitPlatform()
}