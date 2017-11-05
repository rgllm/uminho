;; Jess Exercise 2: persons

(deftemplate person
 (slot hair-color)
 (multislot name)
 (slot eye-color)
 (slot age)
 (slot abstraction))

(deffacts persons
 (person (hair-color black)
 (eye-color blue)
 (name Joao Marsupilami Silva)
 (age 23))
 (person (hair-color brown)
 (eye-color blue)
 (name Gonçalo Rodrigues Conceição)
 (age 38))
 (person (hair-color red)
 (eye-color brown)
 (name António Carvalho Deus)
 (age 20))
 (person (hair-color blond)
 (eye-color green)
 (name Ronaldo Martins Pereira)
 (age 88))
 (person (hair-color brown)
 (eye-color black)
 (name Francisco dos Santos Pereira)
 (age 2)))

;;1

(defrule lastNameAndAge
        (person (name $? ?ln) (age ?i) )
         => (printout t "Last Name: " ?ln " - Age: " ?i crlf ))

;;2

(defrule darkHair
      (person {hair-color == brown || hair-color == black} (name $? ?ln))
      => (printout t "Last Name: " ?ln crlf )
)


;;3

(defrule greenBlueEyes
      (person {eye-color == green || eye-color == blue} (name $? ?ln) (eye-color ?ec))
      => (printout t "Last Name: " ?ln " - Eye color: " ?ec crlf )
)

;;4

(defrule notGreenBlueEyes
      (person {eye-color != green || eye-color != blue} (name $?n ))
      => (printout t "Name: " ?n  crlf )
)

;;5

(defrule differentAges
      ?p1 <- (person)
      (exists (person {age != p1.age}))
      => (printout t "All the persons have different ages!" crlf )
)

;;6

(defrule blueEyes
      (exists(person {eye-color == blue}))
      => (printout t "There are one or more persons with blue eyes." crlf )
)

;;7

(defrule child
     (person {age <= 12} )
     => (printout t "Child" crlf )
)

(defrule adult
     (person {age >= 13 && age <= 65} )
     => (printout t "Adult" crlf )
)

(defrule thirdAge
     (person {age >= 65} )
     => (printout t "Third age" crlf )
)

;;8(todo: assim ou ver pela idade?)

(defrule allAdults
     (exists (child))
     => (printout t "There are childs." crlf )
)

;;9

(defrule ClassAge
     (or (person {age <= 12} )
         (person {age >= 13 && age <= 65} )
         (person {age >= 65} ) )
     => (printout t ?x crlf )
)

