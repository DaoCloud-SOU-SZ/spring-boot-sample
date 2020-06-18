pipeline {
    agent {
        kubernetes {
            label 'spring-boot-sample-jobs'
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    jnlp-slave: 'true'
spec:
  securityContext:
    unAsUser: 0
  nodeSelector:
    jenkins.io/builder: 'true'
  volumes:
  - name: docker
    hostPath:
      path: /var/run/docker.sock
      type: ''
  - name: localtime
    hostPath:
      path: /etc/localtime
      type: ''
  - name: repository
    hostPath:
      path: /root/.m2/repository
      type: ''
  containers:
  - name: docker
    image: 192.168.101.9/kube-system/docker:19.03.8
    resources:
      limits:
        cpu: '4'
        memory: 4Gi
      requests:
        cpu: 100m
        memory: 128Mi
    command:
    - 'cat'
    tty: true
    volumeMounts:
    - name: localtime
      mountPath: /etc/localtime
      readOnly: true
    - name: docker
      mountPath: /var/run/docker.sock
    securityContext:
      privileged: true
  - name: maven
    image: 192.168.101.9/daocloud/maven:master-1805018
    resources:
      limits:
        cpu: '4'
        memory: 4Gi
      requests:
        cpu: 100m
        memory: 128Mi
    command:
    - cat
    tty: true
    volumeMounts:
    - name: localtime
      mountPath: /etc/localtime
      readOnly: true
    - name: docker
      mountPath: /var/run/docker.sock
    - name: repository
      mountPath: /root/.m2/repository
    securityContext:
      privileged: true
'''            

        }
    }
    environment {
        DCE = credentials('dce-registry')
        REGISTRY_URL = 'https://192.168.101.9'
        DOCKER_IMAGE = '192.168.101.9/daocloud/spring-boot-sample-jenkins'
        SOURCE_URL   = 'http://192.168.50.6/demo/spring-boot-sample.git'
    }
    stages{
        // stage('git clone') {
        //     steps {
        //         git credentialsId: 'GitLab', url: "${SOURCE_URL}"
        //         script {
        //             IMAGE_TAG = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
        //             IMAGE_TAG = "master-${IMAGE_TAG}"
        //             sh "echo ${IMAGE_TAG} > tag"
        //         }
        //     }
        // }
        stage('maven compile') {
            steps {
                container('maven') {
                    bash "mvn -B clean package -Dmaven.test.skip=true"
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
                    sh "sudo docker login ${REGISTRY_URL} -u ${DCE_USR} -p ${DCE_PSW}"                    
                    sh "sudo docker build -f Dockerfile ./ -t ${DOCKER_IMAGE}:${IMAGE_TAG}"
                    sh "sudo docker push ${DOCKER_IMAGE}:${IMAGE_TAG}"
                    sh "sudo docker rmi ${DOCKER_IMAGE}:${IMAGE_TAG}"
                }
            }     
        }
    }
}