package de.adikanski.gradle.kafkatest

import de.adikanski.gradle.kafkatest.extensions.KafkaTestExtension
import de.adikanski.gradle.kafkatest.tasks.KafkaTestStartupTask
import de.adikanski.gradle.kafkatest.tasks.KafkaTestShutdownTask

import org.gradle.api.Plugin
import org.gradle.api.Project

class KafkaTestPlugin implements Plugin<Project> {

  void apply(Project project) {
    project.task('startupKafkaTestServer', type: KafkaTestStartupTask)
    project.task('shutdownKafkaTestServer', type: KafkaTestShutdownTask)

    project.extensions.add('kafkaTest', new KafkaTestExtension())
  }
}
