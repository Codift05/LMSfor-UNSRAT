package com.edudesk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.CompareArrows
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edudesk.ui.components.TopNavigationBar
import com.edudesk.ui.navigation.NavController
import com.edudesk.ui.navigation.Screen

// ── Design tokens ──────────────────────────────────────────────────────────
private val Blue    = Color(0xFF496E96)
private val Dark    = Color(0xFF1E293B)
private val Muted   = Color(0xFF64748B)
private val Border  = Color(0xFFDDE3EA)
private val BgPage  = Color(0xFFE2E8EF)
private val BgCard  = Color.White
private val GreenOk = Color(0xFF16A34A)

// ── Data model ─────────────────────────────────────────────────────────────
data class LessonContent(
    val title: String,
    val subtitle: String,
    val duration: String,
    val sections: List<LessonSection>
)

data class LessonSection(
    val heading: String,
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val body: String,
    val codeBlock: String? = null,
    val bullets: List<String> = emptyList()
)

// ── Full lesson data for all 8 modules ────────────────────────────────────
val LESSONS: List<LessonContent> = listOf(

    // 1 ─ Pengenalan PBO
    LessonContent(
        title = "Pengenalan Pemrograman Berorientasi Objek",
        subtitle = "Memahami paradigma OOP dan mengapa ia menjadi standar industri modern.",
        duration = "60 menit",
        sections = listOf(
            LessonSection(
                heading = "Apa itu Pemrograman Berorientasi Objek?",
                icon = Icons.Default.Info, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Pemrograman Berorientasi Objek (OOP) adalah paradigma pemrograman yang mengorganisir kode ke dalam unit-unit yang disebut Objek. Setiap objek merepresentasikan entitas nyata dan memiliki atribut (data) serta perilaku (fungsi).\n\nJava adalah bahasa OOP yang paling populer di dunia enterprise dan akademik. Hampir seluruh framework besar seperti Spring, Android SDK, dan Hadoop ditulis dengan Java.",
                bullets = listOf(
                    "Kode lebih terstruktur dan mudah dipahami",
                    "Mendukung reusabilitas melalui pewarisan",
                    "Memudahkan kolaborasi tim besar",
                    "Digunakan oleh 90% perusahaan Fortune 500"
                )
            ),
            LessonSection(
                heading = "4 Pilar Utama OOP",
                icon = Icons.Default.ViewModule, iconBg = Color(0xFFF0F4FF), iconTint = Color(0xFF4B6FD4),
                body = "OOP berdiri di atas empat konsep fundamental yang akan kita pelajari sepanjang kursus ini:",
                bullets = listOf(
                    "Encapsulation — Menyembunyikan detail implementasi, hanya expose interface yang diperlukan",
                    "Inheritance — Kelas anak mewarisi properti & method dari kelas induk",
                    "Polymorphism — Satu interface, banyak implementasi berbeda",
                    "Abstraction — Menyederhanakan kompleksitas dengan memodelkan entitas dunia nyata"
                )
            ),
            LessonSection(
                heading = "Perbandingan: Procedural vs OOP",
                icon = Icons.AutoMirrored.Filled.CompareArrows, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Pada pemrograman prosedural, program adalah serangkaian perintah berurutan. Pada OOP, program adalah interaksi antar objek:",
                codeBlock = """// Procedural
fun hitungGaji(jam: Int, tarif: Double): Double {
    return jam * tarif
}

// OOP
class Karyawan(val nama: String, val tarif: Double) {
    fun hitungGaji(jam: Int) = jam * tarif
    fun info() = "Nama: ${'$'}nama"
}

val k = Karyawan("Budi", 50000.0)
println(k.hitungGaji(8))"""
            ),
            LessonSection(
                heading = "Latihan & Evaluasi",
                icon = Icons.Default.Quiz, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Setelah mempelajari materi ini, jawab pertanyaan berikut untuk menguji pemahaman kamu:",
                bullets = listOf(
                    "Apa perbedaan utama antara paradigma prosedural dan OOP?",
                    "Sebutkan dan jelaskan 4 pilar OOP dengan contoh kehidupan nyata",
                    "Mengapa encapsulation penting dalam pengembangan software skala besar?",
                    "Buat analogi OOP sederhana menggunakan benda-benda di sekitarmu"
                )
            )
        )
    ),

    // 2 ─ Instalasi Tools
    LessonContent(
        title = "Instalasi Tools dan Basic Syntax Java",
        subtitle = "Menyiapkan lingkungan pengembangan dan memahami struktur program Java pertamamu.",
        duration = "75 menit",
        sections = listOf(
            LessonSection(
                heading = "Instalasi JDK dan IDE",
                icon = Icons.Default.Download, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Sebelum menulis kode Java, kamu membutuhkan dua hal utama:",
                bullets = listOf(
                    "JDK (Java Development Kit) — versi 17 LTS direkomendasikan, unduh dari adoptium.net",
                    "IDE — IntelliJ IDEA Community (gratis) atau VS Code dengan extension Java",
                    "Verifikasi instalasi dengan perintah: java -version dan javac -version di terminal",
                    "Pastikan JAVA_HOME sudah terset di environment variable sistem"
                )
            ),
            LessonSection(
                heading = "Hello World & Struktur Program Java",
                icon = Icons.Default.Code, iconBg = Color(0xFFF0F4FF), iconTint = Color(0xFF4B6FD4),
                body = "Setiap program Java minimal terdiri dari satu class dengan method main sebagai entry point:",
                codeBlock = """public class HelloWorld {
    // Entry point program Java
    public static void main(String[] args) {
        System.out.println("Halo, Dunia!");
        System.out.println("Belajar Java itu menyenangkan!");
    }
}"""
            ),
            LessonSection(
                heading = "Tipe Data & Variabel",
                icon = Icons.Default.DataObject, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Java adalah bahasa strongly-typed — setiap variabel harus dideklarasikan dengan tipe:",
                codeBlock = """int umur = 20;
double ipk = 3.85;
boolean aktif = true;
char grade = 'A';
String nama = "Mahasiswa";

// Type casting
double nilai = (double) umur / 3;

// String concatenation
System.out.println("Nama: " + nama + ", IPK: " + ipk);"""
            ),
            LessonSection(
                heading = "Operator & Kontrol Alur",
                icon = Icons.Default.AccountTree, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Java memiliki operator aritmatika, logika, dan perbandingan yang mirip dengan bahasa C:",
                codeBlock = """// Percabangan
int nilai = 85;
if (nilai >= 90) {
    System.out.println("Grade A");
} else if (nilai >= 80) {
    System.out.println("Grade B");
} else {
    System.out.println("Grade C");
}

// Perulangan
for (int i = 1; i <= 5; i++) {
    System.out.println("Iterasi ke-" + i);
}"""
            )
        )
    ),

    // 3 ─ Class dan Object
    LessonContent(
        title = "Class dan Object dalam Java",
        subtitle = "Membangun blueprint dan instansiasi objek — inti dari pemrograman OOP.",
        duration = "90 menit",
        sections = listOf(
            LessonSection(
                heading = "Mendefinisikan Class",
                icon = Icons.Default.Class, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Class adalah template atau blueprint untuk membuat objek. Di dalamnya terdapat field (atribut) dan method (perilaku):",
                codeBlock = """public class Mahasiswa {
    // Field / Atribut
    private String nama;
    private String nim;
    private double ipk;

    // Constructor — dipanggil saat objek dibuat
    public Mahasiswa(String nama, String nim, double ipk) {
        this.nama = nama;
        this.nim  = nim;
        this.ipk  = ipk;
    }

    // Method
    public String getInfo() {
        return nim + " - " + nama + " (IPK: " + ipk + ")";
    }
}"""
            ),
            LessonSection(
                heading = "Membuat dan Menggunakan Objek",
                icon = Icons.Default.Widgets, iconBg = Color(0xFFF0F4FF), iconTint = Color(0xFF4B6FD4),
                body = "Objek dibuat dengan keyword `new` yang memanggil constructor class:",
                codeBlock = """public class Main {
    public static void main(String[] args) {
        // Instantiasi objek
        Mahasiswa mhs1 = new Mahasiswa("Budi Santoso", "19021001", 3.75);
        Mahasiswa mhs2 = new Mahasiswa("Sari Dewi",   "19021002", 3.90);

        // Memanggil method
        System.out.println(mhs1.getInfo());
        System.out.println(mhs2.getInfo());
    }
}"""
            ),
            LessonSection(
                heading = "Encapsulation dengan Getter & Setter",
                icon = Icons.Default.Lock, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Encapsulation melindungi data dengan menjadikan field `private` dan menyediakan akses melalui method public:",
                codeBlock = """public class RekeningBank {
    private double saldo;  // private — tidak bisa diakses langsung

    public double getSaldo() { return saldo; }

    public void setor(double jumlah) {
        if (jumlah > 0) saldo += jumlah;
    }

    public boolean tarik(double jumlah) {
        if (jumlah > saldo) return false;
        saldo -= jumlah;
        return true;
    }
}"""
            ),
            LessonSection(
                heading = "Static vs Instance Members",
                icon = Icons.Default.Compare, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Member static dimiliki oleh class (bukan objek), sedangkan instance member milik tiap objek:",
                codeBlock = """public class Counter {
    private static int totalObjek = 0; // milik class
    private int id;                    // milik tiap objek

    public Counter() {
        totalObjek++;
        id = totalObjek;
    }

    public static int getTotal() { return totalObjek; }
    public int getId()           { return id; }
}
// Counter.getTotal() → akses tanpa buat objek"""
            )
        )
    ),

    // 4 ─ Inheritance
    LessonContent(
        title = "Inheritance (Pewarisan)",
        subtitle = "Mewarisi dan memperluas fungsionalitas class untuk membangun hierarki yang bersih.",
        duration = "90 menit",
        sections = listOf(
            LessonSection(
                heading = "Konsep Dasar Inheritance",
                icon = Icons.Default.AccountTree, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Inheritance memungkinkan sebuah class (subclass) mewarisi field dan method dari class lain (superclass). Ini mengurangi duplikasi kode secara signifikan.\n\nGunakan keyword `extends` untuk mewarisi class:",
                codeBlock = """// Superclass
public class Kendaraan {
    protected String merk;
    protected int tahun;

    public Kendaraan(String merk, int tahun) {
        this.merk  = merk;
        this.tahun = tahun;
    }

    public void info() {
        System.out.println(merk + " (" + tahun + ")");
    }
}

// Subclass mewarisi Kendaraan
public class Mobil extends Kendaraan {
    private int jumlahPintu;

    public Mobil(String merk, int tahun, int pintu) {
        super(merk, tahun); // Memanggil constructor induk
        this.jumlahPintu = pintu;
    }
}"""
            ),
            LessonSection(
                heading = "Method Overriding",
                icon = Icons.Default.Edit, iconBg = Color(0xFFF0F4FF), iconTint = Color(0xFF4B6FD4),
                body = "Subclass dapat memberikan implementasi baru untuk method yang sudah ada di superclass menggunakan `@Override`:",
                codeBlock = """public class Mobil extends Kendaraan {
    @Override
    public void info() {
        super.info(); // Panggil versi induk
        System.out.println("  Pintu: " + jumlahPintu);
    }
}

public class Motor extends Kendaraan {
    @Override
    public void info() {
        System.out.println("[Motor] " + merk);
    }
}"""
            ),
            LessonSection(
                heading = "Hierarki Akses: super & instanceof",
                icon = Icons.Default.Shield, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Keyword `super` mengakses anggota superclass, `instanceof` memeriksa tipe objek saat runtime:",
                codeBlock = """Kendaraan k = new Mobil("Toyota", 2022, 4);

if (k instanceof Mobil m) {
    // Pattern matching (Java 16+)
    System.out.println("Ini mobil dengan " + m.jumlahPintu + " pintu");
}

// Downcasting
Mobil m2 = (Mobil) k;"""
            ),
            LessonSection(
                heading = "Final Class & Method",
                icon = Icons.Default.Block, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Gunakan `final` untuk mencegah class diwarisi atau method di-override:",
                bullets = listOf(
                    "final class — class tidak bisa diwarisi (contoh: java.lang.String)",
                    "final method — method tidak bisa di-override oleh subclass",
                    "Gunakan final jika perilaku class harus tetap konsisten",
                    "Semua class di Java secara implisit mewarisi java.lang.Object"
                )
            )
        )
    ),

    // 5 ─ Polymorphism
    LessonContent(
        title = "Polymorphism (Polimorfisme)",
        subtitle = "Satu interface, banyak bentuk — kunci fleksibilitas desain OOP.",
        duration = "80 menit",
        sections = listOf(
            LessonSection(
                heading = "Runtime Polymorphism",
                icon = Icons.Default.Transform, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Polymorphism runtime terjadi saat method yang dipanggil ditentukan berdasarkan tipe objek aktual (bukan tipe referensi) saat program berjalan:",
                codeBlock = """public class Hewan {
    public void suara() { System.out.println("..."); }
}
public class Anjing extends Hewan {
    @Override public void suara() { System.out.println("Guk!"); }
}
public class Kucing extends Hewan {
    @Override public void suara() { System.out.println("Meow!"); }
}

// Polimorfisme dalam aksi
Hewan[] zoo = { new Anjing(), new Kucing(), new Anjing() };
for (Hewan h : zoo) {
    h.suara(); // Outputnya berbeda tiap iterasi!
}"""
            ),
            LessonSection(
                heading = "Compile-time Polymorphism (Overloading)",
                icon = Icons.Default.LayersClear, iconBg = Color(0xFFF0F4FF), iconTint = Color(0xFF4B6FD4),
                body = "Method overloading — banyak method dengan nama sama tapi parameter berbeda:",
                codeBlock = """public class Kalkulator {
    public int tambah(int a, int b)          { return a + b; }
    public double tambah(double a, double b) { return a + b; }
    public int tambah(int a, int b, int c)   { return a + b + c; }

    // Java otomatis memilih versi yang tepat
}
Kalkulator k = new Kalkulator();
k.tambah(2, 3);        // → int version
k.tambah(2.5, 1.5);    // → double version"""
            ),
            LessonSection(
                heading = "Polymorphism dengan Array & List",
                icon = Icons.AutoMirrored.Filled.List, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Manfaat terbesar polymorphism: menyimpan berbagai tipe objek dalam satu koleksi dan memprosesnya secara seragam:",
                codeBlock = """List<Kendaraan> garasi = new ArrayList<>();
garasi.add(new Mobil("Honda", 2020, 4));
garasi.add(new Motor("Yamaha", 2021));
garasi.add(new Truk("Isuzu", 2019, 10));

// Satu loop untuk semua jenis kendaraan
for (Kendaraan k : garasi) {
    k.info(); // Setiap objek menjalankan versi info()-nya sendiri
}"""
            ),
            LessonSection(
                heading = "Kapan Menggunakan Polymorphism?",
                icon = Icons.Default.Lightbulb, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Tips penggunaan polymorphism di dunia nyata:",
                bullets = listOf(
                    "Gunakan referensi tipe superclass/interface jika ingin kode fleksibel",
                    "Design pattern Strategy sangat bergantung pada polymorphism",
                    "Hindari type-checking (instanceof) berlebihan — pertanda desain yang kurang baik",
                    "Polymorphism menjadi dasar dari Dependency Injection di framework modern"
                )
            )
        )
    ),

    // 6 ─ Interface & Abstract Class
    LessonContent(
        title = "Interface dan Abstract Class",
        subtitle = "Mendefinisikan kontrak dan kerangka yang harus dipatuhi oleh implementor.",
        duration = "90 menit",
        sections = listOf(
            LessonSection(
                heading = "Abstract Class",
                icon = Icons.Default.Layers, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Abstract class adalah class yang tidak bisa diinstansiasi langsung. Ia berisi kombinasi method konkret (sudah ada implementasi) dan method abstract (harus di-override):",
                codeBlock = """public abstract class Bentuk {
    protected String warna;

    // Method konkret — sudah ada implementasi
    public String getWarna() { return warna; }

    // Method abstract — WAJIB di-override subclass
    public abstract double hitungLuas();
    public abstract double hitungKeliling();
}

public class Lingkaran extends Bentuk {
    private double r;
    public Lingkaran(double r) { this.r = r; }

    @Override public double hitungLuas()     { return Math.PI * r * r; }
    @Override public double hitungKeliling() { return 2 * Math.PI * r; }
}"""
            ),
            LessonSection(
                heading = "Interface",
                icon = Icons.Default.ConnectWithoutContact, iconBg = Color(0xFFF0F4FF), iconTint = Color(0xFF4B6FD4),
                body = "Interface adalah kontrak murni — hanya mendefinisikan method signature tanpa implementasi (kecuali default method). Sebuah class bisa mengimplementasi banyak interface:",
                codeBlock = """public interface Bisa Terbang {
    void terbang();
    default String deskripsi() { return "Bisa terbang"; }
}

public interface BisaBerenang {
    void berenang();
}

// Satu class, banyak interface
public class Bebek extends Hewan implements BisaTerbang, BisaBerenang {
    @Override public void terbang()  { System.out.println("Bebek terbang rendah"); }
    @Override public void berenang() { System.out.println("Bebek berenang"); }
}"""
            ),
            LessonSection(
                heading = "Abstract Class vs Interface — Kapan Pakai?",
                icon = Icons.Default.Balance, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Gunakan Abstract Class ketika ada perilaku default yang dibagikan ke semua subclass. Gunakan Interface ketika mendefinisikan kemampuan yang bisa dimiliki oleh class yang tidak berhubungan:",
                bullets = listOf(
                    "Abstract Class: 'adalah' — Anjing adalah Hewan",
                    "Interface: 'bisa' — Anjing bisa Berlari, bisa Berenang",
                    "Java tidak mendukung multiple inheritance class, tapi support multiple interface",
                    "Sejak Java 8, interface bisa punya default method dan static method"
                )
            ),
            LessonSection(
                heading = "Functional Interface & Lambda",
                icon = Icons.Default.Functions, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Functional interface hanya punya satu abstract method dan bisa ditulis ringkas sebagai lambda expression:",
                codeBlock = """@FunctionalInterface
interface Operasi {
    int hitung(int a, int b);
}

// Lambda — lebih ringkas dari anonymous class
Operasi tambah = (a, b) -> a + b;
Operasi kali   = (a, b) -> a * b;

System.out.println(tambah.hitung(5, 3)); // 8
System.out.println(kali.hitung(5, 3));   // 15"""
            )
        )
    ),

    // 7 ─ Exception Handling
    LessonContent(
        title = "Exception Handling",
        subtitle = "Menulis kode yang tangguh dan mampu menangani kondisi error dengan elegan.",
        duration = "75 menit",
        sections = listOf(
            LessonSection(
                heading = "Apa itu Exception?",
                icon = Icons.Default.Warning, iconBg = Color(0xFFFFF5F5), iconTint = Color(0xFFE53935),
                body = "Exception adalah kejadian tidak normal yang terjadi saat program berjalan dan mengganggu alur normal eksekusi. Tanpa penanganan yang baik, program akan crash.\n\nHierarki Exception di Java: Throwable → Error / Exception → RuntimeException",
                bullets = listOf(
                    "Checked Exception — harus ditangani (IOException, SQLException)",
                    "Unchecked Exception — RuntimeException dan turunannya (NullPointerException, ArrayIndexOutOfBoundsException)",
                    "Error — masalah serius JVM (OutOfMemoryError) — jangan ditangkap"
                )
            ),
            LessonSection(
                heading = "Try-Catch-Finally",
                icon = Icons.Default.Security, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Blok try-catch adalah mekanisme utama menangani exception:",
                codeBlock = """public class ContohException {
    public static void main(String[] args) {
        try {
            int[] arr = new int[5];
            arr[10] = 99; // Ini akan lempar exception!
            System.out.println("Baris ini tidak dieksekusi");

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Index di luar batas! " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Error umum: " + e.getMessage());

        } finally {
            // SELALU dieksekusi — biasanya untuk close resource
            System.out.println("Blok finally selalu jalan");
        }
    }
}"""
            ),
            LessonSection(
                heading = "Custom Exception & Throw",
                icon = Icons.Default.ReportProblem, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Kamu bisa membuat exception sendiri sesuai kebutuhan bisnis aplikasi:",
                codeBlock = """// Custom exception
public class SaldoTidakCukupException extends Exception {
    private double kekurangan;

    public SaldoTidakCukupException(double kekurangan) {
        super("Saldo tidak cukup! Kurang: Rp " + kekurangan);
        this.kekurangan = kekurangan;
    }
}

public class Rekening {
    private double saldo = 500_000;

    public void tarik(double jumlah) throws SaldoTidakCukupException {
        if (jumlah > saldo)
            throw new SaldoTidakCukupException(jumlah - saldo);
        saldo -= jumlah;
    }
}"""
            ),
            LessonSection(
                heading = "Try-with-Resources (Java 7+)",
                icon = Icons.Default.CleaningServices, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Untuk resource seperti file atau koneksi database, gunakan try-with-resources agar resource otomatis ditutup:",
                codeBlock = """// Resource otomatis tertutup setelah blok try
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    System.out.println("Gagal baca file: " + e.getMessage());
}
// br.close() dipanggil otomatis!"""
            )
        )
    ),

    // 8 ─ Java GUI & Database
    LessonContent(
        title = "Java GUI dan Database Connection",
        subtitle = "Membangun antarmuka grafis dan menghubungkan aplikasi Java ke database.",
        duration = "120 menit",
        sections = listOf(
            LessonSection(
                heading = "Java Swing — Komponen Dasar GUI",
                icon = Icons.Default.DesktopWindows, iconBg = Color(0xFFEFF4FB), iconTint = Blue,
                body = "Java Swing adalah framework GUI bawaan Java. Meski sudah ada JavaFX, Swing masih banyak digunakan di aplikasi enterprise:",
                codeBlock = """import javax.swing.*;
import java.awt.*;

public class FormLogin extends JFrame {
    public FormLogin() {
        setTitle("EduDesk — Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        JTextField txtUser = new JTextField();
        panel.add(txtUser);

        panel.add(new JLabel("Password:"));
        JPasswordField txtPass = new JPasswordField();
        panel.add(txtPass);

        JButton btnLogin = new JButton("Masuk");
        panel.add(new JLabel());
        panel.add(btnLogin);

        btnLogin.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Login: " + txtUser.getText())
        );

        add(panel);
        setVisible(true);
    }
}"""
            ),
            LessonSection(
                heading = "JDBC — Koneksi ke Database",
                icon = Icons.Default.Storage, iconBg = Color(0xFFF0F4FF), iconTint = Color(0xFF4B6FD4),
                body = "JDBC (Java Database Connectivity) adalah API standar Java untuk berinteraksi dengan database relasional seperti MySQL, PostgreSQL, atau SQLite:",
                codeBlock = """import java.sql.*;

public class DatabaseHelper {
    private static final String URL  = "jdbc:mysql://localhost:3306/edudesk";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void getMahasiswa() {
        String sql = "SELECT nim, nama, ipk FROM mahasiswa WHERE ipk > ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, 3.0);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.printf("%-12s %-25s %.2f%n",
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getDouble("ipk"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}"""
            ),
            LessonSection(
                heading = "CRUD Operations",
                icon = Icons.Default.TableRows, iconBg = Color(0xFFFFF8EB), iconTint = Color(0xFFD97706),
                body = "Operasi dasar database: Create, Read, Update, Delete menggunakan PreparedStatement (lebih aman dari SQL injection):",
                codeBlock = """// INSERT (Create)
String insertSQL = "INSERT INTO mahasiswa(nim, nama, ipk) VALUES(?,?,?)";
try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
    ps.setString(1, "19021010");
    ps.setString(2, "Andi Firmansyah");
    ps.setDouble(3, 3.85);
    ps.executeUpdate();
}

// UPDATE
String updateSQL = "UPDATE mahasiswa SET ipk=? WHERE nim=?";
try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
    ps.setDouble(1, 3.90);
    ps.setString(2, "19021010");
    ps.executeUpdate();
}

// DELETE
String deleteSQL = "DELETE FROM mahasiswa WHERE nim=?";
try (PreparedStatement ps = conn.prepareStatement(deleteSQL)) {
    ps.setString(1, "19021010");
    ps.executeUpdate();
}"""
            ),
            LessonSection(
                heading = "Integrasi GUI + Database",
                icon = Icons.Default.Link, iconBg = Color(0xFFF0FAF4), iconTint = GreenOk,
                body = "Menggabungkan Swing dan JDBC — pattern yang umum digunakan di proyek akhir semester:",
                bullets = listOf(
                    "Pisahkan logika database ke class terpisah (DAO pattern)",
                    "Gunakan SwingWorker untuk query database agar UI tidak freeze",
                    "Selalu gunakan PreparedStatement — jangan pernah string concatenation untuk query",
                    "Tutup Connection, Statement, dan ResultSet di blok finally atau try-with-resources",
                    "Connection pooling (HikariCP) untuk aplikasi dengan banyak pengguna"
                )
            )
        )
    )
)

