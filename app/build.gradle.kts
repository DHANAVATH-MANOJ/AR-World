import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

val cloudName = localProperties.getProperty("CLOUD_NAME") ?: ""
val cloudApiKey = localProperties.getProperty("CLOUD_API_KEY") ?: ""
val cloudApiSecret = localProperties.getProperty("CLOUD_API_SECRET") ?: ""

android {
    namespace = "com.manoj.arworld"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.manoj.arworld"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLOUD_NAME", "\"$cloudName\"")
        buildConfigField("String", "CLOUD_API_KEY", "\"$cloudApiKey\"")
        buildConfigField("String", "CLOUD_API_SECRET", "\"$cloudApiSecret\"")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Sceneform UX (Thomas Gorisse’s maintained fork)
    implementation(libs.sceneform.ux)

    // ARCore
    implementation("com.google.ar:core:1.44.0")
    implementation(libs.assets)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("com.cloudinary:cloudinary-android:3.0.2")



}