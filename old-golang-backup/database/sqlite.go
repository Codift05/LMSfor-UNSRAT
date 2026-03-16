package database

import (
	"edudesk/models"

	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func InitDB() (*gorm.DB, error) {
	db, err := gorm.Open(sqlite.Open("edudesk.db"), &gorm.Config{})
	if err != nil {
		return nil, err
	}
	// Auto-migrate semua model
	err = db.AutoMigrate(
		&models.User{},
		&models.Course{},
		&models.Assignment{},
		&models.Submission{},
	)
	if err != nil {
		return nil, err
	}
	return db, nil
}
