# bmstu-schedule-loader [![maven-central](https://img.shields.io/maven-central/v/com.github.romanqed/bmstu-schedule-loader?color=blue)](https://repo1.maven.org/maven2/com/github/romanqed/bmstu-schedule-loader/)

A simple library for downloading and parsing the schedule of the selected department and group.

## Getting Started

To install it, you will need:

* any build of the JDK no older than version 8
* Maven/Gradle

## Installing

### Gradle dependency

```Groovy
dependencies {
    implementation group: 'net.sf.biweekly', name: 'biweekly', version:  '0.6.6'
    implementation group: 'com.github.romanqed', name: 'jutils', version: '1.2.5'
    implementation group: 'com.github.romanqed', name: 'bmstu-schedule-loader', version: 'LATEST'
}
```

### Maven dependency

```
<dependency>
    <groupId>com.github.romanqed</groupId>
    <artifactId>bmstu-schedule-loader</artifactId>
    <version>LATEST</version>
</dependency>

<dependency>
    <groupId>net.sf.biweekly</groupId>
    <artifactId>biweekly</artifactId>
    <version>0.6.6</version>
</dependency>

<dependency>
    <groupId>com.github.romanqed</groupId>
    <artifactId>jutils</artifactId>
    <version>1.2.5</version>
</dependency>
```

## Built With

* [Gradle](https://gradle.org) - Dependency management
* [biweekly](https://github.com/mangstadt/biweekly) - ICalendar parser

## Authors
* **RomanQed** - *Main work* - [RomanQed](https://github.com/RomanQed)

See also the list of [contributors](https://github.com/RomanQed/bmstu-schedule-loader/contributors)
who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
