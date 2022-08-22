plugins {
    id("gradle-data")
    id("kotlin-android-extensions")
}

android {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":appusercore"))
    implementation(project(":annotations"))
    implementation(project(":tokenmanager"))
    implementation(project(":moslcommons"))
    implementation(project(":appcore"))
    implementation(project(":rx_preferences"))

    implementation(project(":trading_api"))
    debugImplementation(project(":trading_api_mock"))
    implementation(project(":login_domain"))
    implementation(project(":appuser"))
    implementation(AndroidxDependencies.security)
    implementation(RxDependencies.relay)
    implementation(OkHttpDependencies.core)
}

androidExtensions {
    features = setOf("parcelize")
}
