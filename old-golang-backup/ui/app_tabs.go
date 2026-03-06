package ui

import (
	"edudesk/models"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/container"
	"gorm.io/gorm"
)

func AppTabsView(db *gorm.DB, w fyne.Window, user *models.User) fyne.CanvasObject {
	dashboardTab := container.NewTabItem("Dashboard", DashboardView(db, w, user))
	courseTab := container.NewTabItem("Mata Kuliah", CourseView(db, user))
	assignmentTab := container.NewTabItem("Tugas", AssignmentView(db, user))
	profileTab := container.NewTabItem("Profile", ProfileView(user, w, db))

	tabs := container.NewAppTabs(
		dashboardTab,
		courseTab,
		assignmentTab,
		profileTab,
	)

	tabs.SetTabLocation(container.TabLocationTop)
	return tabs
}
