# Kanban API 
Important : Un swagger est installé pour visualiser les données à envoyer sur chaque route. Il est disponible à cette adresse : http://localhost:5432/swagger-ui/index.html#/.

Pour faire fonctionner le projet, il faut bien configurer l'accès à la base de données. Cela se trouve dans le fichier application.properties. Ayant utilisé une base de données locale, j'ai configuré ce fichier selon ma machine. Cependant, il faut bien vérifier qu'une base au nom de "kanban_board" existe pour que le projet puisse s'y associer.

Ce swagger est intéressant puisqu'il indique les données à envoyer selon les méthodes, et les réponses qui vont être renvoyées.

La logique de l'application se base sur 3 entités principales:
- Les tableaux (Board)
- Les sections (Section)
- Les fiches / cartes (Card)

Un tableau possède un nom (label) et un ensemble de sections. Chaque section ne peut être associé qu'à un seul tableau.

Les sections possèdent un nom (label), un ensemble de fiches et une position. La position permet de trier l'affichage des sections lors de l'affichage d'un tableau en particulier. Comme pour les tableaux, une fiche ne peut être associée qu'à une section.

Les fiches possèdent de multiples attributs permettant d'associer du travail à une personne donnée. Tous les attributs présentés dans le sujet en font partis. De plus, une fiche est associée à un utilisateur ou non, et possède également une collection de tags. Les tags permettent d'associer des traits de caractère à une fiche (Exemple : on crée un tag "sprint 1" pour toutes les cartes du sprint 1).

Il existe une dernière entité Address. Cette entité permet de répertorier toutes les adresses entrées dans l'application. Les adresses sont associées à un utilisateur ou une fiche.

Pour tester les routes, j'ai ajouté le fichier "kanban-api.postman_collection.json" au projet. Ce fichier est une collection de route créée sur Postman. Si vous avez le logiciel, il suffit juste d'aller dans Fichier > Importer pour obtenir une collection d'URL déjà créée.
Les routes sont ajoutées avec une URL de projet qui est : http://localhost:5432.

Les routes pour accéder aux tableaux sont :
- (GET) /api/boards : Permet d'accéder à tous les tableaux disponibles.
- (POST) /api/boards : Permet de créer un nouveau tableau.
- (GET) /api/board/{id} : Permet d'accéder à un tableau en particulier.
- (POST) /api/board/{id} : Permet de modifier un tableau en particulier.
- (DELETE) /api/board/{id} : Permet de supprimer un tableau en particulier.
- (PATCH) /api/board/{id}/section : Permet de créer une nouvelle section associée au tableau rentré.

Les routes pour accéder aux sections sont :
- (GET) /api/sections : Permet d'accéder à toutes les sections disponibles.
- (GET) /api/section/{id} : Permet d'accéder à une section en particulier.
- (POST) /api/section/{id} : Permet de modifier une section en particulier.
- (DELETE) /api/section/{id} : Permet de supprimer une section en particulier.
- (PATCH) /api/section/{id}/card : Permet de créer une nouvelle fiche associée à la section rentrée.

Les routes pour accéder aux fiches sont :
- (GET) /api/cards : Permet d'accéder à toutes les fiches disponibles.
- (GET) /api/card/{id} : Permet d'accéder à une fiche en particulier.
- (POST) /api/card/{id} : Permet de modifier une fiche en particulier.
- (DELETE) /api/card/{id} : Permet de supprimer une fiche en particulier.
- (PATCH) /api/card/{id}/user : Permet d'associer un utilisateur à la fiche rentrée. Attention, ici on ne crée pas l'utilisateur, le body contient seulement l'id de l'utilisateur à associer.
- (DELETE) /api/card/{id}/user : Permet de dissocier l'utilisateur de la fiche.
- (PATCH) /api/card/{id}/tag : Permet d'associer un tag à la fiche rentrée. Même fonctionnement que pour l'utilisateur.
- (DELETE) /api/card/{id}/tag : Permet de dissocier un tag de la fiche rentrée.
- (PATCH) /api/card/{id}/address : Permet de créer une adresse automatiquement associée à la fiche rentrée.
- (DELETE) /api/card/{id}/address : Permet de dissocier l'adresse associée à la fiche rentrée.
- (PATCH) /api/card/{id}/section : Permet de changer la section associée à la fiche rentrée.

Les routes pour accéder aux utilisateurs sont :
- (GET) /api/users : Permet d'accéder à tous les utilisateurs disponibles.
- (POST) /api/users : Permet de créer un nouvel utilisateur.
- (GET) /api/user/{id} : Permet d'accéder à un utilisateur en particulier.
- (POST) /api/user/{id} : Permet de modifier un utilisateur en particulier.
- (DELETE) /api/user/{id} : Permet de supprimer un utilisateur en particulier.
- (PATCH) /api/user/{id}/address : Permet de créer une adresse automatiquement associée à l'utilisateur rentré.
- (DELETE) /api/user/{id}/address : Permet de dissocier l'adresse associée à l'utilisateur rentré.

Les routes pour accéder aux tags sont :
- (GET) /api/tags : Permet d'accéder à tous les tags disponibles.
- (POST) /api/tags : Permet de créer un nouveau tag.
- (GET) /api/tag/{id} : Permet d'accéder à un tag en particulier.
- (POST) /api/tag/{id} : Permet de modifier un tag en particulier.
- (DELETE) /api/tag/{id} : Permet de supprimer un tag en particulier.