# :pushpin: 세탁 서비스 플랫폼

## 1. 제작 기간 & 참여 인원

-   2023년 08월 21일 ~ 09월 15일
-   팀 프로젝트(7인)

</br>

## 2. 사용 기술

#### `Back-end`

-   Java 11 / Gradle
-   Oracle 11g
-   Apache Tomcat 9.0.78
-   Spring Boot 2.7.14
-   MyBatis 2.3.1
-   jQuery 3.7.1
-   Thymeleaf 3.1.2
-   IntelliJ 2023.2.1
-   Git, Github

#### `Front-end`

-   HTML5
-   CSS3
-   Javascript

</br>

## 3. ERD 설계

![ERD](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/3dac4ee1-a7b8-4032-af05-29147616aade)


## 4. 핵심 기능

이 서비스의 핵심 기능은 컨텐츠 등록 기능입니다.  
사용자는 단지 컨텐츠의 카테고리를 선택하고, URL만 입력하면 끝입니다.  
이 단순한 기능의 흐름을 보면, 서비스가 어떻게 동작하는지 알 수 있습니다.

<details>
<summary><b>핵심 기능 설명 펼치기</b></summary>
<div markdown="1">

### 4.1. 전체 흐름

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow1.png)

### 4.2. 사용자 요청

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_vue.png)

-   **URL 정규식 체크** :pushpin: [코드 확인](https://github.com/Integerous/goQuality/blob/b587bbff4dce02e3bec4f4787151a9b6fa326319/frontend/src/components/PostInput.vue#L67)

    -   Vue.js로 렌더링된 화면단에서, 사용자가 등록을 시도한 URL의 모양새를 정규식으로 확인합니다.
    -   URL의 모양새가 아닌 경우, 에러 메세지를 띄웁니다.

-   **Axios 비동기 요청** :pushpin: [코드 확인]()
    -   URL의 모양새인 경우, 컨텐츠를 등록하는 POST 요청을 비동기로 날립니다.

### 4.3. Controller

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_controller.png)

-   **요청 처리** :pushpin: [코드 확인](https://github.com/Integerous/goQuality/blob/b2c5e60761b6308f14eebe98ccdb1949de6c4b99/src/main/java/goQuality/integerous/controller/PostRestController.java#L55)

    -   Controller에서는 요청을 화면단에서 넘어온 요청을 받고, Service 계층에 로직 처리를 위임합니다.

-   **결과 응답** :pushpin: [코드 확인]()
    -   Service 계층에서 넘어온 로직 처리 결과(메세지)를 화면단에 응답해줍니다.

### 4.4. Service

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_service1.png)

-   **Http 프로토콜 추가 및 trim()** :pushpin: [코드 확인]()

    -   사용자가 URL 입력 시 Http 프로토콜을 생략하거나 공백을 넣은 경우,  
        올바른 URL이 될 수 있도록 Http 프로토콜을 추가해주고, 공백을 제거해줍니다.

-   **URL 접속 확인** :pushpin: [코드 확인]()

    -   화면단에서 모양새만 확인한 URL이 실제 리소스로 연결되는지 HttpUrlConnection으로 테스트합니다.
    -   이 때, 빠른 응답을 위해 Request Method를 GET이 아닌 HEAD를 사용했습니다.
    -   (HEAD 메소드는 GET 메소드의 응답 결과의 Body는 가져오지 않고, Header만 확인하기 때문에 GET 메소드에 비해 응답속도가 빠릅니다.)

    ![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_service2.png)

-   **Jsoup 이미지, 제목 파싱** :pushpin: [코드 확인]()
    -   URL 접속 확인결과 유효하면 Jsoup을 사용해서 입력된 URL의 이미지와 제목을 파싱합니다.
    -   이미지는 Open Graphic Tag를 우선적으로 파싱하고, 없을 경우 첫 번째 이미지와 제목을 파싱합니다.
    -   컨텐츠에 이미지가 없을 경우, 미리 설정해둔 기본 이미지를 사용하고, 제목이 없을 경우 생략합니다.

### 4.5. Repository

