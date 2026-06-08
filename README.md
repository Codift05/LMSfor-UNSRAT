<div align="center">
  <h1>IELS Secure CBT Platform</h1>
  <p><strong>Platform Ujian Berbasis Komputer Tingkat Lanjut untuk Fakultas Teknik Universitas Sam Ratulangi</strong></p>

  [![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
  [![Compose Desktop](https://img.shields.io/badge/Compose_Desktop-4285F4?style=for-the-badge&logo=android&logoColor=white)](https://www.jetbrains.com/lp/compose-multiplatform/)
  [![Ktor](https://img.shields.io/badge/Ktor-087CFA?style=for-the-badge&logo=ktor&logoColor=white)](https://ktor.io/)
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
  [![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
</div>

<br/>

IELS Secure CBT (Computer-Based Test) adalah sistem manajemen ujian modern yang dirancang secara khusus untuk memenuhi standar akademik di Fakultas Teknik Universitas Sam Ratulangi (Fatek UNSRAT). Platform ini memisahkan arsitektur *Backend* dan *Frontend* untuk menghadirkan performa tinggi, keamanan maksimal, dan pengalaman pengguna (*User Experience*) tingkat korporasi.

## Arsitektur Sistem

Proyek ini dibangun menggunakan arsitektur *Client-Server* modern:

*   **`iels-backend/`** : REST API berkinerja tinggi yang dibangun menggunakan Ktor (Netty) dan JetBrains Exposed ORM. Terhubung secara langsung dengan *database* terpusat (PostgreSQL).
*   **`iels-kotlin/`** : Aplikasi *Native Desktop* (Frontend) yang dibangun menggunakan Jetpack Compose Multiplatform. Dirancang untuk antarmuka Admin (Pengelolaan Ujian) dan Portal Mahasiswa.

## Fitur Utama

### Untuk Administrator & Instruktur
*   <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/desktop.svg" width="16" height="16"/> **Live Exam Monitoring**: Pantau aktivitas mahasiswa yang sedang melangsungkan ujian secara *real-time*.
*   <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/chart-pie.svg" width="16" height="16"/> **Visual Analytics**: Dashboard interaktif yang menyajikan grafik persentase rata-rata kelulusan secara otomatis.
*   <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/ranking-star.svg" width="16" height="16"/> **Automated Grading & Ranking**: Sistem penilaian otomatis yang langsung menyusun peringkat skor mahasiswa begitu ujian selesai.
*   <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/file-pdf.svg" width="16" height="16"/> **Corporate PDF Export**: Hasilkan rekapitulasi nilai ujian lengkap dengan kop surat institusi menggunakan *engine* iText7.

### Untuk Mahasiswa
*   <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/shield-halved.svg" width="16" height="16"/> **Secure Token Access**: Akses ujian diamankan menggunakan sistem token otentikasi.
*   <img src="https://raw.githubusercontent.com/FortAwesome/Font-Awesome/6.x/svgs/solid/expand.svg" width="16" height="16"/> **Kiosk Mode**: Antarmuka ujian mendukung mode layar penuh (*Fullscreen*) untuk meminimalisir gangguan selama pengerjaan.

## Visualisasi & Arsitektur Sistem

<details>
<summary><strong>1. Arsitektur Sistem (Client-Server)</strong></summary>

```mermaid
graph TD
    subgraph Client Tier
        UI[Native Desktop App UI]
        SM[Session Manager]
        HTTP[Ktor HTTP Client]
        PDF[PDF Engine iText7]
    end

    subgraph Application Tier
        API[Ktor REST API Endpoints]
        Auth[Auth Service]
        Exam[Exam & Monitoring Service]
        ORM[JetBrains Exposed ORM]
    end

    subgraph Data Tier
        DB[(PostgreSQL Database)]
    end

    UI --> SM
    UI --> HTTP
    HTTP -- "HTTP/JSON" --> API
    API --> Auth
    API --> Exam
    Auth --> ORM
    Exam --> ORM
    ORM -- "JDBC" --> DB
```
</details>

<details>
<summary><strong>2. Skema Database (Entity Relationship)</strong></summary>

```mermaid
erDiagram
    USERS {
        int id PK
        string name
        string nim UK
        string role
        boolean isActive
    }
    EXAMS {
        int id PK
        int instructor_id FK
        string title
        int duration_minutes
        string token UK
    }
    QUESTIONS {
        int id PK
        int exam_id FK
        string text
        string correct_answer
    }
    EXAM_SESSIONS {
        int id PK
        int user_id FK
        int exam_id FK
        string start_time
        string status
    }
    EXAM_RESULTS {
        int id PK
        int user_id FK
        int exam_id FK
        int score
        int cheat_count
    }

    USERS ||--o{ EXAMS : "creates"
    USERS ||--o{ EXAM_SESSIONS : "has"
    USERS ||--o{ EXAM_RESULTS : "achieves"
    EXAMS ||--o{ QUESTIONS : "contains"
    EXAMS ||--o{ EXAM_SESSIONS : "tracks"
    EXAMS ||--o{ EXAM_RESULTS : "records"
```
</details>

<details>
<summary><strong>3. Sequence Diagram: Live Monitoring & CBT Flow</strong></summary>

```mermaid
sequenceDiagram
    actor Admin
    participant API as Ktor Backend
    participant DB as PostgreSQL
    actor Student

    Admin->>API: GET /exams/sessions (Refresh)
    API->>DB: Query active
    DB-->>API: Return empty
    API-->>Admin: Show "No Active Exams"

    Student->>API: POST /exams/start-session {token}
    API->>DB: INSERT Session (status='active')
    DB-->>API: Created
    API-->>Student: Start CBT Engine

    Admin->>API: GET /exams/sessions
    API->>DB: Query active
    DB-->>API: Returns Student A (active)
    API-->>Admin: Display Student A
```
</details>

<details>
<summary><strong>4. Use Case Diagram</strong></summary>

```mermaid
flowchart LR
    Admin([Administrator])
    Student([Mahasiswa])
    
    subgraph IELS CBT
        Login[Login Portal]
        Dash[View Analytics / Dashboard]
        Create[Create Exam & Token]
        Monitor[Live Monitoring]
        Rank[View Rankings & Export PDF]
        CBT[Start CBT Kiosk Mode]
        Submit[Submit Exam]
    end
    
    Admin --> Login
    Admin --> Dash
    Admin --> Create
    Admin --> Monitor
    Admin --> Rank

    Student --> Login
    Student --> Dash
    Student --> CBT
    Student --> Submit
```
</details>

<details>
<summary><strong>5. Activity Diagram (CBT Engine Flow)</strong></summary>

```mermaid
flowchart TD
    Start((Start)) --> Portal[Portal Login / Register]
    Portal --> Dash[Student Dashboard]
    Dash --> Token[Input Token Ujian]
    Token --> Kiosk[Masuk Kiosk Mode]
    Kiosk --> Jawab[Jawab Soal]
    
    Jawab --> Curang{Pindah Aplikasi?}
    Curang -- Ya --> Warn[Catat Cheat Count / Peringatan]
    Curang -- Tidak --> Lanjut{Soal Habis?}
    Warn --> Lanjut
    
    Lanjut -- Belum --> Jawab
    Lanjut -- Sudah --> Submit[Kumpulkan & Hitung Skor]
    Submit --> Selesai((End))
```
</details>

## Panduan Instalasi & Menjalankan

### Persyaratan Sistem
*   Java Development Kit (JDK) 17 atau lebih baru
*   Docker & Docker Compose (Untuk Database PostgreSQL)

### 1. Menjalankan Database
Pastikan layanan Docker sudah berjalan, lalu jalankan perintah ini di root repositori untuk menghidupkan basis data:
```bash
docker-compose up -d
```

### 2. Menjalankan Backend (API Server)
Buka terminal baru dan arahkan ke direktori backend:
```bash
cd iels-backend
./gradlew run
```
*Server API Ktor akan beroperasi pada alamat `http://localhost:8081`.*

### 3. Menjalankan Frontend (Aplikasi Desktop)
Buka terminal lain dan jalankan klien Compose Desktop:
```bash
cd iels-kotlin
./gradlew run
```

## Teknologi Pendukung Terkini

*   **Bahasa Pemrograman**: Kotlin 2.0.0
*   **Antarmuka Pengguna**: Compose Desktop 1.6.11
*   **Kerangka Kerja API**: Ktor 2.3.0
*   **ORM Database**: JetBrains Exposed 0.49.0
*   **Keamanan**: jBcrypt (Password Hashing)
*   **Pemrosesan Dokumen**: iText7 Core

---
<div align="center">
  <p>Dikembangkan dengan dedikasi untuk keunggulan akademik <strong>Fakultas Teknik Universitas Sam Ratulangi</strong>.</p>
</div>
