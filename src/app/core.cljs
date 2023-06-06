(ns app.core
  (:require [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui pet [{:keys [name animal breed]}]
  ($ :div
     ($ :h1 name)
     ($ :h2 animal)
     ($ :h2 breed)))

(defui app []
  ($ :.app
     ($ :h1 "Adopt Me!")
        ($ pet {:name "Pickles" :animal "cat" :breed "Siamese"})
        ($ pet {:name "Doink" :animal "cat" :breed "Mixed"})
        ($ pet {:name "Zoë" :animal "dog" :breed "Staffordshire Bull Terrier"})
        ($ pet {:name "Frank" :animal "bird" :breed "Parrot"})))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))