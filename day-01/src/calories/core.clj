(ns calories.core 
  (:require [clojure.string :as str]))

(defn to_int 
  [x] 
  (try (Integer/parseInt x)
       (catch Exception _ 0)))

(defn sum-partition [col]
  (->> col
       (map to_int)
       (apply +)))


(def calories-by-elf
  (->>
       (slurp "./resources/input.txt")
       (str/split-lines)
       (partition-by #(not= % ""))
       (map sum-partition)))


(defn -main
  []
  (let [max-calories (apply max calories-by-elf) 
        top-elves (->>
                   calories-by-elf
                   (sort >)
                   (take 3)
                   (apply +))]
      (println max-calories) 
      (println top-elves)))