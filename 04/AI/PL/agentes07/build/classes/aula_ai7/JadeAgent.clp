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
        (slot content)
        (slot encoding) 
	(multislot reply-to)
        (slot reply-by) 
)

(deftemplate Me
            (slot name)
)

(deftemplate Department
        (slot id)
        (slot temperature) 
)

;to do "debug" and see if messages are being received
(defrule receivedMSG
    (ACLMessage (sender ?s)
                (content ?c))
    =>
    (printout t "Just received a message from " ?s "with temp " ?c crlf))


(defrule registerDep
    ?m <-(ACLMessage (communicative-act ?c) 
                     (sender ?s)
                     (receiver ?r) 
                     (content ?ct)
       )
    => (assert ( Department (id ?s) (temperature ?ct) ))
       (retract ?m)
)



(defrule checkTempHeat
       (Department {temperature > 30} (id ?id))
 =>    
       (bind ?c "cooling")
       (bind ?m (assert (ACLMessage (communicative-act 7) (receiver ?id) (content ?c))))
       (send ?m)
       (retract ?m)

)

(defrule checkTempCool
       (Department {temperature < 20} (id ?id))
 =>    
       (bind ?c "heating")
       (bind ?m (assert (ACLMessage (communicative-act 7) (receiver ?id) (content ?c))))
       (send ?m)
       (retract ?m)

)