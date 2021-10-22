# Scenario

In this nightclazz, we are building and deploying an application using only serverless on Google Cloud.
* To build the solution, we will use Vue3 for the frontend, and Java 17 for the backend
* To deploy the solution, **Firebase** will host our SPA, **CloudRun** the server, and **Datastore** for the data storage.

* Main steps
** Code the missing part of the application (Frontend & backend)
** Configure the Google Cloud project
** Deploy everything to Google Cloud using command line
** If time: Show monitoring tool
** Opening: Cloud Build & CD, security with Firebase and JWT tokens


* Clone project
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
spring.cloud.gcp.datastore.namespace: ${DATASTORE_NAMESPACE:test}
```

* If you want to try to post a message once the server is deployed
```
curl -d 'Hello world!' -H 'Content-Type: plain/text' -X POST http://localhost:8080/api/messages
```


* Go to the 20211102-nightclazz/awesomechat-frontend
* Check the App.vue file
* See that we have a form to send our message
* Add the post and get messages

* Deploying using the Cloudrun cmd (check for parameters)
```shell script
gcloud run deploy awesomechat-backend --source .
```

* `firebase init`
    * Select `Hosting`
    * Set the project ID: tzp-20201207
    * dist as public folder
    * index.html a rewrite rule

* Show the generated file `firebase.json` et `.firebaserc`
* Deploying using Firebase cmd (check for parameters)
```shell script
firebase deploy --project=$PROJECT_ID --only hosting
```

* Add the rewrite rule for Cloud run. Be careful of the matching rules priorities. `firebase.json`
```json
{
    "source": "/api/**",
    "run": {
        "serviceId": "application-backend",
        "region": "europe-west1"
    }
}
```


## Prerequisites

* Create project in Google Cloud `nightclazz-0211`
* Set Datastore in Datastore mode (eur3 region)
* Enable cloudrun APIs
```shell script
gcloud services enable run.googleapis.com
```

* Create a firebase project by selecting the same Google Cloud project previously created
```
vue create .
```
* Install vuetify
```
vue add vuetify
```
