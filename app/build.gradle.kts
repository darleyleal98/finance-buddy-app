plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.darleyleal.financebuddy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.darleyleal.financebuddy"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.camera.view)
    testImplementation(libs.hilt.android.testing)
    val nav_version = "2.8.0"
    val lifecycle_version = "2.8.5"
    val latest_release = "Beta-0.0.5"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.navigation.runtime.ktx)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.mockk)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("io.coil-kt.coil3:coil-gif:3.0.4")
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

    implementation("androidx.compose.material:material-icons-extended:$1.4.3")
    //noinspection UseTomlInstead
    implementation("androidx.biometric:biometric:1.1.0")
    //noinspection UseTomlInstead
    implementation("androidx.appcompat:appcompat:1.7.0")
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle_version")
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.navigation:navigation-compose:$nav_version")
    //noinspection UseTomlInstead,GradleDependency
    implementation("com.google.dagger:hilt-android:2.51.1")
    //noinspection UseTomlInstead,GradleDependency
    ksp("com.google.dagger:hilt-android-compiler:2.51.1")
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.core:core-splashscreen:1.0.1")
    //noinspection UseTomlInstead,GradleDependency
    implementation("androidx.biometric:biometric:1.4.0-alpha02")

    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.androidx.room.ktx)
    ksp("androidx.room:room-compiler:2.6.1")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.21")

    implementation("androidx.compose.ui:ui-text-google-fonts:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5")

    implementation("io.github.thechance101:chart:$latest_release")

    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    androidTestImplementation("androidx.test:runner:1.5.2")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // To recognize Latin script
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.1")

    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")
    implementation("androidx.camera:camera-extensions:1.3.0")

    // ML Kit Text Recognition
    implementation("com.google.mlkit:text-recognition:16.0.0")
}