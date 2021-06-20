import styled from 'styled-components/macro'
import { toRoundedDouble } from '../utils/detectedText-utils'

export default function ResultPage({ data }) {
  return (
    <ResultText>
      {data && (
        <>
          <SubHeadline>Detected Text </SubHeadline>
          <ResultValue>{data.text}</ResultValue>
          <SubHeadline>Confidence </SubHeadline>
          <ResultValue>{toRoundedDouble(data.confidence)}%</ResultValue>
        </>
      )}
    </ResultText>
  )
}

const SubHeadline = styled.h2`
  color: whitesmoke;
`

const ResultText = styled.div`
  display: grid;
  grid-template-rows: 1fr auto 1fr 1fr;
  justify-items: center;
  text-align: center;
`

const ResultValue = styled.p``
