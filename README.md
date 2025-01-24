# GOMO SERVER

프로젝트 GOMO의 서버 시스템

<br>
<br>

## 프로젝트 의존성

```
dependencies {
    // spring web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // spring data jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // time-based uuid - for entity id
    implementation 'com.fasterxml.uuid:java-uuid-generator:5.1.0'

    // test
    testRuntimeOnly "org.junit.platform:junit-platform-launcher"
    testImplementation "org.springframework.boot:spring-boot-starter-test"
    
    // lombok - for use in test
    testCompileOnly 'org.projectlombok:lombok'
    testAnnotationProcessor 'org.projectlombok:lombok'
    
    // rest assured - for acceptance test
    testImplementation 'io.rest-assured:rest-assured'
    
    // test container - for database isolation in test environment
    testImplementation "org.testcontainers:testcontainers"
    testImplementation "org.testcontainers:mysql"
    
    // spring rest docs - for documentation
    testImplementation 'org.springframework.restdocs:spring-restdocs-restassured'
    
    // open api specification - for swagger ui
    testImplementation "com.epages:restdocs-api-spec-mockmvc:${restdocsApiSpecVersion}"
    testImplementation "com.epages:restdocs-api-spec-restassured:${restdocsApiSpecVersion}"
}
```

<br>
<br>

## 코드 스타일

#1.&nbsp; 클래스 필드 사이에 빈 라인은 없다.<br>
#1-1.&nbsp; 단, 어노테이션 윗라인은 빈라인으로 한다.<br>

#2.&nbsp; 모든 클래스는 생성자 메서드 대신 정적 팩터리 메서드로 객체를 생성한다.<br>
#2-1.&nbsp; 단, 테스트 코드에서 필요하다면 public을 허용하고 사용한다.<br>
#2-2.&nbsp; 도메인 모델 생성자의 접근 제어자는 private을 원칙으로 한다.<br>
#2-3.&nbsp; 엔티티는 CGLib 가 접근할 수 있도록 protected를 원칙으로 한다.<br>

#3.&nbsp; 정적 팩터리 메서드의 이름은 `of()`를 원칙으로 한다.<br>
#3-1.&nbsp; 단, 인자가 하나도 없고 내부적으로 기본값을 세팅한다면 `createDefault()`를 사용한다.<br>

#4.&nbsp; 도메인 모델 '생성자, 정적 팩터리 메서드'의 인자는 **한 라인에 하나**만 작성한다.<br>
#4-1.&nbsp; 메서드 인자는 모두 **하나의 라인에 모두** 작성한다.<br>
#4-2.&nbsp; 메서드 파라미터도 **하나의 라인에 모두** 작성한다.<br>
#4-3.&nbsp; 단, 메서드 파라미터가 많아 Hard wrap 160을 넘긴다면 **한 라인에 하나**만 작성한다.<br>

<br>
<br>

## 유비쿼터스 언어

### 회원

| 한글명      | 영문명            | 설명                       |
|----------|----------------|--------------------------|
| 사용자      | Member         | 서비스에 가입하고 사용하는 사람        |
| 이메일      | Email          | 사용자의 이메일                 |
| 비밀번호     | Password       | 사용자의 비밀번호                |
| 핸들       | Handle         | 사용자가 지정한 고유 식별자 |
| 이름       | Member name           | 사용자의 이름                  |
| 한 줄 다짐   | Motto          | 사용자가 지정한 좌우명 혹은 다짐       |
| 프로필 이미지  | Profile image  | 사용자의 프로필 이미지             |
| 퀘스트 설정 값 | Quest property | 사용자가 관리하는 퀘스트 설정 값       |
| 이메일 인증 코드 | Email auth code | 이메일 인증을 위해 사용자에게 발송하는 코드       |

<br>

### 관심사

