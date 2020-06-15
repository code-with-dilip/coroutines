val groovyVersion: String by project
val spockGroovyVersion: String by project

plugins {
    application
    kotlin("jvm")
    groovy
    //kotlin("kotlin-allopen")
}


dependencies {

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.3")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.1")
    testImplementation("io.mockk:mockk:1.9")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7")


    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.5.1")
    testRuntimeOnly ("org.junit.vintage:junit-vintage-engine:5.5.1")
    testImplementation("org.codehaus.groovy:groovy-all:$groovyVersion")
    testImplementation("org.spockframework:spock-core:$spockGroovyVersion")

}

val test by tasks.getting(Test::class){
    useJUnitPlatform {}
    testLogging.showStandardStreams = true
}

application {
    mainClassName = "com.learncoroutines.AirPortAppKt"
}

sourceSets {
    test {
        withConvention(GroovySourceSet::class) {
            groovy {
                srcDirs(listOf("src/test/intg", "src/test/unit"))
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
