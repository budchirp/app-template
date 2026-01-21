plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "dev.cankolay.app.android.presentation"
    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
    }

    sourceSets.all {
        kotlin.srcDir(srcDir = "src/$name/kotlin")
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }

        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.bundles.hilt)
    implementation(libs.appcompat)
    implementation(libs.material3)
    ksp(libs.hilt.compiler)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.bundles.navigation)
    implementation(libs.bundles.adaptive)

    implementation(libs.bundles.lifecycle)

    implementation(libs.bundles.coil)
    implementation(libs.squircle.shape)
    implementation(libs.materialKolor)
}