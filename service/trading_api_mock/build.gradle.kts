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
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidxDependencies.appcompat)
    implementation(KotlinDependencies.ktxCore)
    testImplementation(TestingDependencies.junit)
    androidTestImplementation(AndroidxDependencies.testRunner)
    androidTestImplementation(AndroidxDependencies.testEspresso)

    implementation(DaggerDependencies.core)
    kapt(DaggerDependencies.compiler)
    implementation(RxDependencies.java)
    implementation(RetrofitDependencies.mock)

    implementation(project(":trading_api"))
  }
