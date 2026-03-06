package models

import "time"

type Assignment struct {
	ID          uint      `gorm:"primaryKey"`
	Title       string
	Description string
	Deadline    time.Time
	CourseID    uint
	Course      Course
	UserID      uint
	IsDone      bool
}
