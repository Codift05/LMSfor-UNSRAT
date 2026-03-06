package ui

import (
	"edudesk/models"
	"edudesk/services"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/layout"
	"fyne.io/fyne/v2/theme"
	"fyne.io/fyne/v2/widget"
	"gorm.io/gorm"
)

func CourseView(db *gorm.DB, user *models.User) fyne.CanvasObject {
	courseService := &services.CourseService{DB: db}
	courses, _ := courseService.GetCourses(user.ID)
	selected := -1

	title := canvas.NewText("Daftar Mata Kuliah", theme.PrimaryColor())
	title.TextSize = 24
	title.TextStyle = fyne.TextStyle{Bold: true}

	list := widget.NewList(
		func() int { return len(courses) },
		func() fyne.CanvasObject { return widget.NewLabel("") },
		func(i int, o fyne.CanvasObject) {
			o.(*widget.Label).SetText(courses[i].Name + " (" + courses[i].Code + ")")
		},
	)
	list.OnSelected = func(i int) { selected = i }

	addBtn := widget.NewButtonWithIcon("Tambah", theme.ContentAddIcon(), func() {
		nameEntry := widget.NewEntry()
		codeEntry := widget.NewEntry()
		descEntry := widget.NewEntry()
		dialog := widget.NewButton("Simpan", func() {
			course := &models.Course{
				Name:        nameEntry.Text,
				Code:        codeEntry.Text,
				Description: descEntry.Text,
				UserID:      user.ID,
			}
			courseService.AddCourse(course)
			courses, _ = courseService.GetCourses(user.ID)
			list.Refresh()
		})
		dialog.Importance = widget.HighImportance

		w := fyne.CurrentApp().NewWindow("Tambah Mata Kuliah")
		w.SetContent(container.NewPadded(container.NewVBox(
			widget.NewLabel("Nama Mata Kuliah"), nameEntry,
			widget.NewLabel("Kode"), codeEntry,
			widget.NewLabel("Deskripsi"), descEntry,
			layout.NewSpacer(),
			dialog,
		)))
		w.Resize(fyne.NewSize(350, 300))
		w.Show()
	})
	addBtn.Importance = widget.HighImportance

	delBtn := widget.NewButtonWithIcon("Hapus", theme.DeleteIcon(), func() {
		if selected >= 0 && selected < len(courses) {
			courseService.DeleteCourse(courses[selected].ID)
			courses, _ = courseService.GetCourses(user.ID)
			list.Refresh()
		}
	})
	delBtn.Importance = widget.DangerImportance

	topPanel := container.NewVBox(container.NewPadded(title), widget.NewSeparator())
	bottomPanel := container.NewPadded(container.NewHBox(layout.NewSpacer(), addBtn, delBtn))

	card := widget.NewCard("", "", list)
	return container.NewBorder(topPanel, bottomPanel, nil, nil, card)
}
