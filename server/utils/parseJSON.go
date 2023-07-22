package utils

import (
	"encoding/json"
	"net/http"
)

func ParseJSON(r *http.Request, v interface{}) error {
	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(v); err != nil {
		return err
	}

	err := r.Body.Close()
	if err != nil {
		return err
	}

	return nil
}
