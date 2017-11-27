;; Jess Exercise 3: fruits

(deftemplate fruit
  (slot name)
  (slot form)
  (slot color)
  (slot diameter)
  (slot seedsnumber)
  (slot fruitorigin)
  (slot surface)
  (slot seedclass)
)

(defrule Rule1
    (fruit {form == long && (color == green || color == yellow)})
    => (printout t "Fruit=Banana" crlf))

(defrule Rule2
    (fruit {(form == round || form == oblong) && diameter > 4})
    => (printout t "Fruitorigin = Plant-crawling" crlf))

(defrule Rule3
    (fruit {form == round && diameter < 4})
    => (printout t "Fruitorigin = tree" crlf))

(defrule Rule4
    (fruit {seedsnumber == 1 })
    => (printout t "seedclass= lump" crlf))

(defrule Rule5
    (fruit {seedsnumber > 1})
    => (printout t "seedclass= multiple" crlf))

(defrule Rule6
    (fruit {fruitorigin == plant-crwaling && color == green})
    => (printout t "Fruit= Watermelon" crlf))

(defrule Rule7
     (fruit {fruitorigin == plant-crwaling && surface == soft && color == yellow})
    => (printout t "Fruit= Melon" crlf))

(defrule Rule8
     (fruit {fruitorigin == plant-crwaling && surface == rough && color == brownish})
    => (printout t "Fruit= Cantaloupemelon" crlf))

(defrule Rule9
     (fruit {fruitorigin == tree && seedclass == lump && color == orange && diameter < 3})
    => (printout t "Fruit = Apricot" crlf))

(defrule Rule10
     (fruit {fruitorigin == tree && seedclass == multiple && color == orange})
    => (printout t "Fruit = Orange" crlf))

(defrule Rule11
     (fruit {fruitorigin == tree && seedclass == lump && color == red})
    => (printout t "Fruit = Cherry" crlf))

(defrule Rule12
     (fruit {fruitorigin == tree && seedclass == lump && color == orange && diameter >= 3})
    => (printout t "Fruit = Peach" crlf))

(defrule Rule13
     (fruit {fruitorigin == tree && seedclass == multiple && (color == green || color == red)})
    => (printout t "Fruit = Apple" crlf))

(defrule Rule14
     (fruit {fruitorigin == tree && seedclass == lump && color == purple})
    => (printout t "Fruit = Plum" crlf))

