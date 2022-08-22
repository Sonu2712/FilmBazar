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

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
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
    implementation(project(":moslcommons"))
    implementation(project(":flashbar"))
    implementation(project(":appcore"))
    implementation(project(":domaincore"))
    api(project(":core_data"))
    api(project(":core_domain"))
    implementation(project(":appuser"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidxDependencies.appcompat)
    implementation(AndroidxDependencies.fragment)
    implementation(KotlinDependencies.ktxCore)
    implementation(PlayServiceDependencies.googlePlay)

    implementation(Dependencies.coil) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }

    implementation(RxBindingDependencies.bindingMaterial)

    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.android)

    // DaggerDependencies
    implementation(DaggerDependencies.core)
    kapt(DaggerDependencies.compiler)
    implementation(DaggerDependencies.android)
    kapt(DaggerDependencies.androidCompiler)

    // RxJava 2.0
    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.relay)
    implementation(RxDependencies.android)
    implementation(RxBindingDependencies.bindingPlatform)
    implementation(RxBindingDependencies.bindingCore)
    implementation(RxBindingDependencies.bindingAppcompat)

    implementation(Dependencies.timber)

    testImplementation(TestingDependencies.junit)
    androidTestImplementation(AndroidxDependencies.testJunit)
    androidTestImplementation(AndroidxDependencies.testEspresso)

    implementation(AndroidxDependencies.material)
    implementation(AndroidxDependencies.constraintLayout)
    implementation(Dependencies.lottie)

    implementation(GroupieDependencies.core)

    implementation(Dependencies.coil){
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }

    implementation(AutoReadOTPDependencies.base)
    implementation(AutoReadOTPDependencies.identity)
    implementation(AutoReadOTPDependencies.auth)
    implementation(AutoReadOTPDependencies.apiPhone)
}

androidExtensions {
    features = setOf("parcelize")
}
