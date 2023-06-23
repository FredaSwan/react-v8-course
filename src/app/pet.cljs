(ns app.pet 
  (:require [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui pet [{:keys [name animal breed images location id]}]
  (let [hero (if (empty? images)
               "http://pets-images.dev-apis.com/pets/none.jpg"
               (first images))
        _     (println location)
        #_#_[:keys [city state]] location]
    ($ :a {:href (str "/details/" id) :class-name "pet"}
       ($ :div {:class-name "image-container"}
          ($ :img {:src hero :alt name}))
       ($ :div {:class-name "info"}
          ($ :h1 name)
          ($ :h2 (str animal " – " breed " – " location ))))))
