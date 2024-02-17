import java.io.FileInputStream
import java.util.Properties
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("maven-publish")
}

android {
    namespace = "com.koai.wordsdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        aarMetadata {
            minCompileSdk = 29
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("com.koai:base:1.0.4")
}


afterEvaluate{
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.koai"
                artifactId = "wordsdk"
                version = "1.0.2"

                afterEvaluate {
                    from(components["release"])
                }
            }
        }
        repositories{
            val propsFile = rootProject.file("github.properties")
            val props = Properties()
            props.load(FileInputStream(propsFile))
            maven(url = "${props["mavenRepo"]}"){
                credentials{
                    username = props["username"].toString()
                    password = props["token"].toString()
                }
            }
        }
    }
}

tasks.register("localBuild"){
    dependsOn("assembleRelease")
}

tasks.register("createReleaseTag"){
    doLast{
        val tagName = "v" + android.defaultConfig.versionName
        try {
            exec{
                commandLine("git", "tag", "-a", tagName, "-m", "Release tag $tagName")
            }

            exec{
                commandLine("git", "push", "origin", tagName)
            }
        }catch (e: Exception){
            println(e.toString())
        }
    }
}

tasks.register("cleanBuildPublish"){
    dependsOn("clean")
    dependsOn("localBuild")
    dependsOn("publishReleasePublicationToMavenRepository")
    val assembleReleaseTask = getTasksByName("localBuild", false).stream().findFirst().orElse(null)
    if (assembleReleaseTask!=null){
        assembleReleaseTask.mustRunAfter("clean")
        assembleReleaseTask.finalizedBy("publishReleasePublicationToMavenRepository")
    }
}
