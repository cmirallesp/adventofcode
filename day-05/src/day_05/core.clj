(ns day-05.core
  (:gen-class)
  (:require [clojure.string :as string]))

;(def STEPS 3)
(def STEPS 8)
;(def NO-STEPS 5)
(def NO-STEPS 10)


(defn transpose [col]
  (apply mapv vector col))

(defn drop-empty-cells [col]
  (filter #(re-matches #"\[\S\]\s*" %) col))

(defn parse-cells [line]
  """Given a string '[A]    [B]' returns a list of cells ('[A]','   ','[B]')"""
  (re-seq #"\S{3}\s|\S{3}|\s{4}|\s{3}" line))

(defn pop-ls [col] 
  (let [[x & xs] col]
    (or xs ())))

(defn peek-ls [col] 
  (let [[x & xs] col]
    x) )

(defn remove-idx [i items]
  (->> items
       (keep-indexed #(when-not (= i %1) %2))))

(defn replace-idx [i item items]
  (keep-indexed #(if (= i %1) item %2) items))

(defn get-idx [i items]
  (->> items
       (keep-indexed #(when (= i %1) %2))
       (flatten)))

(defn move-crate-2 [i from to board & [{:keys [model] :or {model 9000}}]]
  (let [source (get-idx from board)
        cell (if (= model 9000) (peek-ls source) (first (get-idx i source)))
        target (get-idx to board)]
    ;(prn 'source= source)
    ;(prn 'cell= cell)
    ;(prn 'target= target)
    (->> board
        (replace-idx from (if (= model 9000) (pop-ls source) (remove-idx i source)))
        (replace-idx to (conj target cell)))))

(defn process-step [step board model]
  (let [[_, m, f, t] (re-find #"[^\d]*(\d+).*(\d+).*(\d+)" step)
        number-of-steps (Integer/parseInt m)
        from (Integer/parseInt f)
        to (Integer/parseInt t)]
    ;(prn (str 'number-of-steps= number-of-steps) (str 'from= from) (str 'to= to))
    (loop [i number-of-steps 
           result board]
      ;(prn 'i= i)
      (if (= i 0)
        (do
          ;(prn 'result= result)
          result)
        (recur (- i 1)
               (move-crate-2 (- i 1) (- from 1) (- to 1) result {:model model}))))))

(defn process-steps [steps board model]
  (loop [i-step 0
           result board]
      (if (= i-step (count steps)) result
        (let [step (first (get-idx i-step steps))
              next-board (process-step step result model)]
          ;(prn 'step= i-step)
          (recur (+ i-step 1)
                 next-board)))))


(defn -main []
  (let [lines (->> (slurp "./resources/input.txt")
                   (string/split-lines))
       board (->> lines
                   (take STEPS)
                   (map parse-cells)
                   (transpose)
                   (map drop-empty-cells)
                   (map #(map string/trim %)))
        steps (drop NO-STEPS lines)
        result-1 (process-steps steps board 9000)
        part-1 (map peek-ls result-1)
        result-2 (process-steps steps board 9001)
        part-2 (map peek-ls result-2)]
    (prn 'part-1= part-1)
    (prn 'part-2= part-2)))
