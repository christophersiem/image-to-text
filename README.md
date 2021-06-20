# AWS Image to text

This repo uses [Amazon Rekognition](https://docs.aws.amazon.com/rekognition/latest/dg/text-detecting-text-procedure.html) to detect text in an image.

## How to run

You need to have an AWS account to run this app.
-   clone this repo
-   go to the frontend folder and run `npm install`
-   set the following environment variables:
    - `AWS_SECRET_KEY`,
    - `AWS_ACCESS_KEY`,
    - `AWS_BUCKET_NAME` (name of bucket where picture gets uploaded to)

## Preview

![Overview](frontend/public/preview2.png)

