package controllers

import (
	"github.com/Lalit3716/ideabook_server/app/models"
	"github.com/Lalit3716/ideabook_server/utils"
	"github.com/gorilla/mux"
	"gorm.io/gorm"
	"net/http"
	"strconv"
)

type CreateIdeaDao struct {
	models.Idea
	Tags []string `json:"tags"`
}

func GetIdeas(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	var ideas []models.Idea
	queryParams := r.URL.Query()

	tags, err := utils.ParseTags(db, queryParams.Get("tags"), ",")

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Invalid tags")
		return
	}

	search := queryParams.Get("search")
	orderBy := queryParams.Get("orderBy")
	sort := queryParams.Get("sort")

	if orderBy == "" {
		orderBy = "created_at"
	}

	if sort == "" {
		sort = "desc"
	}

	order := orderBy + " " + sort
	//ogSearch := search
	if search == "" {
		search = "%%"
	} else {
		search = "%" + search + "%"
	}

	if len(tags) == 0 {
		result := db.Where("title LIKE ? OR description LIKE ?", search, search).Preload("Likes").Preload("Tags").Order(order).Find(&ideas)
		if result.Error != nil {
			utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
			return
		}
	} else {
		result := db.Joins("JOIN idea_tags ON idea_tags.idea_id = id").Where("idea_tags.tag_id IN (?) AND (title LIKE ? OR description LIKE ?)", tags, search, search).Preload("Likes").Preload("Tags").Order(order).Find(&ideas)
		if result.Error != nil {
			utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
			return
		}

		utils.ResponseJSON(w, http.StatusOK, utils.UniqueIdeas(ideas))
		return
	}

	if ideas == nil {
		ideas = make([]models.Idea, 0)
	}

	utils.ResponseJSON(w, http.StatusOK, ideas)
}

func GetYourIdeas(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	var ideas []models.Idea
	queryParams := r.URL.Query()

	tags, err := utils.ParseTags(db, queryParams.Get("tags"), ",")

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, "Invalid tags")
		return
	}

	search := queryParams.Get("search")
	orderBy := queryParams.Get("orderBy")
	sort := queryParams.Get("sort")

	if orderBy == "" {
		orderBy = "created_at"
	}

	if sort == "" {
		sort = "desc"
	}

	order := orderBy + " " + sort
	//ogSearch := search
	if search == "" {
		search = "%%"
	} else {
		search = "%" + search + "%"
	}

	uid := r.Context().Value("uid").(string)

	if len(tags) == 0 {
		result := db.Where("user_uid = ? AND (title LIKE ? OR description LIKE ?)", uid, search, search).Preload("Likes").Preload("Tags").Order(order).Find(&ideas)
		if result.Error != nil {
			utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
			return
		}
	} else {
		result := db.Joins("JOIN idea_tags ON idea_tags.idea_id = id").Where("idea_tags.tag_id IN (?) AND (title LIKE ? OR description LIKE ?) AND (user_uid = ?)", tags, search, search, uid).Preload("Likes").Preload("Tags").Order(order).Find(&ideas)
		if result.Error != nil {
			utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
			return
		}

		utils.ResponseJSON(w, http.StatusOK, utils.UniqueIdeas(ideas))
		return
	}

	if ideas == nil {
		ideas = make([]models.Idea, 0)
	}

	utils.ResponseJSON(w, http.StatusOK, ideas)
}

func GetIdea(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)

	id, err := strconv.Atoi(params["id"])

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, err)
		return
	}

	var idea models.Idea

	result := db.Model(&models.Idea{}).Preload("Likes").Preload("Comments").Preload("Tags").First(&idea, id)

	if result.Error != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
		return
	}

	utils.ResponseJSON(w, http.StatusOK, idea)
}

func CreateIdea(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	var reqBody CreateIdeaDao

	if err := utils.ParseJSON(r, &reqBody); err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, err)
		return
	}

	var idea models.Idea

	uid := r.Context().Value("uid").(string)
	idea.UserUid = uid

	username := r.Context().Value("username").(string)
	idea.Username = username

	idea.Title = reqBody.Title
	idea.Description = reqBody.Description

	result := db.Create(&idea)

	if result.Error != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
		return
	}

	var tags []models.Tag

	for _, tag := range reqBody.Tags {
		var t models.Tag

		result := db.First(&t, "name = ?", tag)

		if result.Error != nil {
			utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
			return
		}

		tags = append(tags, t)
	}

	err := db.Model(&idea).Association("Tags").Append(tags)
	if err != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, err)
		return
	}

	idea.Likes = make([]models.Like, 0)
	idea.Comments = make([]models.Comment, 0)
	idea.Tags = tags
	utils.ResponseJSON(w, http.StatusCreated, "Created Successfully")
}

func UpdateIdea(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	var reqBody CreateIdeaDao

	id, err := strconv.Atoi(params["id"])

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, err)
		return
	}

	var idea models.Idea

	if err := utils.ParseJSON(r, &reqBody); err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, err)
		return
	}

	// Find the idea
	result := db.First(&idea, id)

	if result.Error != nil {
		utils.ResponseJSON(w, http.StatusNotFound, "Idea not found")
		return
	}

	// Check if the user is the owner of the idea
	uid := r.Context().Value("uid").(string)

	if idea.UserUid != uid {
		utils.ResponseJSON(w, http.StatusUnauthorized, "Unauthorized")
		return
	}

	var tags []models.Tag

	for _, tag := range reqBody.Tags {
		var t models.Tag

		result := db.First(&t, "name = ?", tag)

		if result.Error != nil {
			utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
			return
		}

		tags = append(tags, t)
	}

	if len(tags) > 0 {
		err := db.Model(&idea).Association("Tags").Replace(tags)
		if err != nil {
			utils.ResponseJSON(w, http.StatusInternalServerError, err)
			return
		}
	}

	idea.Title = reqBody.Title
	idea.Description = reqBody.Description

	result = db.Save(&idea)

	if result.Error != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
		return
	}

	utils.ResponseJSON(w, http.StatusOK, "Updated Idea Successfully")
}

func DeleteIdea(db *gorm.DB, w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)

	id, err := strconv.Atoi(params["id"])

	if err != nil {
		utils.ResponseJSON(w, http.StatusBadRequest, err)
		return
	}

	// Find the idea
	var idea models.Idea

	result := db.First(&idea, id)

	if result.Error != nil {
		utils.ResponseJSON(w, http.StatusNotFound, "Idea not found")
		return
	}

	// Check if the user is the owner of the idea
	uid := r.Context().Value("uid").(string)

	if idea.UserUid != uid {
		utils.ResponseJSON(w, http.StatusUnauthorized, "Unauthorized")
		return
	}

	// Delete the Tags Associations first
	err = db.Model(&idea).Association("Tags").Clear()
	if err != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, err)
		return
	}

	// Delete the idea
	result = db.Unscoped().Select("Comments", "Likes").Delete(&idea)

	if result.Error != nil {
		utils.ResponseJSON(w, http.StatusInternalServerError, result.Error)
		return
	}

	utils.ResponseJSON(w, http.StatusOK, "Idea deleted successfully")
}
