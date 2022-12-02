(ns day-02.core
  (:require [clojure.string :as str]))
  ;(:gen-class))

(def shape-scores {"X" 1 "Y" 2})

(def game-scores {"A Y" 6 "B Z" 6 "C X" 6
                  "A X" 3 "B Y" 3 "C Z" 3})

(def lose-movement {"A" "Z"
                    "B" "X" 
                    "C" "Y"})

(def win-movement {"A" "Y" 
                   "B" "Z" 
                   "C" "X"})

(def draw-movement {"A" "X" 
                    "B" "Y" 
                    "C" "Z"})

(defn get-movement [game]
  (let [[your-hand my-action] (str/split game #" ") 
        my-movement (cond
                   (= my-action "X") (get lose-movement your-hand)
                   (= my-action "Y") (get draw-movement your-hand)
                   (= my-action "Z") (get win-movement your-hand))]
    (str your-hand " " my-movement)))

(defn score [game]
  (let [[_ my-hand] (str/split game #" ") 
        shape-score (get shape-scores my-hand 3)
        game-score (get game-scores game 0)]
    
      (+ shape-score game-score)))

(defn -main
  []
  (let [lines (->> (slurp "./resources/input.txt")
                   (str/split-lines)) 
        first (->> lines
                   (map score)
                   (apply +))
        second (->> lines
                    (map (comp score get-movement))
                    (apply +))]
    (prn (str "first part:" first)
         (str "second part:" second))))