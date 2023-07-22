package routes

import (
	"github.com/Lalit3716/ideabook_server/app"
	"github.com/Lalit3716/ideabook_server/app/controllers"
)

func SetupRoutes(a *app.App) {
	router := a.Router
	router.HandleFunc("/api/v1", a.HandleRequest(controllers.GetHello)).Methods("GET")

	// Idea Routes
	router.HandleFunc("/api/v1/ideas", a.HandleRequest(controllers.GetIdeas)).Methods("GET")
	router.HandleFunc("/api/v1/ideas/me", a.HandleRequest(controllers.GetYourIdeas)).Methods("GET")
	router.HandleFunc("/api/v1/ideas/{id}", a.HandleRequest(controllers.GetIdea)).Methods("GET")
	router.HandleFunc("/api/v1/ideas", a.HandleRequest(controllers.CreateIdea)).Methods("POST")
	router.HandleFunc("/api/v1/ideas/{id}", a.HandleRequest(controllers.UpdateIdea)).Methods("PUT")
	router.HandleFunc("/api/v1/ideas/{id}", a.HandleRequest(controllers.DeleteIdea)).Methods("DELETE")

	// Like Routes
	router.HandleFunc("/api/v1/ideas/{id}/like", a.HandleRequest(controllers.LikeIdea)).Methods("POST")
	router.HandleFunc("/api/v1/ideas/{id}/like", a.HandleRequest(controllers.DisLikeIdea)).Methods("DELETE")

	// Comment Routes
	router.HandleFunc("/api/v1/ideas/{id}/comments", a.HandleRequest(controllers.PostComment)).Methods("POST")
	router.HandleFunc("/api/v1/ideas/{id}/comments/{cid}", a.HandleRequest(controllers.DeleteComment)).Methods("DELETE")

	// Tag Routes
	router.HandleFunc("/api/v1/tags", a.HandleRequest(controllers.GetTags)).Methods("GET")
	router.HandleFunc("/api/v1/tags", a.HandleRequest(controllers.SeedTags)).Methods("POST")
}
