(ns app.details
  (:require ["@tanstack/react-query" :refer [useQuery]]
            ["react-router-dom" :refer [useParams]]
            [app.carousel :refer [carousel]]
            [app.fetch-pet :refer [fetch-pet]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defn pick-loader [animal]
  (case animal
    "bird"    "🦜"
    "cat"     "🐱"
    "dog"     "🐶"
    "rabbit"  "🐰"
    "reptile" "🦎"
    "⏳"))

(defui details []
  (let [id      (.-id (useParams))
        animal  (.-animal (useParams))
        result (useQuery (clj->js ["details" id]) fetch-pet)] 
    (cond 
      (.-isLoading result) ($ :div {:class-name "loading-pane"}
                               ($ :h2 {:class-name "loader"} (pick-loader animal)))
      :else (let [pet (first (.-pets (.-data result)))
                  pet-name (.-name pet)] 
              (js/console.log pet)
              ($ :div {:class-name "details"}
                 ($ carousel {:images (.-images pet)})
                 ($ :div
                    ($ :h1 pet-name)
                    ($ :h2 (str ^string (.-animal pet) " – " ^string (.-breed pet) " – " (.-city pet) ", " (.-state pet))
                       ($ :p (.-description pet))
                       ($ :button (str "Adopt " pet-name)))
                    ))))))