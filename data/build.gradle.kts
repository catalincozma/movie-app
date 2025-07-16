plugins {
    kotlin("kapt")
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.catalincozma.data"
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
}

dependencies {
    implementation(project(":model"))
    implementation(project(":domain"))

    // Room
    implementation(Deps.roomRuntime)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    // Retrofit & Gson
    implementation(Deps.retrofit)
    implementation(Deps.gson)
    implementation(Deps.converterGson)

    // Coroutines
    implementation(Deps.coroutines)

    // Hilt
    implementation(Deps.hiltAndroid)
    kapt(Deps.hiltCompiler)

    // Testing
    testImplementation(Deps.junit)
    testImplementation(Deps.androidxJunit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.mockk)
    testImplementation(Deps.espressoCore)
    testImplementation(Deps.jupiter)
}