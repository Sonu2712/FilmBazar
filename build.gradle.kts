// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    //val kotlin_version by extra("1.4.21")
    val kotlin_version by extra("1.4.30")
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://repo.spring.io/release") }
        maven { url = uri("https://repository.jboss.org/maven2") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("http://192.168.101.169:9090/artifactory/libs-release-local") }
        maven { url = uri("https://artifacts.netcore.co.in/artifactory/android") }
    }

    dependencies {
        classpath(PluginsDependencies.androidBuild)
        classpath(PluginsDependencies.kotlinGradlePlugin)
        classpath(PluginsDependencies.googleServices)
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.10.0")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://repo.spring.io/release") }
        maven { url = uri("https://repository.jboss.org/maven2") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("http://192.168.101.169:9090/artifactory/libs-release-local") }
        maven { url = uri("https://artifacts.netcore.co.in/artifactory/android") }
        maven {
            url = uri("s3://hvsdk/android/releases")
            content {
                // this repository only contains artifacts with group "my.company"
                includeGroup("co.hyperverge")
            }
            credentials(AwsCredentials::class.java) {
                accessKey = "AKIAXB3KY4F5AQJD6WHT"
                secretKey = "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"
            }}

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register("kotlinCompile", org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
    allprojects {
        kotlinOptions {
            jvmTarget = "1.8"
            allWarningsAsErrors = true
        }
    }
}

/*detekt {
    failFast = true // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
    config = files("$projectDir/config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
    // baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt

    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
    }
}*/
val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

val detektFormat by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Formats whole project."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(projectDir)
    config.setFrom(listOf(files("$projectDir/config/detekt/statistics.yml"), files("$projectDir/config/detekt/format.yml")))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
   // baseline.set(baselineFile)
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

allprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = "1.8"
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detektVersion}")
        detekt("io.gitlab.arturbosch.detekt:detekt-cli:${Versions.detektVersion}")
    }
}
