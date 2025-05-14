# CatBreedExplorer

A modern Android app built with Kotlin and Jetpack Compose to explore, search, and favorite cat breeds using [The Cat API](https://thecatapi.com/). Designed with clean architecture principles, offline support, and rich UI in mind.

---

## 🚀 Features

- 🐱 Browse paginated list of cat breeds with images and key facts
- 🔎 Real-time search with debounce
- 📄 Detailed breed screen with:
  - Carousel/grid of multiple breed images
  - Wikipedia integration
- ❤️ Mark/unmark favorite breeds (persisted across app restarts)
- 🔌 Offline support (caches details + favorites using Room)
- 🎨 Modern UI with Material 3 and Jetpack Compose
- 🌙 Light/Dark mode support
- 🧪 Unit tests for business logic using Mockk and JUnit
- 🔐 Secure API key management via `local.properties`

---

## 🏛 Architecture

This project follows **Clean Architecture** using **MVVM + UseCases**, with strict separation of concerns:
com.challenge.catbreedexplorer
├── data # Retrofit, DTOs, Room, Mappers
├── domain # Models, UseCases, Interfaces
├── presentation# UI (Compose), ViewModels, Navigation
├── di # Hilt modules
├── utils # Error handling, Constants, Extensions



---

## 🧰 Tech Stack

| Category         | Technology |
|------------------|------------|
| Language         | Kotlin |
| UI Framework     | Jetpack Compose (Material 3) |
| Architecture     | Clean Architecture (MVVM + UseCases) |
| DI               | Hilt |
| Networking       | Retrofit + OkHttp (with Interceptor) |
| Image Loading    | Coil |
| Local Storage    | Room Database |
| Async            | Kotlin Coroutines + Flow |
| Testing          | JUnit, Mockk, Coroutine Test |
| Linting          | KtLint |
| Build Tools      | Android Studio Hedgehog/Koala (2025+) |
| API              | [The Cat API](https://thecatapi.com/) |

---

## 🧪 Testing

- ✅ ViewModels and Repositories covered with unit tests
- ✅ Mockk used for mocking dependencies
- ✅ Coroutine test rules for testing Flows
- 🔜 (Optional) UI testing with Compose Test

---

## 🧱 Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/CatBreedExplorer.git
   cd CatBreedExplorer




