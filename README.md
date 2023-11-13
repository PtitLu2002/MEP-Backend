# Mise en production - Backend

## Description
Mise en production du backend du cours de Fullstack : covid-api.  

### Auteurs de *covid-api*
`Samuel DITTE-DESTRÉE`  
`Theo RUSINOWITCH`  
`Lucie GARNIER`  
URL GitLab of covid-api : https://gitlab.univ-lorraine.fr/dittedes1u/polydoctor-backend.git  

## How to run
### Locally
1. Clone the repository `git clone https://github.com/PtitLu2002/MEP-Backend.git`
2. Run `docker compose build` and `docker compose up`  
or run `docker compose up --build`  

### With Jenkins


## Tests endpoints
- Get centers : `http://localhost:8080/public/centers`  
  - Status : 200
  - Sould return 2 centers
```
[
    {
        "id": 1,
        "name": "Centre Nancy",
        "address": "1 rue principale",
        "city": "Nancy",
        "postalCode": "54000",
        "rdvs": []
    },
    {
        "id": 2,
        "name": "Centre des Congrès Prouvé",
        "address": "2 bis Place Thiers",
        "city": "Nancy",
        "postalCode": "54000",
        "rdvs": []
    }
]
```
- Get center by id : `http://localhost:8080/public/centers/1`
  - Status : 200
  - Should return the center with the ID 1
```
{
    "id": 1,
    "name": "Centre Nancy",
    "address": "1 rue principale",
    "city": "Nancy",
    "postalCode": "54000",
    "rdvs": []
}
```



