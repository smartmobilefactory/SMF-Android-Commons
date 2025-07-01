# SMF-Android-Commons

Collection of shared Android libraries and Gradle build configurations for SMF Android projects.

## Components

- **gradle-commons**: Shared Gradle configuration files for standardized build processes
- **smfsetup**: Core setup library with Sentry initialization utilities
- **smftools**: Development tools library with debugging utilities and UI containers

## Setup

### Gradle Commons

Modify root `build.gradle`:
```gradle
buildscript {
    apply from: 'android-commons/gradle-commons/root.gradle'
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
    dependencies {
        ...
        defineClasspath(dependencyHandler)
    }
}

allprojects {
    ...
    forAllProjects(project)
}
```

Modify app `build.gradle`:
```gradle
apply from: "$rootDir/android-commons/gradle-commons/android-commons.gradle"
```

## Libraries

### smfsetup
Provides Sentry error tracking integration. Initialize with:
```kotlin
SmfSentry.setup(application, sentryDSN, buildType)
```

### smftools
Development utilities including:
- Activity hierarchy management
- Debug containers and UI tools
- RxJava utilities and extensions

## Development

Build and test commands are available via Gradle:
```bash
./gradlew build
./gradlew ktlint
./gradlew detekt
./gradlew allUnitTest
```

