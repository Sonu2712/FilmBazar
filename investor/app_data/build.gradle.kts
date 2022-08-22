
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

    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.android)
    implementation(RxDependencies.relay)

    implementation(Dependencies.timber)
    implementation(Dependencies.gson)

    implementation(RoomDependencies.runtime)

    androidTestImplementation(AndroidxDependencies.testRunner)
    androidTestImplementation(AndroidxDependencies.testEspresso)

    debugImplementation(OkHttpDependencies.logger)
    implementation(AndroidxDependencies.security)

    implementation(project(":moslcommons"))
    implementation(project(":appcore"))
    debugImplementation(project(":debugviewcore"))

    implementation(project(":trading_api"))
    debugImplementation(project(":trading_api_mock"))
    implementation(project(":core_data"))
    implementation(project(":app_domain"))
    implementation(project(":login_data"))
    implementation(project(":home_data"))
    implementation(project(":appuser"))
}
