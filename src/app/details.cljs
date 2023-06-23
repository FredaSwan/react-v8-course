(ns app.details
(:require [app.pet :refer [pet]]
          [uix.core :as uix :refer [defui $]]
          [uix.dom]))

(defui details []
  ($ :h1 "Welcome to details!"))