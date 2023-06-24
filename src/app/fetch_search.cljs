(ns app.fetch-search
  (:require [shadow.cljs.modern :refer [js-await]]))

(defn fetch-search [context]
  #_(js/console.log "Fetch search: " context)
  (let [query  (.-queryKey context)
       {:keys [animal location breed]} (js->clj (aget query 1) :keywordize-keys true) ;; aget = get array index
        url    (str "http://pets-v2.dev-apis.com/pets?animal=" animal
                    "&location=" location "&breed=" breed)]
    (js-await [fetch-result (js/fetch url)]
              (if (not (.-ok fetch-result))
                (js/Error. (str "pet search/" animal ", " location ", " breed " fetch not okay"))
                (.json fetch-result)))))

