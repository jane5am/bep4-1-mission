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

## 2. 구조 설명

### 2.1 모듈 구성

본 프로젝트는 하나의 애플리케이션 안에서 기능별로 모듈을 분리하는 **모듈러 모놀리스(Modular Monolith)** 구조로 구성했습니다.

주요 모듈은 다음과 같습니다.

* **Member 모듈**

  * 회원 생성 및 수정 등 회원 관련 기능을 담당합니다.
  * 회원의 원본 데이터(Source of Truth)를 관리합니다.

* **Post 모듈**

  * 게시글 및 댓글 관련 기능을 담당합니다.
  * 회원 정보를 직접 참조하지 않고, Post 모듈 내부에 `PostMember`라는 회원 복제 데이터를 두어 사용합니다.

이처럼 모듈별로 자신의 도메인에 필요한 데이터를 관리하도록 구성하여, Post 모듈이 Member 모듈의 엔티티에 직접 의존하지 않도록 했습니다.

### 2.2 이벤트와 HTTP API를 구분한 이유

모듈 간 통신은 **같은 애플리케이션 내부에서 발생하는 통신**과 **외부 클라이언트와의 통신**을 구분했습니다.

#### 이벤트(Event)

Member 모듈에서 회원이 생성되거나 수정되었을 때 Post 모듈의 회원 복제 데이터를 동기화해야 합니다.

이 경우 두 모듈이 동일한 애플리케이션 내부에 존재하므로 HTTP API를 호출하지 않고 **Spring Application Event**를 사용했습니다.

```text
Member 모듈
    │
    ├─ 회원 생성
    │      ↓
    │  MemberJoinedEvent
    │      ↓
    └──────────────→ Post 모듈
                       ↓
                 PostMember 동기화
```

회원 변경이 발생하면 `MemberJoinedEvent` 또는 `MemberModifiedEvent`를 발행하고, Post 모듈의 `PostEventListener`가 이벤트를 받아 `PostMember`를 생성하거나 수정합니다.

이처럼 이벤트를 사용하면 Member 모듈이 Post 모듈의 구체적인 구현이나 API를 직접 호출하지 않아도 되므로 모듈 간 결합도를 낮출 수 있습니다.

또한 이벤트 리스너는 `AFTER_COMMIT` 단계에서 실행되도록 구성하여, 회원 변경 트랜잭션이 정상적으로 커밋된 이후 Post 모듈의 복제 데이터를 동기화하도록 했습니다.

#### HTTP API

HTTP API는 외부 클라이언트가 애플리케이션의 기능을 요청할 때 사용합니다.

예를 들어 회원 관련 API는 다음과 같이 HTTP 요청을 통해 접근할 수 있습니다.

```text
Client
  │
  │ HTTP Request
  ↓
Member Controller
  ↓
Member Facade
  ↓
Member 모듈
```

따라서 본 프로젝트에서는 **내부 모듈 간 데이터 동기화에는 이벤트**, **외부 클라이언트와의 통신에는 HTTP API**를 사용하도록 역할을 구분했습니다.

### 2.3 회원 복제 흐름

Post 모듈은 게시글 작성자를 표현하기 위해 Member 모듈의 `Member` 엔티티를 직접 참조하지 않습니다.

대신 Post 모듈 내부에 `PostMember`를 두고, Member 모듈의 회원 정보를 필요한 범위만 복제하여 사용합니다.

회원 생성 및 수정 흐름은 다음과 같습니다.

```text
[Member 모듈]

회원 생성/수정
     │
     ↓
MemberJoinedEvent
또는
MemberModifiedEvent
     │
     ↓
[Post 모듈]
PostEventListener
     │
     ↓
PostFacade.syncMember()
     │
     ↓
PostMember 생성/수정
```

Post 엔티티에서는 다음과 같이 `PostMember`를 작성자로 사용합니다.

```java
@ManyToOne(fetch = LAZY)
private PostMember author;
```

즉, 게시글을 조회할 때 Post 모듈이 Member 모듈의 엔티티를 직접 참조하지 않고 **자신의 모듈에서 관리하는 회원 복제 데이터**를 사용합니다.

이 구조를 통해 각 모듈이 자신의 도메인에 필요한 데이터를 독립적으로 관리할 수 있으며, 모듈 간 직접적인 엔티티 의존성을 줄일 수 있습니다.

