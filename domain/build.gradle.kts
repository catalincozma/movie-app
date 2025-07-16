plugins {
    kotlin("kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.catalincozma.domain"
    compileSdk = Sdk.compile

    defaultConfig {
        minSdk = Sdk.min

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

    implementation(Deps.coroutines)
    implementation(Deps.hiltAndroid)
    kapt(Deps.hiltCompiler)

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.androidxJunit)
    androidTestImplementation(Deps.espressoCore)

    testImplementation(Deps.junit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.turbine)
    testImplementation(Deps.mockk)
    testImplementation(kotlin("test"))
}