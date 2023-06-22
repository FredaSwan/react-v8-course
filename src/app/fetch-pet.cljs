(ns app.fetch-pet
  (:require [shadow.cljs.modern :refer [js-await]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defn fetch-pet [context]
  (js/console.log "Fetch pet context" context)
  (let [query (.-queryKey context)
        id (aget query 1)
        url (str "http://pets-v2.dev-apis.com/pets?id=" id)]
    (js-await [fetch-result (js/fetch url)]
              (js/console.log "My result" fetch-result)
              (if (not (.-ok fetch-result))
                (js/Error. (str "details/" id " fetch not okay"))
                (.json fetch-result)))))