| 한글명     | 영문명               | 설명                            |
|---------|-------------------|-------------------------------|
| 관심사     | Interest          | 사용자가 등록한 관심 분야                |
| 등록자     | Registrant        | 관심사를 등록한 사용자                  |
| 이름      | Interest name     | 관심사의 이름                       |
| 로고      | Interest logo     | 관심사의 로고 이미지                   |
| 숙련도     | Proficiency       | 관심사에 대한 역량                    |
| 레벨      | Level             | 숙련도의 등급                       |
| 점수      | Score             | 현재 보유중인 점수                    |
| 총 점수    | Total score       | 이제까지 획득한 모든 점수의 합계            |
| 점수 임계치  | Score threshold   | 다음 레벨을 위한 임계 점수               |
| 주요 관심사  | Major interest    | 등록한 관심사 중, 사용자가 특별하게 생각하는 관심사 |
| 관심사 그래프 | Interest network  | 여러 가지 관심사가 계층 구조를 이룬 자료 구조    |
| 관심사 연결선 | Interest relation | 관심사 그래프에서 두 관심사의 간선           |
| 상위 관심사  | Parent interest   | 관심사 그래프에서 상위 계층에 속하는 관심사      |
| 하위 관심사  | Child interest    | 관심사 그래프에서 하위 계층에 속하는 관심사      |

<br>

### 퀘스트

| 한글명       | 영문명                 | 설명                            |
|-----------|---------------------|-------------------------------|
| 퀘스트       | Quest               | 관심 분야의 역량을 향상하기 위한 과제         |
| 주제        | Subject             | 퀘스트가 다루는 관심사                  |
| 수행자       | Participant         | 퀘스트를 등록하고 수행하는 사용자            |
| 내용        | Content             | 퀘스트 내용                        |
| 퀘스트 타입    | Quest type          | 일간, 주간, 월간 등의 퀘스트 유형          |
| 배정 받은 퀘스트 | Assign quest        | 시스템이 배정 한, 혹은 사용자가 직접 생성한 퀘스트 |
| 퀘스트 증명    | Completion proof    | 배정 받은 퀘스트를 수행 완료했다는 인증     |
| 반복 퀘스트    | Repeat quest        | 반복적으로 생성되는 퀘스트                |
| 퀘스트 보상    | Quest reward        | 퀘스트 완료 보상                     |
| 점수 보상     | Score reward        | 퀘스트 완료로 인해 지급되는 숙련도 점수        |
| 포인트 보상    | Point reward        | 퀘스트 완료로 인해 지급되는 포인트           |
| 점수 보상 정책  | Score reward policy | 퀘스트 타입별 숙련도 점수 보상 정책          |
| 포인트 보상 정책 | Point reward policy | 퀘스트 타입별 포인트 보상 정책             |

<br>

### 스트릭

| 한글명       | 영문명                 | 설명                            |
|-----------|---------------------|-------------------------------|
| 스트릭       | Streak              | 기간 별 퀘스트를 완료한 개수 만큼 쌓이는 점수    |
| 성취자       | Achiever            | 퀘스트를 등록하고 수행하는 사용자            |
| 스트릭 타입    | Streak type         | 일간, 주간, 월간 등의 스트릭 유형          |

<br>

### 포인트

| 한글명    | 영문명         | 설명                   |
|--------|-------------|----------------------|
| 포인트    | Point       | 서비스 내의 재화            |
| 거래자    | Transactor     | 포인트를 획득하고 사용하는 사용자   |
| 스트릭 타입 | Streak type | 일간, 주간, 월간 등의 스트릭 유형 |

<br>

### 설문

| 한글명    | 영문명            | 설명              |
|--------|----------------|-----------------|
| 설문     | Survey         | 사용자 정보를 수집하는 설문 |
| 설문 응답자 | Respondent      | 설문을 작성한 사용자     |
| 설문 문항  | Survey question | 설문에 포함되는 질문     |
| 항목     | Survey item     | 문항에 포함되는 항목     |
| 답변     | Survey result   | 사용자가 수행한 설문 기록  |