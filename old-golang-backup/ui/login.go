package ui

import (
	"edudesk/services"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/layout"
	"fyne.io/fyne/v2/theme"
	"fyne.io/fyne/v2/widget"
	"gorm.io/gorm"
)

func LoginView(db *gorm.DB, w fyne.Window) fyne.CanvasObject {
	nimEntry := widget.NewEntry()
	nimEntry.SetPlaceHolder("NIM atau Email")
	passEntry := widget.NewPasswordEntry()
	passEntry.SetPlaceHolder("Password")
	errorLabel := widget.NewLabel("")
	errorLabel.Hide()

	auth := &services.AuthService{DB: db}

	loginBtn := widget.NewButtonWithIcon("LOGIN", theme.LoginIcon(), func() {
		user, err := auth.Login(nimEntry.Text, passEntry.Text)
		if err != nil {
			errorLabel.SetText("NIM/email atau password salah")
			errorLabel.Show()
			return
		}
		errorLabel.Hide()
		w.SetContent(AppTabsView(db, w, user))
	})
	loginBtn.Importance = widget.HighImportance

	title := canvas.NewText("EduDesk LMS", theme.PrimaryColor())
	title.TextSize = 28
	title.TextStyle = fyne.TextStyle{Bold: true}

	subtitle := canvas.NewText("Silakan login untuk melanjutkan", theme.ForegroundColor())

	formBox := container.NewVBox(
		widget.NewLabel("Username / Email"),
		nimEntry,
		widget.NewLabel("Password"),
		passEntry,
		layout.NewSpacer(),
		container.NewPadded(loginBtn),
		errorLabel,
	)

	card := widget.NewCard("", "", formBox)

	content := container.NewVBox(
		layout.NewSpacer(),
		container.NewCenter(container.NewVBox(
			container.NewCenter(title),
			container.NewCenter(subtitle),
			widget.NewSeparator(),
			container.NewPadded(card),
		)),
		layout.NewSpacer(),
	)
	return content
}
