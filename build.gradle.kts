// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.buildGradle)
        classpath(Plugins.googleService)
        classpath(Plugins.navigationSafeArgs)
        classpath(Plugins.hilt)
        classpath(Plugins.kotlin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts.kts files
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}