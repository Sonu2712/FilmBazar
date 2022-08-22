plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion = Versions.buildToolsVersion

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":appusercore"))
    implementation(project(":annotations"))
    implementation(project(":domaincore"))
    implementation(project(":appcore"))
    implementation(project(":moslcommons"))
    implementation(project(":tokenmanager"))
    implementation(project(":rx_preferences"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(KotlinDependencies.kotlin)
    implementation(KotlinDependencies.ktxCore)

    implementation(AndroidxDependencies.appcompat)
    implementation(AndroidxDependencies.security)

    implementation(DaggerDependencies.core)
    kapt(DaggerDependencies.compiler)

    implementation(Dependencies.gson)
    implementation(Dependencies.timber)

    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.android)
    implementation(RxDependencies.relay)
}
