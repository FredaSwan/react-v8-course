(ns app.carousel
  (:require [uix.core :as uix :refer [$]]
            [uix.dom]))

(defn handle-index-click [this e] 
  (let [new-active (clj->js {:active (js/parseInt (.-index (.-dataset (.-target e))))})] 
    (.setState this new-active)))

(def carousel
  (uix/create-class
    {:display-name "carousel"
     :getInitialState (fn [] (clj->js {:active 0}))
     :default-props (clj->js {:images ["http://pets-images.dev-apis.com/pets/none.jpg"]}) 
     :render (fn []
               (this-as this 
                        (let [active (.-active (.-state this))
                              images (:images (js->clj (.-argv (.-props this)) :keywordize-keys true))]
                          ($ :div.carousel
                             ($ :img {:src (get images active) :alt "animal"})
                             ($ :div.carousel-smaller
                                (map-indexed (fn [index photo] 
                                               ($ :img {:on-click (partial handle-index-click this) 
                                                        :data-index index
                                                        :key photo
                                                        :src photo
                                                        :class (if (= index active) "active" "")
                                                        :alt "animal thumbnail"}))
                                             images))))))}))