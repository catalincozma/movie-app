plugins {
    kotlin("kapt")
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.catalincozma.ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":model"))
    implementation(project(":domain"))

    // Coroutines
    implementation(Deps.coroutines)

    // Hilt
    implementation(Deps.hiltAndroid)
    kapt(Deps.hiltCompiler)
    implementation(Deps.hiltNavigationCompose)

    // Navigation
    implementation(Deps.navigationCompose)
    implementation(Deps.navigationUiKtx)
    implementation(Deps.navigationAnimation)

    // Lifecycle & ViewModel
    implementation(Deps.lifecycleRuntime)
    implementation(Deps.lifecycleViewmodelCompose)

    // Compose Material3
    implementation(Deps.material3)

    // Coil for image loading
    implementation(Deps.coilCompose)

    testImplementation(Deps.junit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.turbine)
    testImplementation(Deps.mockk)
    testImplementation(kotlin("test"))
}