plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}