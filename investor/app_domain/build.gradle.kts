plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)

    implementation(RxDependencies.java)
    implementation(DaggerDependencies.core)
    implementation(project(":moslcommons"))

    implementation(project(":core_domain"))
}
