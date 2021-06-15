import axios from "axios";


export const uploadImage = (image) => {
    const formData = new FormData();
    formData.append('image', image);
    axios.post("/api/image/upload", formData,{
        headers: {
            'Content-Type': 'multipart/form-data'
        }})
        .then(response => response.data)
        .catch(error => console.error(error.message))

}
