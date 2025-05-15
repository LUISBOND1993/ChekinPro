<p align="center">
  <img src="shared image.jpg" alt="Logo CheckinPro" width="120">
</p>

# CheckinPro
ChekinPro es una aplicación móvil desarrollada en Kotlin para dispositivos Android, diseñada para gestionar de manera eficiente el ingreso y salida de visitantes en conjuntos residenciales. La aplicación permite a los residentes registrar visitantes, controlar accesos y mantener un historial detallado de visitas, todo respaldado por Firebase para autenticación y almacenamiento de datos.
## Características Principales
 - Registro y autenticación de usuarios mediante Firebase Authentication.
 - Gestión de visitantes con registro de datos personales y de vehículos.
 - Visualización del historial de visitas por residente.
 - Control de acceso con validación de permisos.
 -  Interfaz intuitiva y amigable para el usuario
   <p align="center">
  <img src="https://github.com/user-attachments/assets/a9c00b60-9d39-4fb7-9f3e-95b26c15523c" width="250"/>
  <img src="https://github.com/user-attachments/assets/8033a619-852c-4043-86c9-ff7c7fa2e7cf" width="250"/>
  <img src="https://github.com/user-attachments/assets/f5445df8-47c6-45ec-a2e8-66a8a54e809b" width="250"/>
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/2bdfd6c5-a61c-44ab-8930-00206e2bda49" width="250"/>
  <img src="https://github.com/user-attachments/assets/b49e98ee-3478-4172-b405-44e92a0008d4" width="250"/>
  <img src="https://github.com/user-attachments/assets/7589d53a-ed16-428c-9be9-ee75d29de492" width="250"/>
</p>

## Instalación
Para ejecutar ChekinPro en tu entorno local, sigue estos pasos:
1. Clona este repositorio:
https://github.com/LUISBOND1993/ChekinPro.git
2. Abre el proyecto en Android Studio.
3. Configura una instancia de Firebase y agrega el archivo google-services.json en el directorio app/.
4. Sincroniza el proyecto con Gradle.
5. Ejecuta la aplicación en un emulador o dispositivo físico.

## Tecnologías Utilizadas
- Kotlin
- Android SDK
- Firebase Authentication
- Firebase Firestore
- ConstraintLayout
- Material Design Components

## 📁 Estructura del Proyecto
ChekinPro/
└── app/
└── src/
└── main/
├── java/com/example/chekinpro/
│ ├── Login.kt
│ ├── Menu.kt
│ └── ConfiguracionCuentaActivity.kt
└── res/
├── layout/
├── drawable/
└── values/
└── strings.xml
├── AndroidManifest.xml
└── build.gradle

## 📄 Licencia
Este proyecto está licenciado bajo la Licencia MIT.

## 👨‍💻 Autores
- Carlos Sutachan Parra
- Luis Bonilla Devia
## Necesidad que resuelve
ChekinPro responde a una necesidad clave en los conjuntos residenciales: el control preciso y transparente de visitas y cobros asociados. Muchas veces los propietarios carecen de visibilidad sobre quién ingresa, cuánto tiempo permanece un visitante y si se ha realizado el pago correspondiente por el uso de parqueaderos o áreas comunes. Nuestra aplicación permite a los residentes registrar entradas y salidas, calcular automáticamente el cobro según la duración de la visita y mantener un historial confiable y actualizado. Esto no solo mejora la seguridad, sino que también facilita la gestión administrativa y evita malentendidos en temas financieros con la administración del conjunto.

