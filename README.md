# 🐦 Flappy Bird - Java Swing Edition

Un clon del clásico Flappy Bird desarrollado en **Java** utilizando la librería **Swing**. Esta versión incluye características avanzadas como un sistema de configuración centralizado, skins personalizables, dificultad dinámica y registro de nicknames.

## 📸 Fondos

| Modo Día | Modo Noche | Modo Navideño |
|:---:|:---:|:---:|
| ![Dia](resources/bg-day.png) | ![Noche](resources/bg-night.png) | ![Navidad](resources/bg-christmas.png) |

## ✨ Características Principales

* **☕ Java Puro:** Sin librerías externas pesadas, solo Java Swing y AWT.
* **⚙️ Configuración Centralizada:** Control total del juego desde un solo archivo (`Configuracion.java`).
* **🎨 Sistema de Skins:** Cambia fácilmente la apariencia del ave, las tuberías y el fondo.
* **📈 Dificultad Dinámica:** El juego se vuelve más rápido automáticamente cada ciertos puntos.
* **👤 Sistema de Jugador:** Entrada de Nickname al inicio y cuenta regresiva.
* **🔄 Reinicio Rápido:** Mecánica de "Game Over" con reinicio instantáneo.

## 📂 Estructura del Proyecto

El proyecto consta de tres clases principales y una carpeta de recursos:

```text
📦 FlappyBird-Project
 ┣ 📂 resources          <-- ¡IMPORTANTE! Aquí van tus imágenes
 ┃ ┣ 📜 bird-yellow.png
 ┃ ┣ 📜 bird-blue.png
 ┃ ┣ 📜 bg-day.png
 ┃ ┣ 📜 bg-christmas.png
 ┃ ┗ 📜 ... (otros assets)
 ┣ 📜 App.java           <-- Clase Principal (Main)
 ┣ 📜 FlappyBird.java    <-- Lógica del juego y renderizado
 ┗ 📜 Configuracion.java <-- Ajustes globales