(ns app.app
  (:require [app.details :refer [details]]
            [app.search-params :refer [search-params]]
            ["react-router-dom" :refer [BrowserRouter Routes Route]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui app []
  ($ BrowserRouter
     ($ :h1 "Adopt Me!")
     ($ Routes
        ($ Route {:path "/details/:id" :element ($ details)})
        ($ Route {:path "/" :element ($ search-params)}))))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))