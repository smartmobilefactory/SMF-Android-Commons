# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

SMF-Android-Commons is a collection of shared Android libraries and Gradle build configurations used across SMF Android projects. It provides common functionality for setup, development tools, and standardized build processes.

## Architecture

The project is structured into three main components:

1. **gradle-commons/**: Shared Gradle configuration files that standardize build processes across projects
2. **smfsetup/**: Core setup library containing Sentry initialization utilities  
3. **smftools/**: Development tools library with debugging utilities, activity hierarchy management, and UI containers

### Key Libraries and Integrations

- **Sentry**: Error tracking (v6.23.0) - configured in `smfsetup/SmfSentry.kt` (DSN provided at runtime)
- **Timber**: Logging framework used throughout, integrated with Sentry breadcrumbs
- **RxJava2**: Reactive programming support in smftools

## Development Commands

### Building
```bash
./gradlew build
```

### Code Quality
```bash
# Run ktlint code style check
./gradlew ktlint

# Auto-fix ktlint style issues
./gradlew ktlintFormat

# Run detekt static analysis
./gradlew detekt

# Run all unit tests
./gradlew allUnitTest

# Generate license reports
./gradlew allLicenseReport
```

### Security
```bash
# Run OWASP dependency check
./gradlew dependencyCheck
```

## Gradle Configuration System

The project uses a centralized Gradle configuration approach:

- **root.gradle**: Defines shared build tasks and applies common plugins
- **android-commons.gradle**: Common Android library configuration with lint and security checks
- **ktlint.gradle**: Kotlin code style checking (v0.40.0)
- **detekt.gradle**: Static code analysis (v1.6.0)

Projects using this commons library should:
1. Apply `root.gradle` in their root `build.gradle`
2. Apply `android-commons.gradle` in module `build.gradle` files
3. Code quality tools (ktlint, detekt) are automatically applied to all modules except `smfsetup`

## Build Variants

**smftools** supports multiple build variants:
- Build types: debug, alpha, beta, release
- Product flavors: internal, production
- Internal builds include additional debugging tools (Telescope, Madge, Scalpel)

## Testing

Unit tests are configured to run with the `allUnitTest` task which automatically detects and runs test tasks matching the pattern `test.*UnitTest`.

## Target Specifications

- **Compile SDK**: 34
- **Java Version**: 17
- **Min SDK**: 15 (smftools), 21 (smfsetup)