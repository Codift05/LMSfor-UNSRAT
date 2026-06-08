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
<br/>
<img src="https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Codift05/LMSfor-UNSRAT/main/docs/diagrams/ARCHITECTURE_DIAGRAM.puml" alt="Architecture Diagram"/>
</details>

<details>
<summary><strong>2. Skema Database (Entity Relationship)</strong></summary>
<br/>
<img src="https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Codift05/LMSfor-UNSRAT/main/docs/diagrams/DATABASE_SCHEMA_DIAGRAM.puml" alt="Database Schema"/>
</details>

<details>
<summary><strong>3. Sequence Diagram: Live Monitoring & CBT Flow</strong></summary>
<br/>
<img src="https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Codift05/LMSfor-UNSRAT/main/docs/diagrams/CBT_MONITORING_FLOW.puml" alt="Live Monitoring Sequence"/>
</details>

<details>
<summary><strong>4. Use Case Diagram</strong></summary>
<br/>
<img src="https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Codift05/LMSfor-UNSRAT/main/docs/diagrams/USE_CASE_DIAGRAM_ADMIN.puml" alt="Admin Use Case"/>
<br/><br/>
<img src="https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Codift05/LMSfor-UNSRAT/main/docs/diagrams/USE_CASE_DIAGRAM_STUDENT.puml" alt="Student Use Case"/>
</details>

<details>
<summary><strong>5. Activity Diagram</strong></summary>
<br/>
<img src="https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Codift05/LMSfor-UNSRAT/main/docs/diagrams/ACTIVITY_DIAGRAM_ADMIN.puml" alt="Admin Activity"/>
<br/><br/>
<img src="https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Codift05/LMSfor-UNSRAT/main/docs/diagrams/ACTIVITY_DIAGRAM_STUDENT.puml" alt="Student Activity"/>
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
