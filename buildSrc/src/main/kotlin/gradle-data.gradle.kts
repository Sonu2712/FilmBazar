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
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidxDependencies.appcompat)
    implementation(KotlinDependencies.ktxCore)

    testImplementation(TestingDependencies.junit)
    androidTestImplementation(AndroidxDependencies.testJunit)
    androidTestImplementation(AndroidxDependencies.testEspresso)

    implementation(DaggerDependencies.core)
    kapt(DaggerDependencies.compiler)

    implementation(RetrofitDependencies.core) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(RetrofitDependencies.rxJava) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(RetrofitDependencies.mock) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }

    implementation(Dependencies.gson)

    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.android)
    implementation(Dependencies.timber)

    debugImplementation(project(":debugviewcore"))

    api(project(":core_domain"))
    implementation(project(":core_data"))
    implementation(project(":trading_api"))
    debugImplementation(project(":trading_api_mock"))
}
