import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.21"
    application
    `maven-publish`
}

group = "org.almgergo.tools"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // https://mvnrepository.com/artifact/org.jetbrains.dokka/dokka-gradle-plugin
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.8.20")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            // Artifact metadata
            groupId = "com.almgergo.tools"
            artifactId = "LazyTools"
            version = "0.1.0"

            // Optional: if you want to include source code or javadoc, you can add them as artifacts
//            artifact(tasks["javadocJar"])
            artifact(tasks["sourceJar"])
        }
    }

    repositories {
        maven {
            // The URL to your Maven repository
            url = uri("https://path.to.your.maven.repo")

            // If the repository requires authentication
            credentials {
                username = project.property("mavenUsername") as String?
                password = project.property("mavenPassword") as String?
            }
        }
    }
}

tasks {
    register<Jar>("sourceJar") {
        from(sourceSets["main"].allSource)
        classifier = "sources"
    }

//    register<Jar>("javadocJar") {
//        from(tasks["dokkaHtml"])
//        classifier = "javadoc"
//    }
}