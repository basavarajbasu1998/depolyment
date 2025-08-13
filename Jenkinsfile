pipeline {
    agent { label 'tenjin-pt-test' }

    parameters {
        string(name: 'GIT_REPO_URL', defaultValue: 'https://github.com/basavarajbasu1998/depolyment.git', description: 'Git repository URL')
        string(name: 'GIT_BRANCH', defaultValue: 'dev', description: 'Git branch to build')
        string(name: 'BACKEND_DIR', defaultValue: 'backend', description: 'Backend folder in repo')
        string(name: 'FRONTEND_DIR', defaultValue: 'frontend', description: 'Frontend folder in repo')
        string(name: 'DOCKER_HUB_USERNAME', defaultValue: 'princebasu543', description: 'Docker Hub username')
        password(name: 'DOCKER_HUB_PASSWORD', defaultValue: '', description: 'Docker Hub password') 
        string(name: 'DOCKER_HUB_BACKEND_REPO', defaultValue: 'backend', description: 'Docker Hub backend repo name')
        string(name: 'DOCKER_HUB_FRONTEND_REPO', defaultValue: 'frontend', description: 'Docker Hub frontend repo name')
        string(name: 'DOCKER_IMAGE_TAG', defaultValue: '1.0.0', description: 'Docker image tag/version')
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: params.GIT_BRANCH, url: params.GIT_REPO_URL
            }
        }

        stage('Build Backend') {
            steps {
                dir(params.BACKEND_DIR) {
                    script {
                        docker.image('maven:3.9.6-amazoncorretto-21').inside('-v /root/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock') {
                            sh 'mvn clean package -DskipTests'
                        }
                    }
                }
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                dir(params.BACKEND_DIR) {
                    sh "docker build -t ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_BACKEND_REPO}:${params.DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir(params.FRONTEND_DIR) {
                    script {
                        docker.image('node:18').inside('-v $HOME/.npm:/root/.npm -v /var/run/docker.sock:/var/run/docker.sock') {
                            sh 'npm install'
                            sh 'npm run build'
                        }
                    }
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                dir(params.FRONTEND_DIR) {
                    sh "docker build -t ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_FRONTEND_REPO}:${params.DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    sh "echo '${params.DOCKER_HUB_PASSWORD}' | docker login -u ${params.DOCKER_HUB_USERNAME} --password-stdin"
                    sh "docker push ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_BACKEND_REPO}:${params.DOCKER_IMAGE_TAG}"
                    sh "docker push ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_FRONTEND_REPO}:${params.DOCKER_IMAGE_TAG}"
                }
            }
        }
    }
}
