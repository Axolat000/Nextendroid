========================================================================
                         NEXTENDROID (Android)
         Official & Modern Android Client for Nextendo Network
========================================================================

Nextendroid is a native Android application built in Kotlin using Jetpack
Compose, offering an elegant, responsive interface to interact with
Nextendo Network services (Nextendo Account & Cloud Services).

------------------------------------------------------------------------
                             FEATURES
------------------------------------------------------------------------

1. AUTHENTICATION:
   - Login with e-mail/username + password.
   - Account registration in-app (username, e-mail, password, country),
     with live username-availability checking and respect for the
     server's registration_open flag.
   - Guest login (quick account with just a username).
   - Forgot password / reset password flow.
   - Animated transitions between the auth screens.

2. HOME SCREEN:
   - Dynamic greeting personalized based on the time of day.
   - "ONLINE" Section: Horizontal scrollable list of currently connected
     friends displaying active game icons and status.
   - "WHAT YOUR FRIENDS PLAY": Grid/list of the 14 official Nextendo
     supported games played by your friends, with high-resolution box arts
     and overlaid profile pictures (PFP) of your friends.
   - "ON NEXTENDO": Live leaderboard showing online player count per game.
   - Manual refresh button and 15-second background auto-refresh loop.

3. FRIENDS:
   - Complete list of friends sorted by status (favorites, online, offline).
   - Favorite friend toggle (★ / ☆).
   - Detailed friend view (FriendDetailScreen) displaying friend code,
     online presence, and individual play history.
   - Remove a friend or block a user, each with a confirmation dialog.

4. FRIEND REQUESTS (ADD FRIEND):
   - Display your own friend code with one-tap "Copy" button.
   - Send friend requests using friend codes (POST /api/friends).
   - Manage incoming friend requests with instant Accept & Decline actions.

5. CLOUD SAVES:
   - Cloud save storage usage visualization (used MB / remaining MB).
   - List of cloud saves automatically synced with Ryujinx emulator and
     Nintendo Switch console.
   - Download a save to the device or delete it from the cloud.
   - Eligibility gate: explains when cloud saves are disabled because the
     e-mail isn't verified yet or the Discord account isn't linked.
   - Booster membership eligibility badge.

6. ACCOUNT & PROFILE:
   - Username editing with real-time server resynchronization.
   - Country selector with emoji flags.
   - Language selector as a dropdown menu (English, French, Spanish,
     German, Portuguese, Russian, Italian).
   - Custom Profile Picture (PFP) picker from device gallery
     (Base64 JPEG encoding and upload via POST /api/profile).
   - E-mail status (linked/verified) with a "resend verification e-mail"
     action, and a "change e-mail" dialog (requires current password).
   - Discord link status, showing the linked account when available.
   - Live Nextendo network status indicator (operational / down /
     checking), pinging the server directly from the app.
   - "My sessions" screen: lists every connected device (browser,
     Ryujinx, Switch) with per-device or "disconnect everywhere" revoke.
   - Delete account (irreversible, requires password confirmation).

7. SETTINGS:
   - Accent color theme picker (Nextendo Pink, Ink, Plum, Raspberry,
     Ember, Gold, Mint, Coral, or custom HEX code).
   - Live interactive UI button and text color preview.

8. POLISH:
   - Animated transitions between tabs and sub-screens, animated bottom
     navigation bar, pulsing online/status indicators, animated login
     card and error messages.

------------------------------------------------------------------------
                        BACKEND API ENDPOINTS
------------------------------------------------------------------------

The application communicates directly with the Nextendo Network REST API:

Auth & account
- POST /api/login                : User authentication
- POST /api/register             : Create an account
- POST /api/guest                : Create a guest account
- GET  /api/site-config          : Public config (e.g. registration_open)
- GET  /api/username-available   : Check username availability
- POST /api/forgot                : Request a password-reset e-mail
- POST /api/reset                 : Reset password with a token
- POST /api/resend-verification  : Resend the e-mail verification link
- POST /api/email                 : Change e-mail (requires password)
- POST /api/delete-account        : Delete own account (requires password)
- GET  /api/sessions              : List active sessions/devices
- POST /api/sessions/revoke       : Revoke a single session
- POST /api/sessions/revoke-all   : Revoke every session (log out everywhere)

Profile
- GET  /api/me                   : Logged-in user account details
- GET  /api/profile              : View user profile
- POST /api/profile              : Update profile picture (Base64 JPEG)
- POST /api/username             : Update display username
- POST /api/country              : Update country code

Friends
- GET  /api/friends              : List friends & incoming requests
- POST /api/friends              : Send friend request
- POST /api/friends/accept       : Accept friend request
- POST /api/friends/decline      : Decline friend request
- POST /api/friends/favorite     : Set favorite friend {pid, favorite}
- POST /api/friends/remove       : Remove a friend
- POST /api/friends/block        : Block a user
- GET  /api/friends/history      : Individual play history per friend PID

Saves & games
- GET    /api/saves              : Cloud save list & quota
- DELETE /api/save/{titleId}     : Delete a cloud save
- GET    /api/save/{titleId}     : Download a cloud save (binary)
- GET    /api/online-counts      : Network online player statistics
- GET    /api/history            : Own play history
- GET    /api/gameinfo           : Extra game info (tagline/description)
- GET    /api/gamemedia/{titleId}/cover : Server-hosted game cover art
                                    (used as a fallback for games not
                                    bundled locally in assets/covers)

Note: /api/admin/* (user management, bans, reports) exists on the
backend but is intentionally NOT used by this client — it's reserved
for the web admin panel.

------------------------------------------------------------------------
                        PROJECT STRUCTURE
------------------------------------------------------------------------

Nextendroid/
├── app/
│   ├── src/main/
│   │   ├── assets/covers/     <- Local high-res box arts for 14 Nextendo games
│   │   └── java/com/axolat/nextendroid/
│   │       ├── data/          <- Repositories, Models (Models, GameDictionary) & API REST
│   │       ├── ui/
│   │       │   ├── components/<- Compose Components (AvatarView, GameCard, BottomNavBar...)
│   │       │   ├── screens/   <- Screens (HomeScreen, FriendsScreen, AccountScreen,
│   │       │   │                 RegisterScreen, ForgotPasswordScreen, ResetPasswordScreen,
│   │       │   │                 SessionsScreen, SettingsScreen...)
│   │       │   ├── theme/     <- Theme, Colors & AppLanguage/Strings
│   │       │   └── viewmodel/ <- NextendoViewModel (StateFlow & Coroutines)
│   │       └── MainActivity.kt
└── README.txt

------------------------------------------------------------------------
                            BUILDING
------------------------------------------------------------------------

To build the application in Debug mode:
  ./gradlew assembleDebug

The output APK will be generated at:
  app/build/outputs/apk/debug/app-debug.apk

------------------------------------------------------------------------
                             LICENSE
------------------------------------------------------------------------

This project is distributed under the open-source MIT License.
See the LICENSE file for more details.
========================================================================
