# CatBreedExplorer

Cat Breed Explorer is a modern Android application built with Jetpack Compose, MVI architecture, Room, Retrofit, and Hilt, allowing users to explore various cat breeds with detailed information and images. It is designed with performance, offline support, and clean architecture in mind.

Although the project is implemented as a single Gradle module, it follows a modular code structure, separating responsibilities across clearly defined layers: data, domain, ui, and di. This architecture is intentionally designed to be scalable and can be easily refactored into multiple Gradle modules as the project grows.
---

## 🚀 Features

📄 Cat List Screen
- Fetches and displays a list of cat breeds from thecatapi.com
- Real-time search functionality
- Offline-first design: shows locally cached data if offline
- Handles screen rotation and maintains state
- Secure API key management via `local.properties`

📷 Cat Detail Screen
- Displays detailed breed information: name, origin, lifespan, temperament, and description
- Shows breed-specific image gallery
- Offline cache support for previously viewed breed details and images
- Opens breed Wikipedia link in browser (if available)
- Avoids unnecessary API calls for already cached images

📅 Offline & Caching Strategy
- Uses Room to persist both breed and image data
- Refreshes data only when online
- Maintains full functionality when offline, thanks to smart caching

🔧 Tech Stack
- Language: Kotlin
- UI: Jetpack Compose
- Architecture: Clean Architecture + MVI
- Networking: Retrofit + OkHttp
- DI: Hilt
- Persistence: Room

---

## 🏛 Architecture Overview
![image](https://github.com/user-attachments/assets/ccd2b2d5-108c-426e-94b7-1ff547c09eca)

---

## 📹 Demo Video
https://drive.google.com/file/d/1pVMDehKo6sDvC8WVcmYMc24BqWa6fCIL/view?usp=sharing

This demo showcases:
- Navigating from the cat list to breed detail screen
- Smooth scrolling and responsive layouts in both portrait and landscape modes
- Proper offline caching (data remains accessible when network is disconnected)
- Image loading optimizations (images are not reloaded unnecessarily)
- Opening the breed’s Wikipedia page in a browser
- Error handling and fallback mechanisms in case of network/API failures
---

## ✅ Completed Tasks

- Implemented Clean Architecture with MVI pattern
- Room database integration with DAOs for caching breeds and images
- Retrofit integration with API key handling
- Dynamic search filtering in Cat List
- Detail screen loads from local DB if offline
- NetworkChecker utility without requiring extra permissions
- Jetpack Compose-based fully reactive UI
- UI state preservation on rotation
- Efficient API usage by skipping redundant image loads
- Comprehensive ViewModel unit testing with MockK + Turbine
- GitHub README + documentation and video demo

---


## ❌ Remaining / Optional Improvements

- Pagination or lazy loading of API data (currently fetches all at once)
- UI tests (e.g., using Compose Test framework)
- Better error handling and retry logic per screen
- Dark mode or theme customization
- Localization support
- test in  some other real device (SDK 21 to 34) 
---


## ⚙ RequirementsAndroid 
- Android Studio Ladybug (2024.2.1 Patch 2) or later
- Min SDK 21+
- AGP 8.7.3
- kotlin 2.0.21
- Gradle 8.9
- Internet access for first-time API fetch



