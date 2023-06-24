(ns app.app
  (:require [app.details :refer [details]]
            [app.search-params :refer [search-params]]
            ["react-router-dom" :refer [BrowserRouter Routes Route Link]]
            ["@tanstack/react-query" :refer [QueryClient QueryClientProvider]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(def query-client
  (QueryClient. {:default-options
                 {:queries {:stale-time (apply * [1000 60 10])
                            :cache-time js/Infinity}}}))

(defui app []
  ($ BrowserRouter
     ($ QueryClientProvider {:client query-client}
        ($ :header ($ Link {:to "/"} "Adopt Me!"))
        ($ Routes
           ($ Route {:path "/details/:id-:animal" :element ($ details)})
           ($ Route {:path "/home" :element ($ search-params)})))))

(defonce root
  (uix.dom/create-root (js/document.getElementById "root")))

(defn render []
  (uix.dom/render-root ($ app) root))

(defn ^:export init []
  (render))