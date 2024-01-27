pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "${extra["mavenRepo"]}"){
            credentials{
                username = extra["username"].toString()
                password = extra["token"].toString()
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "${extra["mavenRepo"]}"){
            credentials{
                username = extra["username"].toString()
                password = extra["token"].toString()
            }
        }
    }
}

rootProject.name = "WordSdk"
include(":app")
include(":wordsdk")
