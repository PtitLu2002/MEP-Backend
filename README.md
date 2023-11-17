# Mise en production - Backend

## Table of contents
- [Mise en production - Backend](#mise-en-production---backend)
  - [Table of contents](#table-of-contents)
  - [How to run locally](#how-to-run-locally)
    - [Push on DockerHub](#push-on-dockerhub)
  - [How to run with Jenkins](#how-to-run-with-jenkins)
    - [Install Docker on Jenkins](#install-docker-on-jenkins)
    - [Docker credentials](#docker-credentials)
    - [Pipeline](#pipeline)
    - [Issues](#issues)
  - [Tests endpoints](#tests-endpoints)
  - [Description](#description)
    - [Auteurs](#auteurs)
      - [*covid-api* backend](#covid-api-backend)
      - [MEP](#mep)
  - [Resources](#resources)

## How to run locally
1. Clone the repository `git clone https://github.com/PtitLu2002/MEP-Backend.git`
2. Run `docker compose build` and `docker compose up`  
or run `docker compose up --build` 

### Push on DockerHub
1. Run `docker login` and enter your DockerHub credentials
2. Run `docker build -t mep-backend .`
3. Get the ID of the image with `docker images ls`
4. Run `docker tag IMAGE_ID USER_ID/mep-backend:latest`
5. Run `docker push USER_ID/mep-backend:latest`
The image of the docker is available on DockerHub at the following URL : https://hub.docker.com/r/ptitlu2002/mep-backend  

## How to run with Jenkins
### Install Docker on Jenkins
1. Go to `Manage Jenkins` > `Manage Plugins` > `Available`
   1. Install `Docker plugin`
   2. Install `Docker API PLugin`
   3. Install `Docker Pipeline`
2. Go to `Manage Jenkins` > `Tool` > `Installations Docker`
   1. `Add Docker`
   2. Name it
   3. Check `Install automatically`
   4. In `Add an installator` choose `Download from docker.com` and Docker version `latest`


### Docker credentials
1. Go to `Docker Hub` > `Account Settings` > `Security` > `New Access Token`
   1. Create a token
   2. Copy it
2. Go to `Manage Jenkins` > `Credentials` > `Jenkins` > `Stores scoped to Jenkins`
   1. Click on `(global)`
   2. Click on `Add credentials`
   3. Choose  `Username and password`
   4. Enter your Docker Hub username
   5. In `Password` paste the DockerHub token you have created
   6. In `ID` put `docker-token`


### Pipeline
``` groovy
node {
    // configure the docker home
    def DOCKER_HOME = tool 'DOCKER'
    env.PATH = "${DOCKER_HOME}/bin:${env.PATH}"
    try{
        stage('Preparation') {
            // remove the folder if already exists to avoid errors and then clone the repository
            sh '''rm -rf MEP-Backend
                git clone https://github.com/PtitLu2002/MEP-Backend.git
            '''
        }
    } catch (err) {
        echo "Git clone failed"
        echo err.toString()
        currentBuild.result = 'FAILURE'
    }
    try {
        stage('Build'){
            def mepImage = docker.build('ptitlu2002/covid-api')
        }
    } catch (err) {
        echo "Docker build failed"
        echo err.toString()
        currentBuild.result = 'FAILURE'
    }
    try {
        stage('Push'){
            docker.withRegistry("https://registry.hub.docker.com", "docker-token") {
                mepImage.push("ptitlu2002/${env.BUILD_NUMBER}")
                mepImage.push("ptitlu2002/latest")
            }
        }
    }  catch (err) {
        echo "Docker push failed"
        echo err.toString()
        currentBuild.result = 'FAILURE'
    }
}
```

### Issues
The pipeline works well until the push stage. I can't push on DockerHub with Jenkins. I have an issue when I try to login.   
The following error appears :  
```
Docker push failed
java.io.IOException: Cannot run program "docker": error=2, No such file or directory
```


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



## Description
Deployement of the backend backend du cours de Fullstack : covid-api.  

### Auteurs
#### *covid-api* backend
`Samuel DITTE-DESTRÉE`  
`Theo RUSINOWITCH`  
`Lucie GARNIER`  

#### MEP
`Lucie GARNIER` 

## Resources
- Git backend : `https://gitlab.univ-lorraine.fr/dittedes1u/polydoctor-backend.git`
- Image on DockerHub : `https://hub.docker.com/r/ptitlu2002/mep-backend`
- Link of the application : `http://localhost:8080`