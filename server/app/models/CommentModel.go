package models

import "gorm.io/gorm"

type Comment struct {
	gorm.Model
	UserID  string `json:"user_id"`
	IdeaID  uint   `json:"idea_id"`
	Content string `json:"content"`
}
