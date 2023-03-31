# Guess me! (friend & family)

<p align="center">
 <br>
 <div width="400" style="background: none;" align="center">
  <img src='https://user-images.githubusercontent.com/65845941/229036268-f624d0cb-aa3a-425e-986f-04c79246fed2.png' alt="Guess me Logo" width="400" />
 </div>
</p>
<p align="center">
 <a href="https://www.notion.so/osam-handover/f9e9132e0b0c4832a3d77e27411241e0?v=fe68ef0118be4d11aa0b1d0f6e8c20ea">Documentation</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="https://bit.ly/osam-handover-video">Demo Video</a>
</p>
<p align="center">
 <img alt="GitHub language count" src="https://img.shields.io/github/contributors/GUESS-ME-GDSC/Server?style=for-the-badge&logo">
 <img alt="GitHub language count" src="https://img.shields.io/github/issues-closed/GUESS-ME-GDSC/Server?style=for-the-badge&logo">
 <img alt="GitHub language count" src="https://img.shields.io/github/stars/GUESS-ME-GDSC/Server?style=for-the-badge&logo">
</p>

## 👋 Project Introduction

**Guess me! (friend & family)** 는 고령층의 기억력 감퇴 예방과 가족 기억을 위한 인물 정보 저장 / 글쓰기 퀴즈 앱입니다.

> 모든 연령층을 위한 건강한 삶 보장과 복지를 증진하는 것은 매우 중요한 과제입니다.<br> > **Guess me! (friend & family)** 는 노인의 치매를 예방하고 약해져 가는 기억력 때문에 소중한 사람을 잊지 않도록 돕고자 합니다.<br>
>
> 더 나아가, 서비스를 이용하면서 가까운 사람들의 정보를 입력할 때 기본 정보는 물론이고 뭘 좋아하는지, 옷 취향은 어떤지 등을 추가로 알아가기는 시간을 가지고, 서로 사진찍고 음성을 녹음하는 과정도 거치며 소통하는 소중한 시간을 더 만들어주는 매개체로써 동작하고자 합니다.

## 📖 Table of Contents

<ol>
 <li><a href="#features">Features</a></li>
 <li><a href="#stacks">Stacks</a></li>
 <li><a href="#expectation">Expectation</a></li>
 <li><a href="#competitiveness">Competitiveness</a></li>
 <li><a href="#gettingstarted">Getting Started</a></li>
 <li><a href="#structure">Folder Structure</a></li>
 <li><a href="#teaminfo">TEAM INFO</a></li>
</ol>

<h2 id="features"> ✨ Key Features </h2>

**2가지 핵심 기능**이 존재합니다.

- **인물 등록**
- **퀴즈 풀기**

<h2 id="expectation"> ✨ Expectations </h2>
 
 ### 👍 기억력 강화
  - 퀴즈를 풀면서 뇌 활동을 촉진할 수 있습니다.
  - 글쓰기를 통해 답을 제출함으로써 치매 예방에 도움이 됩니다.
 
 ### 🤝 가족 간 소통 활성화
  - 서비스를 사용하게 되면 반드시 가족과 소통하는 시간을 가지게 됩니다.
  - 가족들의 정보를 입력하면서 서로를 더 알아갈 수 있습니다.
 
<h2 id="stacks"> 🛠️  Tech Stacks </h2>

<img width="500" alt="Guessme_project_architecture" src="https://user-images.githubusercontent.com/65845941/229057300-5074a74c-d4de-4222-a8ee-59ad32e63125.png">

### 🚉 Platform

