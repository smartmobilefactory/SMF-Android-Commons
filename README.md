# SMF-Android-Commons

Setup gradle commons:

Modify root `build.gradle`
```
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

Modify app `build.gradle`

```
apply from: "$rootDir/android-commons/gradle-commons/android-commons.gradle"

