#!/bin/bash

curl -X POST -H "Content-Type: application/json" -d '{
  "projectName": "sample-app",
  "fullyQualifiedName": "legacy.TennisGame",
  "refactorings": [
    {
        "type": "refactorings/core/extract-method",
        "snippet": "if (P1point==0)\n                score = \"Love\";\n            if (P1point==1)\n                score = \"Fifteen\";\n            if (P1point==2)\n                score = \"Thirty\";\n            score += \"-All\";",
        "methodName": "calculateScoreForEqualPoints"
    },
    {
        "type": "refactorings/core/extract-method",
        "snippet": "if (P1point > 0 && P2point==0)\n        {\n            if (P1point==1)\n                P1res = \"Fifteen\";\n            if (P1point==2)\n                P1res = \"Thirty\";\n            if (P1point==3)\n                P1res = \"Forty\";\n            \n            P2res = \"Love\";\n            score = P1res + \"-\" + P2res;\n        }",
        "methodName": "calculateScoreForPlayer1Lead"
    },
    {
        "type": "refactorings/core/extract-method",
        "snippet": "if (P2point > 0 && P1point==0)\n        {\n            if (P2point==1)\n                P2res = \"Fifteen\";\n            if (P2point==2)\n                P2res = \"Thirty\";\n            if (P2point==3)\n                P2res = \"Forty\";\n            \n            P1res = \"Love\";\n            score = P1res + \"-\" + P2res;\n        }",
        "methodName": "calculateScoreForPlayer2Lead"
    },
    {
        "type": "refactorings/core/rename-method",
        "oldName": "SetP1Score",
        "newName": "setPlayer1Score"
    },
    {
        "type": "refactorings/core/rename-method",
        "oldName": "SetP2Score",
        "newName": "setPlayer2Score"
    },
    {
        "type": "refactorings/core/rename-method",
        "oldName": "P1Score",
        "newName": "incrementPlayer1Score"
    },
    {
        "type": "refactorings/core/rename-method",
        "oldName": "P2Score",
        "newName": "incrementPlayer2Score"
    },
    {
        "type": "refactorings/core/rename-field",
        "oldName": "P1point",
        "newName": "player1Point"
    },
    {
        "type": "refactorings/core/rename-field",
        "oldName": "P2point",
        "newName": "player2Point"
    },
    {
        "type": "refactorings/core/rename-field",
        "oldName": "P1res",
        "newName": "player1Result"
    },
    {
        "type": "refactorings/core/rename-field",
        "oldName": "P2res",
        "newName": "player2Result"
    }
  ]
}' http://127.0.0.1/vul/a/refactorings/chained
