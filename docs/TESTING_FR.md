# Functional Requirements (FR) - Master Testing Documentation

Dokumen ini mendefinisikan persyaratan fungsional dan strategi pengujian (Testing FR) secara komprehensif untuk seluruh modul pada sistem **IELS Secure CBT**. Dokumen ini dirancang sebagai acuan utama untuk *Quality Assurance* (QA) dan pembuatan dokumentasi proyek.

---

## 1. Pendekatan Pengujian (Testing Approach)
Sistem ini menggunakan pendekatan **Modern White-Box Testing** dengan *stack* teknologi:
*   **JUnit 5 (Jupiter)**: Framework utama *test runner*.
*   **MockK**: *Mocking framework* native Kotlin untuk isolasi layer *database*.
*   **Kotest Assertions**: Pengecekan hasil uji dengan bahasa deklaratif (Fluent API).
*   **Ktor testApplication**: Pengujian *Routing API* tanpa membutuhkan *network port* sungguhan.

---

## 2. Daftar Detail Fitur yang Diuji (Test Coverage List)

Berikut adalah daftar lengkap (FR) seluruh fitur dan komponen logika yang wajib di-tes pada sistem IELS CBT.

### Modul A: Otentikasi & Manajemen Pengguna (Authentication Module)
*   **FR-TEST-001:** `POST /auth/login` - Berhasil login dan mendapatkan data *User* menggunakan kredensial (NIM/Email & Password) yang valid.
*   **FR-TEST-002:** `POST /auth/login` - Ditolak (401 Unauthorized) saat *password* salah atau akun tidak ditemukan. Verifikasi *Bcrypt checkpw* berjalan.
*   **FR-TEST-003:** `POST /auth/register` - Pendaftaran akun siswa berhasil (Role otomatis ter-set sebagai *student*).
*   **FR-TEST-004:** `POST /auth/register` - Sistem menolak pendaftaran (409 Conflict) jika NIM atau Email sudah terdaftar di *database*.
*   **FR-TEST-005:** Sistem Klien (Compose UI) - Berhasil memvalidasi Token lokal (*SessionManager*) untuk fitur *Auto-login*.

### Modul B: Pembuatan & Manajemen Ujian (Admin Exam Management)
*   **FR-TEST-006:** `POST /exams` - Admin berhasil membuat entri Ujian baru beserta deskripsi dan durasinya.
*   **FR-TEST-007:** `POST /exams` - Sistem otomatis membuat dan menyimpan *Secure Token* unik untuk ujian tersebut.
*   **FR-TEST-008:** `POST /exams` - Penyimpanan daftar soal (*Questions*) berbentuk *Multiple Choice* lengkap dengan Kunci Jawaban terkait dengan ID Ujian (*Foreign Key*).
*   **FR-TEST-009:** Klien (Compose UI) - Validasi form untuk mencegah pembuatan ujian jika kunci jawaban (*Correct Answer*) kosong.

### Modul C: Inisialisasi Sesi CBT (Student Session Flow)
*   **FR-TEST-010:** `GET /exams/token/{token}` - Siswa berhasil mengambil paket ujian menggunakan *Token* yang benar.
*   **FR-TEST-011:** **[CRITICAL]** Keamanan *Payload* - Sistem *Backend* **WAJIB** menyamarkan/mengosongkan nilai `correctAnswer` dari JSON yang dikirimkan ke klien (Mencegah kecurangan inspeksi HTTP).
*   **FR-TEST-012:** `GET /exams/token/{token}` - Menghasilkan *404 Not Found* jika *Token* salah atau sudah tidak aktif.
*   **FR-TEST-013:** `POST /exams/start-session` - Backend membuat entitas `ExamSession` baru (atau memperbarui yang lama) dengan status `"active"` sesaat setelah siswa mengklik tombol "Mulai Ujian".

### Modul D: CBT Engine & Deteksi Kecurangan (Frontend Engine)
*   **FR-TEST-014:** Klien UI CBT - Masuk ke *Kiosk/Fullscreen Mode* segera setelah paket soal berhasil dirender.
*   **FR-TEST-015:** Deteksi Kehilangan Fokus (*Focus Loss Detection*) - CBT Engine merekam aksi (*side-effect*) saat pengguna menekan tombol *minimize*, berpindah jendela/aplikasi (*Alt+Tab*).
*   **FR-TEST-016:** Sistem Peringatan - Klien memunculkan *Dialog* peringatan ketika kecurangan terdeteksi, dan menginkremen variabel state `cheatCount` secara lokal.
*   **FR-TEST-017:** Pengatur Waktu (*Countdown Timer*) - Saat *timer* menyentuh angka 00:00, ujian otomatis tertutup dan disubmit ke *backend*.

### Modul E: Pengumpulan & Penilaian (Submission & Grading)
*   **FR-TEST-018:** `POST /exams/submit` - *Backend* mampu memetakan jawaban (*Answers Map*) yang dikirim siswa dengan Kunci Jawaban di *Database*.
*   **FR-TEST-019:** Kalkulasi Skor - Logika `finalScore` bekerja dengan presisi (Rumus: `(Jumlah_Benar * 100) / Total_Soal`).
*   **FR-TEST-020:** Pencatatan Transaksi - Backend berhasil menyimpan record ke tabel `ExamResults` dengan parameter nilai akhir (`score`) dan total deteksi kecurangan (`cheatCount`).
*   **FR-TEST-021:** Sinkronisasi Status - Backend mengubah status di tabel `ExamSessions` dari `"active"` menjadi `"finished"`.

### Modul F: Monitoring & Pelaporan Admin (Admin Analytics)
*   **FR-TEST-022:** `GET /exams/sessions` - API berhasil mengambil daftar seluruh siswa yang `ExamSessions`-nya masih berstatus `"active"` untuk *Live Monitoring Table*.
*   **FR-TEST-023:** `GET /exams/stats` - Kalkulasi analitik agregat (Total Mahasiswa dan Rata-rata Kelulusan) untuk divisualisasikan pada *Bar Chart* di Klien.
*   **FR-TEST-024:** `GET /exams/results` - Sistem mengembalikan daftar nilai seluruh siswa secara terurut (*Sorted Descending* berdasarkan `score`) untuk penentuan *Ranking* atau *Leaderboard*.
*   **FR-TEST-025:** Pembuatan PDF - *iText7 Engine* mampu menerima daftar hasil (*ExamResultDetailDto*) dan memformatnya menjadi dokumen PDF yang utuh lengkap dengan Kop Surat Universitas.

---

## 3. Kriteria Kelulusan (Pass Criteria) Uji Otomatis
1.  **Backend Coverage:** *White-box test* pada `AuthRoutesTest.kt` dan `ExamRoutesTest.kt` menutupi 100% percabangan kondisi (contoh: jalur Sukses, jalur Gagal Token, kalkulasi `cheatCount`).
2.  **No Flakiness:** Pengujian menggunakan *Mocking API* (`io.mockk`) menghilangkan ketergantungan pada *network* lambat, sehingga tes beroperasi secara terisolasi tanpa *flaky errors*.