// ════════════════════════════════════════════════════════════════════════════
// LESSON SCREEN
// ════════════════════════════════════════════════════════════════════════════

@Composable
fun LessonScreen() {
    val lessonIndex = NavController.currentLessonIndex
    val lesson = LESSONS.getOrNull(lessonIndex) ?: return

    Scaffold(topBar = { TopNavigationBar() }) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BgPage)
        ) {
            // ── LEFT: Lesson Navigation Sidebar ───────────────────────────
            Column(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight()
                    .background(BgCard)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 24.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("Modul Pembelajaran", fontSize = 13.sp, color = Muted, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("PBO — Java", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Dark)
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Border)
                Spacer(modifier = Modifier.height(12.dp))

                LESSONS.forEachIndexed { i, l ->
                    val isActive = i == lessonIndex
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 2.dp),
                        color = if (isActive) BgSelected else Color.Transparent,
                        shape = RoundedCornerShape(10.dp),
                        onClick = { NavController.navigateToLesson(i) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(3.dp).height(18.dp)
                                    .background(
                                        if (isActive) Blue else Color.Transparent,
                                        RoundedCornerShape(2.dp)
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .background(
                                        if (isActive) Blue else Color(0xFFF1F5F9),
                                        RoundedCornerShape(6.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "${i + 1}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isActive) Color.White else Muted
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                l.title,
                                fontSize = 12.sp,
                                fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isActive) Blue else Dark,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }

            // Thin separator
            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(Border))

            // ── RIGHT: Lesson Content ──────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 52.dp, vertical = 36.dp)
            ) {
                // Header
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { NavController.navigateTo(Screen.CourseDetail) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Muted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            "Modul ${lessonIndex + 1} dari ${LESSONS.size}",
                            fontSize = 12.sp, color = Blue, fontWeight = FontWeight.SemiBold
                        )
                        Text(lesson.title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Dark)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(48.dp))
                    Text(lesson.subtitle, fontSize = 14.sp, color = Muted)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.AccessTime, null, tint = Muted, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(lesson.duration, fontSize = 12.sp, color = Muted)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress bar
                LinearProgressIndicator(
                    progress = { (lessonIndex + 1).toFloat() / LESSONS.size },
                    modifier = Modifier.fillMaxWidth().height(6.dp),
                    color = Blue,
                    trackColor = Color(0xFFE2E8F0),
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Lesson sections
                lesson.sections.forEach { section ->
                    LessonSectionCard(section)
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (lessonIndex > 0) {
                        OutlinedButton(
                            onClick = { NavController.navigateToLesson(lessonIndex - 1) },
                            shape  = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Border),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.size(16.dp), tint = Muted)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Modul Sebelumnya", color = Muted, fontWeight = FontWeight.Medium)
                        }
                    } else { Spacer(modifier = Modifier.width(1.dp)) }

                    if (lessonIndex < LESSONS.size - 1) {
                        Button(
                            onClick = { NavController.navigateToLesson(lessonIndex + 1) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Blue),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                        ) {
                            Text("Modul Selanjutnya", fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(16.dp))
                        }
                    } else {
                        Button(
                            onClick = { NavController.navigateTo(Screen.CourseDetail) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GreenOk),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Selesai! Kembali ke Kursus", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

// ── Section card ──────────────────────────────────────────────────────────
@Composable
private fun LessonSectionCard(section: LessonSection) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BgCard),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Border),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Section header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(section.iconBg, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(section.icon, null, tint = section.iconTint, modifier = Modifier.size(19.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(section.heading, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Dark)
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(16.dp))

            // Body text
            Text(section.body, fontSize = 14.sp, color = Color(0xFF475569), lineHeight = 24.sp)

            // Code block
            section.codeBlock?.let { code ->
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E293B), RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(10.dp).background(Color(0xFFFF5F57), CircleShape))
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(modifier = Modifier.size(10.dp).background(Color(0xFFFFBD2E), CircleShape))
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(modifier = Modifier.size(10.dp).background(Color(0xFF28C840), CircleShape))
                            Spacer(modifier = Modifier.weight(1f))
                            Text("Java", fontSize = 11.sp, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            code,
                            fontSize = 13.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            color = Color(0xFFE2E8F0),
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Bullet points
            if (section.bullets.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                section.bullets.forEach { bullet ->
                    Row(
                        modifier = Modifier.padding(vertical = 5.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 7.dp)
                                .size(6.dp)
                                .background(Blue, RoundedCornerShape(3.dp))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(bullet, fontSize = 14.sp, color = Color(0xFF334155), lineHeight = 22.sp)
                    }
                }
            }
        }
    }
}

private val BgSelected = Color(0xFFEFF4FB)
private val CircleShape = androidx.compose.foundation.shape.CircleShape
