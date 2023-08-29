#!/usr/bin/env bash
# -- > Create S3 Bucket
#echo $(awslocal s3 mb s3://movie-images-s3-bucket)
## --> List S3 Buckets
#echo $(awslocal s3 ls)
aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api create-bucket --bucket movie-images-s3-bucket
#aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3 ls
aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api put-object --bucket movie-images-s3-bucket --key img/godfather.jpg --body src/main/resources/s3/godfather.jpg
#➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3 ls
#2023-08-18 08:43:11 movie-images-s3-bucket
#➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api list-objects --bucket movie-images-s3-bucket
#➜  MovieBooker git:(main) aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api get-object --bucket movie-images-s3-bucket --key /img/godfather.jpg godfather.jpg
aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api put-object --bucket movie-images-s3-bucket --key img/star-wars.jpg --body src/main/resources/s3/star-wars.jpg
#aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api get-object --bucket movie-images-s3-bucket --key /img/star-wars.jpg star-wars.jpg
echo $(aws --endpoint-url=http://localhost:4566 --region=us-east-1 s3api list-objects --bucket movie-images-s3-bucket)

# 1. Run docker-compose
# 2. Add bootstrap data
# 3. Add images to bucket
