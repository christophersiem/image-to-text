import styled from 'styled-components/macro'

export default function UploadImageForm({ onSubmit, setImage }) {
  return (
    <Form onSubmit={onSubmit}>
      <input
        type={'file'}
        title={' '}
        accept={'image/*'}
        onChange={event => setImage(event.target.files[0])}
      />
      <button>Upload</button>
    </Form>
  )
}

const Form = styled.form`
  display: grid;
  grid-template-rows: 1fr 1fr;
  justify-content: center;
  justify-items: center;

  input {
    width: 100%;
  }

  button {
    height: 50%;
    width: 50%;
  }
`
