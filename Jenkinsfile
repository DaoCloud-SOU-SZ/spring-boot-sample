pipeline {
  agent none
  stages {
    stage('maven') {
      agent {
        docker {
          image '192.168.101.9/daocloud/maven:master-1805018'
        }

      }
      steps {
        sh 'mvn -B clean package -Dmaven.test.skip=true'
      }
    }

    stage('docker build') {
      agent {
        docker {
          image '192.168.101.9/kube-system/docker:19.03.8'
        }

      }
      steps {
        sh 'docker build -f Dockerfile ./ 192.168.101.9/daocloud/spring-boot-sample:0.0.1'
      }
    }

  }
}