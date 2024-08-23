pipeline {
    agent any

    tools {
            maven 'Maven 3.9.9' // 替换为你在 Jenkins 中配置的 Maven 名称
    }

    environment {
        DOCKER_IMAGE_NAME = 'xsrkt26/wangwang'
        DOCKER_IMAGE_TAG = 'hotel-latest'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from Git repository...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                script {
                    try {
                        bat 'mvn clean package'
                    } catch (Exception e) {
                        echo "Build failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                script {
                    try {
                        bat 'mvn test'
                    } catch (Exception e) {
                        echo "Tests failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    try {
                        bat '''
                            docker login -u xsrkt26 -p wzh20030304
                            docker build -t %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG% .
                            '''
                    } catch (Exception e) {
                        echo "Docker build failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo 'Docker Compose...'
                script {
                    try {
                        // 使用 Docker Compose 启动后端和数据库服务
                        bat 'docker-compose -f compose.yaml up -d'
                    } catch (Exception e) {
                          echo "Docker Compose failed: ${e.message}"
                          currentBuild.result = 'FAILURE'
                          throw e
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                script {
                    try {
                        bat """
                        docker login -u xsrkt26 -p wzh20030304
                        docker push %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG%
                        """
                    } catch (Exception e) {
                        echo "Docker push failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Deploying to Kubernetes...'
                script {
                    try {
                        withCredentials([file(credentialsId: 'kubeconfig-credentials-id', variable: 'KUBECONFIG_FILE')]) {
                            bat """
                            kubectl --kubeconfig=%KUBECONFIG_FILE% apply -f kubernetes\\deployment.yaml
                            """
                        }
                    } catch (Exception e) {
                        echo "Kubernetes deployment failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            cleanWs()
        }

        success {
            echo 'Build and deployment succeeded!'
        }

        failure {
            echo 'Build or deployment failed. Check the logs for details.'
        }
    }
}
