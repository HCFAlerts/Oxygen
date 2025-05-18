![image](https://github.com/user-attachments/assets/89543cb0-3c95-4947-8a96-ff86a8f51a53)



# 🌊 Oxygen | v1.7 - Modern BungeeCore

![Open Issues](https://img.shields.io/github/issues/HCFAlerts/Oxygen)
![Forks](https://img.shields.io/github/forks/HCFAlerts/Oxygen)
![Stars](https://img.shields.io/github/stars/HCFAlerts/Oxygen)
![License](https://img.shields.io/github/license/HCFAlerts/Oxygen)

> 🥇 **#1 in Modern BungeeCores** – trusted by servers for performance, simplicity, and customization.  
> A lightweight, modern, and fully customizable BungeeCord core with MOTD countdown, social commands, maintenance mode, and more.

---

## 📚 Table of Contents

- [About](#about)
- [Features](#features)
- [Installation](#installation)
- [Configuration](#configuration)
- [Media](#media)
- [Contributing](#contributing)
- [Credits](#credits)
- [License](#license)

---

## 🧾 About

**Oxygen** is a fully standalone BungeeCord core focused on **performance**, **flexibility**, and **modern design**.  
Just a clean and powerful foundation for networks that want full control over their MOTD, maintenance systems, and hub utility features.

Created for serious Minecraft networks needing customizable social commands, hover messages, auto hub redirection, and a Discord bot module.

---

## ✨ Features

- ⚡ **Optimized & Lightweight**  
- 🧾 **Fully Customizable** via YAML  
- 🖥️ **MOTD System**  
  - Countdown display (e.g., for SOTW, EOTW, etc.)  
  - Custom player slots  
  - Hover text support  
  - Maintenance mode display  
- 📢 **Social/Media Commands**  
  - `/discord`, `/teamspeak`, `/twitter`, `/store`, `/website`  
- 🆘 **Utility Commands**  
  - `/help`, `/hub`, `/find`, `/server`  
- 🛠️ **Maintenance Mode**  
  - Dynamic MOTD updates  
  - Join-kick logic with bypass  
- 🦺 **Auto Hub Redirection**  
  - Detects hub server and redirects players on kick  
- 🤖 **Discord Bot Integration** (Optional module)  
- 🌐 **Multi-language Support Ready**

---

## 🧰 Installation

1. Download the latest `.jar` from the [Releases](https://github.com/HCFAlerts/Oxygen/releases) tab or [BuiltByBit](https://builtbybit.com/resources/oxygen-modern-bungee-core.54077/).
2. Place it in your **BungeeCord's `/plugins`** directory.
3. Start or restart your BungeeCord proxy.
4. Modify the `config.yml` to match your server needs (MOTD, maintenance mode, command responses, etc).

---

## 🧩 Configuration

All settings can be edited in `plugins/Oxygen/config.yml`.
Examples include:

- Custom MOTD layout with placeholders
- Countdown mode for scheduled events
- Maintenance toggles
- Command aliases and responses
- Discord bot token (if enabled)

Simple and clean YAML-based setup for fast integration.

📄 [View config.yml](src/main/resources/config.yml)

---

## 🖼️ Media

**MOTD (Maintenance: OFF)**  
![Screenshot_1](https://github.com/user-attachments/assets/d03f0c0c-e9b5-451f-bbf0-a4dacb72c01f)

**MOTD (Maintenance: ON)**  
![Screenshot_2](https://github.com/user-attachments/assets/cf6324ad-3c38-407b-b998-bcb794dbde56)

**Maintenance Hover Message**  
![Screenshot_3](https://github.com/user-attachments/assets/ad65ac74-e888-4808-a74d-b31473e42f1c)

**Help Command**  
![Screenshot_4](https://github.com/user-attachments/assets/83d726dc-3c30-41d3-b111-680930065a85)

---

## 🤝 Contributing

We welcome suggestions, bug reports, and pull requests!

1. Fork the repository  
2. Create a new branch:  
   ```bash
   git checkout -b fix/your-fix
   ```
3. Commit your changes:  
   ```bash
   git commit -am "Describe your fix or improvement"
   ```
4. Push the branch and create a Pull Request.

---

## 🙏 Credits

- 👨‍💻 **Original Author**: [LiteClubDevelopment](https://github.com/LiteClubDevelopment), [HCFAlerts](https://github.com/HCFAlerts)  

Special thanks to the Minecraft plugin community for their ongoing feedback and support.

📝 **License Notice**:  
You are free to use and modify this code for your own projects.  
However, **you may not resell it or claim it as your own**.  
Please respect the original authors and use this project responsibly.

---

## 📄 License

This project is licensed under the [GPL License](LICENSE).
