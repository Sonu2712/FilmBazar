
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
    implementation(project(":annotations"))
    implementation(project(":rx_preferences"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidxDependencies.appcompat)
    implementation(KotlinDependencies.ktxCore)
    testImplementation(TestingDependencies.junit)

    implementation(DaggerDependencies.core)
    kapt(DaggerDependencies.compiler)

    implementation(RetrofitDependencies.core) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(RetrofitDependencies.rxJava)
    implementation(RetrofitDependencies.mock)
    implementation(RetrofitDependencies.gson)
    implementation(RetrofitDependencies.protoBuf) { exclude(group = "com.google.protobuf") }

    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.android)
    implementation(RxDependencies.relay)

    implementation(Dependencies.timber)

    androidTestImplementation(AndroidxDependencies.testRunner)
    androidTestImplementation(AndroidxDependencies.testEspresso)

    debugImplementation(OkHttpDependencies.logger)
    implementation(AndroidxDependencies.security)

    implementation(project(":moslcommons"))
    implementation(project(":appcore"))
    debugImplementation(project(":debugviewcore"))
    implementation(project(":tokenmanager"))

    debugImplementation(Dependencies.protoUtil)
    implementation(project(":appuser"))
    implementation(project(":core_domain"))
    implementation(project(":trading_api"))
}

androidExtensions {
    features = setOf("parcelize")
}