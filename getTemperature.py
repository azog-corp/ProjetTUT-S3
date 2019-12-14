# coding: utf-8
import time
from w1thermsensor import W1ThermSensor
from datetime import datetime
from datetime import timedelta

#création d'une string au format : jj/mm/aaaa hh:mm:ss
# que l'on renvoie
def recupDate() :
    date = datetime.now()
    strDate = date.strftime("%d/%m/%Y %H:%M:%S")
    print(strDate)
    return strDate

# Récupération de la température renvoyé par le thermomètre
# et renvoie de celle-ci
def recupTemp() :
    thermometre = W1ThermSensor()
    temperature = thermometre.get_temperature()
    print(" %s" % temperature + "\n")
    return temperature

# Écrit la date et la température dans le fichier
# prédéfini, après la dernière ligne du fichier
# ( il créé une nouvelle ligne à chaque fois )
def ecritFichier (strDate, temperature) :
    fichier = open("temperatures.txt", "a+")
    fichier.write(strDate + " %s" % temperature + '\n')
    print("date écrite %s"% strDate)
    fichier.close()

# Au lancement du programme, verifie le fichier texte pour comparer la 
# différence entre la dernière date écrite et la date actuelle afin de completer
# les dates manquantes
# Récupère le numéro de la dernière ligne, et cette dernière ligne est placée dans un string
def verifNbDate() :
    numLigne = 0
    derniereLigne = "test"
    fichier = open("temperatures.txt", "r+")
    for ligne in fichier :
        if ligne != "":
            derniereLigne = ligne
            numLigne+=1
    fichier.close()
    print("test : %s" % derniereLigne)
    if derniereLigne != "" :
        correctionFichier(derniereLigne,numLigne)
            
# Verifie qu'il manque des dates dans le fichier
# pour toutes les dates qu'il manque rajoute 
# cette date et la température -300.0
def correctionFichier(strDate, numLigne) :
    splitted = strDate.split(" ")
    print(splitted[0] + " " + splitted[1])
    derniereDate =  datetime.strptime(splitted[0] + " " + splitted[1],'%d/%m/%Y %H:%M:%S') 
    now = datetime.now()
    if derniereDate + timedelta(seconds=11) < now :
        #la date est inférieur à la date actuel de plus de 10 secondes
        temperatureInvalide = -300.0
        while derniereDate + timedelta(seconds=10) < now :
            derniereDate = derniereDate + timedelta(seconds=10)
            derniereDatestr = derniereDate.strftime("%d/%m/%Y %H:%M:%S")
            #ajout des dates manquantes
            ecritFichier(derniereDatestr, temperatureInvalide)

# lance les différentes méthodes
verifNbDate()
while True:
    strDate = recupDate()
    temperature = recupTemp()
    ecritFichier(strDate, temperature)
    time.sleep(10)

