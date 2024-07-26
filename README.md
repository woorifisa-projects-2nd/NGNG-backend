# NGNG-backend
## 😎사기 잡는 안전한 C2C 거래 중개 플랫폼, 내꺼니꺼

![Group 47819 (2)](https://github.com/woorifisa-projects-2nd/NGNG-frontend/assets/62551858/48af984a-7586-47ea-b688-9398b94fc070)

# 1. 프로젝트 개요
우리 FIS 아카데미 클라우드 서비스 개발반 최종 프로젝트

## 개발 기간 : 2024.03.18 ~ 2024.04.30

## `사기 거래 방지를 위한 중고거래 플랫폼` 주제 선정 배경
국내 중고거래 시장이 급성장함에 따라 사기 피해액도 증가하고 있습니다.

당근마켓이 직거래를 유행시킨 이후 비대면 거래 사기는 줄어들었지만 
`'3자 사기'`(돈거래는 피해자들끼리 하고 범인은 물건을 중간에서 빼돌리는 형태)처럼 
새로운 직거래 사기 유형이 등장했습니다.

이처럼 사기꾼들은 계속해서 수법을 바꿔나가면서 어떻게든 사기를 치기 때문에 
결국 사용자들이 스스로 꼼꼼하게 따져 보고 구매를 해야 하는 게 현실입니다.

이때 사기글 판별 전문가나 실제로 중고거래 사기 피해를 입었던 사람이 
내가 중고 거래를 할 때 옆에서 지켜봐준다면 훨씬 안심하고 거래를 할 수 있지 않을까요?

저희는 이런  아이디어를 바탕으로 프로젝트를 기획했습니다.

# 2. 프로젝트 시스템 아키텍처, ERD
![image](https://github.com/woorifisa-projects-2nd/NGNG-chat-server/assets/62551858/57b35b0f-2966-49f8-a3ef-d30a87605977)
![image (1)](https://github.com/woorifisa-projects-2nd/NGNG-chat-server/assets/62551858/3b81650b-e00c-4172-95f3-27770b843b19)(https://www.erdcloud.com/d/akdosZBK9b5xTTRDd)

# 3. 기술 스택
## Front-End
![Next](https://img.shields.io/badge/Next.js-000?logo=nextdotjs&logoColor=fff&style=for-the-badge)
![Tailwind_CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)
![Context API](https://img.shields.io/badge/Context%20API-blue?style=for-the-badge)
![SWR](https://img.shields.io/badge/SWR-blue?style=for-the-badge)

## Back-End
![java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-green?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white)
![Logstash](https://img.shields.io/badge/Logstash-005571?style=for-the-badge&logo=Logstash&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white)

## Infra
![EC2](https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white)
![S3](https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white)
![Amazon RDS](https://img.shields.io/badge/Amazon%20RDS-blue?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Nginx](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white)
![docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Co-work tool
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)

# 4. 주요 기능
## 회원가입
- 전환번호 인증, 이메일 인증을 통한 회원가입
  | | |
  |---|---|
  | ![image](https://github.com/user-attachments/assets/8bd39b41-b879-4933-8edc-81ccb635a629) | ![image](https://github.com/user-attachments/assets/7a45d203-46b6-4f63-896a-2a0c3606db58) |

## 로그인 / JWT
- 
  | |
  |---|
  | ![로그인](https://github.com/user-attachments/assets/83aa6a62-1042-4700-9b8d-37ca2900025c) |
  
## 상품페이지
- 판매글 등록과 함께 채팅방 생성
  | |
  |---|
  | ![상품정보](https://github.com/user-attachments/assets/b6db18bc-bdaa-4222-8bee-9e95ec279bbc) |

## 마이페이지 / 포인트
- 마이페이지 구매, 판매, 포인트 관리 및 거래 진행
  | | |
  |---|---|
  | ![마이페이지](https://github.com/user-attachments/assets/fb2438f4-a4e6-4e0a-b9f4-4211952e6171) | ![포인트](https://github.com/user-attachments/assets/a680d588-3120-41df-9e9a-338354f3e8db) |

## 신고기능
- 신고는 게시글, 사용자, 거래에 대한 신고를 진행 / 신고 내용은 관리자가 확인하고 패널티를 부여
  | |
  |---|
  | ![신고하기](https://github.com/user-attachments/assets/02f143cf-4a0b-486d-8f7b-285ffb0444e4) | 

## 관리자페이지
- 패널티 시스템은 3아웃 패널티 제도로 7일, 30일, 영구정지의 단계로 적용
  | |
  |---|
  | ![패널티](https://github.com/user-attachments/assets/a36ac0a2-da85-4c37-99b2-ca64f3e867f1) |


# 5. 결과 및 성과

- 키워드를 기준으로 문서를 탐색하는 역색인 방식인 Elasticsearch를 적용해 상품 개수가 많아지더라도 빠른 검색 속도 제공
- 상품 조회 API에 Redis 캐시를 적용해 다수의 사용자가 동시에 요청할 경우 DB부하를 줄여 응답속도 개선

# 6. 팀원 구성

| [김주찬](https://github.com/rlawncks125) | [문지환](https://github.com/mnjihwan) | [송원섭](https://github.com/sws6641) | [조명하](https://github.com/chomyungha51) |
|---|---|---|---|
| - 판매, 구매 이력 조회<br> - 페이지 기능 개발<br> - 인프라 구축<br> - 로깅 | - 인증, 인가 프로세스 개발<br> - 검색 기능 개발<br> - 사용자 조회, 수정 개발 | - 관리자 기능 개발<br>(신고, 상품, 사용자, 로그)<br> - 신고하기 기능 개발<br> - 로깅 | - 오픈채팅, 1:1채팅  기능 개발<br> - 상품 등록, 판매 기능 개발<br> - 프론트 다크모드, PWA 개발 |

---
