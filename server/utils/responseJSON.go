package utils

import (
	"encoding/json"
	"log"
	"net/http"
)

func ResponseJSON(w http.ResponseWriter, code int, payload interface{}) {
	response, jsonErr := json.Marshal(payload)
	if jsonErr != nil {
		w.WriteHeader(http.StatusInternalServerError)
		_, err := w.Write([]byte("HTTP 500: Internal Server Error"))
		if err != nil {
			log.Fatal("Could not write response")
		}
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(code)
	_, err := w.Write(response)
	if err != nil {
		log.Fatal("Could not write response")
	}
}
