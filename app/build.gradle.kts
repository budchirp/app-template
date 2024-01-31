plugins {
  alias(libs.plugins.android)
  alias(libs.plugins.kotlin)
  kotlin("kapt")
  alias(libs.plugins.hilt)
  alias(libs.plugins.aboutlibraries)
}

android {
  namespace = "me.budchirp.app"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "me.budchirp.app"

    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.compileSdk.get().toInt()

    versionCode = 1
    versionName = "1.0"

    vectorDrawables { useSupportLibrary = true }
  }

  signingConfigs {
    named("debug") {
      storeFile = rootProject.file("app-key.keystore")
      storePassword = "android"
      keyAlias = "android"
      keyPassword = "android"
    }
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      isShrinkResources = true
      isDebuggable = false

      signingConfig = signingConfigs.getByName("debug")

      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    getByName("debug") {
      isMinifyEnabled = false
      isShrinkResources = false
      isDebuggable = true

      signingConfig = signingConfigs.getByName("debug")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions { jvmTarget = "17" }

  buildFeatures { compose = true }

  androidResources { generateLocaleConfig = true }

  composeOptions { kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get() }

  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
  implementation(libs.bundles.core)

  implementation(platform(libs.compose.bom))
  implementation(libs.bundles.compose)

  implementation(libs.compose.navigation)

  implementation(libs.bundles.lifecycle)

  implementation(libs.datastore)

  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)

  implementation(libs.aboutlibraries)
}

kapt { correctErrorTypes = true }
