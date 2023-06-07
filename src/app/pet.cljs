(ns app.pet 
  (:require [uix.core :as uix :refer [defui $]]
            [uix.dom]))

(defui pet [{:keys [name animal breed]}]
  ($ :div
     ($ :h1 name)
     ($ :h2 animal)
     ($ :h2 breed)))
