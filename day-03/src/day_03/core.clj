(ns day-03.core
  (:gen-class) 
  (:require [clojure.set :as set]
            [clojure.string :as string]))

(defn priority
  [input]
  (let [base-lower (byte \a)
        base-upper (byte \A)
        x (byte input)]
    (if (< x base-lower)
      (+ 27 (- x base-upper))
      (+ 1 (- x base-lower)))))
  
(defn to-set [col]
  (reduce #(conj %1 %2) #{} col))

(defn common-char [x]
  (let [[l, r] (split-at (/ (count x) 2) x)]
    (->
     (set/intersection 
          (to-set l) 
          (to-set r))
     (first))))

(defn find-badged [three-backpacks]
  (first
   (apply set/intersection 
         (map to-set three-backpacks))))
    
(defn -main
  [& _args]
  (let [lines (->> (slurp "./resources/input.txt")
                   (string/split-lines))
        first (->> lines
                   (map (comp priority common-char))
                   (apply +))
        second (->> lines
                    (partition 3)
                    (map (comp priority find-badged))
                    (apply +))]
    (prn 'First= first)
    (prn 'Second= second)))