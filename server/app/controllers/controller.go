package controllers

import (
	"gorm.io/gorm"
	"net/http"

	"github.com/Lalit3716/ideabook_server/utils"
)

func GetHello(db *gorm.DB, w http.ResponseWriter, _ *http.Request) {
	utils.ResponseJSON(w, http.StatusOK, "Hello World!!")
}
