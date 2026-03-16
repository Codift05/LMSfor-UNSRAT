package models

import "time"

type Submission struct {
	ID           uint      `gorm:"primaryKey"`
	AssignmentID uint
	Assignment   Assignment
	FilePath     string
	SubmittedAt  time.Time
}
