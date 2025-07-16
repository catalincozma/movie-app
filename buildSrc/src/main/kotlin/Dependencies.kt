object Versions {
    const val kotlin = "1.9.10"
    const val room = "2.6.1"
    const val retrofit = "2.9.0"
    const val gson = "2.10.1"
    const val coroutines = "1.7.3"
    const val hilt = "2.47"
    const val junit = "4.13.2"
    const val androidxJunit = "1.1.5"
    const val espressoCore = "3.5.1"
    const val composeBom = "2024.05.00"
    const val activityCompose = "1.9.0"
    const val navigation = "2.7.7"
    const val accompanistNavigationAnimation = "0.34.0"
    const val material3 = "1.1.0"
    const val coilCompose = "2.5.0"
    const val coroutinesTest = "1.7.3"
    const val turbine = "0.12.1"
    const val mockk = "1.13.5"
}

object Deps {
    // Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    // Retrofit & Gson
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.1.0"

    // Testing
    const val junit = "junit:junit:${Versions.junit}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

    // Compose & Activity
    const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val ui = "androidx.compose.ui:ui"
    const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"

    // Navigation
    const val navigationCompose = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanistNavigationAnimation}"

    // Lifecycle / ViewModel
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    const val lifecycleViewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"

    // Material3
    const val material3 = "androidx.compose.material3:material3:${Versions.material3}"

    // Coil
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilCompose}"

    // Testing
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val jupiter = "org.junit.jupiter:junit-jupiter:5.10.0"
}

object Sdk {
    const val min = 24
    const val compile = 35
}