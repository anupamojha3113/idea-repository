package app

import (
	"fmt"
	"github.com/Lalit3716/ideabook_server/app/config"
	"github.com/Lalit3716/ideabook_server/app/models"
	"github.com/gorilla/mux"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"log"
	"net/http"
)

type App struct {
	DB     *gorm.DB
	Router *mux.Router
}

func (a *App) Initialize(dbConfig *config.DBConfig) {
	dsn := fmt.Sprintf("%s:%s@tcp(%s:%d)/%s?charset=%s&parseTime=True&loc=Local",
		dbConfig.Username,
		dbConfig.Password,
		dbConfig.Host,
		dbConfig.Port,
		dbConfig.Dbname,
		dbConfig.Charset,
	)

	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Error),
	})

	if err != nil {
		log.Fatal("Could not connect database")
	} else {
		log.Println("Connected to database :)")
	}

	a.DB = models.DBMigrate(db)
	a.Router = mux.NewRouter()
	config.InitFirebaseApp()
}

func (a *App) HandleRequest(f func(db *gorm.DB, w http.ResponseWriter, r *http.Request)) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		f(a.DB, w, r)
	}
}

func (a *App) Run(addr string) {
	go log.Printf("Server started on PORT %s\n\n", addr)
	err := http.ListenAndServe(addr, a.Router)
	if err != nil {
		log.Fatal("Could not start server :(")
	}
}
