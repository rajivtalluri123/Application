#!/usr/bin/python

import sys
import json
import base64
import tempfile

if len(sys.argv) < 2:
  print "Error: first argument should be path to a Aws Kinesis get-records response file!"
  raise Exception("Error!")


with open(sys.argv[1]) as data_file:
  kinesisresp = json.load(data_file)

records = kinesisresp.get('Records',{})
i = 1

for record in records: 
  tempFile = tempfile.NamedTemporaryFile(prefix = "aws_data_json", delete = False)
  print tempFile.name
  data = base64.b64decode(record["Data"])
  data = json.loads(data)
  tempFile.write(json.dumps(data, indent=2))
  tempFile.close()
  



 
