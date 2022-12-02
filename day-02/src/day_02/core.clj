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

(defn calc-my-shape [game]
  (let [[your-shape my-action] (str/split game #" ")
        my-shape (cond
                   (= my-action "X") (get lose-movement your-shape)
                   (= my-action "Y") (get draw-movement your-shape)
                   (= my-action "Z") (get win-movement your-shape))]
    (str your-shape " " my-shape)))

(defn score [game]
  (let [my-shape (second (str/split game #" ")) 
        shape-score (get shape-scores my-shape 3) 
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
                    (map (comp score calc-my-shape))
                    (apply +))]
    (prn (str "first part:" first)
         (str "second part:" second))))