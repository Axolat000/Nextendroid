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

1. HOME SCREEN:
   - Dynamic greeting personalized based on the time of day.
   - "ONLINE" Section: Horizontal scrollable list of currently connected 
     friends displaying active game icons and status.
   - "WHAT YOUR FRIENDS PLAY": Grid/list of the 14 official Nextendo 
     supported games played by your friends, with high-resolution box arts 
     and overlaid profile pictures (PFP) of your friends.
   - "ON NEXTENDO": Live leaderboard showing online player count per game.
   - Manual refresh button and 15-second background auto-refresh loop.

2. FRIENDS:
   - Complete list of friends sorted by status (favorites, online, offline).
   - Favorite friend toggle (★ / ☆).
   - Detailed friend view (FriendDetailScreen) displaying friend code, 
     online presence, and individual play history.

3. FRIEND REQUESTS (ADD FRIEND):
   - Display your own friend code with one-tap "Copy" button.
   - Send friend requests using friend codes (POST /api/friends).
   - Manage incoming friend requests with instant Accept & Decline actions.

4. CLOUD SAVES:
   - Cloud save storage usage visualization (used MB / remaining MB).
   - List of cloud saves automatically synced with Ryujinx emulator and 
     Nintendo Switch console.
   - Booster membership eligibility badge.

5. ACCOUNT & PROFILE:
   - Username editing with real-time server resynchronization.
   - Country selector with emoji flags.
   - Custom Profile Picture (PFP) picker from device gallery 
     (Base64 JPEG encoding and upload via POST /api/profile).
   - Multi-language support (English, French, Spanish, German, 
     Portuguese, Russian, Italian).

6. SETTINGS:
   - Accent color theme picker (Nextendo Pink, Ink, Plum, Raspberry, 
     Ember, Gold, Mint, Coral, or custom HEX code).
   - Live interactive UI button and text color preview.

------------------------------------------------------------------------
                        BACKEND API ENDPOINTS
------------------------------------------------------------------------

The application communicates directly with Nextendo Network REST API:
- POST /api/login             : User authentication
- GET  /api/me                : Logged-in user account details
- GET  /api/profile           : View user profile
- POST /api/profile           : Update profile picture (Base64 JPEG)
- POST /api/username          : Update display username
- POST /api/country           : Update country code
- GET  /api/friends           : List friends & incoming requests
- POST /api/friends           : Send friend request
- POST /api/friends/accept    : Accept friend request
- POST /api/friends/decline   : Decline friend request
- POST /api/friends/favorite  : Toggle favorite friend
- GET  /api/friends/history   : Individual play history per friend PID
- GET  /api/saves             : Cloud save list & quota
- GET  /api/online-counts     : Network online player statistics

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
│   │       │   ├── screens/   <- Screens (HomeScreen, FriendsScreen, AccountScreen, SettingsScreen...)
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
