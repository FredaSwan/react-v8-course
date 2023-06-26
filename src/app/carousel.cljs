(ns app.carousel
  (:require [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(def carousel
  (uix.core/create-class
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
                                               ($ :img {:key photo
                                                        :src photo
                                                        :class (if (= index active) "active" "")
                                                        :alt "animal thumbnail"}))
                                             images))))))}))

;; class component reference example from uix lib
#_(def error-boundary
  (uix.core/create-class
   {:displayName "error-boundary"
    :getInitialState (fn [] #js {:error nil})
    :getDerivedStateFromError (fn [error] #js {:error error})
    :componentDidCatch (fn [error error-info]
                         (this-as this
                                  (let [props (.. this -props -argv)]
                                    (when-let [on-error (:on-error props)]
                                      (on-error error)))))
    :render (fn []
              (this-as this
                       (if (.. this -state -error)
                         ($ :div "error")
                         (.. this -props -children))))}))