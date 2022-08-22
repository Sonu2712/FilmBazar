plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(KotlinDependencies.kotlin)

    implementation(DaggerDependencies.core)
    implementation(RxDependencies.java)

    api(project(":core_domain"))
}
