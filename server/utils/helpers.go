package utils

import (
	"github.com/Lalit3716/ideabook_server/app/models"
	"gorm.io/gorm"
	"strings"
)

func ParseTags(db *gorm.DB, tagsStr string, delimiter string) ([]uint, error) {
	var tagsArray []string
	var tags []uint

	if tagsStr != "" {
		tagsArray = strings.Split(tagsStr, delimiter)
	}

	for _, tag := range tagsArray {
		var t models.Tag
		result := db.Where("name = ?", tag).First(&t)
		if result.Error != nil {
			return nil, result.Error
		}
		tags = append(tags, t.ID)
	}

	return tags, nil
}

func UniqueIdeas(ideas []models.Idea) []models.Idea {
	keys := make(map[uint]bool)
	var list []models.Idea
	for _, entry := range ideas {
		if _, value := keys[entry.ID]; !value {
			keys[entry.ID] = true
			list = append(list, entry)
		}
	}

	if list == nil {
		list = make([]models.Idea, 0)
	}

	return list
}
