plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
}

val appVersionCode: Int
    get() = if (!project.hasProperty("ci")) {
        1 // we don't care version code when not build from CI
    } else 596700 + Env.numberOfCommits

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        applicationId = "vancore.all_in_one"
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
    implementation(Deps.androidCore)

    /// Android Layout/Design
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)
    implementation(Deps.swipeRefreshLayout)

    /// Navigation
    implementation(Deps.navigationUi)
    implementation(Deps.navigationRuntime)
    implementation(Deps.navigationFragment)

    /// Paging
    implementation(Deps.pagingRuntime)
    implementation(Deps.pagingCommon) // alternatively - without Android dependencies for testing
    implementation(Deps.pagingRxJava) // optional - RxJava support

    /// Room
    kapt (Deps.roomCompiler)
    implementation(Deps.roomRuntime)
    implementation(Deps.roomKtx) // optional - Kotlin Extensions and Coroutines support for Room
    implementation(Deps.roomTesting) // optional - Test helpers

    // ViewModel and LifeCycle
    implementation(Deps.lifecycleRuntime)
    implementation(Deps.lifecycleViewModel)
    implementation(Deps.lifecycleLiveData)
    implementation(Deps.lifecycleExtensions)

    // Retrofit
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.retrofitLoggingInterceptor)
    implementation(Deps.okHttp)

    // Hilt DI
    implementation(Deps.hiltAndroid)
    implementation(Deps.hiltCompiler)

    /// Testing
    testImplementation(Deps.jUnit)
    testImplementation(Deps.jUnitAndroidX)
    testImplementation(Deps.espressoCore)
}