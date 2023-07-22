package config

import (
	"fmt"
	"github.com/joho/godotenv"
	"os"
	"strconv"
)

type DBConfig struct {
	Dialect  string
	Host     string
	Port     int64
	Username string
	Password string
	Dbname   string
	Charset  string
}

func GetDBConfig() *DBConfig {
	err := godotenv.Load(".env.dev")

	if err != nil {
		fmt.Println("Error loading env file")
	}

	// Get the value of the PORT environment variable
	port, e := strconv.ParseInt(os.Getenv("DB_PORT"), 10, 16)

	if e != nil {
		fmt.Println("Error parsing DB_PORT in env file")
	}

	return &DBConfig{
		Dialect:  os.Getenv("DB_DIALECT"),
		Host:     os.Getenv("DB_HOST"),
		Port:     port,
		Username: os.Getenv("DB_USERNAME"),
		Password: os.Getenv("DB_PASSWORD"),
		Dbname:   os.Getenv("DB_NAME"),
		Charset:  "utf8mb4",
	}
}
