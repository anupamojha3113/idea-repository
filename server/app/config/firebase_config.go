package config

import (
	"context"
	"firebase.google.com/go/auth"
	"fmt"
	"google.golang.org/api/option"

	firebase "firebase.google.com/go"
)

var Client *auth.Client
var Ctx *context.Context

func InitFirebaseApp() {
	opt := option.WithCredentialsFile("google-services.json")

	ctx := context.Background()
	app, err := firebase.NewApp(ctx, nil, opt)

	if err != nil {
		fmt.Println("Error initializing app:", err)
	}

	client, e := app.Auth(ctx)
	if e != nil {
		fmt.Println("Error getting Auth client:", e)
	}

	fmt.Println("Firebase app initialized Successfully")

	Client = client
	Ctx = &ctx
}
