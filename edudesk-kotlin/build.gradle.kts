plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("org.jetbrains.compose") version "1.6.11"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

group = "com.edudesk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)

    // Bcrypt for password hashing
    implementation("org.mindrot:jbcrypt:0.4")

    // Ktor Client for HTTP requests (Supabase REST API)
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-okhttp:2.3.0")
    implementation("io.ktor:ktor-client-serialization:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
    
    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    
    // Environment variables
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    
    // JSON handling
    implementation("com.google.code.gson:gson:2.10.1")
}

compose.desktop {
    application {
        mainClass = "com.edudesk.MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg
            )
            packageName = "EduDesk LMS"
            packageVersion = "1.0.0"
        }
    }
}
