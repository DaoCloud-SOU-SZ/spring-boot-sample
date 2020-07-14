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
        DOCKER_IMAGE = '192.168.101.9/daocloud/spring-boot-demo-jenkins'
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
        stage('k8s deploy') {
            steps {
                container('kubectl'){
                    sh "sed -i 's/INSTANCE_NAME/spring-boot/g' k8s/deployment-dev-jenkins.yml"                    
                    sh "sed -i 's?INSTANCE_IMAGE?${DOCKER_IMAGE}:${IMAGE_TAG}?g' k8s/deployment-dev-jenkins.yml"
                    sh "curl -sk -u ytfkxt67:4xxqsmgpmsnfnmvsjtht46see4tvh4mi63snqiwq http://192.168.101.10/dce/update-service.py?ClusterId=91822e42-89c9-a7f3-ce94-271abd966915 | python2 -u - -s spring-boot-demo-jenkins-1 -i ${DOCKER_IMAGE}:${IMAGE_TAG} -t default -c spring-boot-demo-jenkins-1"
                    sh "kubectl -n default apply -f k8s/deployment-dev-jenkins.yml"
                }
            }     
        }
    }
}