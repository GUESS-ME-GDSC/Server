# Guess me! (friend & family)

<p align="center">
 <br>
 <div width="400" style="background: none;" align="center">
  <img src='https://user-images.githubusercontent.com/65845941/229036268-f624d0cb-aa3a-425e-986f-04c79246fed2.png' alt="Guess me Logo" width="400" />
 </div>
</p>
<p align="center"><a href="#">Demo Video</a>
</p>
<p align="center">
 <img alt="GitHub language count" src="https://img.shields.io/github/contributors/GUESS-ME-GDSC/Server?style=for-the-badge&logo">
 <img alt="GitHub language count" src="https://img.shields.io/github/issues-closed/GUESS-ME-GDSC/Server?style=for-the-badge&logo">
 <img alt="GitHub language count" src="https://img.shields.io/github/stars/GUESS-ME-GDSC/Server?style=for-the-badge&logo">
</p>

## 👋 Project Overview

**Guess me! (friend & family)** is a person information storage/writing quiz app for preventing memory loss and family memory of the elderly.

> How can we prevent an increase in the incidence of dementia in the elderly population and help them maintain memories of their loved ones? Dementia is a progressive disease that affects millions of people worldwide and can be fatal not only to individuals but also to family and friends. As the world's population ages, the proportion of people with dementia is increasing, and this trend poses a significant challenge to individuals, families and communities around the world.

> Our project aims to develop ways to reduce the risk of dementia and promote healthy aging in the elderly population. The memory loss of a loved one due to dementia is a major problem that we are trying to solve. Handwriting and quiz-type repetitive learning have been shown to be effective in promoting brain activity, strengthening memory, and improving cognitive ability in the elderly. By encouraging these activities, we hope to reduce the risk of dementia and help individuals maintain memories of their loved ones.

> Ultimately, **Guess me! (friend & family)** aims to contribute to global efforts to improve the lives of individuals, families and communities around the world by preventing dementia and promoting healthy aging in the elderly. We hope to reduce the burden of dementia on individuals, families, and society as a whole by focusing on early prevention and intervention.<br>

> Furthermore, when entering information from close people while using the service, we want to create time to know what we like, what we like, and how we like clothes, and to make more valuable time to communicate through the process of taking pictures and recording voices.

## 📖 Table of Contents

<ol>
 <li><a href="#features">Features</a></li>
 <li><a href="#stacks">Stacks</a></li>
 <li><a href="#expectation">Expectation</a></li>
 <li><a href="#competitiveness">Competitiveness</a></li>
 <li><a href="#gettingstarted">Getting Started</a></li>
 <li><a href="#structure">Source Code Structure</a></li>
 <li><a href="#teaminfo">TEAM INFO</a></li>
</ol>

<h2 id="features"> ✨ Key Features </h2>

There is **Two key features** exist.

- **Register Person Information**
- **Quiz about registered people**

<h2 id="expectation"> ✨ Expectations </h2>
 
 ### 👍 Memory Enhancement
- You can promote brain activity by taking quizzes.
- It helps prevent dementia by submitting answers through writing.
 
 ### 🤝 Enabling Family Communication
- If you use the service, you will have time to communicate with your family.
- You can get to know each other more by entering your family's information.
 
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

- **Architecture**
  - MVVM
- **Android Jetpack**
  - ViewModel
  - LiveData
  - View Binding
  - Navigation
- **Network**
  - Retrofit2 / okHttp3
- **Asynchronous**
  - Kotlin Coroutine
- **Database**
  - DataStore
- **DI**
  - Hilt

<h2 id="gettingstarted"> 🏃 Getting Started </h2>

You don't need to install anything to run Guessme.
It's all deployed on the cloud.

The only thing you need is a [Android Client](https://github.com/GUESS-ME-GDSC/Android).

<h2 id="structure"> 🕹️ Source Code Structure </h2>

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
        <th>Name</th>
        <td width="100" align="center">HaeChan Kim</td>
        <td width="100" align="center">JeongHo Kim</td>
        <td width="100" align="center">Yuri Mun</td>
    </tr>
    <tr>
        <th>Role</th>
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
        <th>Email</th>
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
