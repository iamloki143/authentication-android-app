plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)       // Hilt plugin
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
    //id("com.google.devtools.ksp")
    //id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.loki.theapp"
    compileSdk =36

    defaultConfig {
        applicationId = "com.loki.theapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions{
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.compose.runtime)
    //RoomDatabase
    val room_version ="2.8.4"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    //default
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
    //Hilt Implementation
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    //ViewModel Dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")
    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    //icons
    implementation("androidx.compose.material:material-icons-extended")
    //Image handling
    implementation("io.coil-kt:coil-compose:2.6.0")
    //Biometric
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    //lottie
    implementation("com.airbnb.android:lottie-compose:6.0.0")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    //Navigation
    implementation("androidx.navigation:navigation-compose:2.9.0")
    //OkHttp
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //icons
    implementation("androidx.compose.material:material-icons-extended")

}