(ns app.results
  (:require [app.pet :refer [pet]]
            [cljs.core :as cljs]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui results [pets]
  (let [pets (:children pets)]
    ($ :div {:class-name "search"} 
       (if (empty? pets)
         ($ :h1 "No Pets Found")
         (map #($ pet {:animal   (:animal %)
                       :name     (:name %)
                       :id       (:id %)
                       :breed    (:breed %)
                       :images   (:images %)
                       :location (str (:city %) ", " (:state %))
                       :key      (:id %)})
              pets)))))