// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        val naVersion = "2.7.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$naVersion")
    }
}

plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}