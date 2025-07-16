# ⚽️ Pokedex

Pokedex is a multiplatform application built with **Kotlin Multiplatform** and **Jetpack Compose**. It allows users to search for Pokémon, browse an infinite list of entries, and view detailed information about each Pokémon. The app follows a clean architecture structure and adopts the **MVI (Model-View-Intent)** pattern for scalable and maintainable code.

<img src="https://github.com/user-attachments/assets/70f25678-64c0-4d9f-b979-a1d49a54a89a" width="500" alt="PokedexApp" />
---

## 🚀 Getting Started

We recommend using **2.1.21** and the latest stable versions of **Android Studio** with **Kotlin Multiplatform support**.

### Clone the Repository

```bash
git clone https://github.com/rwnhrmwn23/pokedex-kmp.git
```

---

## 📦 Dependencies

| Plugin / Library          | Description                              |
| ------------------------- | ---------------------------------------- |
| **Ktor**                  | HTTP client for multiplatform networking |
| **Koin**                  | Dependency injection framework           |
| **Kotlinx Serialization** | JSON parsing                             |
| **Kamel**                 | Multiplatform image loading              |
| **Navigation Compose**    | Navigation handling in Compose UI        |

---

## 🧱 Architecture Overview

Pokedex follows a **Clean Architecture** structure:

* **Data Layer**: handles API calls using Ktor
* **Domain Layer**: holds business logic and pure models
* **Presentation Layer**: built with Jetpack Compose and MVI pattern

---

## ✨ Features

* 🔍 **Pokémon Search** with real-time API fetching
* ♾️ **Infinite Scroll List** for browsing Pokémon
* 📃 **Pokémon Details** including type, stats, and abilities
* ✔️ Clean Architecture with **MVI Pattern**
* 🌟 Fully built with **Jetpack Compose UI**
* 🚀 Shared logic across Android and iOS via **KMP**

---

## 📊 Screens Overview

<img src="https://github.com/user-attachments/assets/7090183f-226f-4536-ba27-845fd4505848" width="180" height="390" alt="Pokedex">
<img src="https://github.com/user-attachments/assets/b98631ea-ee3a-4266-8655-cc086cfca64f" width="180" height="390" alt="Pokedex">
<img src="https://github.com/user-attachments/assets/d464b3a3-74ac-4abb-8e84-1c967e01c052" width="180" height="390" alt="Pokedex">
<img src="https://github.com/user-attachments/assets/57dfd55e-663d-4093-b7bd-ec00df5ecf8e" width="180" height="390" alt="Pokedex">
<img src="https://github.com/user-attachments/assets/bc1fcdf1-67e5-41e8-a4c6-c26c714c6d90" width="180" height="390" alt="Pokedex">

---

## 📚 Why This Project?

Pokedex was created to demonstrate how Kotlin Multiplatform and Jetpack Compose can be combined with best practices in architecture (Clean Architecture + MVI) to build performant, maintainable, and modern mobile apps.

---

## 🎨 Design Inspiration

Special ❤️ to

- [Ricardo Schiniegoski](https://www.figma.com/design/6OpmtlNA56ACP4CfQg8uGy/Pok%C3%A9dex--Community-?node-id=1037-1380&t=OXO84B4FgwvB8p6R-0)
- [Junior Saraiva](https://www.figma.com/design/PZhxne81EwlBOfn5QVbpEx/Pok%C3%A9dex---Pok%C3%A9mon-App--Community-?node-id=95-236&p=f&t=IFU7vh288JPH8Ewm-0)
