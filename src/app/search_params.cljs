(ns app.search-params
  (:require ["@tanstack/react-query" :refer [useQuery]]
            [app.fetch-search :refer [fetch-search]]
            [app.results :refer [results]]
            [app.use-breed-list :refer [use-breed-list]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(def animals ["bird" "cat" "dog" "rabbit" "reptile"])

(defui search-params []
  (let [[request-params set-request-params!] (uix/use-state (clj->js {:location ""
                                                                      :animal   ""
                                                                      :breed    ""}))

        [animal set-animal!]     (uix/use-state "")
        breeds                   (first (use-breed-list animal))

        result (useQuery (clj->js ["search" request-params]) fetch-search)
        pets    (if (.-isLoading result)
                  []
                  (js->clj (.-pets (.-data result)) :keywordize-keys true))

        form-submit-handler (fn [e]
                              (.preventDefault e)
                              (let [form-data (js/FormData. (.-target e))
                                    obj (clj->js {:animal   (or (.get form-data "animal") "")
                                                  :breed    (or (.get form-data "breed") "")
                                                  :location (or (.get form-data "location") "")})]
                                (set-request-params! obj)))] 

    ($ :div {:class-name "search-params"}
       ($ :form
          {:on-submit form-submit-handler}

          ($ :label {:html-for "location"}
             "Location"
             ($ :input {:id "location"
                        :name "location"
                        :placeholder "Location"}))
          
          ($ :label {:html-for "animal"}
             "Animal"
             ($ :select {:id "animal"
                         :name "animal"
                         :value animal
                         :on-change #(set-animal! (.. % -target -value))}
                ($ :option)
                (map #($ :option {:key % :value %} %) animals)))
          
          ($ :label {:html-for "breed"}
             "Breed"
             ($ :select {:disabled (empty? breeds)
                         :name "breed"
                         :id "breed"}
                ($ :option)
                (map #($ :option {:key % :value %} %) breeds)))
          
          ($ :button "Submit"))
       
       ($ results pets))))