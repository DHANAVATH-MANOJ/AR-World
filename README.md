# AR World 🌎✨

**AR World** is an innovative **Augmented Reality (AR)** application that transforms real-world objects into **interactive 3D models** using just a photo. Users can visualize how objects would appear in their actual environment before making any physical changes.

---

## How It Works ⚙️

1. **Upload Image**  
   Users upload a photo of an object.  

2. **Cloud Hosting 🌐**  
   Images are uploaded to **Cloudinary** to generate a **globally accessible URL**.  

3. **3D Model Generation 🖥️**  
   The **Meshi API** converts the image into a high-quality 3D model.  
   Each upload receives a **unique ID** to track the model creation progress.  

4. **AR Visualization 📱**  
   The 3D model URL is passed to an **ARCore Fragment**, converted into a `.glb` file URI, and rendered in **real-time AR** within the user’s environment.  


---

## Key Features ✨

- 🖼️ Convert 2D images into **fully interactive 3D models**.  
- ☁️ **Cloud-based image hosting** for global accessibility.  
- ⏱️ Track **3D model generation progress** in real-time.  
- 📱 **Seamless AR visualization** using ARCore.  
- 🌍 **Immersive real-world previews** of objects.

---

## Benefits 🌟

- Provides a **realistic preview** of objects in any environment.  
- Simplifies **prototyping and visualization** for designers, architects, and hobbyists.  
- Combines **cloud technology with AR** for smooth and efficient performance.  

---

## Tech Stack 🛠️

- **Language:** Kotlin / Java (Android)  
- **AR Framework:** ARCore  
- **3D Model API:** Meshi API  
- **Cloud Storage:** Cloudinary  
- **3D File Format:** `.glb`  


---


