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

    fun amisHeader(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "AMIS"
        AppLanguage.EN -> "FRIENDS"
        AppLanguage.ES -> "AMIGOS"
        AppLanguage.DE -> "FREUNDE"
        AppLanguage.PT -> "AMIGOS"
        AppLanguage.RU -> "ДРУЗЬЯ"
        AppLanguage.IT -> "AMICI"
    }

    fun selectLanguage(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Langue de l'application"
        AppLanguage.EN -> "App Language"
        AppLanguage.ES -> "Idioma de la aplicación"
        AppLanguage.DE -> "App-Sprache"
        AppLanguage.PT -> "Idioma da aplicação"
        AppLanguage.RU -> "Язык приложения"
        AppLanguage.IT -> "Lingua dell'applicazione"
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
        AppLanguage.ES -> "Copiar"
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

    fun playersMet(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Joueurs rencontrés"
        AppLanguage.EN -> "Players met"
        AppLanguage.ES -> "Jugadores conocidos"
        AppLanguage.DE -> "Getroffene Spieler"
        AppLanguage.PT -> "Jogadores encontrados"
        AppLanguage.RU -> "Встреченные игроки"
        AppLanguage.IT -> "Giocatori incontrati"
    }

    fun incomingRequests(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "DEMANDES ENTRANTES"
        AppLanguage.EN -> "INCOMING REQUESTS"
        AppLanguage.ES -> "SOLICITUDES ENTRANTES"
        AppLanguage.DE -> "EINGEHENDE ANFRAGEN"
        AppLanguage.PT -> "PEDIDOS RECEBIDOS"
        AppLanguage.RU -> "ВХОДЯЩИЕ ЗАПРОСЫ"
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

    fun emailStatus(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Statut E-mail"
        AppLanguage.EN -> "Email Status"
        AppLanguage.ES -> "Estado del correo"
        AppLanguage.DE -> "E-Mail-Status"
        AppLanguage.PT -> "Estado do e-mail"
        AppLanguage.RU -> "Статус e-mail"
        AppLanguage.IT -> "Stato e-mail"
    }

    fun verified(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Vérifié"
        AppLanguage.EN -> "Verified"
        AppLanguage.ES -> "Verificado"
        AppLanguage.DE -> "Bestätigt"
        AppLanguage.PT -> "Verificado"
        AppLanguage.RU -> "Подтвержден"
        AppLanguage.IT -> "Verificato"
    }

    fun discordAccount(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Compte Discord"
        AppLanguage.EN -> "Discord Account"
        AppLanguage.ES -> "Cuenta de Discord"
        AppLanguage.DE -> "Discord-Konto"
        AppLanguage.PT -> "Conta do Discord"
        AppLanguage.RU -> "Аккаунт Discord"
        AppLanguage.IT -> "Account Discord"
    }

    fun linked(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Lié"
        AppLanguage.EN -> "Linked"
        AppLanguage.ES -> "Vinculado"
        AppLanguage.DE -> "Verknüpft"
        AppLanguage.PT -> "Vinculado"
        AppLanguage.RU -> "Привязан"
        AppLanguage.IT -> "Collegato"
    }

    fun unlinked(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Non lié"
        AppLanguage.EN -> "Unlinked"
        AppLanguage.ES -> "No vinculado"
        AppLanguage.DE -> "No vinculado"
        AppLanguage.PT -> "Não vinculado"
        AppLanguage.RU -> "Не привязан"
        AppLanguage.IT -> "Non collegato"
    }

    fun networkStatus(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Réseau Nextendo"
        AppLanguage.EN -> "Nextendo Network"
        AppLanguage.ES -> "Red Nextendo"
        AppLanguage.DE -> "Nextendo-Netzwerk"
        AppLanguage.PT -> "Rede Nextendo"
        AppLanguage.RU -> "Сеть Nextendo"
        AppLanguage.IT -> "Rete Nextendo"
    }

    fun operational(lang: AppLanguage): String = when (lang) {
        AppLanguage.FR -> "Opérationnel"
        AppLanguage.EN -> "Operational"
        AppLanguage.ES -> "Operativo"
        AppLanguage.DE -> "Betriebsbereit"
        AppLanguage.PT -> "Operacional"
        AppLanguage.RU -> "Работает"
        AppLanguage.IT -> "Operativo"
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
}
