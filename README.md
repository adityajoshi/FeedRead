# FeedRead

FeedRead is a simple Android RSS Feed Reader app built with Kotlin and Material Design 3. It allows users to browse articles from popular RSS sources, view article details, and automatically adapts to the system's light or dark theme.

## Features
- Browse a list of popular RSS feed sources
- View articles from selected sources
- Read full article content in a dedicated screen
- Modern Material 3 UI with system theme (light/dark) support
- Fast and responsive with RecyclerView and coroutines

## Screenshots
*Add your screenshots here!*

## Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- Android device or emulator (API 34+ recommended)

### Setup
1. Clone this repository:
   ```sh
   git clone https://github.com/adityajoshi/FeedRead.git
   cd FeedRead
   ```
2. Open the project in Android Studio.
3. Let Gradle sync and download dependencies.
4. Build and run the app on your device or emulator.

## Project Structure
- `app/src/main/java/xyz/simpletools/feedread/` — Main app code (activities, fragments, adapters, utils)
- `app/src/main/res/layout/` — UI layouts
- `app/src/main/res/values/` — Themes, colors, and strings
- `app/src/main/res/navigation/` — Navigation graph

## Customization
- To add or change RSS sources, edit the `feeds` list in `FirstFragment.kt`.
- The app uses Material 3 color tokens for both light and dark themes. You can customize colors in `res/values/colors.xml` and `res/values-night/colors.xml`.

## License
MIT License. See [LICENSE](LICENSE) for details.

---

*Made with ❤️ using Kotlin and Android Jetpack.* 