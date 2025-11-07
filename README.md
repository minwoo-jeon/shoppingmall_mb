# EveryDayShop🛒 (백엔드 중심 개인 쇼핑몰 프로젝트) 
해당 프로젝트는 사용자가 상품을 조회하고 장바구니에 담아 주문할 수 있는 **간단한 쇼팡몰 프로젝트**입니다.  
실제 서비스 흐름에 맞춰 **백엔드 아키텍처 설계부터 CI/CD 배포까지** 직접 구현했습니다.<br>
( 2025.10 ~ 2025.11 )

## ⚙️ 기술 스택
| 구분 | 기술 |
|------|------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.x , Spring Security |
| **ORM / DB 연동** | MyBatis |
| **Database** | MySQL (AWS RDS) |
| **Build Tool** | Gradle |
| **Deploy** | AWS EC2, GitHub Actions |
| **Testing** | JUnit, Postman
| **Infra** | Ubuntu |
| **Version Control** | Git / GitHub |


## 🧩 주요 기능

### 👤 회원
- 회원가입 / 로그인 
- 비밀번호 암호화 (BCrypt)

### 🛍️ 상품
- 상품 목록 / 상세 조회
- 상품 수정 / 상품 삭제

### 🛒 장바구니
- 상품 추가 / 삭제 / 수량 변경
- 장바구니 합계 금액 계산

### 💳 주문
- 주문 생성 / 결제 완료 처리
- 주문 내역 조회 / 주문 취소


## 🧩 RESTful API 구조



### 🛍️ 상품 API

| 기능 | Method | URL | 설명 |
|------|---------|------|------|
| 상품 목록 조회 | GET | `/api/products` | 전체 상품 조회 |
| 상품 상세 조회 | GET | `/api/products/{id}` | 특정 상품 조회 |
| 상품 수정 | PUT | `/api/products/{id}` |  상품 수정 |
| 상품 삭제 | DELETE | `/api/products/{id}` |  상품 삭제 |

---

### 🧾 주문 API

| 기능 | Method | URL | 설명 |
|------|---------|------|------|
| 주문 생성 | `POST` | `/api/orders` | 장바구니 상품을 기반으로 주문 생성 및 결제 요청 |
| 주문 목록 조회 | `GET` | `/api/orders/{orderId}` | 로그인 사용자의 주문 내역 전체 조회 |
| 주문 취소 | `PUT` | `/api/orders/{orderId}/cancel` | 결제 취소 포함 주문 상태 변경 |



### 🛒 장바구니 API

| 기능 | Method | URL | 설명 |
|------|---------|------|------|
| 장바구니 조회 | GET | `/api/carts/{userId}/items` | 로그인 사용자의 장바구니 조회 |
| 상품 추가 | POST | `/api/carts/{userId}/items` | 장바구니에 상품 추가 |
| 상품 삭제 | DELETE | `/api/carts/{userId}/{itemId}` | 장바구니 상품 삭제 |

---

### 👤 회원 API

| 기능 | Method | URL | 설명 |
|------|---------|------|------|
| 회원가입 | POST | `/api/users/join` | 새로운 회원 등록 |
| 로그인 | POST | `/api/users/login` | 로그인 |


  
## 💡 ERD 구조
<img width="1000" height="550" alt="Image" src="https://github.com/user-attachments/assets/1cf9240c-e6c5-4846-a91c-80d5c18dc167" />








