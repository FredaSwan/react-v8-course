(ns app.details
  (:require ["@tanstack/react-query" :refer [useQuery]]
            ["react-router-dom" :refer [useParams useNavigate]]
            [app.adopted-pet-context :refer [adopted-pet-context]]
            [app.carousel :refer [carousel]]
            [app.error-boundary :refer [error-boundary]]
            [app.fetch-pet :refer [fetch-pet]]
            [app.modal :refer [modal]]
            [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defn pick-loader [animal]
  (case animal
    "bird"    "🦜"
    "cat"     "🐱"
    "dog"     "🐶"
    "rabbit"  "🐰"
    "reptile" "🦎"
    "⏳"))

(defui details []
  (let [id      (.-id (useParams))
        animal  (.-animal (useParams))
        result (useQuery (clj->js ["details" id]) fetch-pet)
        navigate (useNavigate)
        [show-modal set-show-modal!] (uix/use-state false)
        [_ set-adopted-pet!] (uix/use-context adopted-pet-context)] 
    (cond 
      (.-isLoading result) ($ :div {:class-name "loading-pane"}
                               ($ :h2 {:class-name "loader"} (pick-loader animal)))
      :else (let [pet (first (.-pets (.-data result)))
                  pet-name (.-name pet)] 
              (js/console.log pet)
             ;; uncoment throw error to test error boundary
              #_(throw (js/Error. "This is a fake error to test error boundary"))
              ($ :div {:class-name "details"}
                 ($ carousel {:images (.-images pet)})
                 ($ :div
                    ($ :h1 pet-name)
                    ($ :h2 (str ^string (.-animal pet) " – " ^string (.-breed pet) " – " (.-city pet) ", " (.-state pet))
                       ($ :p (.-description pet))
                       ($ :button {:on-click #(set-show-modal! true)} (str "Adopt " pet-name)))
                    (when show-modal 
                      ($ modal 
                         ($ :div
                            ($ :h1 (str "Would you like to adopt " (.-name pet) "?"))
                            ($ :.buttons
                               ($ :button {:on-click #(do (set-adopted-pet! pet)
                                                          (navigate "/home"))} "Yes")
                               ($ :button {:on-click #(set-show-modal! false)} "No")))))))))))

(defui details-error-boundary []
  ($ error-boundary
     ($ details)))