# PimpMyTrainee

## Continuous Integration
### SonarCloud
[![Badges from SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=sqale_rating)](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=sqale_rating)
[![Badges from SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=reliability_rating)](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=reliability_rating)
[![Badges from SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=security_rating)](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=security_rating)
[![Badges from SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=vulnerabilities)](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=vulnerabilities)
[![Badges from SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=bugs)](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=bugs)
[![Badges from SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=sqale_index)](https://sonarcloud.io/api/project_badges/measure?project=ThomasCaud_PimpMyTrainee&metric=sqale_index)

## Links
[subject](https://e.edim.co/125089730/Projet1.pdf?&Expires=1538641667&Signature=KSnoNLeWNyCXUW2MLOhubhjVYgON-M67J0OPsC~UKobCK19MLI90~hkmrX0Ppt6dINvlzjJiqLUFz8RxVwGITyPFPfD2pRHZKnSI4VQhYCZKSjHcoMQcHNW4-eWK6X~oJvkG5ueoDfBa7WkHBKsF58A67IrfnKNmjDhghgY~u-~shGPTjxOH3oZK6bIK7zhlJMPNmMWZuQtGXXQkncHSB54XjioRjWo2QBDDYn~yPFXfIigd7aBNSJOKYsVPnGg6qldFJiNZxoFnVN69zDNGEf9GXNhx7HuW0yMJq7v2UeuDA-piW5E-O-G5cYmxg89KUSgIEHx~1z9Dip7AMdRlvQ__&Key-Pair-Id=APKAJMSU6JYPN6FG5PBQ)

## Déployer en local
### Prérequis :
- Serveur MYSQL 8
- Serveur Tomcat 8.5 ou plus
- Disposer d'une clé à l'API SendGrid, permettant l'envoi des emails

### Procédure d'installation :
* Clôner le projet en local : https://github.com/ThomasCaud/PimpMyTrainee.git
* Sur Eclipse, créer un nouveau projet web dynamique à partir de ces sources, avec une instance de serveur tomcat.
* Modifier le buildpath :
	* Le default output folder doit être : PimpMyTrainee/WebContent/WEB-INF/classes
	* Pour les sources en input, il doit y avoir le dossier **src** & le dossier **resource**
* Créer un fichier **common.properties** dans le dossier **resource** avec les infos suivantes :
	* url = jdbc:mysql://localhost:3306/pimpmytrainee?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
	* driver = com.mysql.cj.jdbc.Driver
	* user = {votre user MYSQL}
	* password = {votre mot de passe MYSQL}
	* send_grid_key = {clé de l'api SendGrid}
	* default_admin_email = {l'adresse email par défaut pour le compte admin}
	* default_password = {le mot de passe par défaut pour les comptes de l'application}
* Lancer le serveur tomcat avec les sources de l'application
* Aller sur la page **databaseAdministration** pour créer les différentes tables de l'application, ainsi qu'un premier compte administrateur

*NB : Vous pouvez changer l'url et le driver si vous utilisez autre chose

[backlog](https://docs.google.com/spreadsheets/d/1vrUvAffUUXPhspSMyQPbueTpPJWQQVnDg4vZOlJkims/edit#gid=0)

[conception docs](https://www.draw.io/?state=%7B%22ids%22:%5B%221_sYbC-iX6ZuQz93vhznhe_jSPPEFOHzX%22%5D,%22action%22:%22open%22,%22userId%22:%22107121994581211840457%22%7D)

[memo Java EE](https://hackmd.io/xleRSMswRuKDRJ1SaE9KPA?edit)
