;; Jess Exercise 1: persons
/*
(deffacts ages 
    (person Rosa 30)
    (person Maria 5)
    (person Tiago 33))
;;12

(defrule separateInfo
    (person ?n ?i)
    =>
    (assert (name ?n))
    (assert (age ?i))
    (printout t "Name " ?n " has " ?i " splited" crlf) )

;;3

(defrule AskSurname
     (printout t "Your name: " crlf)
     (readline t ?n )
     (printout t "Your surname:  " crlf)
     (readline t ?sn )
      => 
         (assert (nameandsurname (name ?n) (surname ?sn)))
         (printout t "Name: " ?n " Surname: " ?sn crlf) 
)

;;4

(deftemplate person
    (multislot name))

(deffacts persons
    (person (name Rosa Pereira)) 
    (person (name Maria Melo)) 
    (person (name Tiago Silva)))

(defrule LastName
   (person (name $? ?fn))
   => (printout t "Last name: " ?fn crlf)
)
*/

;;5 (TODO: prints repetidos)

(deftemplate person
  (multislot name)
  (slot age))

(deffacts persons
  (person (name Rosa Araujo Pereira)
          (age 33))
  (person (name Maria Sampaio Melo)
          (age 48))
  (person (name Tiago Jose da Fonseca Silva)
          (age 21)))

(defrule printInfo
     (person (name ?n $? $? ?sn) (age ?a))
      => (printout t "Name: " ?n " " ?sn " Age: " ?a crlf)    
)