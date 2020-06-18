pipeline {
    agent {
        kubernetes {
            label 'spring-boot-sample-jobs'
            yamlFile 'pipeline-job.yaml'             

        }
    }
    environment {
        DCE = credentials('dce-registry')
        REGISTRY_URL = 'https://192.168.101.9'
        DOCKER_IMAGE = '192.168.101.9/daocloud/spring-boot-sample-jenkins'
        SOURCE_URL   = 'http://192.168.50.6/demo/spring-boot-sample.git'
    }
    stages{
        stage('maven compile') {
            steps {
                container('maven') {
                    sh "mvn -B clean package -Dmaven.test.skip=true"
                }
                script {
                    IMAGE_TAG = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
                    IMAGE_TAG = "master-${IMAGE_TAG}"
                    sh "echo ${IMAGE_TAG} > tag"
                }
            }
        }
        stage('docker builder') {
            steps {
                container('docker'){
                    sh "docker login ${REGISTRY_URL} -u ${DCE_USR} -p ${DCE_PSW}"                    
                    sh "docker build -f Dockerfile ./ -t ${DOCKER_IMAGE}:${IMAGE_TAG}"
                    sh "docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"
                    sh "docker rmi ${DOCKER_IMAGE}:${IMAGE_TAG}"
                }
            }     
        }
    }
}