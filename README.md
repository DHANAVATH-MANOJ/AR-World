AR World

AR World is an innovative Augmented Reality (AR) application that enables users to transform real-world objects into interactive 3D models using just a photo. By leveraging cutting-edge AR technology, users can visualize and experience how objects would appear in their actual environment before making any physical changes.

The app integrates with the Meshi API to generate high-quality 3D models from uploaded images. As the API requires a globally accessible URL, images are first uploaded to Cloudinary, ensuring they are available online regardless of their original local storage. Once uploaded, the application retrieves a unique ID to monitor the progress of the 3D model creation.

After processing, the generated 3D model URL is passed to an ARCore Fragment, which converts it into a .glb file URI. ARCore then renders the 3D model in real-time within the user’s environment, delivering an immersive AR experience.

Key Features

Convert 2D images into fully interactive 3D models.

Cloud-based image hosting via Cloudinary for global accessibility.

Track 3D model generation progress in real-time.

Seamless AR visualization using ARCore.

Interactive and immersive real-world previews of objects.

Benefits

Provides a realistic preview of objects in any environment.

Simplifies prototyping and visualization for designers, architects, and hobbyists.

Combines cloud technology with AR to deliver smooth and efficient performance.

Tech Stack

Language: Kotlin / Java (Android)

AR Framework: ARCore

3D Model API: Meshi API

Cloud Storage: Cloudinary

3D File Format: .glb
