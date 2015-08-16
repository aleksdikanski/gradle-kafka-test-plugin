package de.adikanski.gradle.kafkatest

import de.adikanski.gradle.kafkatest.tasks.*

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

import spock.lang.*

class KafkaTestPluginConfigurationSpec  extends Specification {

  def "plugin should add startup task" (){
    given: "a Gralde project"
    Project project = ProjectBuilder.builder().build()

    when: "the plugin is applied"
    project.pluginManager.apply 'de.adikanski.kafka-test'

    then: "a startup task is added"
    project.tasks.startupKafkaTestServer instanceof KafkaTestStartupTask
  }

  def "plugin should add shutdown task"(){
    given:
    Project project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.apply 'de.adikanski.kafka-test'

    then:
    project.tasks.shutdownKafkaTestServer instanceof KafkaTestShutdownTask
  }
}
