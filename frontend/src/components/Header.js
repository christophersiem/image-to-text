import styled from 'styled-components/macro'

export default function Header() {
  return (
    <Wrapper>
      <Title>AWS Detect Text</Title>
    </Wrapper>
  )
}

const Wrapper = styled.div`
  text-align: center;
`

const Title = styled.h1`
  color: whitesmoke;
  font-size: 2rem;
`
