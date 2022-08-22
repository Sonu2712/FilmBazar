plugins {
    id("gradle-ui")
}

android {

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
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
    implementation(project(":moslcommons"))
    implementation(project(":validator"))
    implementation(project(":annotations"))

    api(project(":login_data"))
    api(project(":login_domain"))
    implementation(project(":appuser"))
    implementation(project(":core_ui"))

    //implementation(project(":tokenmanager"))
    implementation(project(":appcore"))

    implementation(OkHttpDependencies.okio)

    implementation(Dependencies.coil) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }

    implementation(project(":google-pay-client-api-1.0.1"))

    //Nsdl
    implementation("com.android.volley:volley:1.1.1")
}
