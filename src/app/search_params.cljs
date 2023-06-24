(ns app.search-params
  (:require [app.results :refer [results]]
            [app.use-breed-list :refer [use-breed-list]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(def animals ["bird" "cat" "dog" "rabbit" "reptile"])

(defn request-pets [set-pets! animal location breed] 
  (let [url (str "http://pets-v2.dev-apis.com/pets?animal=" animal "&location=" location "&breed=" breed)
        res (js/fetch url)]
    (-> res
        (.then (fn [response]
                ;;  (js/console.log response)
                 (.json response)))
        (.then (fn [json-data]
                 ;; (println "our data" json-data)
                 (set-pets! (:pets (js->clj json-data :keywordize-keys true))))))))

(defui search-params []
  (let [[location set-location!] (uix/use-state "")
        [animal set-animal!]     (uix/use-state "")
        [breed set-breed!]       (uix/use-state "")
        [pets set-pets!]         (uix/use-state "")
        breeds                   (first (use-breed-list animal))]

    (uix/use-effect (fn []
                      (request-pets set-pets! animal location breed))
                    [])

    ($ :div {:class-name "search-params"}
       ($ :form
          {:on-submit
           #(do (.preventDefault %)
                (request-pets set-pets! animal location breed))}
          ($ :label {:html-for "location"}
             "Location"
             ($ :input {:id "location"
                        :value location
                        :placeholder "Location"
                        :on-change #(set-location! (.. % -target -value))}))
          ($ :label {:html-for "animal"}
             "Animal"
             ($ :select {:id "animal"
                         :value animal
                         :on-change #(do (set-animal! (.. % -target -value))
                                         (set-breed! ""))}
                ($ :option)
                (map #($ :option {:key % :value %} %) animals)))
          ($ :label {:html-for "breed"}
             "Breed"
             ($ :select {:disabled (empty? breeds)
                         :id "breed"
                         :value breed
                         :on-change #(set-breed! (.. % -target -value))}
                ($ :option)
                (map #($ :option {:key % :value %} %) breeds)))
          ($ :button "Submit"))
       
       ;; new version of pet rendering
       ($ results pets)

       ;; old version of pet rendering
       #_(map #($ pet {:name (:name %)
                     :breed (:breed %)
                     :animal (:animal %)
                     :key (:id %)})
            pets)
       )))