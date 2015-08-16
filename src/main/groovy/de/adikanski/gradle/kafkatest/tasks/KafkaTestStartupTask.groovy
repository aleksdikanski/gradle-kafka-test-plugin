package de.adikanski.gradle.kafkatest.tasks

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable

import org.apache.curator.test.InstanceSpec
import org.apache.curator.test.TestingServer;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.util.Properties;

class KafkaTestStartupTask extends DefaultTask {

  KafkaTestStartupTask() {
    description = "Starts an instance of a Kafka Test Server"
    group = "Kafka Test"
  }

  @TaskAction
  def startupAction() {

    String kafaDataDir = System.getProperty("user.dir") + "/build/tmp/zk-" + new Date().toString().replace(" ", "_");

    def zkInstanceSpec = new InstanceSpec(new File(kafaDataDir), 3181, 54317, 54318, true, 1)
    // Starts the Zookeeper instance
    def zooKeeperServer = new TestingServer(zkInstanceSpec, true);

    project.ext.set "zooKeeperServer", zooKeeperServer

    // configure and run Kafka
     String logFile = System.getProperty("user.dir") + "/build/tmp/kafka-logs-" + new Date().toString().replace(" ", "_");
     def kafkaProperties = new Properties();
     kafkaProperties.put("zookeeper.connect", "localhost:3181");
     kafkaProperties.put("broker.id", "0");
     kafkaProperties.put("hostname", "localhost");
     kafkaProperties.put("port", "9192");
     kafkaProperties.put("log.dirs", logFile);
     kafkaProperties.put("log.flush.interval.messages", String.valueOf(1));

     def kafkaConfig = new KafkaConfig(kafkaProperties);
     KafkaServerStartable kafka = new KafkaServerStartable(kafkaConfig);
     kafka.startup();

     project.ext.set "kafkaServer", kafka
  }
}
