plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    kotlin ("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "vancore.five_skills"
        minSdk = 21
        targetSdk =  31
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    // Jetpack Compose
    implementation(Dependencies.composeUI)
    // Tooling support (Previews, etc.)
    implementation(Dependencies.composeUITooling)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(Dependencies.composeFoundation)
    // Material Design
    implementation(Dependencies.composeMaterialDesign)
    // Material design icons
    implementation(Dependencies.composeMaterialDesignIcons)
    implementation(Dependencies.composeMaterialDesignIconsExtended)
    // Integration with activities
    implementation(Dependencies.composeActivities)
    // Integration with ViewModels
    implementation(Dependencies.composeViewModels)
    // Integration with observables
    implementation(Dependencies.composeRuntimeLiveData)
    implementation(Dependencies.composeRuntimeRxJava)

    // Hilt DI
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")

    // Firebase
    implementation(Dependencies.firebaseBom)
    implementation(Dependencies.firebaseFirestore)
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.firebaseAuthKTX)
    implementation(Dependencies.firebaseUIAuth)
    implementation(Dependencies.firebasePlayServices)
    // No versioning needed because of BoM
    implementation(Dependencies.firebaseAuth)
}