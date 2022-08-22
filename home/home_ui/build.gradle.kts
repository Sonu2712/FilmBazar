
plugins {
    id("gradle-ui")
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
    buildFeatures {
        dataBinding = true
        viewBinding = true
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
    implementation(project(":moslcommons"))

    implementation(project(":appuser"))
    api(project(":home_domain"))
    api(project(":home_data"))

    implementation(Dependencies.coil) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }

    implementation(AndroidxDependencies.swipeRefresh)
    implementation(RxBindingDependencies.swipeRefresh)

    implementation(project(":appcore"))
    //implementation(project(":tokenmanager"))

    implementation(GroupieDependencies.core)
    implementation(GroupieDependencies.dataBinding)

    implementation(OkHttpDependencies.okio)
}
