(ns app.fetch-pet
  (:require [shadow.cljs.modern :refer [js-await]]))

(defn fetch-pet [context]
  (js/console.log "Fetch pet context" context)
  (let [query (.-queryKey context)
        id (aget query 1)
        url (str "http://pets-v2.dev-apis.com/pets?id=" id)]
    (js-await [fetch-result (js/fetch url)] 
              (if (not (.-ok fetch-result))
                (js/Error. (str "details/" id " fetch not okay"))
                (.json fetch-result)))))