- [Docker](https://www.docker.com/)
- [Google Cloud Platform](https://cloud.google.com/)
- [Google Cloud SQL](https://cloud.google.com/sql)
- [Google Cloud Storage](https://cloud.google.com/storage)

### 🦾 Server

- [JAVA 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Spring Boot v2.7.9](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)
- [JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT](https://jwt.io/)

### 😎 Android

- [Android](https://developer.android.com/)
- [Kotlin](https://kotlinlang.org/)

<h2 id="gettingstarted"> 🏃 프로젝트 사용법 (Getting Started) </h2>

You don't need to install anything to run Guessme.
It's all deployed on the cloud.

The only thing you need is a [Android Client](https://github.com/GUESS-ME-GDSC/Android).

<h2 id="structure"> 🕹️ 소스코드 구조 (Source Code Structure) </h2>

### Server

```
Server/
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ gdsc
   │  │     └─ mju
   │  │        └─ guessme
   │  │           ├─ GuessmeApplication.java
   │  │           ├─ domain
   │  │           │  ├─ auth
   │  │           │  │  ├─ AuthController.java
   │  │           │  │  ├─ AuthService.java
   │  │           │  │  ├─ UserDetailsServiceImpl.java
   │  │           │  │  ├─ dto
   │  │           │  │  │  └─ AuthReqDto.java
   │  │           │  │  └─ jwt
   │  │           │  │     ├─ JwtTokenFilter.java
   │  │           │  │     └─ JwtTokenProvider.java
   │  │           │  ├─ info
   │  │           │  │  ├─ InfoController.java
   │  │           │  │  ├─ InfoService.java
   │  │           │  │  ├─ dto
   │  │           │  │  │  ├─ DeleteInfoByIdListReqDto.java
   │  │           │  │  │  └─ InfoObj.java
   │  │           │  │  ├─ entity
   │  │           │  │  │  └─ Info.java
   │  │           │  │  └─ repository
   │  │           │  │     └─ InfoRepository.java
   │  │           │  ├─ person
   │  │           │  │  ├─ PersonController.java
   │  │           │  │  ├─ PersonService.java
   │  │           │  │  ├─ dto
   │  │           │  │  │  ├─ AddInfoReqDto.java
   │  │           │  │  │  ├─ CreatePersonReqDto.java
   │  │           │  │  │  ├─ PersonDetailResDto.java
   │  │           │  │  │  ├─ PersonResDto.java
   │  │           │  │  │  ├─ UpdatePersonDto.java
   │  │           │  │  │  └─ UpdatePersonReqDto.java
   │  │           │  │  ├─ entity
   │  │           │  │  │  └─ Person.java
   │  │           │  │  └─ repository
   │  │           │  │     └─ PersonRepository.java
   │  │           │  ├─ quiz
   │  │           │  │  ├─ QuizController.java
   │  │           │  │  ├─ QuizService.java
   │  │           │  │  └─ dto
   │  │           │  │     ├─ NewScoreDto.java
   │  │           │  │     ├─ QuizDto.java
   │  │           │  │     ├─ QuizResDto.java
   │  │           │  │     └─ ScoreReqDto.java
   │  │           │  └─ user
   │  │           │     ├─ UserController.java
   │  │           │     ├─ UserService.java
   │  │           │     ├─ dto
   │  │           │     │  ├─ BlahBlahReqDto.java
   │  │           │     │  └─ BlahResDto.java
   │  │           │     ├─ entity
   │  │           │     │  └─ User.java
   │  │           │     └─ repository
   │  │           │        └─ UserRepository.java
   │  │           └─ global
   │  │              ├─ config
   │  │              │  ├─ JwtSecurityConfig.java
   │  │              │  └─ SecurityConfig.java
   │  │              ├─ infra
   │  │              │  └─ gcs
   │  │              │     ├─ GCSConfig.java
   │  │              │     └─ GcsService.java
   │  │              └─ response
   │  │                 ├─ BaseException.java
   │  │                 ├─ BaseResponse.java
   │  │                 ├─ ExceptionController.java
   │  │                 └─ UserNotFoundException.java
   │  └─ resources
   │     ├─ application.properties
   │     ├─ static
   │     └─ templates
   └─ test
      └─ java
         └─ gdsc
            └─ mju
               └─ guessme
                  ├─ GuessmeApplicationTests.java
                  └─ domain
                     └─ auth
                        └─ AuthServiceTest.java

```

<h2 id="teaminfo"> 👨‍👦‍👦 Team Info </h2>

<table width="500">
    <thead>
    </thead>
    <tbody>
    <tr>
        <th>이름</th>
        <td width="100" align="center">Kim HaeChan</td>
        <td width="100" align="center">Kim JeongHo</td>
        <td width="100" align="center">Mun yuri</td>
    </tr>
    <tr>
        <th>역할</th>
        <td width="150" align="center">
            Server
        </td>
        <td width="150" align="center">
            Server
        </td>
        <td width="150" align="center">
            Android
        </td>
    </tr>
    <tr>
        <th>GitHub</th>
        <td width="100" align="center">
            <a href="https://github.com/bluesun147">
                <img src="http://img.shields.io/badge/bluesun147-green?style=social&logo=github"/>
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/hou27">
                <img src="http://img.shields.io/badge/hou27-green?style=social&logo=github"/>
            </a>
        </td>
        <td width="100" align="center">
            <a href="https://github.com/915dbfl">
                <img src="http://img.shields.io/badge/915dbfl-green?style=social&logo=github"/>
            </a>
        </td>
    </tr>
    <tr>
        <th>이메일</th>
        <td width="175" align="center">
            <a href="mailto:er196725@googlemail.com">
                <img src="https://img.shields.io/badge/er196725@googlemail.com-green?logo=gmail&style=social">
            </a>
        </td>
        <td width="175" align="center">
            <a href="mailto:ataj125@gmail.com">
                <img src="https://img.shields.io/badge/ataj125@gmail.com-green?logo=gmail&style=social">
            </a>
        </td>
        <td width="175" align="center">
            <a href="mailto:myr1068@gmail.com">
                <img src="https://img.shields.io/badge/myr1068@gmail.com-green?logo=gmail&style=social">
            </a>
        </td>
    </tr>
    </tbody>
</table>
