import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
    }
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.8"
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.0")
    implementation("com.android.tools.build:gradle-api:4.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.70")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.70")
}
