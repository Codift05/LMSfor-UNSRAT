package services

import (
	"gorm.io/gorm"
	"edudesk/models"
)

type CourseService struct {
	DB *gorm.DB
}

func (s *CourseService) GetCourses(userID uint) ([]models.Course, error) {
	var courses []models.Course
	err := s.DB.Where("user_id = ?", userID).Find(&courses).Error
	return courses, err
}

func (s *CourseService) AddCourse(course *models.Course) error {
	return s.DB.Create(course).Error
}

func (s *CourseService) DeleteCourse(id uint) error {
	return s.DB.Delete(&models.Course{}, id).Error
}
