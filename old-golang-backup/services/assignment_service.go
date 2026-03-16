package services

import (
	"time"
	"gorm.io/gorm"
	"edudesk/models"
)

type AssignmentService struct {
	DB *gorm.DB
}

func (s *AssignmentService) GetAssignments(userID uint) ([]models.Assignment, error) {
	var assignments []models.Assignment
	err := s.DB.Where("user_id = ?", userID).Find(&assignments).Error
	return assignments, err
}

func (s *AssignmentService) AddAssignment(a *models.Assignment) error {
	return s.DB.Create(a).Error
}

func (s *AssignmentService) UpdateAssignment(a *models.Assignment) error {
	return s.DB.Save(a).Error
}

func (s *AssignmentService) DeleteAssignment(id uint) error {
	return s.DB.Delete(&models.Assignment{}, id).Error
}

func (s *AssignmentService) MarkDone(id uint) error {
	return s.DB.Model(&models.Assignment{}).Where("id = ?", id).Update("is_done", true).Error
}

func (s *AssignmentService) UploadSubmission(assignmentID uint, filePath string) error {
	submission := models.Submission{
		AssignmentID: assignmentID,
		FilePath: filePath,
		SubmittedAt: time.Now(),
	}
	return s.DB.Create(&submission).Error
}

func (s *AssignmentService) GetDeadlineSoon(userID uint) ([]models.Assignment, error) {
	var assignments []models.Assignment
	soon := time.Now().Add(72 * time.Hour)
	err := s.DB.Where("user_id = ? AND deadline <= ? AND is_done = ?", userID, soon, false).Find(&assignments).Error
	return assignments, err
}
