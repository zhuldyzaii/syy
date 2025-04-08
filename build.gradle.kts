buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Откатим плагин до стабильной версии
        classpath("com.android.tools.build:gradle:8.8.0")  // Рабочая версия, которая раньше была стабильной

        // Добавь другие зависимости, если необходимо
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}
