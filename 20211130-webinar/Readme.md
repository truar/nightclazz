# Scenario

In this webinar, we are building and deploying an application on Cloud Run. It is a simply Spring Boot Application responding 'Hello World' when requesting /


* Slides on Serverless introduction
* Manipulation of Google Cloud console
* Slides on Cloud Run
    * Creation of the project webinar-20211130
    * Browsing Cloud Run
    * Start the creation of Firestore in Datastore mode
* Back to the application (the backend)
    * Live code for the missing part
    * Deploy the application on Cloud Run (using buildpacks)
    * Post new messages
    * Go check content in the Datastore service
* Other topic to discuss
    * Application authentication (Identity platform, Firebase authentication)
    * Continuous integration using Cloud Build
    * Infra as code with Cloudrun deployment file
    * Developer tools on Google Cloud (Source repositories, Debugger, Trace, Logs...)
    * Buildpacks


# Creation of the project on Google Cloud

* Create project in Google Cloud `webinar-20211130`
* Enable cloudrun APIs (using the console)
```shell script
gcloud config set core/project webinar-20211130
gcloud services enable run.googleapis.com
```


# Show the important parts of the application
* Go to 20211130-webinar/mygreatapp-api
* Check the controller to see what we are exposing
* Configure in the application.yaml the namespace
```yaml
server:
    port: ${PORT:8080}
    servlet:
        context-path: /api
```

* If you want to try to post a message once the server is deployed
```
curl http://localhost:8080/api/
```

# Deploy the application on Cloud Run (using buildpacks) (5' in total)

* Deploy on CloudRun using buildpacks (and enable APIs when prompted)
```
cd mygreatapp-api
gcloud run deploy mygreatapp-api --source=. \
    --cpu=2 \
    --memory=2Gi \
    --max-instances=3 \
    --platform=managed \
    --region=europe-west1 \
    --allow-unauthenticated

curl {URL_FROM_CLOUD_RUN}/api/
```
* Go check the logs of Cloud Run to show them the output (start time and others)

Bonus:
* Deployment using a descriptor file
```shell script
gcloud beta run services replace cloudrun-config.yaml \
          --platform=managed --region=europe-west1
gcloud run services add-iam-policy-binding awesomechat-backend \
  --platform=managed --region=europe-west1 \
  --member="allUsers" --role="roles/run.invoker"
``` 
