(deftemplate ACLMessage 
	(slot communicative-act) 
        (slot sender)
        (multislot receiver) 
	(slot reply-with)
        (slot in-reply-to)
        (slot envelope) 
	(slot conversation-id)
        (slot protocol)
        (slot language) 
	(slot ontology)
        (multislot content)
        (slot encoding) 
	(multislot reply-to)
        (slot reply-by) 
)

(deftemplate Case 
        (slot spirit)
		(slot age)
		(slot healt)
		(slot condicion)
		(multislot position)
		(multislot destiny)
		(slot weather) 
)

(deftemplate Station 
        (slot name)
		(multislot position)
		(slot price)
)

(defrule receivedUser
    (ACLMessage (sender ?se)
                (content ?u ?s ?a ?sex ?h ?c ?x ?y ?xd ?yd))
    =>
    (store "word" ?s)
	(printout t "Just received a message from " ?se "with temp " ?s crlf)
)

    