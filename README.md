# basicweb

## 소개
```
1. spring framework와 react를 이용하여 구현한 웹서비스.
2. 사용자 인증과 간단한 CRUD(Create,Read,Update,Delete)를 제공을 구현함.
```

## 개발환경

```
+ ECLIPSE
+ MAVEN
+ Visual Studio Code
+ Nodejs
```

## MAVEN에서 사용한 dependency

```
+ spring-boot-starter-security
+ mybatis-spring-boot-starter
+ spring-boot-starter-test
+ h2
+ mysql-connector-java
+ commons-io
```

## MAVEN에서 사용한 plugin

> 외부 dependency를 다운받아 하나의 jar 파일로 만들기 위해 필요
```
+ spring-boot-maven-plugin
+ maven-assembly-plugin
```

## nodejs에서 사용한 depenency
```
+ creact-react-app에 의한 dependencies
+ react-router-dom
+ bootstrap
+ jquery
+ popper.js
+ axios
+ font-awesome에 의한 dependencies
```

## 실행하기

```
 1. Clone or Download 하기
 2. console를 통해 해당 폴더로 이동
 3. backend 실행하기 java -jar basicweb-backend.jar(db는 h2이다.)
 4-1. frontend 설치 : npm install 실행
 4-2. frontend 실행 : npm start 실행
 5. 웹 브라우저에 http://localhost:5000 실행
```


## react로 구현한 클라이언트 특징

```
+ 네트워크 통신을 위해 aixos 라이브러리 사용했으며, 비동기 처리방식인 Promise, async-await 그리고 callback 방식으로 데이터를 처리했다.
+ 네트워크 통신으로 인한 재렌더링을 위해 componentDidMount, componentDidUpdate도 사용했다.
+ Header 그에 따른 컴포넌트 전환과 컴포넌트에 따른 url을 위해 react-router-dom 사용했다.
+ react-bootstrap이나 reactstraop이 있어 bootstrap 사용시 코드의 효율성은 좋지만 어차피 재렌더링되는 것은 같아 사용하지 않았다.(그래서 modal 등의 bootstrap 컴포넌트를 조작하기 위해 jquery를 사용함)
```

## spring boot으로 rest api를 구현한 서버 특징

```
+ cors정책을 해결하기 위한 필터를 구현했지만 spring에서 제공하는 기능이 있어 이것으로 전환
+ spring boot가 기본적으로 servere side redering이기 때문에 인증 실패시 리다이렉트가 된다. 따라서 이를 막기 위해 AuthenticationEntrypoint 구현과 Security configuration에서 필요한 설정을 했다.
+ spring security가 기본적으로 csrf를 막기 위한 동작을 하고 있는데, rest api에서는 이것이 필요 없으므로 막는 동작을 해제했다.(안하면 동작이 제대로 안됨.)
```

## 인증 방식

```
+ rest 서버에서는 토큰 방식을 주로 채택한다고 한다.(http basic, jwt 등)
+ http basic은 보안이 취약함(이유는 인증 토큰이 아이디:비밀번호를 base 64로 인코딩 한 것이기 때문이다.)
+ 따라서 jwt방식으로 구현함(세션을 자동으로 생성하는 것을 막았기때문에 Jwt를 위한 Filter를 만들고 이곳에서 처음 토큰이 들어왔을 때 세션을 만들었다.)
```


## 기능
> 자신의 정보

#### 내 정보
![myinfo-basic](https://github.com/goinghome0331/basicweb/blob/master/img/myinfo.png)
```
 - 내 정보를 볼 수 있으며, 사진, 비밀번호 등만 변경이 가능하게 했다.
```

#### 사진 저장
![register-image](https://github.com/goinghome0331/basicweb/blob/master/img/registerimage.jpg)
```
 - 사진은 base64 인코딩된 데이터를 주고 받아 렌더링한다.
```

#### 사진 제거
![delete-image](https://github.com/goinghome0331/basicweb/blob/master/img/deleteimage.jpg)

#### 비밀번호 변경
![change-passwd](https://github.com/goinghome0331/basicweb/blob/master/img/changePasswd.jpg)
```
 - 비밀번호 변경시 기존 비밀번호 일치 여부, 새 비밀번호와 확인 일치 여부를 확인한다.
```

#### 계정 삭제
![delete-user](https://github.com/goinghome0331/basicweb/blob/master/img/deleteUser.jpg)
```
 - 계정 삭제시 기존 비밀번호 일치 여부를 확인한다.
```

> 게시판
#### 기본
![board-basic](https://github.com/goinghome0331/basicweb/blob/master/img/board.png)
```
 - 글이 10개 이상이면 더 볼 수 있다.
```

#### 게시글 작성
![register-post](https://github.com/goinghome0331/basicweb/blob/master/img/registerPost.jpg)
```
 - 빈 제목, 내용을 확인하여 이상 없으면 등록한다.
```

#### 댓글 작성
![register-comment](https://github.com/goinghome0331/basicweb/blob/master/img/registerComment.jpg)
```
 - 빈 내용을 확인하여 이상 없으면 등록한다.
```

#### 댓글 더보기
![more-comment](https://github.com/goinghome0331/basicweb/blob/master/img/moreComment.jpg)
```
 - 댓글이 10개 이상일 때 더보기를 눌르면 다음 내용을 볼 수 있으며 더이상 없으면 메세지가 보인다.
```

#### 댓글 삭제
![delete-comment](https://github.com/goinghome0331/basicweb/blob/master/img/deleteComment.jpg)
```
 - 해당 댓글 게시자만이 지울 수 있는 버튼이 보이며 버튼을 누르면 삭제할 수 있다. 
```


#### 게시글 수정
![update-post](https://github.com/goinghome0331/basicweb/blob/master/img/updatePost.jpg)
```
 - 해당 글 게시자만이 수정 수 있는 버튼이 보이며 이전 내용에서 내용을 변경할 수 있다.
```

#### 게시글 삭제
![delete-post](https://github.com/goinghome0331/basicweb/blob/master/img/deletePost.jpg)
```
 - 해당 글 게시자만이 지울 수 있는 버튼이 보이며 해당 글과 그 안에 있는 모든 댓글이 삭제된다.
```

## 해결하지 못한 문제

```
+ h2 database 초기화 할 때 drop, insert sql문이 깨져서 실행되어 계속 오류가 발생한다. 여전히 이유를 알 수 없음
```

## 추후 개선하거나 추가하고 싶은 사항

```
+ react를 server side rendering 방식으로 동작 설정(크롤링 될 수 있기 위해...)
+ post 검색 기능
+ 다른 유저 정보 보기
+ react 컴포넌트를 좀 더 세부적으로 모듈화
+ userService 테스트만 존재. 추후 다른 service, servcontroller, mapper 테스트 필요, 클라이언트는 아직 미정
+ logger는 filter 부분만 존재. 추후에 controller, service logger 필요.
```