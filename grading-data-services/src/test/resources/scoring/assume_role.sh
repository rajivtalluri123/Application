#!/bin/bash

if [[ $# -eq 0 ]] ; then
    echo 'Please provide stage parameter: dev, int, cert or prod'
    exit 1
fi

STAGE=$1

FILE=$(mktemp 'rolejson.XXXXXXXXXXXXXXX')
ENV_FILE=$(mktemp 'awsenv.XXXXXXXXXXXXXXX')

aws sts assume-role --role-arn arn:aws:iam::711638685743:role/io.hmheng.hmhone.$STAGE.score.kinesis.crossaccount --role-session-name devkenisis > $FILE

echo export AWS_SECRET_KEY=`python -c "import sys, json; print(json.loads(open(\"$FILE\", \"r\").read())[\"Credentials\"][\"SecretAccessKey\"]+\"\n\");"`> $ENV_FILE
echo export AWS_SECRET_ACCESS_KEY=`python -c "import sys, json; print(json.loads(open(\"$FILE\", \"r\").read())[\"Credentials\"][\"SecretAccessKey\"]+\"\n\");"`>> $ENV_FILE
echo export AWS_ACCESS_KEY_ID=`python -c "import sys, json; print(json.loads(open(\"$FILE\", \"r\").read())[\"Credentials\"][\"AccessKeyId\"]+\"\n\");"` >> $ENV_FILE
echo export AWS_SESSION_TOKEN=`python -c "import sys, json; print(json.loads(open(\"$FILE\", \"r\").read())[\"Credentials\"][\"SessionToken\"]+\"\n\");"` >> $ENV_FILE

rm $FILE

echo $ENV_FILE