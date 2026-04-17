pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "aurora-tv"

include(
    ":app",

    ":core:common",
    ":core:designsystem",

    ":domain",

    ":feature:home",
    ":feature:detail",
    ":feature:player",
)

