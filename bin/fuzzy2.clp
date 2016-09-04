(clear)
(import java.lang.*)

(deffacts hechos-iniciales
    (inicio)
    )

(deftemplate nivel1 
    (declare (from-class nivel1)	;se le indica que cree los slots a partir de la clase original que está en Java
        (include-variables TRUE)	;para hacer uso de sus métodos y variables públicas. Es forzosa esta declaración
        ))

(deftemplate perro 
    (declare (from-class perro)	;se le indica que cree los slots a partir de la clase original que está en Java
        (include-variables TRUE)	;para hacer uso de sus métodos y variables públicas. Es forzosa esta declaración
        ))

(deftemplate principal 
    (declare (from-class principal)	;se le indica que cree los slots a partir de la clase original que está en Java
        (include-variables TRUE)	;para hacer uso de sus métodos y variables públicas. Es forzosa esta declaración
        ))

(deftemplate estrellas 
    (declare (from-class estrellas)	;se le indica que cree los slots a partir de la clase original que está en Java
        (include-variables TRUE)	;para hacer uso de sus métodos y variables públicas. Es forzosa esta declaración
        ))

(deftemplate portada 
    (declare (from-class portada)	;se le indica que cree los slots a partir de la clase original que está en Java
        (include-variables TRUE)	;para hacer uso de sus métodos y variables públicas. Es forzosa esta declaración
        ))

(deftemplate finalJ 
    (declare (from-class finalJ)	;se le indica que cree los slots a partir de la clase original que está en Java
        (include-variables TRUE)	;para hacer uso de sus métodos y variables públicas. Es forzosa esta declaración
        ))

