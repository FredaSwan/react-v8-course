(ns app.error-boundary
  (:require ["react-router-dom" :refer [Link]]
            [uix.core :as uix :refer [$]]))

(def error-boundary
    (uix/create-error-boundary
     {:display-name "error-boundary-details"
      :derive-error-state (fn [error]
                            {:error error})
      :did-catch (fn [error info]
                   (js/console.error (str "ErrorBoudary caught an error: " error info)))}
     (fn [[state _] {:keys [children]}]
       (if (:error state)
         ($ :div.details {:style {:text-align "center"}}
            ($ :h2 "there was an error. pls go away.")
            ($ :pre (pr-str state))
            ($ Link {:to "/home"} "Return to homepage 🏠"))
         children))))