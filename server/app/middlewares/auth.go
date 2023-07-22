package middlewares

import (
	"context"
	"github.com/Lalit3716/ideabook_server/app/config"
	"net/http"
)

func authMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		// Get auth token from header
		token := r.Header.Get("Authorization")
		// Check if token is valid
		idToken, err := config.Client.VerifyIDToken(*config.Ctx, token)

		if err != nil {
			http.Error(w, "Forbidden", http.StatusForbidden)
			return
		}

		// HardCoding ID token for development
		//var idToken struct {
		//	UID    string
		//	Claims map[string]interface{}
		//}
		//idToken.UID = "123456789"
		//idToken.Claims = make(map[string]interface{})
		//idToken.Claims["name"] = "Lalit"

		var ctxWithUser = context.WithValue(r.Context(), "uid", idToken.UID)
		ctxWithUser = context.WithValue(ctxWithUser, "username", idToken.Claims["name"])

		next.ServeHTTP(w, r.WithContext(ctxWithUser))
	})
}
