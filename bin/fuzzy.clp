(clear)
(import java.lang.*)

(deffacts hechos-iniciales
    (inicio)
    )

(deftemplate ejemplo 
    (declare (from-class ejemplo)	;se le indica que cree los slots a partir de la clase original que está en Java
        (include-variables TRUE)	;para hacer uso de sus métodos y variables públicas. Es forzosa esta declaración
        ))

;;Funciones de pertenencia de intento
(deffunction iBueno(?intentos)
    (if (>= ?intentos 4) then
        (return 0)
     elif (>= ?intentos 3) then
        (bind ?x (- 4 ?intentos))
        (return ?x)
     elif (>= ?intentos 1) then
        (return 1)
     else 
        (return 0)
        )
    )

(deffunction iMedio(?intentos)
    (if (and (>= ?intentos 5) (<= ?intentos 6)) then
        (bind ?x (- 6 ?intentos))
        (return ?x)
     elif (and (>= ?intentos 4) (<= ?intentos 5)) then
        (return 1)
     elif (and (>= ?intentos 3) (<= ?intentos 4)) then
        (bind ?x (- ?intentos 3))
        (return ?x)
     else
        (return 0)
        )
    
    )

(deffunction iBajo(?intentos)
    (if (>= ?intentos 6)  then
        (return 1)
     elif (and (>= ?intentos 5) (<= ?intentos 6)) then
        (bind ?x (- ?intentos 5))
        (return ?x)
     else
        (return 0)
        )
    )


;;;;Funciones de pertenencia de tiempo
(deffunction tBueno(?tiempo)
    (if (>= ?tiempo 2) then
        (return 0)
     elif (and (<= ?tiempo 2) (>= ?tiempo 1)) then
        (bind ?x (- 2 ?tiempo))
        (return ?x)
     elif (and (<= ?tiempo 1) (>= ?tiempo 0)) then
        (return 1)
     else 
        (return 0)
        )
    )

(deffunction tMedio(?tiempo)
    (if (and (>= ?tiempo 5) (<= ?tiempo 6)) then
        (bind ?x (- 6 ?tiempo))
        (return ?x)
     elif (and (>= ?tiempo 2) (<= ?tiempo 5)) then
        (return 1)
     elif (and (>= ?tiempo 1) (<= ?tiempo 2)) then
        (bind ?x (- ?tiempo 1))
        (return ?x)
     else
        (return 0)
        )
    )


(deffunction tBajo(?tiempo)
    (if (>= ?tiempo 6)  then
        (return 1)
     elif (and (>= ?tiempo 5) (<= ?tiempo 6)) then
        (bind ?x (- ?tiempo 5))
        (return ?x)
     else
        (return 0)
        )
    )

(defrule getData
    (inicio)
    =>
    (bind ?instancia (new ejemplo))
    (add ?instancia)
    
    (bind ?intentos  (?instancia getInt))
    (bind ?tiempo  (?instancia getTie))
    
    (bind ?iMuBueno (iBueno ?intentos))
    (bind ?iMuMedio (iMedio ?intentos))
    (bind ?iMuBajo (iBajo ?intentos))
    
    (bind ?tMuBueno (tBueno ?tiempo))
    (bind ?tMuMedio (tMedio ?tiempo))
    (bind ?tMuBajo (tBajo ?tiempo))
    
    ;a1 = min(imubueno(i),tmubueno(t)) <- Esto quiere decir bueno
    ;a2 = min(imumedio(i),tmubueno(t)) <- Esto quiere decir bueno
    ;a3 = min(imubajo(i),tmubueno(t)) <- Esto quiere decir medio
    ;a4 = min(imubueno(i),tmumedio(t)) <- Esto quiere decir bueno
    ;a5 = min(imumedio(i),tmumedio(t)) <- Esto quiere decir medio
    ;a6 = min(imubajo(i),tmumedio(t)) <- Esto quiere decir bajo
    ;a7 = min(imubueno(i),tmubajo(t)) <- Eso es medio
    ;a8 = min(imumedio(i),tmubajo(t)) <- Eso es bajo
    ;a9 = min(imubajo(i),tmubajo(t)) <- Eso es bajo
    
    (bind ?alpha1 (min ?iMuBueno ?tMuBueno))
    (bind ?alpha2 (min ?iMuMedio ?tMuBueno))
    (bind ?alpha3 (min ?iMuBajo ?tMuBueno))
    (bind ?alpha4 (min ?iMuBueno ?tMuMedio))
    (bind ?alpha5 (min ?iMuMedio ?tMuMedio))
    (bind ?alpha6 (min ?iMuBajo ?tMuMedio))
    (bind ?alpha7 (min ?iMuBueno ?tMuBajo))
    (bind ?alpha8 (min ?iMuMedio ?tMuBajo))
    (bind ?alpha9 (min ?iMuBajo ?tMuBajo))
    
    (bind ?maxBueno (max ?alpha1 ?alpha2 ?alpha4))
    (bind ?maxMedio (max ?alpha7 ?alpha5 ?alpha3))
    (bind ?maxBajo (max ?alpha6 ?alpha8 ?alpha9))
    
    (bind ?supremo (max ?maxBueno ?maxMedio ?maxBajo))
    (if (eq ?supremo ?maxBueno) then
        (assert (estrellas 3))
        (?instancia muestraEstrellas 3)
     elif (eq ?supremo ?maxMedio) then
        (assert (estrellas 2))
        (bind $?lista (?instancia muestraEstrellas 3))
        (printout t "Esta es la lista: "?lista)
     elif (eq ?supremo ?maxBajo) then
        (assert (estrellas 1))
        (?instancia muestraEstrellas 1)
    )
)

(reset)
(run)
