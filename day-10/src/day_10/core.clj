(ns day-10.core
  (:gen-class)
  (:require [clojure.string :as st]))

(defn log-state [state]
  (do
    (prn 'state= state)
    state))

(defn update-cycle [state]
  (assoc state :cycle (+ 1 (:cycle state))))

(defn update-register [state adder]
  (assoc state :register-x (+ adder (:register-x state))))

(defn update-signal-strength [state]
  (let [rx (:register-x state)
        cy (:cycle state)
        ss (:signal-strength state)]
    (if-not (contains? #{20 60 100 140 180 220} cy)
      state
      (assoc state :signal-strength (conj ss (* cy rx))))))

(defn update-sprite [state]
  (let [sprite (:sprite state)
        rx (:register-x state)
        in-position #(<= (- rx 1) %1 (+ 1 rx))
        next-sprite (->> sprite
                    (keep-indexed (fn [idx _] (if (in-position idx) "#" ".")))
                    (st/join))]
    (assoc state :sprite next-sprite)))

(defn update-screen [state]
  (let [cy (:cycle state)
        pos (mod (- cy 1) 40)
        sprite (:sprite state) 
        pixel (get sprite pos)
        screen (if (= pos 0) [] (:screen state))]
  (assoc state :screen (conj screen pixel))))

(defn draw-screen [state]
  (let [cy (:cycle state)
        screen (:screen state)]
    (do
      (when (contains? #{40 80 120 160 200 240} cy) (println (st/join screen)))
      state)))

(defn process-line [state line]
  ;(prn 'line= line)
  (let [is-noop (= line "noop")
        next-cycle #(->(update-cycle %1)
                       (update-signal-strength)
                       (draw-screen)
                       (update-screen))
                       ;(log-state))
        adder (if is-noop 0 (->> line
                                 (re-find #"addx (-*\d{1,})")
                                 (second)
                                 (Integer/parseInt)))]
    (-> (if is-noop state (next-cycle state))
        (next-cycle)
        (update-register adder)
        (update-sprite))))
        ;(log-state))))

(def init-state
  {:cycle 0
   :register-x 1
   :signal-strength []
   :sprite "###....................................."
   :screen []})

(defn -main
  []
  (let [lines (->>
                (slurp "./resources/input.txt")
                (clojure.string/split-lines))
        part-1 (->> lines
                    (reduce process-line init-state)
                    (:signal-strength)
                    (reduce +))]
    (prn 'part-1= part-1)))
