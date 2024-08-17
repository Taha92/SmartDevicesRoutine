
# Smart Devices Routine App

A smart home routine management app built using Jetpack Compose. The app allows users to manage smart devices and services, create routines, and automate their daily tasks or improve productivity.

## Features
- **Dashboard with Smart Widgets:** Simulated smart devices such as smart watches, bulbs, thermostats, weather, news etc.
- **Routine Creation:** Create new routines by selecting from available devices or services and enable disable them.
- **Routine Editing:** Edit and update routines to fine-tune them for better results.
- **Room Database Integration:** Stores smart devices and routines locally.
- **Dagger Hilt for Dependency Injection:** Provides a clean architecture with modular components.
- **Unit Tests:** Ensures the app works as expected with proper testing.

## Technology Stack
- **Kotlin**
- **Jetpack Compose**
- **MVVM**
- **Room Database**
- **Dagger Hilt**
- **Kotlin Coroutines & Flow**
- **JUnit & Espresso for Testing**

---

## Prerequisites
- **Android Studio**: Download the latest version [here](https://developer.android.com/studio).
- **JDK 11+**
- **Gradle**: Used as the build system.
- **Docker**: For local deployment (optional).
- **Minikube**: For running a local Kubernetes cluster (optional).
---

## How to Build the App

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Taha92/SmartDevicesRoutine.git
   cd smart-devices-routine-app

## How to Test the App

1. **Running Tests in Android Studio:**
Right-click on the test folder under src/main and select Run 'All Tests'.

2. **Running Tests from the Command Line:**
   ```bash
   ./gradlew test

## Deploying the App Locally

1. **Build the Docker Image:**
Ensure your Docker daemon is running, and execute the following command to build the Docker image:
   ```bash
   docker build -t smart-devices-app:latest .

2. **Start Minikube:**
   ```bash
   minikube start

3. **Deploy the App to Minikube:**
Create a Kubernetes deployment and service using your app’s Docker image:
   ```bash
   kubectl create deployment smart-devices-app --image=smart-devices-app:latest
   kubectl expose deployment smart-devices-app --type=NodePort --port=8080

4. **Access the App:**
Use Minikube to access the NodePort service:
   ```bash
   minikube service smart-devices-app


