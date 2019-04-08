# Pacman_JavaEE

Site web permettant d'accéder au classment du jeu pacman en réseau.
Les fonctionnalités disponibles sur ce site sont les suivantes :
La page d'accueil permet d'afficher le classement général, les resultats sont cliquables et mènent sur un profil
public de la personne (le classement des parties du joueur).
Il est possible de s'inscrire et de se connecter (et se déconnecter). Lorsqu'on se connecte, on accède à la partie "cachée" du site,
un profil privé qui rappelle le pseudo de la personne, son adresse mail et sa date d'inscription. L'utilisateur peut aussi 
modifier son mot de passe.

La base de donnée est gérée par une DAO et le fichier SQL avec les commandes pour créer cette base est dans le package dao sous 
le nom de bdd_pacman.sql (Création de la base, de l'utilisateur, des tables et insertion de quelques données)
