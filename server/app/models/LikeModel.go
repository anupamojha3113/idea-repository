package models

import (
	"gorm.io/gorm"
)

type Like struct {
	gorm.Model
	UserID string `json:"user_id"`
	IdeaID uint   `json:"idea_id"`
}
