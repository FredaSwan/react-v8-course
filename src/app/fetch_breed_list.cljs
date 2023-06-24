(ns app.fetch-breed-list
  (:require [shadow.cljs.modern :refer [js-await]]))

(defn fetch-breed-list [context]
  #_(js/console.log "Fetch breed list context" context)
  (let [query  (.-queryKey context)
        animal (aget query 1) ;; aget = get array index
        url    (str "http://pets-v2.dev-apis.com/breeds?animal=" animal)]
    (when (not (empty? animal))
      (js-await [fetch-result (js/fetch url)]
                (if (not (.-ok fetch-result))
                  (js/Error. (str "breeds/" animal " fetch not okay"))
                  (.json fetch-result))))))