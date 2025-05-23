plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.dreamhealthy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dreamhealthy"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding =  true
        compose = true
    }
    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}


dependencies {


    implementation("com.google.android.material:material:1.12.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    //for navbar
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    //for melody
    implementation ("androidx.media3:media3-exoplayer:1.7.1")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("androidx.viewpager2:viewpager2:1.1.0")

    implementation("com.google.android.gms:play-services-wearable:19.0.0")

    implementation("androidx.wear.compose:compose-material:1.4.1")
    implementation("androidx.wear.compose:compose-foundation:1.4.1")


    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    /*

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

        val composeBom = platform("androidx.compose:compose-bom:2025.05.00")
        implementation(composeBom)
        androidTestImplementation(composeBom)

        implementation("androidx.compose.foundation:foundation")
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-tooling-preview")
        debugImplementation("androidx.compose.ui:ui-tooling")


        implementation("androidx.compose.material:material-icons-core")
        implementation("androidx.compose.material:material-icons-extended")

        // Health Services API
        implementation("androidx.health:health-services-client:1.1.0-alpha05")

       // Permessions
        implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    */

}