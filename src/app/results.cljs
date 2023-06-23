(ns app.results
  (:require [app.pet :refer [pet]]
            [cljs.core :as cljs]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui results [pets-data]
  (let [pets (:children pets-data)]
    ($ :div {:class-name "search"} 
       (if (empty? pets)
         ($ :h1 "No Pets Found")
         (map #($ pet {:animal   (:animal %)
                       :name     (:name %)
                       :breed    (:breed %)
                       :images   (:images %)
                       :location {(:city %) (:state %)}
                       :key      (:id %)})
              pets)))))