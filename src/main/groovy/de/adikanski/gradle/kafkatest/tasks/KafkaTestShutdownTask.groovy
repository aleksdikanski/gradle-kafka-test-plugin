package de.adikanski.gradle.kafkatest.tasks

import org.apache.curator.utils.CloseableUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class KafkaTestShutdownTask extends DefaultTask {

  KafkaTestShutdownTask() {
    description = "Shutdown an instance of a Kafka Test Server"
    group = "Kafka Test"
  }

  @TaskAction
  def shutdownAction() {

    try {
      project.kafkaServer.shutdown();
    } catch (Exception e) {

    }

    CloseableUtils.closeQuietly(project.zooKeeperServer)

  }
}
