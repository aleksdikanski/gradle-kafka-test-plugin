package de.adikanski.gradle.kafkatest.tasks

import de.adikanski.gradle.kafkatest.extensions.KafkaTestExtension
import kafka.server.KafkaConfig
import kafka.server.KafkaServerStartable
import org.apache.curator.test.InstanceSpec
import org.apache.curator.test.TestingServer
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class KafkaTestStartupTask extends DefaultTask {

  KafkaTestStartupTask() {
    description = "Starts an instance of a Kafka Test Server"
    group = "Kafka Test"
  }

  @TaskAction
  def startupAction() {

    def date = new Date().toString().replace(" ", "_")
    String kafkaDataDir = "${project.buildDir.path}/tmp/zk-${date}"
    def kafkaTestExtension = project.extensions.getByType(KafkaTestExtension)

    def zkInstanceSpec = new InstanceSpec(
        new File(kafkaDataDir),
        kafkaTestExtension.zooKeeperPort,
        kafkaTestExtension.electionPort,
        kafkaTestExtension.quorumPort,
        true,
        1
    )
    // Starts the Zookeeper instance
    def zooKeeperServer = new TestingServer(zkInstanceSpec, true)

    project.ext.set "zooKeeperServer", zooKeeperServer

    // configure and run Kafka
    String logFile = "${project.buildDir.path}/tmp/kafka-logs-$date"
    def kafkaProperties = [
        'zookeeper.connect'          : "localhost:${kafkaTestExtension.zooKeeperPort}",
        'broker.id'                  : 0,
        'host.name'                  : 'localhost',
        'port'                       : kafkaTestExtension.kafkaPort,
        'log.dirs'                   : logFile,
        'log.flush.interval.messages': 1,
    ].collectEntries { k, v -> [k.toString(), v.toString()] } as Properties
    def kafkaConfig = new KafkaConfig(kafkaProperties)
    KafkaServerStartable kafka = new KafkaServerStartable(kafkaConfig)
    kafka.startup()

    project.ext.set "kafkaServer", kafka
  }
}
