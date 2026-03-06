package services

import (
	"golang.org/x/crypto/bcrypt"
	"gorm.io/gorm"
	"edudesk/models"
)

var activeUser *models.User

type AuthService struct {
	DB *gorm.DB
}

func (s *AuthService) Login(nim, password string) (*models.User, error) {
	var user models.User
	if err := s.DB.Where("nim = ?", nim).First(&user).Error; err != nil {
		return nil, err
	}
	if bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(password)) != nil {
		return nil, gorm.ErrRecordNotFound
	}
	activeUser = &user
	return &user, nil
}

func (s *AuthService) Register(user *models.User) error {
	hash, err := bcrypt.GenerateFromPassword([]byte(user.Password), bcrypt.DefaultCost)
	if err != nil {
		return err
	}
	user.Password = string(hash)
	return s.DB.Create(user).Error
}

func GetActiveUser() *models.User {
	return activeUser
}
