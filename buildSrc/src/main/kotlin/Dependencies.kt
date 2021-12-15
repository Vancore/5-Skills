object Dependencies {

    /// Compose
    const val composeVersion = "1.1.0-beta03"
    const val composeActivitiesVersion = "1.4.0"
    const val composeViewModelsVersion = "2.4.0"

    const val composeUI = "androidx.compose.ui:ui:$composeVersion"
    // Tooling support (Previews, etc.)
    const val composeUITooling = "androidx.compose.ui:ui-tooling:$composeVersion"
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    const val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
    // Material Design
    const val composeMaterialDesign = "androidx.compose.material:material:$composeVersion"
    // Material design icons
    const val composeMaterialDesignIcons = "androidx.compose.material:material-icons-core:$composeVersion"
    const val composeMaterialDesignIconsExtended = "androidx.compose.material:material-icons-extended:$composeVersion"
    // Integration with activities
    const val composeActivities = "androidx.activity:activity-compose:$composeActivitiesVersion"
    // Integration with ViewModels
    const val composeViewModels = "androidx.lifecycle:lifecycle-viewmodel-compose:$composeViewModelsVersion"
    // Integration with observables
    const val composeRuntimeLiveData = "androidx.compose.runtime:runtime-livedata:$composeVersion"
    const val composeRuntimeRxJava = "androidx.compose.runtime:runtime-rxjava2:$composeVersion"
    // UI Tests
    const val composeUITesting = "androidx.compose.ui:ui-test-junit4:$composeVersion"

    /// Hilt DI
    const val hiltVersion = "2.38.1"

    const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"

    /// GooglePlay Services
    const val googlePlayServices = "com.google.gms:google-services:4.3.10"

    /// Firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:29.0.0"
    const val firebaseFirestore= "com.google.firebase:firebase-firestore-ktx"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseAuthKTX = "com.google.firebase:firebase-auth-ktx:21.0.1"
    const val firebaseUIAuth = "com.firebaseui:firebase-ui-auth:7.2.0"
    const val firebasePlayServices = "com.google.android.gms:play-services-auth:19.2.0"
    // No versioning needed because of BoM
    const val firebaseAuth = "com.google.firebase:firebase-auth"
}
