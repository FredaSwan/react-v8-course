(ns app.pet
  (:require ["react-router-dom" :refer [Link]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui pet [{:keys [name animal breed images location id]}]
  (let [hero (if (empty? images)
               "http://pets-images.dev-apis.com/pets/none.jpg"
               (first images))]
    ($ Link {:to (str "/details/" id "-" animal) :class-name "pet"}
       ($ :div {:class-name "image-container"}
          ($ :img {:src hero :alt name}))
       ($ :div {:class-name "info"}
          ($ :h1 name)
          ($ :h2 (str animal " – " breed " – " location ))))))
