plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion = Versions.buildToolsVersion

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    android.buildFeatures {
        viewBinding = true
        dataBinding = true
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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidxDependencies.appcompat)
    implementation(KotlinDependencies.ktxCore)
    implementation(AndroidxDependencies.material)
    implementation(AndroidxDependencies.constraintLayout)

    implementation(DaggerDependencies.core)
    kapt(DaggerDependencies.compiler)
    implementation(DaggerDependencies.android)
    kapt(DaggerDependencies.androidCompiler)

    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.android)
    implementation(RxBindingDependencies.bindingPlatform)
    implementation(RxBindingDependencies.bindingCore)

    implementation(Dependencies.timber)

    testImplementation(TestingDependencies.junit)
    androidTestImplementation(AndroidxDependencies.testJunit)
    androidTestImplementation(AndroidxDependencies.testEspresso)

    implementation(project(":core_ui"))
    implementation(project(":core_domain"))
}

androidExtensions {
    features = setOf("parcelize")
}
