(ns app.error-boundary
  (:require ["react-router-dom" :refer [Link]]
            [uix.core :as uix :refer [$]]))

(def error-boundary
    (uix/create-error-boundary
     {:display-name "error-boundary-details" 
      :derive-error-state (fn [error] {:has-error true
                                       :error error})
      :did-catch (fn [error error-info]
                           (js/console.log (str "ErrorBoudary caught an error: " error ", " error-info)))}
     (fn []
       ($ :div.details {:style {:text-align "center"}}
          ($ :h2 "there was an error. pls go away.")
          ($ Link {:to "/home"} "Return to home 🏠")))))

