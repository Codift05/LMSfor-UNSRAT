package models

type Course struct {
	ID          uint   `gorm:"primaryKey"`
	Name        string
	Code        string
	Description string
	UserID      uint
	User        User
}
