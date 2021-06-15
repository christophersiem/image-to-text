import {useState} from "react";
import {uploadImage} from "./service/image-upload-service";


function App() {
    const [image, setImage] = useState();

    const handleSubmit = event => {
        event.preventDefault();
uploadImage(image);
    }
    return (
        <div className="App">
            <p>Hello App</p>
            <form onSubmit={handleSubmit}>
                <input type="file" accept={"image/*"} onChange={event => setImage(event.target.files[0])}/>
                <button>Upload</button>
            </form>
        </div>
    );
}

export default App;
