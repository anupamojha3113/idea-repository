package controllers

import (
	"fmt"
	"github.com/Lalit3716/ideabook_server/app/models"
	"github.com/Lalit3716/ideabook_server/utils"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
	"net/http"
	"strconv"
)

func LikeIdea(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)

	id, err := strconv.Atoi(params["id"])

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Invalid idea id")
		return
	}

	uid := r.Context().Value("uid").(string)

	var idea models.Idea

	if err := db.First(&idea, id).Error; err != nil {
		utils.ResponseJSON(w, http.StatusNotFound, "Idea not found")
		return
	}

	var like models.Like

	err1 := db.Model(&like).Where("user_id = ? AND idea_id = ?", uid, id).First(&like).Error
	if err1 == nil {
		fmt.Println("Already liked!")
		utils.ResponseJSON(w, http.StatusBadRequest, "Already liked!")
		return
	}

	newLike := models.Like{
		UserID: uid,
	}

	err2 := db.Model(&idea).Association("Likes").Append(&newLike)
	if err2 != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, "Error liking idea")
		return
	}

	idea.LikesTotal++

	db.Save(&idea)

	utils.ResponseJSON(w, http.StatusOK, "Liked idea")
}

func DisLikeIdea(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)

	id, err := strconv.Atoi(params["id"])

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Invalid idea id")
		return
	}

	var idea models.Idea

	uid := r.Context().Value("uid").(string)

	if err := db.First(&idea, id).Error; err != nil {
		utils.ResponseJSON(w, http.StatusNotFound, "Idea not found")
		return
	}

	var like models.Like

	err1 := db.Model(&like).Where("user_id = ? AND idea_id = ?", uid, id).First(&like).Error
	if err1 != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Already disliked!")
		return
	}

	err2 := db.Model(&idea).Association("Likes").Delete(&like)
	if err2 != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, "Error disliking idea")
		return
	}

	db.Unscoped().Delete(&like)

	idea.LikesTotal--

	db.Save(&idea)

	utils.ResponseJSON(w, http.StatusOK, "Disliked idea")
}
