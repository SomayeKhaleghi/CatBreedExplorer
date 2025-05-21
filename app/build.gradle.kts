plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.challenge.catbreedexplorer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.challenge.catbreedexplorer"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        debug {
            buildConfigField("String", "CAT_API_KEY", "\"live_GCcvNSFLuUYxjONK9WB1jTlHsQ4pCdwE4H07voDSd4IHc5ITMoUSuNf5nXtVaucp\"")

        }
        release {
            buildConfigField("String", "CAT_API_KEY", "\"live_GCcvNSFLuUYxjONK9WB1jTlHsQ4pCdwE4H07voDSd4IHc5ITMoUSuNf5nXtVaucp\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    /*compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    testImplementation(libs.mockk) // Latest as of 2025
    testImplementation(libs.kotlinx.coroutines.test) // For coroutine testing


    }*/
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
	
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.14"
	}

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:-deprecation")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation(libs.okhttp.v4100)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.coil.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.browser)


    testImplementation(libs.mockk) // Latest as of 2025
    testImplementation(libs.kotlinx.coroutines.test) // For coroutine testing

    testImplementation(libs.turbine)




}