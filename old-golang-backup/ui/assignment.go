package ui

import (
	"edudesk/models"
	"edudesk/services"
	"time"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/canvas"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/layout"
	"fyne.io/fyne/v2/theme"
	"fyne.io/fyne/v2/widget"
	"gorm.io/gorm"
)

func AssignmentView(db *gorm.DB, user *models.User) fyne.CanvasObject {
	assignmentService := &services.AssignmentService{DB: db}
	selected := -1
	assignments, _ := assignmentService.GetAssignments(user.ID)

	title := canvas.NewText("Manajemen Tugas", theme.PrimaryColor())
	title.TextSize = 24
	title.TextStyle = fyne.TextStyle{Bold: true}

	table := widget.NewTable(
		func() (int, int) { return len(assignments), 5 },
		func() fyne.CanvasObject { return widget.NewLabel("") },
		func(id widget.TableCellID, o fyne.CanvasObject) {
			row, col := id.Row, id.Col
			var val string
			switch col {
			case 0:
				val = assignments[row].Course.Name
			case 1:
				val = assignments[row].Title
			case 2:
				val = assignments[row].Deadline.Format("2006-01-02")
			case 3:
				if assignments[row].IsDone {
					val = "✅ Selesai"
				} else {
					val = "⏳ Belum"
				}
			case 4:
				val = "Aksi"
			}
			o.(*widget.Label).SetText(val)
		},
	)
	table.SetColumnWidth(0, 150)
	table.SetColumnWidth(1, 150)
	table.SetColumnWidth(2, 120)
	table.SetColumnWidth(3, 100)
	table.OnSelected = func(id widget.TableCellID) { selected = id.Row }

	addBtn := widget.NewButtonWithIcon("Tambah", theme.ContentAddIcon(), func() {
		titleEntry := widget.NewEntry()
		descEntry := widget.NewEntry()
		deadlineEntry := widget.NewEntry()
		dialog := widget.NewButton("Simpan", func() {
			a := &models.Assignment{
				Title:       titleEntry.Text,
				Description: descEntry.Text,
				Deadline:    time.Now().Add(24 * time.Hour), // TODO: parse deadlineEntry
				UserID:      user.ID,
			}
			assignmentService.AddAssignment(a)
			assignments, _ = assignmentService.GetAssignments(user.ID)
			table.Refresh()
		})
		dialog.Importance = widget.HighImportance

		w := fyne.CurrentApp().NewWindow("Tambah Tugas")
		w.SetContent(container.NewPadded(container.NewVBox(
			widget.NewLabel("Judul Tugas"), titleEntry,
			widget.NewLabel("Deskripsi"), descEntry,
			widget.NewLabel("Deadline (YYYY-MM-DD)"), deadlineEntry,
			layout.NewSpacer(),
			dialog,
		)))
		w.Resize(fyne.NewSize(350, 300))
		w.Show()
	})
	addBtn.Importance = widget.HighImportance

	editBtn := widget.NewButtonWithIcon("Edit", theme.DocumentCreateIcon(), func() {
		if selected >= 0 && selected < len(assignments) {
			// TODO: dialog edit tugas
		}
	})
	delBtn := widget.NewButtonWithIcon("Hapus", theme.DeleteIcon(), func() {
		if selected >= 0 && selected < len(assignments) {
			assignmentService.DeleteAssignment(assignments[selected].ID)
			assignments, _ = assignmentService.GetAssignments(user.ID)
			table.Refresh()
		}
	})
	delBtn.Importance = widget.DangerImportance

	uploadBtn := widget.NewButtonWithIcon("Upload File", theme.FolderOpenIcon(), func() {
		if selected >= 0 && selected < len(assignments) {
			// TODO: file picker upload submission
		}
	})
	doneBtn := widget.NewButtonWithIcon("Tandai Selesai", theme.ConfirmIcon(), func() {
		if selected >= 0 && selected < len(assignments) {
			assignmentService.MarkDone(assignments[selected].ID)
			assignments, _ = assignmentService.GetAssignments(user.ID)
			table.Refresh()
		}
	})

	topPanel := container.NewVBox(container.NewPadded(title), widget.NewSeparator())
	bottomPanel := container.NewPadded(
		container.NewVBox(
			container.NewHBox(addBtn, editBtn, delBtn, layout.NewSpacer(), uploadBtn, doneBtn),
		),
	)

	card := widget.NewCard("", "", table)
	cardContent := container.NewPadded(card)

	return container.NewBorder(topPanel, bottomPanel, nil, nil, cardContent)
}
