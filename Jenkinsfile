pipeline {
    agent any

    tools {
        maven 'Apache Maven'
        jdk 'JDK'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/UrvashiB20/Reqres_API_Automation.git'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh 'mvn target/allure-results'
            }
        }

        stage('Allure Report') {
            steps {
                allure ([
                    results: [[path:'target/allure-results']],
                    reportBuildPolicy: 'ALWAYS'
                ])
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}