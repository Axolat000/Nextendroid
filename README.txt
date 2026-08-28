========================================================================
                         NEXTENDROID (Android)
       Client Android officiel & moderne pour le réseau Nextendo
========================================================================

Nextendroid est une application Android native développée en Kotlin avec
Jetpack Compose, offrant une interface élégante et réactive pour interagir
avec le réseau Nextendo (Nextendo Account & Services).

------------------------------------------------------------------------
                             FONCTIONNALITÉS
------------------------------------------------------------------------

1. ACCUEIL (HOME) :
   - Salutation dynamique personnalisée selon l'heure de la journée.
   - Section "EN LIGNE" : Liste défilante des amis actuellement connectés 
     avec l'icône du jeu en cours et leur statut.
   - Section "À QUOI JOUENT VOS AMIS" : Liste des 14 jeux officiels 
     Nextendo auxquels vos amis ont joué, avec les jaquettes haute résolution 
     et la superposition des photos de profil (PP) de vos amis.
   - Section "SUR NEXTENDO" : Classement du nombre total de joueurs en ligne 
     sur chaque jeu du réseau Nextendo.
   - Bouton de rafraîchissement manuel et mise à jour automatique en arrière-plan (15s).

2. AMIS (FRIENDS) :
   - Liste complète des amis triés par statut (favoris, en ligne, hors ligne).
   - Basculement des favoris (★ / ☆).
   - Fiche détaillée d'un ami (FriendDetailScreen) affichant son code ami, 
     son statut et son historique de jeux.

3. DEMANDES D'AMIS (ADD FRIEND) :
   - Affichage de votre propre code ami avec bouton "Copier".
   - Champ d'envoi de demandes d'ami via code ami (POST /api/friends).
   - Gestion des demandes d'ami entrantes avec boutons "Accepter" et "Refuser".

4. SAUVEGARDES CLOUD (SAVES) :
   - Visualisation de l'espace de stockage cloud utilisé.
   - Liste des sauvegardes synchronisées avec Ryujinx et la Nintendo Switch.
   - Badge d'éligibilité Booster.

5. COMPTE & PROFIL (ACCOUNT) :
   - Modification du pseudo avec resynchronisation serveur.
   - Modification du pays (avec drapeaux emoji).
   - Changement de photo de profil (PP) personnalisée depuis la galerie photo 
     (encodage Base64 et upload vers POST /api/profile).
   - Support multilingue (Français, English, Español, Deutsch, Português, Русский, Italiano).

6. PARAMÈTRES (SETTINGS) :
   - Personnalisation de la couleur d'accentuation (Nextendo Pink, Encre, 
     Prune, Framboise, Braise, Or, Menthe, Corail) ou couleur personnalisée.
   - Aperçu en direct des boutons et textes.

------------------------------------------------------------------------
                        REQUÊTES API BACKEND
------------------------------------------------------------------------

L'application interagit directement avec l'API REST de Nextendo Network :
- POST /api/login             : Connexion utilisateur
- GET  /api/me                : Informations du compte connecté
- GET  /api/profile           : Consultation du profil utilisateur
- POST /api/profile           : Mise à jour de la photo de profil (Base64 JPEG)
- POST /api/username          : Modification du pseudo
- POST /api/country           : Modification du pays
- GET  /api/friends           : Liste d'amis et demandes entrantes
- POST /api/friends           : Envoi de demande d'ami
- POST /api/friends/accept    : Acceptation d'une demande d'ami
- POST /api/friends/decline   : Refus d'une demande d'ami
- POST /api/friends/favorite  : Bascule favori
- GET  /api/friends/history   : Historique de jeu individuel par PID d'ami
- GET  /api/saves             : Sauvegardes cloud
- GET  /api/online-counts     : Statistiques des joueurs en ligne sur Nextendo

------------------------------------------------------------------------
                        STRUCTURE DU PROJET
------------------------------------------------------------------------

Nextendroid/
├── app/
│   ├── src/main/
│   │   ├── assets/covers/     <- Jaquettes locales des 14 jeux Nextendo
│   │   └── java/com/axolat/nextendroid/
│   │       ├── data/          <- Repositories, Modèles (Models, GameDictionary) et API REST
│   │       ├── ui/
│   │       │   ├── components/<- Composants Compose (AvatarView, GameCard, BottomNavBar...)
│   │       │   ├── screens/   <- Écrans (HomeScreen, FriendsScreen, AccountScreen, SettingsScreen...)
│   │       │   ├── theme/     <- Thème, Couleurs et AppLanguage/Strings
│   │       │   └── viewmodel/ <- NextendoViewModel (StateFlow & Coroutines)
│   │       └── MainActivity.kt
└── README.txt

------------------------------------------------------------------------
                            COMPILATION
------------------------------------------------------------------------

Pour compiler l'application en mode Debug :
  ./gradlew assembleDebug

L'APK généré se trouvera dans :
  app/build/outputs/apk/debug/app-debug.apk

------------------------------------------------------------------------
                              LICENCE
------------------------------------------------------------------------

Ce projet est distribué sous la licence open-source MIT License.
Voir le fichier LICENSE pour plus de détails.
========================================================================
