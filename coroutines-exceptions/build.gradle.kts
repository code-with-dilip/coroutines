plugins {
    java
    kotlin("jvm")
}

group = "com.learncoroutines"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.2")

    //mockk
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.1")
    testImplementation("io.mockk:mockk:1.9")


    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}