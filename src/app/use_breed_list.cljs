(ns app.use-breed-list
  (:require ["@tanstack/react-query" :refer [useQuery]]
            [app.fetch-breed-list :refer [fetch-breed-list]]))

(defn use-breed-list [animal]
  (let [results (useQuery (clj->js ["breeds" animal]) fetch-breed-list) 
        data    (.-data results)
        breeds  (when (some? data)(.-breeds data)) 
        breed-list (if (seq breeds)
                     breeds
                     [])] 
    [breed-list (.-status results)]))