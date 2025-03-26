pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Untitled Music"
include(":app")
include(":package:feature:feature-home_screen")
include(":package:core")
include(":package:feature:feature-authentication")



include(":package:feature:feature-album_detail_screen")
include(":package:feature:feature-search-screen")
include(":package:feature:feature-artist_detail_screen")
