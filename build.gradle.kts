// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
<<<<<<< HEAD

    id("com.google.gms.google-services") version "4.4.2" apply false
=======
    alias(libs.plugins.google.services) apply false
>>>>>>> 2ec6db11067b78ba01b82caffee9a259250431c6
}