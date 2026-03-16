package ui

import (
	"edudesk/models"
	"fmt"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/layout"
	"fyne.io/fyne/v2/theme"
	"fyne.io/fyne/v2/widget"
	"gorm.io/gorm"
)

func ProfileView(user *models.User, w fyne.Window, db *gorm.DB) fyne.CanvasObject {
	title := canvas.NewText("Profil Pengguna", theme.PrimaryColor())
	title.TextSize = 24
	title.TextStyle = fyne.TextStyle{Bold: true}

	info := widget.NewCard("Informasi Akun", "", container.NewVBox(
		widget.NewLabel(fmt.Sprintf("Nama: %s", user.Name)),
		widget.NewLabel(fmt.Sprintf("NIM/Username: %s", user.NIM)),
		widget.NewLabel(fmt.Sprintf("Email: %s", user.Email)),
		widget.NewLabel(fmt.Sprintf("Role: %s", user.Role)),
	))

	logoutBtn := widget.NewButtonWithIcon("Logout", theme.LogoutIcon(), func() {
		w.SetContent(LoginView(db, w))
	})
	logoutBtn.Importance = widget.DangerImportance

	content := container.NewVBox(
		container.NewPadded(title),
		widget.NewSeparator(),
		container.NewPadded(info),
		layout.NewSpacer(),
		container.NewPadded(logoutBtn),
	)
	return content
}
