# wanted-pre-onboarding-backend
## 요구사항 분석 (가산점 포함 모두 구현 완료.)
1. 채용공고 (Recruitment)를 등록
2. 채용공고 (Recruitment)를 수정
3. 채용공고 (Recruitment)를 삭제
4. 채용공고 (Recruitment)를 가져오기 (검색까지)
5. 채용 상세 페이지 가져오기
6. 사용자는 채용공고에 지원

## 기술 스택
- spring boot 2.7.16
- java 17
- mysql 8
- spring data jpa
- lombok
- junit5

## 패키지 구조
```text
wanted-pre-onboarding-backend 
└── wantedbackend
    ├── WantedBackendApplication.java
    ├── apply
    │   ├── controller
    │   │   └── ApplyController.java
    │   ├── dto
    │   │   ├── ApplyRequestDto.java
    │   │   └── ApplyResponseDto.java
    │   ├── entity
    │   │   └── Apply.java
    │   ├── repository
    │   │   └── ApplyRepository.java
    │   └── service
    │       └── ApplyService.java
    ├── company
    │   ├── entity
    │   │   └── Company.java
    │   └── repository
    │       └── CompanyRepository.java
    ├── config
    │   └── JasyptConfig.java
    ├── recruitment
    │   ├── controller
    │   │   └── RecruitmentController.java
    │   ├── dto
    │   │   ├── RecruitmentCreateUpdateResponseDto.java
    │   │   ├── RecruitmentDetailResponseDto.java
    │   │   ├── RecruitmentRequestDto.java
    │   │   ├── RecruitmentResponseDto.java
    │   │   └── RecruitmentUpdateDto.java
    │   ├── entity
    │   │   └── Recruitment.java
    │   ├── repository
    │   │   └── RecruitmentRepository.java
    │   └── service
    │       └── RecruitmentService.java
    └── user
        ├── entity
        │   └── User.java
        └── repository
            └── UserRepository.java
```
- 각 엔티티별로 또 그 하위에 각 역할에 맞는 패키지 부여.
  - 예: recruitment 하위에 controller, dto, entity, repository, service 를 두고, 각 패키지 내에 필요한 클래스를 둠.

## 비밀번호 암호화
- config 의 경우 mysql 비밀번호를 숨기기 위한 설정인 JasyptConfig 를 만듦.
  - 실행시키기 위해 먼저 resource/password.txt 에 자신이 원하는 패스워드를 적고, 아래 JasyptConfig 클래스에 있는 source 문자열을 적고 yml 
  파일에 자신이 암호화할 대상을 ENC 로 감싸 적어준다. 그 후 스프링을 실행시켜 로그를 확인. (나는 db username 과 password를 적용시켜줌.)
  - 그리고 mysql 에 wanted 라는 db를 미리 만들어줌.
```java
@Bean
public StringEncryptor jasyptStringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    encryptor.setPoolSize(poolSize);
    encryptor.setAlgorithm(algorithm);
    encryptor.setPassword(getJasyptEncryptorPassword());
    encryptor.setStringOutputType(stringOutputType);
    encryptor.setKeyObtentionIterations(keyObtentionIterations);
    String source = "암호화할 대상";
    log.info("plane :: {}, encrypt :: {}", source, encryptor.encrypt(source));
    return encryptor;
}
```
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost/wanted
    username: ENC(4i9WjdaCbIQO9COSr006xQ==)
    password: ENC(hstdvspZSS42bCWZ0hBskGNliLVlyo49)
```

## 구현 과정
### db
```text
mysql> desc recruitment;
+--------------------------+--------------+------+-----+---------+----------------+
| Field                    | Type         | Null | Key | Default | Extra          |
+--------------------------+--------------+------+-----+---------+----------------+
| recruitment_id           | bigint       | NO   | PRI | NULL    | auto_increment |
| recruitment_compensation | int          | NO   |     | NULL    |                |
| recruitment_content      | varchar(255) | NO   |     | NULL    |                |
| recruitment_position     | varchar(255) | NO   |     | NULL    |                |
| recruitment_technology   | varchar(255) | NO   |     | NULL    |                |
| company_id               | bigint       | NO   | MUL | NULL    |                |
+--------------------------+--------------+------+-----+---------+----------------+
6 rows in set (0.00 sec)
```
```text
mysql> desc company;
+------------------+--------------+------+-----+---------+----------------+
| Field            | Type         | Null | Key | Default | Extra          |
+------------------+--------------+------+-----+---------+----------------+
| company_id       | bigint       | NO   | PRI | NULL    | auto_increment |
| company_country  | varchar(255) | NO   |     | NULL    |                |
| company_location | varchar(255) | NO   |     | NULL    |                |
| company_name     | varchar(255) | NO   |     | NULL    |                |
+------------------+--------------+------+-----+---------+----------------+
4 rows in set (0.00 sec)
```
```text
mysql> desc users;
+---------+--------+------+-----+---------+-------+
| Field   | Type   | Null | Key | Default | Extra |
+---------+--------+------+-----+---------+-------+
| user_id | bigint | NO   | PRI | NULL    |       |
+---------+--------+------+-----+---------+-------+
1 row in set (0.00 sec)
```
```text
mysql> desc apply;
+----------------+--------+------+-----+---------+----------------+
| Field          | Type   | Null | Key | Default | Extra          |
+----------------+--------+------+-----+---------+----------------+
| id             | bigint | NO   | PRI | NULL    | auto_increment |
| recruitment_id | bigint | NO   | MUL | NULL    |                |
| user_id        | bigint | NO   | MUL | NULL    |                |
+----------------+--------+------+-----+---------+----------------+
3 rows in set (0.00 sec)
```
- 다음과 같이 db 설계.
- 각 엔티티 클래스를 Recruitment, Company, User, Apply 만들고, repository 는 JPA 로 생성.

## api
- 각 api 별로 필요한 request, response dto를 따로 정의해 두었음.
- 각 도메인별 dto 패키지 내에 존재.

1. 채용공고 (Recruitment)를 등록 ("/recruitment")

![](screenshot/_1CreateRecruitment.png)

2. 채용공고 (Recruitment)를 수정 ("/recruitment/{id}")

![](screenshot/_2UpdateRecruitment.png)

3. 채용공고 (Recruitment)를 삭제 ("/recruitment/{id}")

![](screenshot/_3DeleteRecruitment.png)

4. 채용공고 (Recruitment)를 가져오기 (검색까지) ("/recruitment" or "/recruitment?search={키워드}")
- /recruitment

![](screenshot/_4_1GetRecruitment.png)

- /recruitment?search=판교 검색

![](screenshot/_4_2SearchRecruitment.png)


5. 채용 상세 페이지 가져오기 ("/recruitment/{id}")

![](screenshot/_5DetailRecruitment.png)

6. 사용자는 채용공고에 지원 ("/apply")

![](screenshot/_6Apply.png)

