(ns app.use-breed-list-use-effect
  (:require #_[shadow.cljs.modern :refer [js-await]]
            [uix.core :as uix]
            [uix.dom]))

(defn request-breed-list 
  [animal set-breed-list! set-status! local-cache set-local-cache!]
  (js/console.log "Request breed list: " animal)
  (let [url (str "http://pets-v2.dev-apis.com/breeds?animal=" animal)
        res (js/fetch url)]
    (set-breed-list! [])
    (set-status! "loading") 
    (-> res
        (.then (fn [response]
                 (js/console.log response)
                 (.json response)))
        (.then (fn [json]
             (let [data (js->clj json :keywordize-keys true)]
             (set-breed-list! (or (:breeds data) []))
             (set-local-cache! (assoc local-cache animal (:breeds data)))
             (set-status! "loaded")))))
    
    ;; The below method using js-await doesn't work, but keeping it here for reference
    #_(js-await [res (js/fetch url)]
                (js/console.log "My result" res)
                (let [json (.json res)
                      better-data (js->clj json :keywordize-keys true)
                      breeds (:breeds better-data)]
                  (js/console.log "JSON: " json "BETTER: " better-data "BREEDS: " breeds)
                  (set-breed-list! (or breeds []))
                  (set-local-cache! (assoc local-cache animal breeds))
                  (set-status! "loaded")))))

(defn use-breed-list [animal]
  (let [[breed-list set-breed-list!]   (uix/use-state [])
        [status set-status!]           (uix/use-state "unloaded")
        [local-cache set-local-cache!] (uix/use-state {})]
    (uix/use-effect
     (fn []
       (cond
         (empty? animal) (set-breed-list! [])
         (contains? local-cache animal) (set-breed-list! (get local-cache animal))
         :else (request-breed-list
                animal set-breed-list! set-status! local-cache set-local-cache!)))
     [animal])
    [breed-list status]))

