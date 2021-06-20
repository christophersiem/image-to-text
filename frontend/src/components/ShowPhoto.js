import styled from 'styled-components/macro'

export default function ShowPhoto({ detectedText }) {
  return (
    <Wrapper>
      {detectedText && <img src={detectedText.url} alt="" />}
    </Wrapper>
  )
}

const Wrapper = styled.div`
  display: grid;
  justify-content: center;
  img {
    max-height: 200px;
  }
`
