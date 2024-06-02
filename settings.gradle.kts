import java.util.Properties

pluginManagement {
    repositories {
        includeBuild("build-logic")
        // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
var project : Properties?=null
try {
    project=  File(rootDir, "local.properties").inputStream().use {
        Properties().apply { load(it) }

    }
   // println("user ${ project?.getProperty("gpr.password")}")


} catch (e: Exception) {

    e.printStackTrace()
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        //  mavenLocal()
        maven {
            url = uri("https://maven.pkg.github.com/mshdabiola/series")
            credentials {
                username = project?.getProperty("gpr.userid")  ?: System.getenv("USERID")
                password = project?.getProperty("gpr.password") ?: System.getenv("PASSWORD")
            }
        }
        mavenCentral()

        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
        maven(url = "https://androidx.dev/storage/compose-compiler/repository/")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}
rootProject.name = "SeriesCbt"
//include(":app")
//include(":app:baselineprofile")
//include(":modules:database")
include(":modules:designsystem")
include(":modules:model")
include(":modules:network")
include(":modules:data")
include(":modules:domain")
include(":modules:testing")
include(":modules:ui")
//include(":modules:mvvn")
include(":modules:analytics")
include(":modules:datastore")

//include(":modules:app")
//include(":desktop")
//include(":modules:setting")

include(":benchmarks")


include(":cbtApp")
//include(":shared")


include(":features:main")
include(":features:profile")
include(":features:stat")
include(":features:question")
include(":features:finish")

include(":features:setting")


