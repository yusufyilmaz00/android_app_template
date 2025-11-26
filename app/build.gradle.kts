import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // project
    id ("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.unluckyprayers.associumhub"
    compileSdk = 36

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")

    // Dosya varsa yükle
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    // Değerleri al (eğer yoksa boş string döndürsün yoksa build patlar)
    val supabaseKey = localProperties.getProperty("supabaseKey") ?: ""
    val supabaseUrl = localProperties.getProperty("supabaseUrl") ?: ""


    defaultConfig {
        applicationId = "com.unluckyprayers.associumhub"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_KEY", "\"$supabaseKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //default dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // project dependencies
    //hilt Core
    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")    // hilt-compose viewmodel
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    //navigation
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    // icons
    implementation(libs.androidx.material.icons.extended)
    // kotlix coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    // local db - Room Kütüphanesi
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    // Kotlin Coroutines ile Room desteği için
    implementation(libs.androidx.room.ktx)
    // app/build.gradle.kts
    implementation("androidx.compose.material:material-icons-extended-android:1.6.7")
    // AppCompat kütüphanesi (Dil değiştirme gibi özellikler için gerekli)
    implementation(libs.androidx.appcompat) // Bu satırı ekleyi
    // Supabase ve login
    implementation(platform("io.github.jan-tennert.supabase:bom: 3.0.1 "))
    implementation("io.ktor:ktor-client-core:3.0.0")
    implementation("io.ktor:ktor-client-cio:3.0.0")
}