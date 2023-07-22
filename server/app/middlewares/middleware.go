package middlewares

import "github.com/Lalit3716/ideabook_server/app"

func SetupMiddlewares(a *app.App) {
	a.Router.Use(authMiddleware)
}
