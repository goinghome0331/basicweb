# basicweb

## 소개

spring framework와 react를 이용하여 구현한 웹서비스
사용자 인증과 간단한 CRUD(Create,Read,Update,Delete)를 제공을 구현함.

## 사용도구

```
+ ECLIPSE
+ JDK
+ MAVEN
+ MYSQL(server)
+ H2 DB(local)
```

## MAVEN에서 사용한 dependency

```
+ spring-boot-starter-security
+ mybatis-spring-boot-starter
+ spring-boot-starter-test
+ mysql-connector-java
+ commons-io
```

## MAVEN에서 사용한 plugin

> 외부 dependency를 다운받아 만든 클래스와 하나의 jar 파일로 만들기 위해 필요
```
+ spring-boot-maven-plugin
+ maven-assembly-plugin
```

## 실행방법

> deployment에서 실행한다.
```
+ backend : java -jar basicweb-backend.jar
+ frontend : serve(server 역할을 할 수 있는 모듈) build
```


## 구조

```
+ 클라이언트 
 - react js로 구현
 - spa 형태

+ 서버

 - spring boot로 구현
 -  rest api형태

+ 매핑 퍼시스턴스(?) 프레임워크
 - MyBatis
```

## react로 구현한 클라이언트 특징

```
+ javascript는 비동기 방식으로 동작하기 때문에 network 통신과 같이 시간이 오래 걸리는 동작 처리 후 그 데이터를 재렌더링하기 위해 state 객체를 조작했다.
+ 비동기 처리를 위해 Promise 동작 방식을 사용했다.
+ 네트워크 통신을 위해 aixos 라이브러리 사용했다.
+ 오류 발생시 이를 처리하기 위한 페이지를 따로 만들었다.
+ header의 active마다 페이지를 다르게 하기 위해 Router 컴포넌트 사용
+ react-bootstrap이 있지만 이를 사용하지는 않았다.(그래서 modal 등의 bootstrap 컴포넌트를 조작하기 위해 jquery를 사용함)

```

## spring boot으로 rest api를 구현한 서버 특징

```
+ cors정책을 해결하기 위한 필터를 만들거나 설정 필요한데 필터를 만들었다.
+ spring boot가 기본적으로 servere side redering이기 때문에 인증 실패시 리다이렉트가 된다. 따라서 이를 막아주기 위한 설정함
 - 이를 위해 AuthenticationEntrypoint,SimpleUrlAuthenticationSuccessHandler 재구현함
  : AuthenticationEntrypoint : 인증 실패 시 401 에러를 보내고 리다이렉션 못하도록 설정해주는 컴포넌트
  : SimpleUrlAuthenticationSuccessHandler : 인증 성공시 원래 기능의 리다이렉션해주는 컴포넌트
+ spring security가 기본적으로 csrf를 막기 위한 동작을 하고 있는데, rest api에서는 이것이 필요 없으므로 막는 동작을 해제했다.
```

## 인증 방식

```
+ rest api에서는 토큰 방식을 주로 채택한다고 한다.(http basic, jwt 등)
+ 우선 http basic으로 구현하였으며 추후 jwt 인증방식으로 교체할 예정
+ http basic은 보안이 취약함(이유는 인증 토큰이 아이디:비밀번호를 base 64로 인코딩 한 것이기 때문이다.)
```

## 아직 미완성인 부분

```
+ react 모듈화
 - react 컴포넌트를 좀 더 세부적으로 모듈화를 통해 전체가 재렌더링을 막아야한다.(현재는 무조건 전체 재렌더링함)
+ 테스트
 -  서버인 spring boot 테스트 중 userService만 했다.
 -  추후 service 뿐만 아니라 controller, mapper 부분을 진행할 예정
 - 클라이언트는 아직 미정
+ logger
 - filter 부분만 적용했다. 
 - 추후에 controller, service 등에 적용할 것이다.
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
+ 클라이언트 부분에 async, await 기능을 이미지 로딩 부분에 사용하려 했지만 실패했다.
```

## 추후 개선하고 싶은 사항

```
+ react를 server side rendering 방식으로 동작하듯 설정(크롤링 될 수 있기 위해...)
+ post 검색 기능
+ 다른 유저 정보 보기
```