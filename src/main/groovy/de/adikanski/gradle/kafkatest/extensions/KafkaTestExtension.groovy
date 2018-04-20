package de.adikanski.gradle.kafkatest.extensions

/**
 * Configuration for Kafka Test Plugin
 *
 * @author Rahul Somasunderam
 */
class KafkaTestExtension {
  int zooKeeperPort = 3181
  int electionPort = 54317
  int quorumPort = 54318
  int kafkaPort = 9192

}
