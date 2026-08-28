package com.axolat.nextendroid.ui.theme

import java.util.Calendar

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    FR("fr", "Français", "🇫🇷"),
    EN("en", "English", "🇬🇧"),
    ES("es", "Español", "🇪🇸"),
    DE("de", "Deutsch", "🇩🇪"),
    PT("pt", "Português", "🇵🇹"),
    RU("ru", "Русский", "🇷🇺"),
    IT("it", "Italiano", "🇮🇹")
}

object Strings {
    fun greeting(lang: AppLanguage): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (lang) {
            AppLanguage.FR -> when (hour) {
                in 5..11 -> "Bonjour"
                in 12..17 -> "Bon après-midi"
                in 18..22 -> "Bonsoir"
                else -> "Bonne nuit"
            }
            AppLanguage.EN -> when (hour) {
                in 5..11 -> "Good morning"
                in 12..17 -> "Good afternoon"
                in 18..22 -> "Good evening"
                else -> "Good night"
            }
            AppLanguage.ES -> when (hour) {
                in 5..11 -> "Buenos días"
                in 12..17 -> "Buenas tardes"
                in 18..22 -> "Buenas noches"
                else -> "Buenas noches"
            }
            AppLanguage.DE -> when (hour) {
                in 5..11 -> "Guten Morgen"
                in 12..17 -> "Guten Tag"
                in 18..22 -> "Guten Abend"
                else -> "Gute Nacht"
            }
            AppLanguage.PT -> when (hour) {
                in 5..11 -> "Bom dia"
                in 12..17 -> "Boa tarde"
                in 18..22 -> "Boa noite"
                else -> "Boa noite"
            }
            AppLanguage.RU -> when (hour) {
                in 5..11 -> "Доброе утро"
                in 12..17 -> "Добрый день"
                in 18..22 -> "Добрый вечер"
                else -> "Доброй ночи"
            }
            AppLanguage.IT -> when (hour) {
                in 5..11 -> "Buongiorno"
                in 12..17 -> "Buon pomeriggio"
                in 18..22 -> "Buonasera"
                else -> "Buonanotte"
            }
        }
    }

    fun onlineHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "EN LIGNE"
        AppLanguage.EN -> "ONLINE"
        AppLanguage.ES -> "EN LÍNEA"
        AppLanguage.DE -> "ONLINE"
        AppLanguage.PT -> "EM LINHA"
        AppLanguage.RU -> "В СЕТИ"
        AppLanguage.IT -> "IN LINEA"
    }

    fun friendsPlayingHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "À quoi jouent vos amis"
        AppLanguage.EN -> "What your friends play"
        AppLanguage.ES -> "A qué juegan tus amigos"
        AppLanguage.DE -> "Was deine Freunde spielen"
        AppLanguage.PT -> "A que jogam os teus amigos"
        AppLanguage.RU -> "Во что играют ваши друзья"
        AppLanguage.IT -> "A cosa giocano i tuoi amici"
    }

    fun seeAll(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Voir tout"
        AppLanguage.EN -> "See all"
        AppLanguage.ES -> "Ver todo"
        AppLanguage.DE -> "Alle anzeigen"
        AppLanguage.PT -> "Ver tudo"
        AppLanguage.RU -> "Посмотреть все"
        AppLanguage.IT -> "Vedi tutti"
    }

    fun onNextendoHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "SUR NEXTENDO"
        AppLanguage.EN -> "ON NEXTENDO"
        AppLanguage.ES -> "EN NEXTENDO"
        AppLanguage.DE -> "AUF NEXTENDO"
        AppLanguage.PT -> "EM NEXTENDO"
        AppLanguage.RU -> "НА NEXTENDO"
        AppLanguage.IT -> "SU NEXTENDO"
    }

    fun tabHome(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Accueil"
        AppLanguage.EN -> "Home"
        AppLanguage.ES -> "Inicio"
        AppLanguage.DE -> "Startseite"
        AppLanguage.PT -> "Início"
        AppLanguage.RU -> "Главная"
        AppLanguage.IT -> "Home"
    }

    fun tabFriends(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Amis"
        AppLanguage.EN -> "Friends"
        AppLanguage.ES -> "Amigos"
        AppLanguage.DE -> "Freunde"
        AppLanguage.PT -> "Amigos"
        AppLanguage.RU -> "Друзья"
        AppLanguage.IT -> "Amici"
    }

    fun tabSaves(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Sauvegardes"
        AppLanguage.EN -> "Saves"
        AppLanguage.ES -> "Guardados"
        AppLanguage.DE -> "Speicherstände"
        AppLanguage.PT -> "Guardados"
        AppLanguage.RU -> "Сохранения"
        AppLanguage.IT -> "Salvataggi"
    }

    fun tabAccount(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Compte"
        AppLanguage.EN -> "Account"
        AppLanguage.ES -> "Cuenta"
        AppLanguage.DE -> "Konto"
        AppLanguage.PT -> "Conta"
        AppLanguage.RU -> "Аккаунт"
        AppLanguage.IT -> "Account"
    }

    fun accountTitle(lang: AppLanguage): String = tabAccount(lang)

    fun changeProfilePicture(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Changer la photo de profil"
        AppLanguage.EN -> "Change profile picture"
        AppLanguage.ES -> "Cambiar foto de perfil"
        AppLanguage.DE -> "Profilbild ändern"
        AppLanguage.PT -> "Mudar foto de perfil"
        AppLanguage.RU -> "Сменить фото профиля"
        AppLanguage.IT -> "Cambia foto profilo"
    }

    fun boosterBadge(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Membre Booster"
        AppLanguage.EN -> "Booster Member"
        AppLanguage.ES -> "Miembro Booster"
        AppLanguage.DE -> "Booster-Mitglied"
        AppLanguage.PT -> "Membro Booster"
        AppLanguage.RU -> "Бустер участник"
        AppLanguage.IT -> "Membro Booster"
    }

    fun country(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Pays"
        AppLanguage.EN -> "Country"
        AppLanguage.ES -> "País"
        AppLanguage.DE -> "Land"
        AppLanguage.PT -> "País"
        AppLanguage.RU -> "Страна"
        AppLanguage.IT -> "Paese"
    }

    fun language(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Langue"
        AppLanguage.EN -> "Language"
        AppLanguage.ES -> "Idioma"
        AppLanguage.DE -> "Sprache"
        AppLanguage.PT -> "Idioma"
        AppLanguage.RU -> "Язык"
        AppLanguage.IT -> "Lingua"
    }

    // Brand name — identical across every locale, so no per-language branch is needed.
    fun discord(@Suppress("UNUSED_PARAMETER") lang: AppLanguage): String = "Discord"

    fun linked(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Lié"
        AppLanguage.EN -> "Linked"
        AppLanguage.ES -> "Vinculado"
        AppLanguage.DE -> "Verknüpft"
        AppLanguage.PT -> "Ligado"
        AppLanguage.RU -> "Привязан"
        AppLanguage.IT -> "Collegato"
    }

    fun notLinked(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Non lié"
        AppLanguage.EN -> "Not linked"
        AppLanguage.ES -> "No vinculado"
        AppLanguage.DE -> "Nicht verknüpft"
        AppLanguage.PT -> "Não ligado"
        AppLanguage.RU -> "Не привязан"
        AppLanguage.IT -> "Non collegato"
    }

    fun networkStatusTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Statut du réseau"
        AppLanguage.EN -> "Network status"
        AppLanguage.ES -> "Estado de la red"
        AppLanguage.DE -> "Netzwerkstatus"
        AppLanguage.PT -> "Estado da rede"
        AppLanguage.RU -> "Статус сети"
        AppLanguage.IT -> "Stato della rete"
    }

    fun networkOperational(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Opérationnel"
        AppLanguage.EN -> "Operational"
        AppLanguage.ES -> "Operativo"
        AppLanguage.DE -> "Betriebsbereit"
        AppLanguage.PT -> "Operacional"
        AppLanguage.RU -> "Работает"
        AppLanguage.IT -> "Operativo"
    }

    fun networkDown(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Indisponible"
        AppLanguage.EN -> "Down"
        AppLanguage.ES -> "No disponible"
        AppLanguage.DE -> "Nicht verfügbar"
        AppLanguage.PT -> "Indisponível"
        AppLanguage.RU -> "Недоступно"
        AppLanguage.IT -> "Non disponibile"
    }

    fun networkChecking(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Vérification..."
        AppLanguage.EN -> "Checking..."
        AppLanguage.ES -> "Comprobando..."
        AppLanguage.DE -> "Wird geprüft..."
        AppLanguage.PT -> "A verificar..."
        AppLanguage.RU -> "Проверка..."
        AppLanguage.IT -> "Verifica in corso..."
    }

    fun amisHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "AMIS"
        AppLanguage.EN -> "FRIENDS"
        AppLanguage.ES -> "AMIGOS"
        AppLanguage.DE -> "FREUNDE"
        AppLanguage.PT -> "AMIGOS"
        AppLanguage.RU -> "ДРУЗЬЯ"
        AppLanguage.IT -> "AMICI"
    }

    fun offlineText(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Hors ligne"
        AppLanguage.EN -> "Offline"
        AppLanguage.ES -> "Desconectado"
        AppLanguage.DE -> "Offline"
        AppLanguage.PT -> "Offline"
        AppLanguage.RU -> "Офлайн"
        AppLanguage.IT -> "Non in linea"
    }

    fun onlineText(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "En ligne"
        AppLanguage.EN -> "Online"
        AppLanguage.ES -> "En línea"
        AppLanguage.DE -> "Online"
        AppLanguage.PT -> "Em linha"
        AppLanguage.RU -> "В сети"
        AppLanguage.IT -> "In linea"
    }

    fun noFriendsOnline(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Aucun ami en ligne pour le moment."
        AppLanguage.EN -> "No friends online right now."
        AppLanguage.ES -> "No hay amigos en línea por el momento."
        AppLanguage.DE -> "Zurzeit ist kein Freund online."
        AppLanguage.PT -> "Nenhum amigo online de momento."
        AppLanguage.RU -> "Сейчас нет друзей в сети."
        AppLanguage.IT -> "Nessun amico online al momento."
    }

    fun storageUsed(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Stockage utilisé"
        AppLanguage.EN -> "Storage used"
        AppLanguage.ES -> "Almacenamiento usado"
        AppLanguage.DE -> "Speicherplatz belegt"
        AppLanguage.PT -> "Armazenamento usado"
        AppLanguage.RU -> "Использовано памяти"
        AppLanguage.IT -> "Spazio utilizzato"
    }

    fun remaining(lang: AppLanguage, mb: String): String = when (lang) {
        AppLanguage.FR -> "Il reste $mb Mo."
        AppLanguage.EN -> "$mb MB remaining."
        AppLanguage.ES -> "Quedan $mb MB."
        AppLanguage.DE -> "$mb MB verbleibend."
        AppLanguage.PT -> "Restam $mb MB."
        AppLanguage.RU -> "Осталось $mb МБ."
        AppLanguage.IT -> "$mb MB rimanenti."
    }

    fun friendsPlayedText(lang: AppLanguage, count: Int): String = when (lang) {
        AppLanguage.FR -> if (count == 1) "1 ami y joue" else "$count amis y jouent"
        AppLanguage.EN -> if (count == 1) "1 friend plays" else "$count friends play"
        AppLanguage.ES -> if (count == 1) "1 amigo juega" else "$count amigos juegan"
        AppLanguage.DE -> if (count == 1) "1 Freund spielt" else "$count Freunde spielen"
        AppLanguage.PT -> if (count == 1) "1 amigo joga" else "$count amigos jogam"
        AppLanguage.RU -> if (count == 1) "1 друг играет" else "Играют $count друзей"
        AppLanguage.IT -> if (count == 1) "1 amico gioca" else "$count amici giocano"
    }

    fun playersCountText(lang: AppLanguage, count: Int): String = when (lang) {
        AppLanguage.FR -> "$count joueurs"
        AppLanguage.EN -> "$count players"
        AppLanguage.ES -> "$count jugadores"
        AppLanguage.DE -> "$count Spieler"
        AppLanguage.PT -> "$count jogadores"
        AppLanguage.RU -> "$count игроков"
        AppLanguage.IT -> "$count giocatori"
    }

    fun addFriendTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Ajouter un ami"
        AppLanguage.EN -> "Add a friend"
        AppLanguage.ES -> "Añadir un amigo"
        AppLanguage.DE -> "Freund hinzufügen"
        AppLanguage.PT -> "Adicionar amigo"
        AppLanguage.RU -> "Добавить друга"
        AppLanguage.IT -> "Aggiungi un amico"
    }

    fun yourFriendCode(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Votre code ami"
        AppLanguage.EN -> "Your friend code"
        AppLanguage.ES -> "Tu código de amigo"
        AppLanguage.DE -> "Dein Freundescode"
        AppLanguage.PT -> "O teu código de amigo"
        AppLanguage.RU -> "Ваш код друга"
        AppLanguage.IT -> "Il tuo codice amico"
    }

    fun copyButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Copier"
        AppLanguage.EN -> "Copy"
        AppLanguage.ES -> "Copy"
        AppLanguage.DE -> "Kopieren"
        AppLanguage.PT -> "Copiar"
        AppLanguage.RU -> "Копировать"
        AppLanguage.IT -> "Copia"
    }

    fun addByCodeTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Ajouter par code ami"
        AppLanguage.EN -> "Add by friend code"
        AppLanguage.ES -> "Añadir por código de amigo"
        AppLanguage.DE -> "Per Freundescode hinzufügen"
        AppLanguage.PT -> "Adicionar por código de amigo"
        AppLanguage.RU -> "Добавить по коду друга"
        AppLanguage.IT -> "Aggiungi tramite codice amico"
    }

    fun sendRequest(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Envoyer la demande"
        AppLanguage.EN -> "Send request"
        AppLanguage.ES -> "Enviar solicitud"
        AppLanguage.DE -> "Anfrage senden"
        AppLanguage.PT -> "Enviar pedido"
        AppLanguage.RU -> "Отправить запрос"
        AppLanguage.IT -> "Invia richiesta"
    }

    fun incomingRequests(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "DEMANDES ENTRANTES"
        AppLanguage.EN -> "INCOMING REQUESTS"
        AppLanguage.ES -> "SOLICITUDES ENTRANTES"
        AppLanguage.DE -> "EINGEHENDE ANFRAGEN"
        AppLanguage.PT -> "PEDIDOS RECEBIDOS"
        AppLanguage.RU -> "ВХОДЯЩИE ЗАПРОСЫ"
        AppLanguage.IT -> "RICHIESTE IN ARRIVO"
    }

    fun noRequests(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Aucune demande d'ami."
        AppLanguage.EN -> "No friend requests."
        AppLanguage.ES -> "No hay solicitudes de amistad."
        AppLanguage.DE -> "Keine Freundesanfragen."
        AppLanguage.PT -> "Nenhum pedido de amizade."
        AppLanguage.RU -> "Нет запросов в друзья."
        AppLanguage.IT -> "Nessuna richiesta di amicizia."
    }

    fun bestFriend(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Meilleur ami"
        AppLanguage.EN -> "Best friend"
        AppLanguage.ES -> "Mejor amigo"
        AppLanguage.DE -> "Bester Freund"
        AppLanguage.PT -> "Melhor amigo"
        AppLanguage.RU -> "Лучший друг"
        AppLanguage.IT -> "Migliore amico"
    }

    fun playHistoryHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "HISTORIQUE DE JEU"
        AppLanguage.EN -> "PLAY HISTORY"
        AppLanguage.ES -> "HISTORIAL DE JUEGO"
        AppLanguage.DE -> "SPIELVERLAUF"
        AppLanguage.PT -> "HISTÓRICO DE JOGO"
        AppLanguage.RU -> "ИСТОРИЯ ИГР"
        AppLanguage.IT -> "CRONOLOGIA DI GIOCO"
    }

    fun logout(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Se déconnecter"
        AppLanguage.EN -> "Log out"
        AppLanguage.ES -> "Cerrar sesión"
        AppLanguage.DE -> "Abmelden"
        AppLanguage.PT -> "Terminar sessão"
        AppLanguage.RU -> "Выйти"
        AppLanguage.IT -> "Disconnettiti"
    }

    fun readOnlyTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Lecture seule"
        AppLanguage.EN -> "Read-only"
        AppLanguage.ES -> "Sólo lectura"
        AppLanguage.DE -> "Schreibgeschützt"
        AppLanguage.PT -> "Apenas leitura"
        AppLanguage.RU -> "Только чтение"
        AppLanguage.IT -> "Solo lettura"
    }

    fun readOnlyDesc(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Vos sauvegardes cloud sont synchronisées automatiquement avec l'émulateur Ryujinx et la console Switch."
        AppLanguage.EN -> "Your cloud saves are automatically synced with Ryujinx emulator and Switch console."
        AppLanguage.ES -> "Tus guardados en la nube se sincronizan automáticamente con el emulador Ryujinx y la consola Switch."
        AppLanguage.DE -> "Deine Cloud-Speicherstände werden automatisch mit dem Ryujinx-Emulator und der Switch-Konsole synchronisiert."
        AppLanguage.PT -> "Os teus guardados na nuvem são sincronizados automaticamente com o emulador Ryujinx e a consola Switch."
        AppLanguage.RU -> "Ваши облачные сохранения автоматически синхронизируются с эмулятором Ryujinx и консолью Switch."
        AppLanguage.IT -> "I tuoi salvataggi cloud vengono synchronizzati automaticamente con l'emulatore Ryujinx e la console Switch."
    }

    fun noSaves(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Aucune sauvegarde cloud trouvée."
        AppLanguage.EN -> "No cloud saves found."
        AppLanguage.ES -> "No se encontraron guardados en la nube."
        AppLanguage.DE -> "Keine Cloud-Speicherstände gefunden."
        AppLanguage.PT -> "Nenhum guardado na nuvem encontrado."
        AppLanguage.RU -> "Сохранения в облаке не найдены."
        AppLanguage.IT -> "Nessun salvataggio cloud trovato."
    }

    // --- Register ---
    fun registerTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Créer un compte"
        AppLanguage.EN -> "Create an account"
        AppLanguage.ES -> "Crear una cuenta"
        AppLanguage.DE -> "Konto erstellen"
        AppLanguage.PT -> "Criar uma conta"
        AppLanguage.RU -> "Создать аккаунт"
        AppLanguage.IT -> "Crea un account"
    }

    fun usernameLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Pseudo"
        AppLanguage.EN -> "Username"
        AppLanguage.ES -> "Nombre de usuario"
        AppLanguage.DE -> "Benutzername"
        AppLanguage.PT -> "Nome de utilizador"
        AppLanguage.RU -> "Имя пользователя"
        AppLanguage.IT -> "Nome utente"
    }

    fun emailLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "E-mail"
        AppLanguage.EN -> "Email"
        AppLanguage.ES -> "Correo electrónico"
        AppLanguage.DE -> "E-Mail"
        AppLanguage.PT -> "E-mail"
        AppLanguage.RU -> "Эл. почта"
        AppLanguage.IT -> "E-mail"
    }

    fun passwordLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Mot de passe"
        AppLanguage.EN -> "Password"
        AppLanguage.ES -> "Contraseña"
        AppLanguage.DE -> "Passwort"
        AppLanguage.PT -> "Palavra-passe"
        AppLanguage.RU -> "Пароль"
        AppLanguage.IT -> "Password"
    }

    fun confirmPasswordLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Confirmer le mot de passe"
        AppLanguage.EN -> "Confirm password"
        AppLanguage.ES -> "Confirmar contraseña"
        AppLanguage.DE -> "Passwort bestätigen"
        AppLanguage.PT -> "Confirmar palavra-passe"
        AppLanguage.RU -> "Подтвердите пароль"
        AppLanguage.IT -> "Conferma password"
    }

    fun createAccountButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Créer mon compte"
        AppLanguage.EN -> "Create my account"
        AppLanguage.ES -> "Crear mi cuenta"
        AppLanguage.DE -> "Konto erstellen"
        AppLanguage.PT -> "Criar a minha conta"
        AppLanguage.RU -> "Создать аккаунт"
        AppLanguage.IT -> "Crea il mio account"
    }

    fun usernameAvailableText(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Pseudo disponible"
        AppLanguage.EN -> "Username available"
        AppLanguage.ES -> "Nombre de usuario disponible"
        AppLanguage.DE -> "Benutzername verfügbar"
        AppLanguage.PT -> "Nome de utilizador disponível"
        AppLanguage.RU -> "Имя доступно"
        AppLanguage.IT -> "Nome utente disponibile"
    }

    fun usernameTakenText(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Pseudo déjà pris"
        AppLanguage.EN -> "Username already taken"
        AppLanguage.ES -> "Nombre de usuario ya en uso"
        AppLanguage.DE -> "Benutzername bereits vergeben"
        AppLanguage.PT -> "Nome de utilizador já em uso"
        AppLanguage.RU -> "Имя уже занято"
        AppLanguage.IT -> "Nome utente già in uso"
    }

    fun alreadyHaveAccount(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Déjà un compte ? Se connecter"
        AppLanguage.EN -> "Already have an account? Log in"
        AppLanguage.ES -> "¿Ya tienes una cuenta? Inicia sesión"
        AppLanguage.DE -> "Bereits ein Konto? Anmelden"
        AppLanguage.PT -> "Já tens conta? Inicia sessão"
        AppLanguage.RU -> "Уже есть аккаунт? Войти"
        AppLanguage.IT -> "Hai già un account? Accedi"
    }

    fun noAccountYet(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Pas encore de compte ? Créer un compte"
        AppLanguage.EN -> "No account yet? Create one"
        AppLanguage.ES -> "¿Aún no tienes cuenta? Crea una"
        AppLanguage.DE -> "Noch kein Konto? Konto erstellen"
        AppLanguage.PT -> "Ainda não tens conta? Cria uma"
        AppLanguage.RU -> "Ещё нет аккаунта? Создать"
        AppLanguage.IT -> "Non hai un account? Creane uno"
    }

    fun continueAsGuest(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Continuer en invité"
        AppLanguage.EN -> "Continue as guest"
        AppLanguage.ES -> "Continuar como invitado"
        AppLanguage.DE -> "Als Gast fortfahren"
        AppLanguage.PT -> "Continuar como convidado"
        AppLanguage.RU -> "Продолжить как гость"
        AppLanguage.IT -> "Continua come ospite"
    }

    fun registrationClosed(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Les inscriptions sont temporairement fermées."
        AppLanguage.EN -> "Registration is temporarily closed."
        AppLanguage.ES -> "Los registros están cerrados temporalmente."
        AppLanguage.DE -> "Die Registrierung ist vorübergehend geschlossen."
        AppLanguage.PT -> "As inscrições estão temporariamente fechadas."
        AppLanguage.RU -> "Регистрация временно закрыта."
        AppLanguage.IT -> "Le registrazioni sono temporaneamente chiuse."
    }

    // --- Forgot / Reset password ---
    fun forgotPasswordLink(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Mot de passe oublié ?"
        AppLanguage.EN -> "Forgot password?"
        AppLanguage.ES -> "¿Olvidaste tu contraseña?"
        AppLanguage.DE -> "Passwort vergessen?"
        AppLanguage.PT -> "Esqueceste-te da palavra-passe?"
        AppLanguage.RU -> "Забыли пароль?"
        AppLanguage.IT -> "Password dimenticata?"
    }

    fun forgotPasswordTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Réinitialiser le mot de passe"
        AppLanguage.EN -> "Reset password"
        AppLanguage.ES -> "Restablecer contraseña"
        AppLanguage.DE -> "Passwort zurücksetzen"
        AppLanguage.PT -> "Repor palavra-passe"
        AppLanguage.RU -> "Сброс пароля"
        AppLanguage.IT -> "Reimposta password"
    }

    fun forgotPasswordDesc(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Entre ton e-mail, on t'envoie un lien pour réinitialiser ton mot de passe."
        AppLanguage.EN -> "Enter your email and we'll send you a link to reset your password."
        AppLanguage.ES -> "Introduce tu correo y te enviaremos un enlace para restablecer tu contraseña."
        AppLanguage.DE -> "Gib deine E-Mail ein, wir senden dir einen Link zum Zurücksetzen deines Passworts."
        AppLanguage.PT -> "Introduz o teu e-mail e enviamos-te um link para repor a palavra-passe."
        AppLanguage.RU -> "Введите e-mail, и мы отправим ссылку для сброса пароля."
        AppLanguage.IT -> "Inserisci la tua e-mail: ti invieremo un link per reimpostare la password."
    }

    fun sendResetLink(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Envoyer le lien"
        AppLanguage.EN -> "Send link"
        AppLanguage.ES -> "Enviar enlace"
        AppLanguage.DE -> "Link senden"
        AppLanguage.PT -> "Enviar link"
        AppLanguage.RU -> "Отправить ссылку"
        AppLanguage.IT -> "Invia link"
    }

    fun resetPasswordTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Nouveau mot de passe"
        AppLanguage.EN -> "New password"
        AppLanguage.ES -> "Nueva contraseña"
        AppLanguage.DE -> "Neues Passwort"
        AppLanguage.PT -> "Nova palavra-passe"
        AppLanguage.RU -> "Новый пароль"
        AppLanguage.IT -> "Nuova password"
    }

    fun resetTokenLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Code reçu par e-mail"
        AppLanguage.EN -> "Code from your email"
        AppLanguage.ES -> "Código recibido por correo"
        AppLanguage.DE -> "Code aus der E-Mail"
        AppLanguage.PT -> "Código recebido por e-mail"
        AppLanguage.RU -> "Код из письма"
        AppLanguage.IT -> "Codice ricevuto via e-mail"
    }

    fun haveCodeAlready(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "J'ai déjà un code"
        AppLanguage.EN -> "I already have a code"
        AppLanguage.ES -> "Ya tengo un código"
        AppLanguage.DE -> "Ich habe bereits einen Code"
        AppLanguage.PT -> "Já tenho um código"
        AppLanguage.RU -> "У меня уже есть код"
        AppLanguage.IT -> "Ho già un codice"
    }

    fun resetPasswordButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Réinitialiser"
        AppLanguage.EN -> "Reset"
        AppLanguage.ES -> "Restablecer"
        AppLanguage.DE -> "Zurücksetzen"
        AppLanguage.PT -> "Repor"
        AppLanguage.RU -> "Сбросить"
        AppLanguage.IT -> "Reimposta"
    }

    fun backToLogin(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Retour à la connexion"
        AppLanguage.EN -> "Back to login"
        AppLanguage.ES -> "Volver al inicio de sesión"
        AppLanguage.DE -> "Zurück zur Anmeldung"
        AppLanguage.PT -> "Voltar ao início de sessão"
        AppLanguage.RU -> "Назад ко входу"
        AppLanguage.IT -> "Torna al login"
    }

    // --- Account: email verification / change / delete ---
    fun resendVerificationButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Renvoyer l'e-mail de vérification"
        AppLanguage.EN -> "Resend verification email"
        AppLanguage.ES -> "Reenviar correo de verificación"
        AppLanguage.DE -> "Bestätigungs-E-Mail erneut senden"
        AppLanguage.PT -> "Reenviar e-mail de verificação"
        AppLanguage.RU -> "Отправить письмо повторно"
        AppLanguage.IT -> "Rinvia e-mail di verifica"
    }

    fun changeEmailTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Changer d'e-mail"
        AppLanguage.EN -> "Change email"
        AppLanguage.ES -> "Cambiar correo"
        AppLanguage.DE -> "E-Mail ändern"
        AppLanguage.PT -> "Mudar e-mail"
        AppLanguage.RU -> "Изменить e-mail"
        AppLanguage.IT -> "Cambia e-mail"
    }

    fun newEmailLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Nouvel e-mail"
        AppLanguage.EN -> "New email"
        AppLanguage.ES -> "Nuevo correo"
        AppLanguage.DE -> "Neue E-Mail"
        AppLanguage.PT -> "Novo e-mail"
        AppLanguage.RU -> "Новый e-mail"
        AppLanguage.IT -> "Nuova e-mail"
    }

    fun currentPasswordLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Mot de passe actuel"
        AppLanguage.EN -> "Current password"
        AppLanguage.ES -> "Contraseña actual"
        AppLanguage.DE -> "Aktuelles Passwort"
        AppLanguage.PT -> "Palavra-passe atual"
        AppLanguage.RU -> "Текущий пароль"
        AppLanguage.IT -> "Password attuale"
    }

    fun saveButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Enregistrer"
        AppLanguage.EN -> "Save"
        AppLanguage.ES -> "Guardar"
        AppLanguage.DE -> "Speichern"
        AppLanguage.PT -> "Guardar"
        AppLanguage.RU -> "Сохранить"
        AppLanguage.IT -> "Salva"
    }

    fun cancelButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Annuler"
        AppLanguage.EN -> "Cancel"
        AppLanguage.ES -> "Cancelar"
        AppLanguage.DE -> "Abbrechen"
        AppLanguage.PT -> "Cancelar"
        AppLanguage.RU -> "Отмена"
        AppLanguage.IT -> "Annulla"
    }

    fun deleteAccountButton(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Supprimer mon compte"
        AppLanguage.EN -> "Delete my account"
        AppLanguage.ES -> "Eliminar mi cuenta"
        AppLanguage.DE -> "Konto löschen"
        AppLanguage.PT -> "Eliminar a minha conta"
        AppLanguage.RU -> "Удалить аккаунт"
        AppLanguage.IT -> "Elimina il mio account"
    }

    fun deleteAccountConfirmTitle(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Supprimer ce compte ?"
        AppLanguage.EN -> "Delete this account?"
        AppLanguage.ES -> "¿Eliminar esta cuenta?"
        AppLanguage.DE -> "Dieses Konto löschen?"
        AppLanguage.PT -> "Eliminar esta conta?"
        AppLanguage.RU -> "Удалить этот аккаунт?"
        AppLanguage.IT -> "Eliminare questo account?"
    }

    fun deleteAccountConfirmDesc(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Cette action est irréversible. Entre ton mot de passe pour confirmer."
        AppLanguage.EN -> "This action is irreversible. Enter your password to confirm."
        AppLanguage.ES -> "Esta acción es irreversible. Introduce tu contraseña para confirmar."
        AppLanguage.DE -> "Diese Aktion ist unwiderruflich. Gib dein Passwort zur Bestätigung ein."
        AppLanguage.PT -> "Esta ação é irreversível. Introduz a tua palavra-passe para confirmar."
        AppLanguage.RU -> "Это действие необратимо. Введите пароль для подтверждения."
        AppLanguage.IT -> "Questa azione è irreversibile. Inserisci la password per confermare."
    }

    // --- Sessions ---
    fun mySessions(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Mes sessions"
        AppLanguage.EN -> "My sessions"
        AppLanguage.ES -> "Mis sesiones"
        AppLanguage.DE -> "Meine Sitzungen"
        AppLanguage.PT -> "As minhas sessões"
        AppLanguage.RU -> "Мои сессии"
        AppLanguage.IT -> "Le mie sessioni"
    }

    fun disconnectDevice(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Déconnecter"
        AppLanguage.EN -> "Disconnect"
        AppLanguage.ES -> "Desconectar"
        AppLanguage.DE -> "Trennen"
        AppLanguage.PT -> "Desligar"
        AppLanguage.RU -> "Отключить"
        AppLanguage.IT -> "Disconnetti"
    }

    fun disconnectThisDevice(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Me déconnecter"
        AppLanguage.EN -> "Disconnect me"
        AppLanguage.ES -> "Desconectarme"
        AppLanguage.DE -> "Mich trennen"
        AppLanguage.PT -> "Desligar-me"
        AppLanguage.RU -> "Отключить меня"
        AppLanguage.IT -> "Disconnettimi"
    }

    fun disconnectAllDevices(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Tout déconnecter"
        AppLanguage.EN -> "Disconnect all"
        AppLanguage.ES -> "Desconectar todo"
        AppLanguage.DE -> "Alle trennen"
        AppLanguage.PT -> "Desligar tudo"
        AppLanguage.RU -> "Отключить всё"
        AppLanguage.IT -> "Disconnetti tutto"
    }

    fun thisDeviceLabel(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "cet appareil"
        AppLanguage.EN -> "this device"
        AppLanguage.ES -> "este dispositivo"
        AppLanguage.DE -> "dieses Gerät"
        AppLanguage.PT -> "este dispositivo"
        AppLanguage.RU -> "это устройство"
        AppLanguage.IT -> "questo dispositivo"
    }

    fun noSessions(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Aucune session active."
        AppLanguage.EN -> "No active sessions."
        AppLanguage.ES -> "No hay sesiones activas."
        AppLanguage.DE -> "Keine aktiven Sitzungen."
        AppLanguage.PT -> "Nenhuma sessão ativa."
        AppLanguage.RU -> "Нет активных сессий."
        AppLanguage.IT -> "Nessuna sessione attiva."
    }

    // --- Friends: remove / block ---
    fun removeFriendAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Retirer cet ami"
        AppLanguage.EN -> "Remove friend"
        AppLanguage.ES -> "Eliminar amigo"
        AppLanguage.DE -> "Freund entfernen"
        AppLanguage.PT -> "Remover amigo"
        AppLanguage.RU -> "Удалить из друзей"
        AppLanguage.IT -> "Rimuovi amico"
    }

    fun blockFriendAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Bloquer"
        AppLanguage.EN -> "Block"
        AppLanguage.ES -> "Bloquear"
        AppLanguage.DE -> "Blockieren"
        AppLanguage.PT -> "Bloquear"
        AppLanguage.RU -> "Заблокировать"
        AppLanguage.IT -> "Blocca"
    }

    fun confirmRemoveFriendDesc(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Il ne sera plus dans ta liste d'amis."
        AppLanguage.EN -> "They will no longer be in your friends list."
        AppLanguage.ES -> "Ya no estará en tu lista de amigos."
        AppLanguage.DE -> "Er wird nicht mehr in deiner Freundesliste sein."
        AppLanguage.PT -> "Deixará de estar na tua lista de amigos."
        AppLanguage.RU -> "Он больше не будет в вашем списке друзей."
        AppLanguage.IT -> "Non sarà più nella tua lista amici."
    }

    fun confirmBlockFriendDesc(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Il ne pourra plus t'envoyer de demande ni te voir en ligne."
        AppLanguage.EN -> "They won't be able to send you requests or see you online anymore."
        AppLanguage.ES -> "Ya no podrá enviarte solicitudes ni verte en línea."
        AppLanguage.DE -> "Er kann dir keine Anfragen mehr senden oder dich online sehen."
        AppLanguage.PT -> "Deixará de poder enviar-te pedidos ou ver-te online."
        AppLanguage.RU -> "Он больше не сможет отправлять запросы или видеть вас в сети."
        AppLanguage.IT -> "Non potrà più inviarti richieste né vederti online."
    }

    // --- Saves: delete / download ---
    fun deleteSaveAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Supprimer"
        AppLanguage.EN -> "Delete"
        AppLanguage.ES -> "Eliminar"
        AppLanguage.DE -> "Löschen"
        AppLanguage.PT -> "Eliminar"
        AppLanguage.RU -> "Удалить"
        AppLanguage.IT -> "Elimina"
    }

    fun downloadSaveAction(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Télécharger"
        AppLanguage.EN -> "Download"
        AppLanguage.ES -> "Descargar"
        AppLanguage.DE -> "Herunterladen"
        AppLanguage.PT -> "Transferir"
        AppLanguage.RU -> "Скачать"
        AppLanguage.IT -> "Scarica"
    }

    fun confirmDeleteSaveDesc(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Cette sauvegarde cloud sera supprimée définitivement."
        AppLanguage.EN -> "This cloud save will be permanently deleted."
        AppLanguage.ES -> "Este guardado en la nube se eliminará permanentemente."
        AppLanguage.DE -> "Dieser Cloud-Speicherstand wird endgültig gelöscht."
        AppLanguage.PT -> "Este guardado na nuvem será eliminado definitivamente."
        AppLanguage.RU -> "Это облачное сохранение будет удалено навсегда."
        AppLanguage.IT -> "Questo salvataggio cloud verrà eliminato definitivamente."
    }

    fun saveDownloadedTo(lang: AppLanguage, path: String): String = when (lang) {
        AppLanguage.FR -> "Téléchargé : $path"
        AppLanguage.EN -> "Downloaded: $path"
        AppLanguage.ES -> "Descargado: $path"
        AppLanguage.DE -> "Heruntergeladen: $path"
        AppLanguage.PT -> "Transferido: $path"
        AppLanguage.RU -> "Скачано: $path"
        AppLanguage.IT -> "Scaricato: $path"
    }

    fun saveNotEligibleEmail(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Vérifie ton adresse e-mail pour activer les sauvegardes dans le cloud."
        AppLanguage.EN -> "Verify your email address to enable cloud saves."
        AppLanguage.ES -> "Verifica tu dirección de correo para activar los guardados en la nube."
        AppLanguage.DE -> "Bestätige deine E-Mail-Adresse, um Cloud-Speicherstände zu aktivieren."
        AppLanguage.PT -> "Verifica o teu e-mail para ativar os guardados na nuvem."
        AppLanguage.RU -> "Подтвердите e-mail, чтобы включить облачные сохранения."
        AppLanguage.IT -> "Verifica il tuo indirizzo e-mail per attivare i salvataggi cloud."
    }

    fun saveNotEligibleDiscord(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Lie ton compte à Discord pour activer les sauvegardes dans le cloud."
        AppLanguage.EN -> "Link your account to Discord to enable cloud saves."
        AppLanguage.ES -> "Vincula tu cuenta a Discord para activar los guardados en la nube."
        AppLanguage.DE -> "Verknüpfe dein Konto mit Discord, um Cloud-Speicherstände zu aktivieren."
        AppLanguage.PT -> "Liga a tua conta ao Discord para ativar os guardados na nuvem."
        AppLanguage.RU -> "Привяжите аккаунт к Discord, чтобы включить облачные сохранения."
        AppLanguage.IT -> "Collega il tuo account a Discord per attivare i salvataggi cloud."
    }
}
