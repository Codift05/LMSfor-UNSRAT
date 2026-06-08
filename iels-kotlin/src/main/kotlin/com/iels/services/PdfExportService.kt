package com.iels.services

import com.iels.models.ExamResultDetailDto
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.properties.TextAlignment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class PdfExportService {
    fun exportResultsToPdf(results: List<ExamResultDetailDto>, destinationPath: String): Boolean {
        return try {
            val file = File(destinationPath)
            file.parentFile?.mkdirs()

            val writer = PdfWriter(file)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)

            // Header
            val title = Paragraph("REKAPITULASI HASIL UJIAN CBT IELS")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(18f)
            document.add(title)

            val subtitle = Paragraph("Fakultas Teknik Universitas Sam Ratulangi")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14f)
            document.add(subtitle)

            val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date())
            document.add(Paragraph("Tanggal Cetak: $dateStr").setTextAlignment(TextAlignment.RIGHT).setFontSize(10f))
            document.add(Paragraph("\n")) // Spacer

            // Table with 6 columns
            val table = Table(floatArrayOf(1f, 3f, 2f, 3f, 1f, 1f))
            table.useAllAvailableWidth()

            // Headers
            table.addHeaderCell(Cell().add(Paragraph("No").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Nama Siswa").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("NIM").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Ujian").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Skor").setBold()))
            table.addHeaderCell(Cell().add(Paragraph("Kecurangan").setBold()))

            // Rows
            results.forEachIndexed { index, result ->
                table.addCell(Cell().add(Paragraph("${index + 1}")))
                table.addCell(Cell().add(Paragraph(result.userName)))
                table.addCell(Cell().add(Paragraph(result.userNim)))
                table.addCell(Cell().add(Paragraph(result.examTitle)))
                table.addCell(Cell().add(Paragraph("${result.score}").setTextAlignment(TextAlignment.CENTER)))
                table.addCell(Cell().add(Paragraph("${result.cheatCount}").setTextAlignment(TextAlignment.CENTER)))
            }

            document.add(table)
            
            val footer = Paragraph("\n\nDokumen ini di-generate secara otomatis oleh IELS System.")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10f)
                .setItalic()
            document.add(footer)

            document.close()
            true
        } catch (e: Exception) {
            println("Error generating PDF: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}
