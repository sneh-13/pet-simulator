# Virtual Pet Game

## 📌 Description
This project is a virtual pet simulation game built with Java and Swing. Players can adopt, name, and care for pets like cats, dogs, or bunnies. The game features multiple screens for different modes including menu navigation, pet care, walking, and parental controls for setting play limits.

---

## 🧰 Requirements

- Java Development Kit (JDK) 21 or later
- Git (optional, for cloning the repository)
- No external libraries are required (standard Java + Swing only)

---

## 🛠 How to Build

### 1. Download or Clone the Repository

If using Git:
```bash
git clone <repository-url>
cd group41
```

Otherwise, download the ZIP from GitLab and extract it.

### 2. Compile the Source Code

Open PowerShell or CMD inside the project folder (`group41`) and run:

```bash
javac -d out Main/*.java GUI/*.java Pets/*.java AnimalPack/*.java
```

This will compile the source code into the `out/` directory.

---

## ▶️ How to Run

### Option 1: Run the Game from the Terminal

1. Open Terminal (macOS/Linux) or Command Prompt (Windows).
2. Navigate to the folder where `PetSimulator.jar` is located.
3. Run the game using:

```bash
java -jar PetSimulator.jar
```

### Option 2: Double-click Executables

1. Locate the files named `run.sh` (Mac/Linux) or `run.bat` (Windows) in the root project folder.
2. Double-click the appropriate file to launch the game.

---

## ⚙️ Building and Creating the Executable

To build a new `.jar` executable for the game:

```bash
make jar
```

This will generate `PetSimulator.jar` in the root of your project.

---

## 🕹️ User Guide

- **Menu Screen**: Choose options like "Start Game" or "Instructions".
- **New Game**: Select a pet, give it a name, and begin your journey.
- **Gameplay**:
  - Feed, walk, and play with your pet to keep it happy.
  - Gain points by caring for your pet.
- **Vet Screen**: Take your pet for checkups.
- **Parental Control Screen**: Access restrictions and time tracking features.

---

## 🔐 Default Login Credentials

### Parent Login
- **Username**: `parent`
- **Password**: `enter`

These credentials are required to access the Parental Control screen.

---

## 🔧 Parental Controls

To access the parental screen:
1. From the menu, select "Parent Login".
2. Enter the credentials above.
3. You can set allowed playtime and review player stats.

Parental controls are built into the main program and don't require separate installation.

---

## 📝 Notes for the TA

- All `.java` source files are located in `/Main`, `/GUI`, `/Pets`, and `/AnimalPack` folders.
- The main entry point is `Main/Main.java`.
- All features including login, pet actions, inventory, and parental controls are functional and tested.
- The GUI is implemented with `JPanel` and `CardLayout` for easy screen switching.
- A `PetSimulator.jar` executable is included and can be run with `java -jar PetSimulator.jar`.
- Use `make jar` to rebuild the `.jar` file if needed.

---

## ✅ Project Structure Overview

```
group41/
├── Main/              # Core logic classes (GamePanel, Main, Player, etc.)
├── GUI/               # All GUI screens (MenuState, ParentLogin, etc.)
├── Pets/              # Pet class parents and handlers
├── AnimalPack/        # Pet species implementations (Dog, Cat, Bunny, etc.)
├── run.bat            # Windows launcher
├── run.sh             # Mac/Linux launcher
├── PetSimulator.jar   # Executable game file
├── README.md          # You are here!
```