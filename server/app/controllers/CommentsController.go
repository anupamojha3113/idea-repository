package controllers

import (
	"github.com/Lalit3716/ideabook_server/app/models"
	"github.com/Lalit3716/ideabook_server/utils"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
	"net/http"
	"strconv"
)

func PostComment(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)

	id, err := strconv.Atoi(params["id"])

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Invalid idea id")
		return
	}

	var idea models.Idea

	if err := db.Model(&models.Idea{}).First(&idea, id).Error; err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Idea not found")
		return
	}

	uid := r.Context().Value("uid").(string)

	var reqBody struct {
		Content string `json:"content"`
	}

	if err := utils.ParseJSON(r, &reqBody); err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, err)
		return
	}

	comment := models.Comment{
		Content: reqBody.Content,
		UserID:  uid,
	}

	if err := db.Model(&idea).Association("Comments").Append(&comment); err != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, err)
		return
	}

	idea.CommentsTotal++
	db.Save(&idea)

	utils.ResponseJSON(w, http.StatusOK, comment)
}

func DeleteComment(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)

	id, err := strconv.Atoi(params["id"])

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Invalid idea id")
		return
	}

	var idea models.Idea

	if err := db.Model(&models.Idea{}).First(&idea, id).Error; err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Idea not found")
		return
	}

	cid, err1 := strconv.Atoi(params["cid"])

	if err1 != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Invalid comment id")
		return
	}

	var comment models.Comment

	if err := db.Model(&models.Comment{}).First(&comment, cid).Error; err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Comment not found")
		return
	}

	if err := db.Model(&idea).Association("Comments").Delete(&comment); err != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, err)
		return
	}

	db.Unscoped().Delete(&comment)
	idea.CommentsTotal--
	db.Save(&idea)
	utils.ResponseJSON(w, http.StatusOK, "Comment deleted")
}
