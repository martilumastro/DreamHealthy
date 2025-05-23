 plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
 }

android {
    namespace = "com.example.dreamhealthy.wear"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dreamhealthy.wear"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
    /*sourceSets{
        getByName("main")
        {
            manifest.srcFile("src/main/AndroidManifest.xml")
        }
    }*/


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    buildToolsVersion = "35.0.1"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.wear.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.android.gms:play-services-wearable:19.0.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
}