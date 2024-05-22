#!/bin/bash

curl -v -X POST -H "Content-Type: application/json" -d '[
  {
    "type": "refactorings/core/extract-method",
    "methodName": "extractedPrintProduct",
    "offset": 123,
    "length": 61 
  },
  {
    "type": "refactorings/core/rename-type",
    "newName": "ProductX"  
  }
]' http://127.0.0.1/vulcan/r/sample-app/vulcan.Product
