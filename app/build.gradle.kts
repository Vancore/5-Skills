plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("kotlin-android-extensions")
}

val appVersionCode: Int
    get() = if (!project.hasProperty("ci")) {
        1 // we don't care version code when not build from CI
    } else 596700 + Env.numberOfCommits

android {

    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        applicationId = "vancore.five_skills"
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
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

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeKotlinCompiler
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
    implementation(Dependencies.pagingLegacySupport) // optional - RxJava support

    // Room
    annotationProcessor(Dependencies.roomCompiler)
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
    kapt(Dependencies.hiltCompiler)

    // Firebase
    implementation(Dependencies.firebaseBom)
    implementation(Dependencies.firebaseFirestore)
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.firebaseAuthKTX)
    implementation(Dependencies.firebaseUIAuth)
    implementation(Dependencies.firebasePlayServices)
    // No versioning needed because of BoM
    implementation(Dependencies.firebaseAuth)

    // KotlinX
    implementation(Dependencies.kotlinXCoroutinesPlayServices)
    implementation(Dependencies.kotlinXStdLib)

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

    // UI Tests
    androidTestImplementation(Dependencies.composeUITesting)

    // Testing
    testImplementation(Dependencies.jUnit)
    testImplementation(Dependencies.jUnitAndroidX)
    testImplementation(Dependencies.espressoCore)
}