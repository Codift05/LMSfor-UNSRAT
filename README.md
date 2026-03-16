# EduDesk LMS — UNSRAT Edition

[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-4285F4?style=for-the-badge&logo=android&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![Exposed](https://img.shields.io/badge/Exposed_ORM-7F52FF?style=for-the-badge&logo=jetbrains&logoColor=white)](https://github.com/JetBrains/Exposed)

EduDesk LMS is a native desktop Learning Management System specifically designed for the UNSRAT (Sam Ratulangi University) academic community. This application provides a modern, high-performance interface for students and educators to manage courses, assignments, and academic tracking with an offline-first approach.

The project has undergone a significant architectural migration from Golang (Fyne framework) to Kotlin (Compose Multiplatform) to deliver a superior native user experience inspired by modern learning platforms like Udemy.

## Core Features

- **Modern Dashboard**: Personalized welcome experience with quick access to ongoing learning and statistics.
- **Course Management**: Detailed overview of enrolled courses, including descriptions and progress tracking.
- **Task & Assignment Manager**: Centralized hub for managing academic deadlines with status filtering and completion tracking.
- **Offline-First Data Storage**: Persistent storage using a local SQLite database for reliable access without constant internet connectivity.
- **Native Performance**: Fully compiled native desktop application leveraging the Jetpack Compose engine.

## Technology Stack

- **Languge**: [Kotlin 1.9+](https://kotlinlang.org/)
- **UI Framework**: [Compose Multiplatform for Desktop](https://www.jetbrains.com/lp/compose-multiplatform/)
- **Database Engine**: [SQLite](https://www.sqlite.org/)
- **ORM**: [JetBrains Exposed](https://github.com/JetBrains/Exposed)
- **Dependency Management**: [Gradle](https://gradle.org/)

## Project Structure

The repository is organized as follows:

- `edudesk-kotlin/`: The current production-ready Kotlin codebase using Compose Multiplatform.
- `old-golang-backup/`: Archived source code from the previous Go/Fyne implementation for reference.
- `edudesk.db`: The local SQLite database file containing structured academic data.

## Getting Started

### Prerequisites

- [OpenJDK 17 or 21](https://adoptium.net/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Recommended IDE for Kotlin development)

### Running the Application

To run the application locally for development:

1. Clone the repository to your local machine.
2. Open the `edudesk-kotlin` folder in IntelliJ IDEA.
3. Allow Gradle to synchronize and download necessary dependencies.
4. Locate `src/main/kotlin/com/edudesk/Main.kt`.
5. Run the `main()` function.

Alternatively, use the Gradle wrapper from the terminal:

```bash
cd edudesk-kotlin
./gradlew run
```

## UI Preview

The application features a clean, professional "Udemy-style" purple and white theme optimized for clarity and focus.

---
Developed for UNSRAT Academic Excellence.
