package ui

import (
	"edudesk/models"
	"edudesk/services"
	"fmt"

	"image/color"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/theme"
	"fyne.io/fyne/v2/widget"
	"gorm.io/gorm"
)

func DashboardView(db *gorm.DB, w fyne.Window, user *models.User) fyne.CanvasObject {
	courseService := &services.CourseService{DB: db}
	courses, _ := courseService.GetCourses(user.ID)

	assignmentService := &services.AssignmentService{DB: db}
	assignments, _ := assignmentService.GetAssignments(user.ID)

	doneCount := 0
	for _, a := range assignments {
		if a.IsDone {
			doneCount++
		}
	}

	welcomeTitle := canvas.NewText(fmt.Sprintf("Selamat Datang, %s!", user.Name), theme.PrimaryColor())
	welcomeTitle.TextSize = 24
	welcomeTitle.TextStyle = fyne.TextStyle{Bold: true}
	welcomeSubtitle := canvas.NewText("Ini adalah ringkasan progres belajar kamu.", color.Gray{Y: 150})
	headerBox := container.NewVBox(welcomeTitle, welcomeSubtitle)

	soon, _ := assignmentService.GetDeadlineSoon(user.ID)

	card1 := widget.NewCard("Mata Kuliah Aktif", "", widget.NewLabel(fmt.Sprintf("%d course(s)", len(courses))))
	card2 := widget.NewCard("Tugas Terdekat", "Deadline ≤ 3 hari", widget.NewLabel(fmt.Sprintf("%d tugas", len(soon))))

	progressVal := 0.0
	if len(assignments) > 0 {
		progressVal = float64(doneCount) / float64(len(assignments))
	}
	progBar := widget.NewProgressBar()
	progBar.SetValue(progressVal)
	card3 := widget.NewCard("Progress Tugas", fmt.Sprintf("%d / %d selesai", doneCount, len(assignments)), progBar)

	cardsGrid := container.NewGridWithColumns(3, card1, card2, card3)

	var reminder fyne.CanvasObject
	if len(soon) > 0 {
		reminderMsg := canvas.NewText(fmt.Sprintf("⚠️ Kamu punya %d tugas yang deadline-nya dekat!", len(soon)), color.NRGBA{R: 255, G: 100, B: 100, A: 255})
		reminderMsg.TextStyle = fyne.TextStyle{Bold: true}
		reminder = container.NewPadded(reminderMsg)
	} else {
		reminder = widget.NewLabel("")
	}

	content := container.NewVBox(
		container.NewPadded(headerBox),
		widget.NewSeparator(),
		container.NewPadded(cardsGrid),
		reminder,
	)

	return container.NewScroll(content)
}
