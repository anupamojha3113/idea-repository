package controllers

import (
	"github.com/Lalit3716/ideabook_server/app/models"
	"github.com/Lalit3716/ideabook_server/data"
	"github.com/Lalit3716/ideabook_server/utils"
	"gorm.io/gorm"
	"net/http"
)

func GetTags(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	var tags []models.Tag

	db.Find(&tags)

	utils.ResponseJSON(w, http.StatusOK, tags)
}

func SeedTags(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	db.Delete(&models.Tag{})

	for _, tag := range data.Tags {
		db.Create(&tag)
	}

	utils.ResponseJSON(w, http.StatusOK, "Tags seeded successfully")
}