![](https://zuminternet.github.io/images/portal/post/2019-04-22-ZUM-Pilot-integer/flow_repo.png)

-   **컨텐츠 저장** :pushpin: [코드 확인]()
    -   URL 유효성 체크와 이미지, 제목 파싱이 끝난 컨텐츠는 DB에 저장합니다.
    -   저장된 컨텐츠는 다시 Repository - Service - Controller를 거쳐 화면단에 송출됩니다.

</div>
</details>

</br>

## 5. 핵심 트러블 슈팅

### 5.1. 카테고리 관리의 문제

-   기존 카테고리는 Table을 만들어 DB에서 관리해왔습니다. 세탁 플랫폼인만큼 한명이 주문완료까지 카테고리 호출횟수는 3번, 검증을 위한 호출과 검수자의 카테고리 사용빈도를 종합적으로 확인했을 때 생각보다 많은 비용을 사용하고 있어 개선이 필요하다고 생각했습니다.

-   카테고리는 수정, 추가가 자주 이루어지지않기 때문에 enum을 사용하기로 결정하였고, 그 결과 속도향상과 유지관리가 간편해졌습니다.
-   추가적으로 멤버쉽에 가입한 고객에게 기존 가격의 15% 기능이 존재했었는데 enum을 사용하면서 Function<T, T> 를 사용하여 카테고리별 할인금액을 일괄적으로 관리할 수 있게되었습니다.

<details>
<summary><b>enum 코드</b></summary>
<div markdown="1">

`aug/laundry/enums/category/Category.java`

```java
public enum Category {

        COMMON("일반", null, null),
            BASIC("생활빨래", 4000L, COMMON),
            ADDITIONAL("생활빨래 20L 초과시 10L 당", 3800L, COMMON),

        CLOTHES("의류", null, null),
            Y_SHIRT("와이셔츠", 2100L, CLOTHES),
            SCHOOL_UNIFORM_SHIRT("교복셔츠", 2100L, CLOTHES),
            SCHOOL_UNIFORM_JACKET("교복자켓", 5000L, CLOTHES),
            REGULAR_SHIRT("일반셔츠", 2100L, CLOTHES),
            BLOUSE("블라우스", 4200L, CLOTHES),
            T_SHIRT("티셔츠", 4200L, CLOTHES),
            SWEAT_SHIRT("맨투맨", 4200L, CLOTHES),
            HOODIE("후드티", 4800L, CLOTHES),
            KNITWEAR("니트", 5500L, CLOTHES),
            SWEATER("스웨터", 5500L, CLOTHES),
            CARDIGAN("가디건", 5500L, CLOTHES),
            PANTS("바지", 4800L, CLOTHES),
            SKIRT("스커트", 4800L, CLOTHES),
            ONEPIECE("원피스", 6800L, CLOTHES),
            JUMPSUIT("점프수트", 6800L, CLOTHES),
            ARTIFICIAL_SKIN("인조가죽하의", 11000L, CLOTHES),
            VEST("조끼", 3000L, CLOTHES),
            PADDED_VEST("패딩조끼", 8000L, CLOTHES),
            SKI_BOARD_PANTS("스키,보드 바지", 24800L, CLOTHES),
            SKI_BOARD_JUMP_SUIT("스키, 보드 점스수트", 46800L, CLOTHES),
            SKI_BOARD_JACKET("스키, 보드 자켓", 37000L, CLOTHES),
            PADDED_PANTS("패딩바지", 11000L, CLOTHES),
            SUIT_JACKET("정장자켓", 5000L, CLOTHES),
            JACKET("자켓", 8000L, CLOTHES),
            JUMPER("점퍼", 8000L, CLOTHES),
            COAT("코트", 14000L, CLOTHES),
            TRENCH_COAT("트렌치 코드", 14000L, CLOTHES),
            LIGHTWEIGHT_PADDING("경량패딩", 9000L, CLOTHES),
            PADDING("일반패딩", 16800L, CLOTHES),
            DOWN_PADDING("다운패딩", 16800L, CLOTHES),
            ARTIFICIAL_LEATHER_JACKET("인조가죽자켓", 15000L, CLOTHES),
            TIE("넥타이", 2500L, CLOTHES),
            MUFFLER("목도리", 4000L, CLOTHES),
            SCARF("스카프", 4000L, CLOTHES),
            GLOVES("장갑", 4000L, CLOTHES),
            KNIT_CAP("니트모자", 4000L, CLOTHES),
            CAP_HAT("캡모자", 6000L, CLOTHES),

        BEDDING("침구류", null, null),
            REGULAR_BLANKET("일반이불", 12000L, BEDDING),
            MICROFIBER_BLANKET("극세사이불", 16000L, BEDDING),
            DOWNFER_BLANKET("다운퍼이불 (오리, 거위털)", 22000L, BEDDING),
            WOOL_BLANKET("양모이불", 23000L, BEDDING),
            SILK_QUIT_OF_SILK("실크이불", 25000L, BEDDING),
            BLANKET_PAD("이불패드", 10000L, BEDDING),
            BLANKET_COVER("이불커버", 10000L, BEDDING),
            SINGLE_BLANKET("홑이불", 10000L, BEDDING),
            REGULAR_TOPPER("일반토퍼", 18000L, BEDDING),
            GOOSE_TOPPER("구스토퍼", 25000L, BEDDING),
            PILLOW_COVER("베개커버", 3500L, BEDDING),
            PILLOW_COTTON("베개(솜)", 10000L, BEDDING),
            PILLOW_DOWNFER("베개(다운퍼)", 12000L, BEDDING),
        SHOES("신발", null, null),
            SNEAKERS("운동화", 6000L, SHOES),
            SHOESS("구두", 7000L, SHOES),
            LOAFERS("로퍼", 7000L, SHOES),
            SPORTS_SHOES("스포츠화", 9000L, SHOES),
            WALKER("워커", 11000L, SHOES),
            BOOTS("부츠", 15000L, SHOES),
            UGG_BOOTS("어그부츠", 20000L, SHOES);


    private final String title;
    private final Long price;
    private final Category parentCategory;
    private final Map<String, Long> childCategories = new ConcurrentHashMap<>();

    Category(String title, Long price, Category parentCategory) {
        this.title = title;
        this.price = price;
        this.parentCategory = parentCategory;
        // parentCategory가 null이 아니라면
        if (Objects.nonNull(parentCategory)) {
            parentCategory.childCategories.put(this.title, Objects.isNull(this.price) ? 0L : this.price);
        }

    }

    // 상위 카테고리 이름 가져오기
    public Category getParentCategory() {
        return parentCategory;
    }

    // 하위 카테고리 리스트 가져오기
    public Map<String, Long> getChildCategories() {
        return Collections.unmodifiableMap(childCategories);
    }

    // 전체 하위카테고리 가져오기
    public static Map<String, Long> getAll() {
        return Arrays.stream(Category.values()).filter(x -> Objects.nonNull(x.parentCategory)).collect(Collectors.toMap(y -> y.title, y -> y.price));
    }

    // 상위 카테고리 전체 가져오기
    public static Set<Category> getParentCategoryAll() {
        return Arrays.stream(Category.values()).filter(x -> Objects.isNull(x.getPrice()) && x != Category.COMMON).collect(Collectors.toSet());
    }

    // 카테고리 Title로 카테고리 가져오기
    public static Optional<Category> findByTitle(String title) {
        return Arrays.stream(Category.values()).filter(x -> x.getTitle().equals(title)).findAny();
    }

}


```

</div>
</details>

<details>
<summary><b>Function 기능</b></summary>
<div markdown="1">

`aug/laundry/enums/category/CategoryPriceCalculator.java`

```java
public enum CategoryPriceCalculator {
    COMMON(value -> value),
    PASS(value -> Math.round((Long)value * 0.85));

    private Function<Long, Long> expression;

    CategoryPriceCalculator(Function expression) {
        this.expression = expression;
    }

    protected Long calculate(Long value) {
        return this.expression.apply(value);
    }

    public Float percent() {
        return this.expression.apply(100L) / 100.0f;
    }
}

```

</div>
</details>


</br>

## 6. 그 외 트러블 슈팅

<details>
<summary>RequestBody를 생성할 때 JSON파라미터를 쿼리 문자열로 변경해야하는 문제</summary>
<div markdown="1">

-   okHttp에서 제공하는 HttpUrl.Builder를 이용하여 URL의 쿼리 문자열을 반환하는 'encodeParameters'메서드를 만들어서 해결

```java
public String encodeParameters(JSONObject params) {
    HttpUrl.Builder urlBuilder = HttpUrl.parse(KAKAO_REDIRECT_URL).newBuilder();
    for (String key : params.keySet()) {
        urlBuilder.addQueryParameter(key, params.getString(key));
    }
    return urlBuilder.build().encodedQuery();
}

```

</div>
</details>

<details>
<summary>카카오 로그인 시 사용자의 정보를 DTO에 담아야하는 문제</summary>
<div markdown="1">

-   ObjectMapper 객체를 이용하여 JSON데이터를 java객체에 저장하면 된다.
-   DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES 설정은 JSON데이터에 java 클래스에 없는 속성이 있어도 예외를 던지지 않게 하는 설정이다.

```java
ObjectMapper obMapper = new ObjectMapper();
obMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
KakaoProfile kakaoProfile = null;
try {
    kakaoProfile = obMapper.readValue(body.string(), KakaoProfile.class);
}catch(JsonMappingException e){
    e.printStackTrace();

}catch (JsonProcessingException e){
    e.printStackTrace();
}
```

</div>
</details>

</br>

## 7. 회고 / 느낀점

>
