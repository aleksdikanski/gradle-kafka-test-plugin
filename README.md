# Gradle Kafka Test Plugin

>Apache Kafka is publish-subscribe messaging rethought as a distributed commit log.

[Gradle] plugin for managing an [Apache Kafka] instance for testing purposes in your build, e.g. before and after running integration tests of microservices with an event-based interface.

It is not intended to start and deploy production ready Kafka clusters. You've been warned.

[![Build Status](https://travis-ci.org/aleksdikanski/gradle-kafka-test-plugin.svg?branch=master)](https://travis-ci.org/aleksdikanski/gradle-kafka-test-plugin)

## Version
0.1.1

## Plugin Usage
To use the plugin, include in your build script:

```groovy
buildscript {
    repositories {
        jcenter()
   }
   dependencies {
       classpath 'com.adikanski.gradle:gradle-kafka-test-plugin:0.1.0'
    }
}

apply plugin: 'de.adikanski.kafka-test'
```

The plugin will add two tasks to your build `startupKafkaServer`and `shutdownKafkaServer`.
They can be used to control the lifecycle of the server in other tasks, e.g. :

```groovy
// task integrationTest runs the integration tests
integrationTest.dependsOn 'startupKafkaTestServer'
integrationTest.finalizedBy 'shutdownKafkaTestServer'
```

The plugin uses the [Apache Curator] library to spin up an instance of a ZooKeeper `TestingServer`,
which is required for Kafka. Afterwards a `KafkaServerStartable` is used to start a Kafka instance.
The server objects are published by the `startupKafkaServer` task in the project properties 'zooKeeperServer' and 'kafkaServer'.

The default ports are currently assigned this way:
  - Zookeeper is listening on port `3181`, also using `54317` and `54318` for quorum messages, etc.
  - Kafka is listening on port `9192`

These values can be customized

```groovy
kafkaTest {
  zooKeeperPort = 3181
  electionPort = 54317
  quorumPort = 54318
  kafkaPort = 9192
}
```
## Task Types
Theremark are two custom task types to start and shutdown a Kafka instance provided by the plugin

`KafkaTestStartTask` is used to spin up an instance of Kafka. This will also start a ZooKeeper instance, as it is a required for Kafka.

```groovy
task startKafka(type: de.adikanski.gradle.kafkatest.tasks.KafkaTestStartupTask) {

}
```

`KafkaTestShutdownTask` is used to shutdown both the Kafka and the ZooKeeper instance. All resources will be deleted, so that no messages and topics are kept between different restarts of the server instances.

```groovy
task startKafka(type: de.adikanski.gradle.kafkatest.tasks.KafkaTestShutdownTask) {

}
```

## Tech Acknowledgements
Gradle Kafka Test Plugin uses a number of open source projects to work properly:

* [Gradle] - _The_ Java build tool
* [Apache Kafka] - An event bus and commit log
* [Apache Curator] - A ZooKeeper Keeper


License
----

MIT

[Gradle]:http://gradle.org
[Apache Kafka]:http://kafka.apache.org
[Apache Curator]:http://curator.apache.org
