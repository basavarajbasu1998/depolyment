pipeline {
    agent any

    tools {
        // Configure these in Jenkins Global Tool Configuration
        jdk 'JDK17'  // or 'JDK11' depending on your Jenkins setup
        maven 'Maven3'
        nodejs 'NodeJS'  // Configure Node.js version
    }

    parameters {
        string(name: 'GIT_REPO_URL', defaultValue: 'https://github.com/basavarajbasu1998/depolyment.git', description: 'Git repository URL')
        string(name: 'GIT_BRANCH', defaultValue: 'dev', description: 'Git branch to build')
        string(name: 'BACKEND_DIR', defaultValue: 'backend', description: 'Backend folder in repo')
        string(name: 'FRONTEND_DIR', defaultValue: 'frontend', description: 'Frontend folder in repo')
        string(name: 'DOCKER_HUB_USERNAME', defaultValue: 'princebasu543', description: 'Docker Hub username')
        password(name: 'DOCKER_HUB_PASSWORD', defaultValue: '', description: 'Docker Hub password')  // Use password parameter
        string(name: 'DOCKER_HUB_BACKEND_REPO', defaultValue: 'backend', description: 'Docker Hub backend repo name')
        string(name: 'DOCKER_HUB_FRONTEND_REPO', defaultValue: 'frontend', description: 'Docker Hub frontend repo name')
        string(name: 'DOCKER_IMAGE_TAG', defaultValue: '1.0.0', description: 'Docker image tag/version')
    }

    environment {
        // Set Java home explicitly
        JAVA_HOME = tool('JDK17')  // Match with your JDK tool name
        PATH = "${JAVA_HOME}/bin:${PATH}"
        MAVEN_HOME = tool('Maven3')
        NODE_HOME = tool('NodeJS')
    }

    stages {
        stage('Checkout Code') {
            steps {
                cleanWs()  // Clean workspace before checkout
                git branch: params.GIT_BRANCH, url: params.GIT_REPO_URL
                
                // Verify Java version
                sh 'java -version'
                sh 'javac -version'
                sh 'mvn -version'
            }
        }

        stage('Build Backend') {
            steps {
                dir(params.BACKEND_DIR) {
                    // Check if pom.xml exists
                    script {
                        if (!fileExists('pom.xml')) {
                            error "pom.xml not found in ${params.BACKEND_DIR} directory"
                        }
                    }
                    
                    // Clean and build
                    sh 'mvn clean compile'  // Test compilation first
                    sh 'mvn package -DskipTests'
                }
            }
            post {
                always {
                    // Archive build artifacts if they exist
                    dir(params.BACKEND_DIR) {
                        archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true, fingerprint: true
                    }
                }
            }
        }

        stage('Build Backend Docker Image') {
            steps {
                dir(params.BACKEND_DIR) {
                    script {
                        if (!fileExists('Dockerfile')) {
                            error "Dockerfile not found in ${params.BACKEND_DIR} directory"
                        }
                    }
                    sh "docker build -t ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_BACKEND_REPO}:${params.DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir(params.FRONTEND_DIR) {
                    script {
                        if (!fileExists('package.json')) {
                            error "package.json not found in ${params.FRONTEND_DIR} directory"
                        }
                    }
                    
                    // Clean npm cache and install
                    sh 'npm cache clean --force'
                    sh 'npm install'
                    
                    // Check if build script exists
                    script {
                        def packageJson = readJSON file: 'package.json'
                        if (!packageJson.scripts.build) {
                            error "No 'build' script found in package.json"
                        }
                    }
                    
                    sh 'npm run build'
                }
            }
            post {
                always {
                    // Archive frontend build artifacts
                    dir(params.FRONTEND_DIR) {
                        archiveArtifacts artifacts: 'dist/**/*,build/**/*', allowEmptyArchive: true, fingerprint: true
                    }
                }
            }
        }

        stage('Build Frontend Docker Image') {
            steps {
                dir(params.FRONTEND_DIR) {
                    script {
                        if (!fileExists('Dockerfile')) {
                            error "Dockerfile not found in ${params.FRONTEND_DIR} directory"
                        }
                    }
                    sh "docker build -t ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_FRONTEND_REPO}:${params.DOCKER_IMAGE_TAG} ."
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    // Use Jenkins credentials instead of parameters for security
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', 
                                                    usernameVariable: 'DOCKER_USERNAME', 
                                                    passwordVariable: 'DOCKER_PASSWORD')]) {
                        
                        // Login to Docker Hub securely
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        
                        // Push images
                        sh "docker push ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_BACKEND_REPO}:${params.DOCKER_IMAGE_TAG}"
                        sh "docker push ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_FRONTEND_REPO}:${params.DOCKER_IMAGE_TAG}"
                        
                        // Logout for security
                        sh 'docker logout'
                    }
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker images to save space
            sh """
                docker image prune -f
                docker rmi ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_BACKEND_REPO}:${params.DOCKER_IMAGE_TAG} || true
                docker rmi ${params.DOCKER_HUB_USERNAME}/${params.DOCKER_HUB_FRONTEND_REPO}:${params.DOCKER_IMAGE_TAG} || true
            """
        }
        success {
            echo "Pipeline completed successfully!"
            // Send notification if needed
        }
        failure {
            echo "Pipeline failed!"
            // Send failure notification
        }
    }
}