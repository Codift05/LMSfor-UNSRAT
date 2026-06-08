package com.iels.utils

import com.iels.models.Question
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File

object PdfParser {

    /**
     * Extracts questions from a formatted PDF file.
     * Expected format:
     * 1. Question text here?
     * A. Option A
     * B. Option B
     * C. Option C
     * D. Option D
     * Kunci: A
     */
    fun extractQuestionsFromPdf(file: File): List<Question> {
        val questions = mutableListOf<Question>()
        try {
            val document = PDDocument.load(file)
            val pdfStripper = PDFTextStripper()
            val text = pdfStripper.getText(document)
            document.close()

            // Split by numbers indicating a question (e.g., "1. ", "2. ")
            val questionBlocks = text.split(Regex("(?m)^\\d+\\.\\s+"))
            
            for (block in questionBlocks) {
                if (block.trim().isEmpty()) continue

                var questionText = ""
                var optionA = ""
                var optionB = ""
                var optionC = ""
                var optionD = ""
                var correctAnswer = ""

                val lines = block.lines().map { it.trim() }.filter { it.isNotEmpty() }
                
                var currentSection = "question"
                for (line in lines) {
                    when {
                        line.startsWith("A.", ignoreCase = true) -> {
                            currentSection = "A"
                            optionA = line.substring(2).trim()
                        }
                        line.startsWith("B.", ignoreCase = true) -> {
                            currentSection = "B"
                            optionB = line.substring(2).trim()
                        }
                        line.startsWith("C.", ignoreCase = true) -> {
                            currentSection = "C"
                            optionC = line.substring(2).trim()
                        }
                        line.startsWith("D.", ignoreCase = true) -> {
                            currentSection = "D"
                            optionD = line.substring(2).trim()
                        }
                        line.startsWith("Kunci:", ignoreCase = true) -> {
                            currentSection = "Kunci"
                            correctAnswer = line.substring(6).trim().uppercase().take(1)
                        }
                        else -> {
                            when (currentSection) {
                                "question" -> questionText += if (questionText.isEmpty()) line else "\n$line"
                                "A" -> optionA += " $line"
                                "B" -> optionB += " $line"
                                "C" -> optionC += " $line"
                                "D" -> optionD += " $line"
                            }
                        }
                    }
                }

                if (questionText.isNotEmpty() && (optionA.isNotEmpty() || optionB.isNotEmpty())) {
                    questions.add(
                        Question(
                            text = questionText.trim(),
                            optionA = optionA.trim(),
                            optionB = optionB.trim(),
                            optionC = optionC.trim(),
                            optionD = optionD.trim(),
                            correctAnswer = correctAnswer
                        )
                    )
                }
            }
        } catch (e: Exception) {
            println("Error parsing PDF: ${e.message}")
        }
        return questions
    }
}
