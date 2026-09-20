## 실행 방법

### 1. 개발 환경

* **JDK:** 25
* **Spring Boot:** 4.0.1
* **Database:** H2
* **Build Tool:** Gradle

### 2. DB 설정

개발 환경에서는 별도의 DB 서버 없이 **H2 Database**를 사용합니다.

```yaml
spring:
  datasource:
    url: jdbc:h2:./db_dev;MODE=MySQL
    username: sa
    password:
    driver-class-name: org.h2.Driver
```


### 3. 실행

저장소를 clone한 후 프로젝트를 실행합니다.

```bash
git clone https://github.com/jane5am/bep4-1-mission.git
cd bep4-1-mission
```

macOS / Linux:

```bash
./gradlew bootRun
```

Windows:

```bash
gradlew.bat bootRun
```

서버가 정상적으로 실행되면 다음 주소에서 접근할 수 있습니다.

```text
http://localhost:8080
```
