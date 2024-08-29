# Prisma-Vi
## Description

**Prisma-Vi** is an app designed to help individuals with color blindness identify colors in their surroundings. By using the device's camera, users can take a photo of anything and, by selecting a specific point in the image, an artificial intelligence system will identify and describe the color based on the Pantone color palette.

## Features

-   **Image Capture**: Take photos directly from the app.
-   **Point Selection**: Choose any point on the image to identify the corresponding color.
-   **Color Identification**: The integrated AI identifies the selected color and describes it according to the Pantone color palette.
-   **Colorblind-Friendly**: The app's interface and functionalities are designed to be accessible to individuals with color blindness.

## Technologies Used

-   **Programming Language**: Kotlin
-   **IDE**: Android Studio
-   **Artificial Intelligence**: 
-   **Color Palette**: Pantone API
-   **Version Control**: Git and GitHub

## How to Run the Projec
### Prerequisites

-   Android Studio installed
-   Android Emulator or physical device with developer mode enabled


### Steps to Run

1.  Clone the repository:
    `git clone https://github.com/yourusername/colorvision.git` 
    
2.  Open Android Studio and import the project:
    -   Select "Open an existing Android Studio project" and navigate to the cloned project directory.
3.  Sync dependencies:
    -   Android Studio should prompt you to sync Gradle dependencies. Click "Sync Now" if prompted.
4.  Run the app:
    -   Select an emulator or connect a physical device, and click "Run" to start the app.
    - 
# App Flow Diagram

## 1. Enter the App
- Logo animation
- Collect information about color blindness for statistics
- Display usage tutorial

## 2. Main Screen
- **Choices**:
  - **Upload**
    - Choose an image from the gallery
    - Open the photo
    - Select the color
    - Display overlay with color information
    - **Actions**:
      - Save color to Palette
      - Deselect color
  - **Camera**
    - Take a photo
    - Open the photo
    - Select the color
    - Display overlay with color information
    - **Actions**:
      - Save color to Palette
      - Deselect color
  - **Options**
    - Open options screen (ends the process)

## 3. Color Screen
- **Save Color to Palette**
  - Add color to Palette
  - Add color to History
- **Deselect Color**
  - Remove selection and return to photo view

## 4. Palette (on Main Screen)
- **Displays**: Up to 5 saved colors
- **Functions**:
  - Delete Palette
  - Delete Color from Palette
  - Rename Palette
  - Add Color to Palette

## 5. History (on Main Screen)
- **Displays**: Up to 7 recently saved colors
- **Additional Information**: Objects with the same color

---
