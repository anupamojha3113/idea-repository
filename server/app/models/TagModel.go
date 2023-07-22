package models

import "gorm.io/gorm"

type Tag struct {
	gorm.Model
	Name  string `gorm:"type:varchar(100);not null" json:"name"`
	Icon  string `gorm:"type:varchar(1000);not null" json:"icon"`
	Ideas []Idea `gorm:"many2many:idea_tags;" json:"ideas"`
}
