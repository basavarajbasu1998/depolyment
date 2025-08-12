pipeline {
    agent any

    parameters {
        string(name: 'GIT_REPO_URL', defaultValue: 'https://github.com/basavarajbasu1998/depolyment.git', description: 'Git repository URL')
        string(name: 'GIT_BRANCH', defaultValue: 'dev', description: 'Git branch to build')
        string(name: 'BACKEND_DIR', defaultValue: 'backend', description: 'Backend folder in repo')
        string(name: 'FRONTEND_DIR', defaultValue: 'frontend', description: 'Frontend folder in repo')
        string(name: 'DOCKER_HUB_USERNAME', defaultValue: 'princebasu543', description: 'Docker Hub username')
        string(name: 'DOCKER_HUB_PASSWORD', defaultValue: '', description: 'Docker Hub password')
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
                    bat 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                dir(params.BACKEND_DIR) {
                    bat "docker build -t ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_BACKEND_REPO}:${params.DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir(params.FRONTEND_DIR) {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                dir(params.FRONTEND_DIR) {
                    bat "docker build -t ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_FRONTEND_REPO}:${params.DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    // Login to Docker Hub using plain password input
                    bat "echo ${params.DOCKER_HUB_PASSWORD} | docker login -u ${params.DOCKER_HUB_USERNAME} --password-stdin"
                    bat "docker push ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_BACKEND_REPO}:${params.DOCKER_IMAGE_TAG}"
                    bat "docker push ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_FRONTEND_REPO}:${params.DOCKER_IMAGE_TAG}"
                }
            }
        }
    }
}
