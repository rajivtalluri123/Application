#!/bin/bash

if [[ $# -eq 0 ]] ; then
    echo 'Please provide stage parameter: dev, int, cert or prod'
    exit 1
fi

STAGE=$1

ENV_FILE=$(source assume_role.sh $STAGE)
source $ENV_FILE
rm $ENV_FILE

for i in {1..100}
do
  sh test_kinesis_item_level.sh $STAGE noauth
done