;;Funciones de pertenencia de intento
(deffunction iBueno(?intentos)
    (if (>= ?intentos 4.0) then
        (return 0)
     elif (>= ?intentos 3.0) then
        (bind ?x (- 4.0 ?intentos))
        (return ?x)
     elif (>= ?intentos 1.0) then
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
    (if (>= ?tiempo 2.0) then
        (return 0)
     elif (and (<= ?tiempo 2.0) (>= ?tiempo 1.0)) then
        (bind ?x (- 2.0 ?tiempo))
        (return ?x)
     elif (and (<= ?tiempo 1.0) (>= ?tiempo 0.0)) then
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

(defrule inicioPortada
    (inicio)
    =>
    (bind ?instanciaP (new portada))
    (add ?instanciaP)
    (?instanciaP muestraPortada)
    (assert (nivel1))
    )

(defrule getDataNivel1
    ?h<-(nivel1)
    =>
    (retract ?h)
    (bind ?instancia (new nivel1))
    (add ?instancia)
    
    (bind ?instancia2 (new estrellas))
    (add ?instancia2)
    
    (bind $?DN3  (?instancia iniciarNivel1))
    (printout t "Esta es la lista: "?DN3 crlf)
    ;(bind ?intentos  (first$ ?DN3))
    (bind ?intentos (nth$ 1 ?DN3))
    (printout t "Estos son los intentos: "?intentos crlf)
    ;(bind ?intentos (explode$ ?intentos))
    ;(bind ?tiempo  (rest$ ?DN3))
    (bind ?tiempo (nth$ 2 ?DN3))
    ;(bind ?tiempo (explode$ ?tiempo))
    (bind ?tiempo (/ ?tiempo 8.5));;;;;;;;;;;;;;;;;;
    (printout t "Este es el tiempo: "?tiempo crlf)
    
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
        (assert (estrella 3))
		(bind $?lista (?instancia2 muestraEstrellas 3))
        (assert (nivel2))
        ;(printout t "Esta es la lista: "?lista)
     elif (eq ?supremo ?maxMedio) then
        (assert (estrella 2))
        (bind $?lista (?instancia2 muestraEstrellas 2))
        (assert (nivel1))
        ;(printout t "Esta es la lista: "?lista)
     elif (eq ?supremo ?maxBajo) then
        (assert (estrella 1))
        (bind $?lista (?instancia2 muestraEstrellas 1))
        (assert (nivel1))
        ;(printout t "Esta es la lista: "?lista)
    )
    (facts)
)

(defrule getDataNivel3
    ?h<-(nivel3)
    =>
    (retract ?h)
    (bind ?instancia (new perro))
    (add ?instancia)
    
    (bind ?instancia2 (new estrellas))
    (add ?instancia2)
    
    (bind $?DN3  (?instancia createAndShowGUI))
    (printout t "Esta es la lista: "?DN3 crlf)
    ;(bind ?intentos  (first$ ?DN3))
    (bind ?intentos (nth$ 1 ?DN3))
    (printout t "Estos son los intentos: "?intentos crlf)
    ;(bind ?intentos (explode$ ?intentos))
    ;(bind ?tiempo  (rest$ ?DN3))
    (bind ?tiempo (nth$ 2 ?DN3))
    (bind ?tiempo (/ ?tiempo 25.5));;;;;;;;;;;;;;;;
    ;(bind ?tiempo (explode$ ?tiempo))
    (printout t "Este es el tiempo: "?tiempo crlf)
    
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
        (assert (estrella 3))
		(bind $?lista (?instancia2 muestraEstrellas 3))
        (assert (final))
        ;(printout t "Esta es la lista: "?lista)
     elif (eq ?supremo ?maxMedio) then
        (assert (estrella 2))
        (bind $?lista (?instancia2 muestraEstrellas 2))
        (assert (nivel3))
        ;(printout t "Esta es la lista: "?lista)
     elif (eq ?supremo ?maxBajo) then
        (assert (estrella 1))
        (bind $?lista (?instancia2 muestraEstrellas 1))
        (assert (nivel3))
        ;(printout t "Esta es la lista: "?lista)
    )
    (facts)
)

(defrule getDataNivel2
    ?h<-(nivel2)
    =>
    (retract ?h)
    (bind ?instancia (new principal))
    (add ?instancia)
    
    (bind ?instancia2 (new estrellas))
    (add ?instancia2)
    
    (bind $?DN3  (?instancia cicloPrincipal))
    (printout t "Esta es la lista: "?DN3 crlf)
    ;(bind ?intentos  (first$ ?DN3))
    (bind ?intentos (nth$ 1 ?DN3))
    (printout t "Estos son los intentos: "?intentos crlf)
    ;(bind ?intentos (explode$ ?intentos))
    (bind ?intentos (/ ?intentos 99999999.5));;;;;;;;;;;;;;;;;;;;;
    ;(bind ?tiempo  (rest$ ?DN3))
    (bind ?tiempo (nth$ 2 ?DN3))
    ;(bind ?tiempo (explode$ ?tiempo))
    (bind ?tiempo (/ ?tiempo 25.5));;;;;;;;;;;;;;;;;;;;;
    (printout t "Este es el tiempo: "?tiempo crlf)
    
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
        (assert (estrella 3))
		(bind $?lista (?instancia2 muestraEstrellas 3))
        (assert (nivel3))
        ;(printout t "Esta es la lista: "?lista)
     elif (eq ?supremo ?maxMedio) then
        (assert (estrella 2))
        (bind $?lista (?instancia2 muestraEstrellas 2))
        (assert (nivel2))
        ;(printout t "Esta es la lista: "?lista)
     elif (eq ?supremo ?maxBajo) then
        (assert (estrella 1))
        (bind $?lista (?instancia2 muestraEstrellas 1))
        (assert (nivel2))
        ;(printout t "Esta es la lista: "?lista)
    )
    (facts)
    
)

(defrule finalDelJuego
    (final)
    =>
    (bind ?instancia (new finalJ))
    (add ?instancia)
    (?instancia muestraFinal)
    (System.exit 0)
    )

(reset)
(run)
