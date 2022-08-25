object Versions {
    const val compileSdkVersion = 32
    const val targetSdkVersion = 32
    const val minSdkVersion = 21
    const val buildToolsVersion = "32.1-rc1"

    const val android_plugin_version = "4.2.0"
    const val kotlinVersion = "1.4.30"
    const val googleServices = "4.3.13"
    const val dexCountGradlePlugin = "2.0.0"
    const val okHttp = "4.9.0"
    const val okIo = "2.9.0"
    const val timber = "4.7.1"
    const val groupie = "2.8.1"
    const val retrofit = "2.9.0"
    const val dagger = "2.28.3"
    const val gson = "2.8.6"
    const val rxBinding = "4.0.0"
    const val room = "2.3.0-alpha02"
    const val wearable = "2.7.0"
    const val proto = "3.11.0"
    const val lottie = "3.4.1"
    const val flashBar = "0.0.22"
    const val detektVersion = "1.10.0"
    const val securityVersion = "1.1.0-alpha02"
}

object Dependencies {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val mpChart = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    const val appIntro = "com.github.AppIntro:AppIntro:5.1.0"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val processPhoenix = "com.jakewharton:process-phoenix:2.0.0"
    const val protoUtil = "com.google.protobuf:protobuf-java-util:${Versions.proto}"
    const val flexBox = "com.google.android:flexbox:2.0.1"
    const val swipeActionBar = "com.github.Tunous:SwipeActionView:1.3.1"
    const val coil = "io.coil-kt:coil:0.9.5"  // 2.1.0
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val workManager = "androidx.work:work-rxjava3:2.4.0"
    const val toolTipBaloon = "com.github.skydoves:balloon:1.4.0"
    const val threeTen = "com.jakewharton.threetenabp:threetenabp:1.2.4"
    const val dataBinding = "androidx.databinding:databinding-compiler:${Versions.android_plugin_version}"
    const val circleImageView = "de.hdodenhof:circleimageview:3.1.0"
}

object PluginsDependencies {
    const val androidBuild = "com.android.tools.build:gradle:${Versions.android_plugin_version}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
}

object AndroidxDependencies {
    const val annotation = "androidx.annotation:annotation:1.1.0"
    const val material = "com.google.android.material:material:1.3.0"
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-alpha03"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
    const val fragment = "androidx.fragment:fragment:1.2.0"
    const val cardView = "androidx.cardview:cardview:1.0.0"
    const val browser = "androidx.browser:browser:1.3.0"
    const val multiDex = "androidx.multidex:multidex:2.0.1"
    const val preference = "androidx.preference:preference:1.1.1"
    const val legacy = "androidx.legacy:legacy-support-core-ui:1.0.0"
    const val testRunner = "androidx.test:runner:1.2.0"
    const val testEspresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val testJunit = "androidx.test.ext:junit:1.1.1"
    const val security = "androidx.security:security-crypto:${Versions.securityVersion}"
}

object KotlinDependencies {
    const val ktxCore = "androidx.core:core-ktx:1.3.1"
    const val ktxFragment = "androidx.fragment:fragment-ktx:1.2.5"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
}

object LifeCycleDependencies {
    const val compiler = "androidx.lifecycle:lifecycle-compiler:2.2.0"
    const val extension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
}

object RetrofitDependencies {
    const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val rxJava = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"
    const val protoBuf = "com.squareup.retrofit2:converter-protobuf:${Versions.retrofit}"
}

object RoomDependencies {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val rx = "androidx.room:room-rxjava3:${Versions.room}"
}

object TestingDependencies {
    const val junit = "junit:junit:4.13"
    const val mockito = "org.mockito:mockito-core:1.10.19"
    const val assertj = "org.assertj:assertj-core:3.12.2"
    const val testcore = "androidx.test:core:1.0.0"
    const val robolectric = "org.robolectric:robolectric:4.2.1"
}

object PlayServiceDependencies {
    const val googlePlay = "com.google.android.play:core:1.10.0"
    const val core = "com.google.android.play:core:1.10.0"
    const val wearable = "com.google.android.gms:play-services-wearable:17.0.0"
}

object DaggerDependencies {
    const val core = "com.google.dagger:dagger:${Versions.dagger}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val android = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val androidCompiler =
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val javax = "javax.inject:javax.inject:1"
}

object GroupieDependencies {
    const val core = "com.xwray:groupie:${Versions.groupie}"
    const val dataBinding = "com.xwray:groupie-databinding:${Versions.groupie}"
}

object RxDependencies {
    const val java = "io.reactivex.rxjava3:rxjava:3.0.5"
    const val kotlin = "io.reactivex.rxjava3:rxkotlin:3.0.0"
    const val android = "io.reactivex.rxjava3:rxandroid:3.0.0"
    const val relay = "com.jakewharton.rxrelay3:rxrelay:3.0.0"
}

object RxBindingDependencies {
    const val bindingPlatform = "com.jakewharton.rxbinding4:rxbinding:${Versions.rxBinding}"
    const val bindingCore = "com.jakewharton.rxbinding4:rxbinding-core:${Versions.rxBinding}"
    const val swipeRefresh =
        "com.jakewharton.rxbinding4:rxbinding-swiperefreshlayout:${Versions.rxBinding}"
    const val bindingViewpager =
        "com.jakewharton.rxbinding4:rxbinding-viewpager:${Versions.rxBinding}"
    const val bindingAppcompat =
        "com.jakewharton.rxbinding4:rxbinding-appcompat:${Versions.rxBinding}"
    const val bindingMaterial =
        "com.jakewharton.rxbinding4:rxbinding-material:${Versions.rxBinding}"
    const val madge = "com.jakewharton.madge:madge:1.1.4"
}

object OkHttpDependencies {
    const val okio = "com.squareup.okio:okio:${Versions.okIo}"
    const val core = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val logger = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
}

object AutoReadOTPDependencies {
    const val base = "com.google.android.gms:play-services-base:17.3.0"
    const val identity = "com.google.android.gms:play-services-identity:17.0.0"
    const val auth = "com.google.android.gms:play-services-auth:18.1.0"
    const val apiPhone = "com.google.android.gms:play-services-auth-api-phone:17.4.0"
}