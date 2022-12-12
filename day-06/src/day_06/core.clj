(ns day-06.core
  (:gen-class))

(defn detect-n-uniq-group [input n]
  (loop [ group []
          i 0]
              (if (and (= (count group) n)
                       (apply distinct? group))
                [i group]
                (let [ next-group (if (= (count group) n) (subvec group 1) group)]
                  (recur (conj next-group (get input i)) (+ i 1))))))
                  ;(if (= (count group) n)
                    ;(recur (conj (subvec group 1) (get input i))
                           ;(+ i 1))
                    ;(recur (conj group (get input i))
                           ;(+ i 1))))))
(defn -main
  []
  (let [input (slurp "./resources/input.txt")
        part-1 (detect-n-uniq-group input 4)
        part-2 (detect-n-uniq-group input 14)]
    (prn 'part-1= part-1)
    (prn 'part-2= part-2)))
