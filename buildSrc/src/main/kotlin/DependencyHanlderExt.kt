import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

fun DependencyHandler.`as24Implementation`(dependencyNotation: Any): Dependency? =
  add("as24Implementation", dependencyNotation)

fun DependencyHandler.`ms24Implementation`(dependencyNotation: Any): Dependency? =
  add("ms24Implementation", dependencyNotation)

fun DependencyHandler.`alphaImplementation`(dependencyNotation: Any): Dependency? =
  add("alphaImplementation", dependencyNotation)

fun DependencyHandler.`betaImplementation`(dependencyNotation: Any): Dependency? =
  add("betaImplementation", dependencyNotation)

fun DependencyHandler.`liveImplementation`(dependencyNotation: Any): Dependency? =
  add("liveImplementation", dependencyNotation)

fun DependencyHandler.`androidTestImplementation`(dependencyNotation: Any): Dependency? =
  add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.`alphaImplementation`(
  dependencyNotation: String,
  dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "alphaImplementation", dependencyNotation, dependencyConfiguration
)
fun DependencyHandler.`androidTestImplementation`(
  dependencyNotation: String,
  dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "androidTestImplementation", dependencyNotation, dependencyConfiguration
)

fun DependencyHandler.`kaptAndroidTest`(dependencyNotation: Any): Dependency? =
  add("kaptAndroidTest", dependencyNotation)