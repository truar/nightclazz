# Scenario

In this nightclazz, we are building and deploying an application using only serverless on Google Cloud.
* To build the solution, we will use Vue3 for the frontend, and Java 11 for the backend
* To deploy the solution, **Firebase** will host our SPA, **CloudRun** the server, and **Datastore** will store the data.

* Slides on Serverless introduction
* Manipulation of Google Cloud console
    * Creation of the project 20211102-nightclazz
    * Browsing some services (Cloud Run, Firestore)
    * Start the creation of Firestore in Datastore mode
* Slides on Cloud Run
* Slides on Datastore
* Back to the application (the backend)
    * Live code for the missing part
    * Deploy the application on Cloud Run (using buildpacks)
    * Post new messages
    * Go check content in the Datastore service
* Slides on Firebase
    * Live code for the missing part
    * Go the application hosted on Firebase
    * Post a new message and check the application being updated
* Other topic to discuss
    * Application authentication (Identity platform, Firebase authentication)
    * Continuous integration using Cloud Build
    * Infra as code with Cloudrun deployment file
    * Developer tools on Google Cloud (Source repositories, Debugger, Trace, Logs...)
    * Buildpacks


# Creation of the project on Google Cloud

* Create project in Google Cloud `20211102-nightclazz`
* Enable cloudrun APIs (using the console)
```shell script
gcloud services enable run.googleapis.com
```
* Set Datastore in Datastore mode (`eur3` region)

# Live code for the missing part
* Go to 20211102-nightclazz/awesomechat-backend
* Add dependencies to add gcp datastore support
```xml
<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-gcp-dependencies</artifactId>
				<version>1.2.5.RELEASE</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-gcp-starter-data-datastore</artifactId>
</dependency>
```
* Update the backend to create an Entity for Datastore
```java
@Entity
public class Message {

    @Id
    private Long id;
    @Field(name = "messageBody")
    private String body;
    private LocalDateTime creationDate;

    public Message(String body, LocalDateTime creationDate) {
        this.body = body;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
```
* Update the repository to extend the DatastoreRepository (we are using the Instant repositories from the Spring Data project)
```java
public interface MessageRepository extends DatastoreRepository<Message, Long> {

}
```
* Check the controller to see what we are exposing
* Configure in the application.yaml the namespace
```yaml
server:
    port: ${PORT:8080}
    servlet:
        context-path: /api
spring.cloud.gcp.datastore.namespace: ${DATASTORE_NAMESPACE:local}
```

* If you want to try to post a message once the server is deployed
```
curl -d 'Hello world!' -H 'Content-Type: plain/text' -X POST http://localhost:8080/api/messages
```

# Deploy the application on Cloud Run (using buildpacks)

* Deploy on CloudRun using buildpacks (and enable APIs when prompted)
```
cd awesomechat-backend
gcloud run deploy awesomechat-backend --source=. \
    --cpu=1 \
    --memory=2Gi \
    --max-instances=3 \
    --platform=managed \
    --region=europe-west1 \
    --allow-unauthenticated \
    --set-env-vars=DATASTORE_NAMESPACE=cloud

curl -d 'Hello world!' -H 'Content-Type: plain/text' -X POST https://awesomechat-backend-4qxvmpfnbq-ew.a.run.app/api/messages
```

* Go to the 20211102-nightclazz/awesomechat-frontend
* Check the `awesomechat.vue` file
```js
 methods: {
    async fetchMessages() {
      const response = await fetch('/api/messages/')
      this.messages = await response.json()
    },
    async postMessage() {
      await fetch('/api/messages/', {
        method: 'POST',
        body: this.newMessageBody
      })
      await this.fetchMessages()
      this.newMessageBody = ''
    }
  },
  async mounted() {
    await this.fetchMessages()
  }
```
* See that we have a form to send our message
* Add the post and get messages

* `firebase init`
    * Select `Hosting`
    * Set the project ID: 20211102-nightclazz
    * dist as public folder
    * index.html a rewrite rule

* Show the generated file `firebase.json` et `.firebaserc`
* Add the rewrite rule for Cloud run. Be careful of the matching rules priorities. `firebase.json`
```json
{
    "source": "/api/**",
    "run": {
        "serviceId": "awesomechat-backend",
        "region": "europe-west1"
    }
}
```
* Deploying using Firebase cmd (check for parameters)
```shell script
firebase deploy --project=20211102-nightclazz --only hosting
```

