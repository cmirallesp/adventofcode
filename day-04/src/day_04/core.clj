(ns day-04.core
  (:gen-class)
  (:require [clojure.string :as string]))

(defn fully-overalpped [range1 range2]
  (and (>= (first range1) (first range2))
       (<= (second range1) (second range2))))

(defn partially-overlapped [range1 range2]
  (and (>= (first range1) (first range2))
       (<= (first range1) (second range2))))

(defn full-overlapping [range1 range2]
  (or (fully-overalpped range1 range2)
      (fully-overalpped range2 range1)))

(defn partial-overlapping [range1 range2]
  (or (partially-overlapped range1 range2)
      (partially-overlapped range2 range1)))

(defn -main
  [& _args]
  (let [ranges (->> (slurp "./resources/input.txt")
                    (string/split-lines)
                    (mapcat #(string/split % #",")) 
                    (mapcat #(string/split % #"-"))
                    (map #(Integer/parseInt %))
                    (partition 2) (partition 2))
   part-1 (->> ranges 
               (filter #(apply full-overlapping %))
               (count))
   part-2 (->> ranges
               (filter #(apply partial-overlapping %))
               (count))]
    (prn 'Part1= part-1)
    (prn 'Part2= part-2)))
