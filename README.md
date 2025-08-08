# 📸 Photo Album App (Android - Kotlin)

A simple and elegant Android app built in **Kotlin** that lets users **store, view, update, and delete personal photo entries** using the **Room Database** for local storage. The app follows **MVVM architecture** and integrates Jetpack components for a robust and scalable design.

---

## 🚀 Features

- Add photos with title, description, and date  
- View all photos in a scrollable gallery  
- Edit or delete existing photo entries  
- Search photos by title or date  
- Persistent offline storage using Room (SQLite abstraction)

---

## 📱 Screenshots

<img width="400" height="704" alt="1" src="https://github.com/user-attachments/assets/be45dd54-1444-4bea-8b53-821ed0f7c905" />
<img width="371" height="662" alt="2" src="https://github.com/user-attachments/assets/f99da8fb-cfa6-4d45-92e1-6548985c514b" />
<img width="374" height="662" alt="3" src="https://github.com/user-attachments/assets/7ed85cd8-59d9-4c95-8ff4-d92e647b9636" />


---

## 🛠️ Tech Stack

**Language:** Kotlin  
**Architecture:** MVVM  
**Database:** Room (SQLite)  
**UI Components:** RecyclerView, EditText, ImageView, etc.  
**Jetpack Components:** ViewModel, LiveData, Lifecycle  
**Image Handling:** URI referencing from device gallery

---

## 🏛️ Room Database Overview

- **Entity:** Represents each photo entry with fields like ID, title, description, date, and image URI.
- **DAO (Data Access Object):** Contains methods to insert, update, delete, and query photos.
- **Database:** Provides the database instance and connects the DAO to the application.

---

## 📁 Folder Structure

PhotoAlbumApp/
├── data/
│ ├── PhotoDao.kt
│ ├── PhotoDatabase.kt
│ └── PhotoEntry.kt
├── ui/
│ ├── MainActivity.kt
│ ├── AddEditActivity.kt
│ └── PhotoAdapter.kt
├── viewmodel/
│ └── PhotoViewModel.kt
├── res/
│ └── layout/
└── AndroidManifest.xml

---

## 🔧 Setup Instructions

1. Clone the repository  
2. Open the project in Android Studio  
3. Run the app on an emulator or physical device (Android 6.0 or above)  
4. Grant storage/gallery permissions if prompted  
5. Start creating your personal photo album!

---

## ✅ Requirements

- Android Studio Hedgehog or later  
- Kotlin 1.8+  
- Gradle 8.0+  
- Minimum SDK: 23 (Android 6.0)

---

## 💡 Future Enhancements

- Cloud sync using Firebase  
- Support for photo categories or tags  
- Dark mode integration  
- Export/share photo albums  
- Pin favorite memories

---

## 📄 License

This project is open-source and available under the MIT License.

---

## 🙌 Acknowledgements

- Android Developers – Room Persistence Library  
- Android Jetpack Architecture Components  
- Kotlin Programming Language
