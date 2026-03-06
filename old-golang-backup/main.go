package main

import (
	"edudesk/database"
	"edudesk/models"
	"edudesk/services"
	"edudesk/ui"
	"log"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/container"
)

func main() {
	db, err := database.InitDB()
	if err != nil {
		log.Fatal("DB error:", err)
	}

	auth := &services.AuthService{DB: db}
	// Seed admin if not exist
	var count int64
	db.Model(&models.User{}).Count(&count)
	if count == 0 {
		auth.Register(&models.User{
			Name:     "Admin EduDesk",
			Email:    "admin@edudesk.com",
			Password: "admin",
			NIM:      "admin",
			Role:     "admin",
		})
	}

	a := app.New()
	w := a.NewWindow("EduDesk LMS")
	w.Resize(fyne.NewSize(900, 600))

	// Tampilkan login page
	login := ui.LoginView(db, w)
	w.SetContent(container.NewVBox(login))
	w.ShowAndRun()
}
