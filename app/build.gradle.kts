plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

val appVersionCode: Int
    get() = if (!project.hasProperty("ci")) {
        1 // we don't care version code when not build from CI
    } else 596700 + Env.numberOfCommits

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        applicationId = "vancore.five_skills"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = appVersionCode
        versionName = Versions.appVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/rxjava.properties")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/proguard/androidx-annotations.pro")
        exclude("META-INF/gradle/incremental.annotation.processors")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Dependencies.androidCore)

    // Android Layout/Design
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.swipeRefreshLayout)

    // Navigation
    implementation(Dependencies.navigationUi)
    implementation(Dependencies.navigationRuntime)
    implementation(Dependencies.navigationFragment)

    // Paging
    implementation(Dependencies.pagingRuntime)
    implementation(Dependencies.pagingCommon) // alternatively - without Android dependencies for testing
    implementation(Dependencies.pagingRxJava)
    implementation("androidx.legacy:legacy-support-v4:1.0.0") // optional - RxJava support

    // Room
    kapt (Dependencies.roomCompiler)
    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomKtx) // optional - Kotlin Extensions and Coroutines support for Room
    implementation(Dependencies.roomTesting) // optional - Test helpers

    // ViewModel and LifeCycle
    implementation(Dependencies.lifecycleRuntime)
    implementation(Dependencies.lifecycleViewModel)
    implementation(Dependencies.lifecycleLiveData)
    implementation(Dependencies.lifecycleExtensions)

    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGsonConverter)
    implementation(Dependencies.retrofitLoggingInterceptor)
    implementation(Dependencies.okHttp)

    // Hilt DI
    implementation(Dependencies.hiltAndroid)
    implementation(Dependencies.hiltCompiler)

    // Firebase
    implementation("com.google.firebase:firebase-bom:29.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.1")
    implementation("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation("com.google.android.gms:play-services-auth:19.2.0")
    // No versioning needed because of BoM
    implementation("com.google.firebase:firebase-auth")

    // KotlinX
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.0.5")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.0.5")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.0.5")
    // Material Design
    implementation("androidx.compose.material:material:1.0.5")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:1.0.5")
    implementation("androidx.compose.material:material-icons-extended:1.0.5")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.4.0")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:1.0.5")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.0.5")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")

    // Testing
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.jUnitAndroidX)
    testImplementation(Dependencies.espressoCore)
}