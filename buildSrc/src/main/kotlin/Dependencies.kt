object Versions {
  // region environments: build, tool, plugin
  const val minSdkVersion = 23
  const val targetSdkVersion = 31
  const val compileSdkVersion = 31
  const val appVersion = "0.1"

  const val kotlin = "1.5.31"
  const val gradlePlugin = "7.0.0"
  const val googleServicePlugin = "4.3.10"
  // endregion

  // region app dependencies
  const val paging = "3.0.0-beta02"
  const val pagingLegacySupport = "1.0.0"
  const val room = "2.3.0"
  const val liveCycle = "2.3.1"
  const val liveCycleExtensions = "2.2.0"
  const val hilt = "2.38.1"
  const val retrofitGson = "2.9.0"
  const val retrofitLoggingInterceptor = "3.14.9"
  const val okHttp = "4.7.2"
  const val navigation = "2.2.2"
  const val androidCoreKtx = "1.3.2"
  const val appCompat = "1.2.0"
  const val material = "1.3.0"
  const val constraintLayout = "2.0.4"
  const val swipeRefreshlayout = "1.1.0"
  const val compose = "1.0.5"
  const val composeKotlinCompiler = "1.0.5"
  const val composeViewModels = "2.4.0"
  const val composeActivities = "1.4.0"
  // endregion

  // region test dependencies
  const val espressoCore = "3.3.0"
  const val jUnit = "4.13.2"
  const val jUnitAndroidX = "1.1.2"
  // endregion
}


object Plugins {
  const val buildGradle = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
  const val googleService = "com.google.gms:google-services:${Versions.googleServicePlugin}"
  const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
  const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
  const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Dependencies {

  const val androidCore = "androidx.core:core-ktx:${Versions.androidCoreKtx}"

  /// Android Layout/Design
  const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
  const val material = "com.google.android.material:material:${Versions.material}"
  const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
  const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshlayout}"

  /// Navigation
  const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
  const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
  const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"

  /// Paging
  const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
  const val pagingCommon = "androidx.paging:paging-common-ktx:${Versions.paging}" // alternatively - without Android dependencies for testing
  const val pagingRxJava = "androidx.paging:paging-rxjava2-ktx:${Versions.paging}" // optional - RxJava support
  const val pagingLegacySupport = "androidx.legacy:legacy-support-v4:${Versions.pagingLegacySupport}" // optional - RxJava support

  /// Room
  const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
  const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
  const val roomKtx = "androidx.room:room-ktx:${Versions.room}" // optional - Kotlin Extensions and Coroutines support for Room
  const val roomTesting = "androidx.room:room-testing:${Versions.room}" // optional - Test helpers

  /// ViewModel and LifeCycle
  const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.liveCycle}"
  const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.liveCycle}"
  const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveCycle}"
  const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.liveCycleExtensions}"

  /// Retrofit
  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitGson}"
  const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
  const val retrofitLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitLoggingInterceptor}"
  const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"

  /// Hilt DI
  const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
  const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
  
  /// Firebase
  const val firebaseBom = "com.google.firebase:firebase-bom:29.0.0"
  const val firebaseFirestore= "com.google.firebase:firebase-firestore-ktx"
  const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
  const val firebaseAuthKTX = "com.google.firebase:firebase-auth-ktx:21.0.1"
  const val firebaseUIAuth = "com.firebaseui:firebase-ui-auth:7.2.0"
  const val firebasePlayServices = "com.google.android.gms:play-services-auth:19.2.0"
  // No versioning needed because of BoM
  const val firebaseAuth = "com.google.firebase:firebase-auth"
  
  /// KotlinX
  const val kotlinXCoroutinesPlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1"
  const val kotlinXStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

  /// Compose
  const val composeUI = "androidx.compose.ui:ui:${Versions.compose}"
  // Tooling support (Previews, etc.)
  const val composeUITooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
  // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
  const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
  // Material Design
  const val composeMaterialDesign = "androidx.compose.material:material:${Versions.compose}"
  // Material design icons
  const val composeMaterialDesignIcons = "androidx.compose.material:material-icons-core:${Versions.compose}"
  const val composeMaterialDesignIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
  // Integration with activities
  const val composeActivities = "androidx.activity:activity-compose:${Versions.composeActivities}"
  // Integration with ViewModels
  const val composeViewModels = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModels}"
  // Integration with observables
  const val composeRuntimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
  const val composeRuntimeRxJava = "androidx.compose.runtime:runtime-rxjava2:${Versions.compose}"
  // UI Tests
  const val composeUITesting = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"

  /// Testing
  const val jUnit = "junit:junit:${Versions.jUnit}"
  const val jUnitAndroidX = "androidx.test.ext:junit:${Versions.jUnitAndroidX}"
  const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}
