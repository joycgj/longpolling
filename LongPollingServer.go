package main

import (
	"net/http"
	"fmt"
	"sync/atomic"
	"time"
	"math/rand"
)

var sequenceId uint64 = 0;

var count uint64 = 0;

func longPollingHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Printf("第 %d 次 long polling\n", atomic.AddUint64(&count, 1))

	seconds := time.Second * time.Duration(rand.Intn(10))
	fmt.Printf("wait %v seconds\n", seconds)

	time.Sleep(seconds)

	fmt.Fprint(w, atomic.AddUint64(&sequenceId, 1))
}

func main() {
	http.HandleFunc("/", longPollingHandler)
	http.ListenAndServe(":8080", nil)
}
