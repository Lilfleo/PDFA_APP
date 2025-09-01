# RECIP'IA

Une application Android moderne de gestion de recettes alimentée par l'intelligence artificielle.

## Description

RECIP'IA est une application Android native développée avec les dernières technologies Android modernes. Elle permet aux utilisateurs de découvrir, créer et gérer leurs recettes favorites avec l'aide de l'intelligence artificielle.

## Fonctionnalités

- Recherche intelligente de recettes grâce à l'IA
- Gestion complète des recettes (création, modification, organisation)
- Stockage local sécurisé
- Interface utilisateur moderne avec Material Design 3
- Architecture MVVM optimisée

## Technologies

### Framework et Interface
- Jetpack Compose - Toolkit UI moderne pour Android
- Material 3 - Système de design Google

### Architecture
- Hilt - Injection de dépendances
- MVVM - Pattern architectural
- Room - Base de données locale
- Coroutines - Programmation asynchrone
- ViewModel - Gestion des données UI
- LiveData/StateFlow - Observation réactive des données

## Prérequis

- Android Studio Hedgehog 2023.1.1 ou supérieur
- SDK Android 24+ (Android 7.0)
- Kotlin 1.9.0+
- Gradle 8.0+

## Installation

### Cloner le repository
```bash
git https://github.com/Lilfleo/PDFA_APP
cd PDFA_APP
```

### Configuration dans Android Studio
1. Ouvrir Android Studio
2. Sélectionner "Open an existing project"
3. Naviguer vers le dossier du projet
4. Synchroniser le projet Gradle
5. Compiler et lancer l'application

## Structure du projet

```
app/src/main/java/com/pdfa/pdfa_app/
├── api/                          # Couche API
├── assets/                       # Ressources statiques
│   └── icons/                    # Icônes de navigation
├── data/                         # Couche de données
│   ├── dao/                      # Data Access Objects
│   ├── database/                 # Configuration base de données
│   ├── model/                    # Entités et modèles
│   └── repository/               # Repositories
├── di/                           # Injection de dépendances Hilt
├── ui/                           # Interface utilisateur
│   ├── theme/                    # Thème Material 3
│   └── viewmodel/                # ViewModels
├── user_interface/               # Composants UI Compose
│   ├── component/                # Composants réutilisables
│   ├── rooting/                  # Navigation et routing
│   └── screens/                  # Écrans principaux
└── utilz/                        # Utilitaires et helpers
```

## Configuration

### Base de données
La base de données Room est initialisée automatiquement au premier lancement. Les migrations sont gérées dans le fichier `AppDatabase.kt`.

## Tests

### Tests unitaires
```bash
./gradlew test
```

### Tests instrumentés
```bash
./gradlew connectedAndroidTest
```

## Build

### Version debug
```bash
./gradlew assembleDebug
```

### Version release
```bash
./gradlew assembleRelease
```

### Android App Bundle
```bash
./gradlew bundleRelease
```

## Contribution

1. Fork du projet
2. Création d'une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit des modifications (`git commit -m 'Ajout nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Ouverture d'une Pull Request


## Liens utiles

- [Documentation Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Guide Room](https://developer.android.com/training/data-storage/room)
- [Documentation Hilt](https://dagger.dev/hilt/)
- [Material Design 3](https://material.io/develop/android)