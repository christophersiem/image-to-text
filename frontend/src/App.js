import { useState } from 'react'
import { uploadImage } from './service/image-upload-service'
import UploadImageForm from './components/UploadImageForm'
import styled from 'styled-components/macro'
import Header from './components/Header'
import ShowPhoto from './components/ShowPhoto'
import ResultPage from './components/ResultPage'

function App() {
  const [image, setImage] = useState()
  const [detectedText, setDetectedText] = useState()

  const handleUploadPhoto = event => {
    event.preventDefault()
    uploadImage(image).then(setDetectedText)
  }
  return (
    <Layout>
      <Header />
      <UploadImageForm onSubmit={handleUploadPhoto} setImage={setImage} />
      <ShowPhoto detectedText={detectedText} />
      <ResultPage data={detectedText} />
    </Layout>
  )
}

export default App

const Layout = styled.div`
  overflow-y: auto;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-rows: 0.5fr 1fr 1fr auto;
  background: linear-gradient(to right, #00c6ff, #0072ff);
`
