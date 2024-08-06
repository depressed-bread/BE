<div align="center">
  <h1 style="display: inline-block; margin: 0 50px;">🍞 우울해서 빵 샀어 🍞</h1>
</div>

## 📚 프로젝트 링크 
<https://develop--depressedbread.netlify.app/>

## 📚 프로젝트 진행 기간 
기간 : 2024.07.07 ~ 2024.08.06

## 📚 기획 배경 
Wellness는 신체적, 정서적, 사회적, 지적인 영역에서 최상의 상태를 유지하거나 이를 적극적으로 만들어 나가는 삶의 방식을 의미한다. 

특히 정서적 건강은 스스로 인식하고 진단하기 어려워 전문가의 도움이 필요하며, 꾸준한 기록과 모니터링이 중요하다. 

또한, 많은 사람들이 감정적 소비로 위안을 얻으려는 경향이 있는데, 이 웹 서비스는 사용자가 자신의 감정 상태를 인식하며 감정에 따른 소비 결과를 분석하고 파악하여 정서적 건강을 관리하는 데 크게 기여할 수 있을 것이다.

## 📚 페이지별 기능

### 시작화면
+ 서비스 접속 초기화면으로 '우울해서 빵 샀어'를 사용자들에게 간단히 소개하는 글과 사진이 나타납니다.
+ 시작하기 버튼을 누르면 로그인 화면으로 넘어갑니다.

<img src="https://github.com/user-attachments/assets/ab750667-7106-4611-8fb7-5b3bd647e235" width="300" height="550"/>

  
### 회원가입 & 로그인

#### 회원가입
+ 계정이 없는 경우 로그인 화면의 하단에 있는 회원가입 버튼을 눌러 회원가입을 진행하게 됩니다.
+ 이름, 이메일, 전화번호, 비밀번호를 필수로 입력하여 계정을 만듭니다. 
+ 입력한 이메일로 만들어진 계정이 있는 경우와 입력되지 않은 칸이 있는 경우 하단에 경고 문구가 뜹니다.
+ 비밀번호는 영어 소문자와 숫자를 포함하여 8자 이상, 20자 이하로 입력해야합니다.
+ 비밀번호를 재입력하여 입력한 비밀번호가 일치해야만 가입이 완료됩니다.

<img src="https://github.com/user-attachments/assets/3c544783-d5f0-4ed4-80d6-f6b8df6e0737" width="300" height="550"/>


#### 로그인

+ 입력한 아이디의 계정이 없거나 비밀번호가 틀린 경우 화면 하단에 경고 문구가 뜹니다.
+ 아이디나 비밀번호를 사용자가 기억하지 못한 경우를 대비하여 아이디 찾기와 임시 비밀번호 사용하기 기능을 구현하였습니다.
+ 임시 비밀번호는 입력한 이메일로 전송이 됩니다.


<img src="https://github.com/user-attachments/assets/899cf203-13ff-4c5c-904f-2fdbe89aee06" width="300" height="550"/>

### 홈화면
+ 홈화면에 있는 달력을 통해 한 달의 전체적인 기록을 간단하게 볼 수 있습니다.
+ 달력에 있는 이모지는 그 날의 내역 중 가장 많은 지출을 차지하는 감정이 표시됩니다.
+ 달력의 날짜를 클릭 / 터치를 하면 그 날의 소비 내역 중 가장 최근의 내역 2개를 볼 수 있습니다.
+ 화면 하단의 소비내역 더보기 버튼을 누르면 조회 화면으로 넘어가 그 날의 전체 소비 내역을 볼 수 있습니다.
+ 소비 내역의 상세보기 버튼을 누르면 상세 조회 화면으로 넘어가 내역의 상세 내역을 볼 수 있습니다.
+ 상세 조회 화면에서는 내역의 내용을 수정 / 삭제 할 수 있습니다.

<img src="https://github.com/user-attachments/assets/b28b39ea-afbd-4ac6-8316-8598104229a0"  width="300" height="550"/>

### 내용 입력
+ 홈 화면 하단에 있는 내용입력 버튼을 통해 새 글을 적을 수 있습니다.
+ 모든 내용을 입력해야 내용입력이 완료됩니다.

<img src ="https://github.com/user-attachments/assets/70398a2a-c4a7-4f5b-916d-287da39dbf48" width="300" height="550">


### 조회
+ 조회화면은 기본적으로 오늘의 내역을 보여줍니다.
+ 오늘, 이번 주 , 이번 달의 내역을 볼 수 있으며 각각의 총액도 함께 확인할 수 있습니다.
+ 날짜를 지정 선택하여 원하는 기간 내의 내역과 총액을 볼 수 있습니다.
+ 화면 왼쪽 상단에 있는 바를 통해 감정별로 내역을 확인할 수 있습니다.

<img src ="https://github.com/user-attachments/assets/886dfefa-e29a-4776-a4b3-c2b9abd4fdee" width="300" height = "550">
<img src="https://github.com/user-attachments/assets/014deb56-a72c-48c5-af18-e89a9f12eee8" width="300" height = "550">

### 설정
+ 우측 상단에 있는 이모티콘을 누르면 설정 화면으로 이동합니다.
+ 사용자의 개인 정보를 변경할 수 있도록 회원 정보 수정, 비밀번호 재설정 및 로그아웃 기능을 구현하였습니다.

<img src="https://github.com/user-attachments/assets/bd6a384f-5f33-4864-832d-31eea0121f34" width="300" height="550"/>

## 📚 디테일
★ API 명세서 : <https://tender-justice-575.notion.site/API-e67342fd71504db08eb1216edb002531>

★ ERD Cloud : <https://www.erdcloud.com/d/xaGGJAs45j5xWrWqr>
![image](https://github.com/user-attachments/assets/416589a7-f66a-4353-97ba-fd0de8c839d1)


## 📚 팀원 소개 및 역할
+ 한지우 : 지출 내역 통계 및 분석 API 개발, 프로덕트 배포 및 서버 관리
  
+ 한결아 : Spring Security를 이용한 회원가입 및 로그인 API 개발

+ 유병훈 : 감정 리스트 조회 API 개발, 감정 지출 게시글 CRUD API 개발 

## 📚 기술 스택
<div align=left> 
 <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
 <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
 <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
 <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
</div>

## 📚 협업 툴
<div align=left> 
 <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
 <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
 <img src="https://img.shields.io/badge/Notion-F3F3F3?style=for-the-badge&logo=Notion&logoColor=black">
 <img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=Figma&logoColor=white">
 <img src="https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=Discord&logoColor=white">
</div>

