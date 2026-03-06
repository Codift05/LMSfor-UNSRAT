# EduDesk LMS — Kotlin Compose Desktop Migration Plan

This plan outlines the migration of the EduDesk LMS desktop application to a modern Kotlin Compose Desktop architecture, drawing heavy UI inspiration from modern learning platforms like Udemy.

## Proposed Architecture & Stack

- **Language**: Kotlin
- **UI Framework**: Compose Desktop (Jetpack Compose Multiplatform)
- **Build System**: Gradle (Kotlin DSL)
- **Database**: SQLite (via Exposed or SQLDelight)
- **Architecture**: MVVM (Model-View-ViewModel)

## Folder Structure

```text
edudesk-kotlin/
├── build.gradle.kts
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   ├── com/edudesk/
│   │   │   │   ├── Main.kt             
│   │   │   │   ├── database/           // SQLite setup & Exposed tables
│   │   │   │   ├── models/             // Data models
│   │   │   │   ├── viewmodels/         // UI State management
│   │   │   │   ├── ui/
│   │   │   │   │   ├── theme/          // Material 3 Colors, Typography, Shapes
│   │   │   │   │   ├── screens/        // Home, MyCourses, Assignments, Profile
│   │   │   │   │   ├── components/     // TopBar, CourseCard, HeroBanner, SectionHeader
│   │   │   │   │   └── navigation/     // Routing state
│   │   └── resources/
│   │       └── drawable/               // Placeholder images, icons
```

## UI Design & Feature Implementation (Udemy Inspired)

The user has requested a UI that feels premium, spacious, and content-rich, similar to Udemy.

### 1. Global Layout & Navigation
- **Top App Bar**: A clean, white top navigation bar containing:
  - App Logo (left)
  - Global Search Bar (center, pill-shaped)
  - Navigation links (My Learning, Assignments)
  - User Avatar / Profile Dropdown (right)
- **Background**: Light gray/off-white background (`#F7F9FA`) to allow content cards to pop.

### 2. Main Dashboard (Home Screen)
- **Hero Banner**: A large, edge-to-edge promotional/welcome banner at the top (e.g., "Welcome back, [Name]", "Learning that gets you"). This can contain a dynamic daily quote or learning tip.
- **Section Headers**: Clean, bold typography (e.g., "What to learn next", "Your Active Courses").
- **Course Carousel/Grid**: Horizontal scrolling rows or clean grids of Course Cards.

### 3. Component Styling
- **Course Cards**: 
  - Image thumbnail at the top (can use dynamic color gradients if no image is available).
  - Bold, concise course title.
  - Subtitle for instructor/lecturer name.
  - Optional: Progress bar at the bottom of the card for active courses, or rating stars (for aesthetics).
  - Subtle drop shadow on hover to make it feel interactive.
- **Typography**: Use a clean, sans-serif font family (like Inter or Roboto) with distinct visual hierarchy (large bold headers, smaller muted text for metadata).

### 4. Core Features (MVP)
- **My Learning**: A dedicated screen showing enrolled courses in a grid, with progress bars indicating completed assignments vs total assignments.
- **Assignments**: A modernized kanban or list view for upcoming deadlines.
- **Database Refactoring**: Re-implement Go's SQLite GORM logic using Kotlin Exposed to ensure data persistence for the new UI.

## Verification Plan

### Automated Steps
- Ensure the Gradle initial sync and build passes without errors (`./gradlew build`).
- Ensure the application window launches successfully via `./gradlew run`.

### Manual Testing
1. Verify the Top Navigation Bar matches the reference layout (Search, Nav items, Avatar).
2. Verify the Hero Banner and Course Card styling adheres to the requested aesthetic (proper padding, elevation, and typography).
3. Test navigation between "Home" and "My Learning".
