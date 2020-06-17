pipeline {
  agent none
  stages {
    stage('maven') {
      steps {
        sh 'mvn -B clean package -Dmaven.test.skip=true'
      }
    }

    stage('docker build') {
      steps {
        sh 'docker build -f Dockerfile ./ 192.168.101.9/daocloud/spring-boot-sample:0.0.1'
      }
    }

  }
}