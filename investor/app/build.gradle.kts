import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}
/*val gitSha = ByteArrayOutputStream()
exec {
    commandLine("git", "rev-parse", "--short", "HEAD")
    standardOutput = gitSha
}

val gitTimestamp = ByteArrayOutputStream()
exec {
    commandLine("git", "log", "-n", "1", "--format=%at")
    standardOutput = gitTimestamp
}*/

android {
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/services/javax.annotation.processing.Processor")
    }
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion = Versions.buildToolsVersion

    val versionPropsFile = FileInputStream(File("${project.rootDir}/investor/version.properties"))
    val versionProps = Properties()
    versionProps.load(versionPropsFile)

    val keystorePropsFile = FileInputStream(File("${project.rootDir}/investor/keystore.properties"))
    val keystoreProps = Properties()
    keystoreProps.load(keystorePropsFile)

    signingConfigs {
        create("internal") {
            try {
                storeFile = file("../mastapp.jks")
                storePassword = keystoreProps.getProperty("KEYSTORE_PASSWORD")
                keyAlias = keystoreProps.getProperty("UAT_ALIAS")
                keyPassword = keystoreProps.getProperty("UAT_ALIAS_PASSWORD")
            } catch (ex: Exception) {
                throw InvalidUserDataException(
                    "You should define KEYSTORE_PASSWORD, UAT_ALIAS & UAT_ALIAS_PASSWORD in keystore.properties"
                )
            }
        }
        create("production") {
            try {
                storeFile = file("../mastapp.jks")
                storePassword = keystoreProps.getProperty("KEYSTORE_PASSWORD")
                keyAlias = keystoreProps.getProperty("PROD_ALIAS")
                keyPassword = keystoreProps.getProperty("PROD_ALIAS_PASSWORD")
            } catch (ex: Exception) {
                throw  InvalidUserDataException(
                    "You should define KEYSTORE_PASSWORD, PROD_ALIAS & PROD_ALIAS_PASSWORD in keystore.properties"
                )
            }
        }
    }

    defaultConfig {
        applicationId = "com.film.bazar"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)

        buildConfigField("String", "APP_SOURCE", "\"${versionProps.getProperty("source")}\"")
        buildConfigField(
            "boolean",
            "IS_INVESTOR",
            "${versionProps.getProperty("isInvestor")!!.toBoolean()}"
        )
       // buildConfigField("String", "GIT_SHA", "\"${String(gitSha.toByteArray()).trim()}\"")
       // buildConfigField("long", "GIT_TIMESTAMP", "${String(gitTimestamp.toByteArray()).trim()}L")

        missingDimensionStrategy("appType", "investor")

        resConfigs("en", "hi", "gu")
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
         viewBinding = true
    }
    bundle {
        language {
            enableSplit = false
        }
    }

    flavorDimensions("releaseType")
    productFlavors {
        create("internal") {
            versionCode = versionProps.getProperty("code").toInt()
            versionName = versionProps.getProperty("name")
            applicationIdSuffix = ".dev"
            buildConfigField(
                "boolean",
                "LOGOUT_ON_UPDATE",
                versionProps.getProperty("clearUserLogin")
            )
            buildConfigField(
                "boolean",
                "ShowCoachMarks",
                versionProps.getProperty("showCoachMarks")
            )
            dimension = "releaseType"
            signingConfig = signingConfigs.getByName("internal")
        }
        create("production") {
            versionCode = versionProps.getProperty("prodCode").toInt()
            versionName = versionProps.getProperty("prodName")
            // applicationIdSuffix = ".beta"
            buildConfigField(
                "boolean",
                "LOGOUT_ON_UPDATE",
                versionProps.getProperty("prodClearUserLogin")
            )
            buildConfigField(
                "boolean",
                "ShowCoachMarks",
                versionProps.getProperty("prodShowCoachMarks")
            )
            dimension = "releaseType"
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            multiDexEnabled = true
            //ext.alwaysUpdateBuildId = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            multiDexEnabled = true
            proguardFile("proguard-retrofit.pro")
            proguardFile("proguard-dagger.pro")
            proguardFile("proguard-gson.pro")
            proguardFile("proguard-netcore_product_experience.pro")
            proguardFile("proguard-conceal.pro")
            proguardFile("proguard-crashlytics.pro")
            proguardFile("proguard-lottie-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName = output.outputFileName
                    .replace(".apk", "${project.name}-${variant.name}-${variant.versionName}.apk")
            }
    }

    if (project.hasProperty("devBuild")) {
        aaptOptions.cruncherEnabled = false
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
    implementation(project(":annotations"))
    implementation(project(":flashbar"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)
    implementation(AndroidxDependencies.appcompat)
    implementation(AndroidxDependencies.fragment)
    implementation(KotlinDependencies.ktxCore)
    implementation(AndroidxDependencies.constraintLayout)
    implementation(AndroidxDependencies.material)
    implementation(Dependencies.threeTen)
    implementation(AndroidxDependencies.browser)
    testImplementation(TestingDependencies.junit)
    androidTestImplementation(AndroidxDependencies.testRunner)
    androidTestImplementation(AndroidxDependencies.testEspresso)

    //DaggerDependencies
    implementation(DaggerDependencies.core)
    kapt(DaggerDependencies.compiler)
    implementation(DaggerDependencies.android)
    kapt(DaggerDependencies.androidCompiler)

    //RxJava 2.0
    implementation(RxDependencies.java)
    implementation(RxDependencies.kotlin)
    implementation(RxDependencies.relay)
    implementation(RxDependencies.android)
    implementation(RxBindingDependencies.bindingPlatform)
    implementation(RxBindingDependencies.bindingCore)
    implementation(RxBindingDependencies.bindingAppcompat)
    implementation(RxBindingDependencies.bindingMaterial)
    implementation(Dependencies.timber)
    implementation(Dependencies.gson)
    implementation(Dependencies.coil) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(Dependencies.lottie) {
        exclude(group = "com.squareup.okio", module = "okio")
    }

    implementation(RetrofitDependencies.core) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
        exclude(group = "com.squareup.okio", module = "okio")
    }
    implementation(RetrofitDependencies.rxJava)
    debugImplementation(RetrofitDependencies.mock)
    implementation(RxBindingDependencies.swipeRefresh)

    implementation(OkHttpDependencies.core)
    implementation(OkHttpDependencies.okio)
    debugImplementation(OkHttpDependencies.logger)

    implementation(AndroidxDependencies.multiDex)

    implementation(GroupieDependencies.core)
    implementation(GroupieDependencies.dataBinding)

    implementation(Dependencies.processPhoenix)

    implementation(AndroidxDependencies.security)
    //implementation(project(":tokenmanager"))
    implementation(project(":appcore"))
    debugImplementation(project(":debugview"))
    debugImplementation(project(":debugviewcore"))
    implementation(project(":rx_preferences"))
    debugImplementation(Dependencies.protoUtil)
    implementation(Dependencies.flexBox)

    implementation(Dependencies.swipeActionBar)

    implementation(LifeCycleDependencies.extension)
    kapt(LifeCycleDependencies.compiler)

    implementation(AutoReadOTPDependencies.base)
    implementation(AutoReadOTPDependencies.identity)
    implementation(AutoReadOTPDependencies.auth)
    implementation(AutoReadOTPDependencies.apiPhone)

    implementation(RoomDependencies.runtime)

    implementation(Dependencies.appIntro)

    implementation(project(":app_domain"))
    implementation(project(":app_data"))
    implementation(project(":trading_api"))
    implementation(project(":core_ui"))
    implementation(project(":core_domain"))
    implementation(project(":core_data"))
    implementation(project(":login_ui"))
    debugImplementation(project(":trading_api_mock"))
    implementation(project(":appuser"))
    implementation(project(":home_ui"))

    implementation(PlayServiceDependencies.googlePlay)
    // implementation(files("libs/YouTubeAndroidPlayerApi.jar"))

    implementation(Dependencies.workManager)

    implementation(project(":google-pay-client-api-1.0.1"))
//Nsdl
    implementation("com.android.volley:volley:1.1.1")
}

kapt {
    javacOptions {
        option("-Xmaxerrs", 2000)
    }
}

androidExtensions {
    features = setOf("parcelize")
}

