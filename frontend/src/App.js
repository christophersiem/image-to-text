import {useState} from "react";
import axios from "axios";


function App() {
    const [image, setImage] = useState();

    const handleSubmit = event => {
        event.preventDefault();
        axios.post("/api/image/upload", image).catch(error => console.error(error.message))
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
