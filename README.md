# :pushpin: 세탁 서비스 플랫폼

## 1. 제작 기간 & 참여 인원

-   2023년 08월 21일 ~ 2023년 09월 15일
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


## 4. 기능 및 역할

이 서비스의 핵심 기능은 컨텐츠 등록 기능입니다.  
사용자는 단지 컨텐츠의 카테고리를 선택하고, URL만 입력하면 끝입니다.  
이 단순한 기능의 흐름을 보면, 서비스가 어떻게 동작하는지 알 수 있습니다.

### 나의 역할
-   메인화면 구현 `Controller : aug/laundry/controller/MainController.java` : [코드확인](https://github.com/KIMSEUNGWO/TeamProject_Second/blob/main/src/main/java/aug/laundry/controller/MainController.java)
-   세탁신청 ~ 주문완료 기능구현 `Controller : aug/laundry/controller/laundry/` : [코드확인](https://github.com/KIMSEUNGWO/TeamProject_Second/blob/main/src/main/java/aug/laundry/controller/laundry/)
-   공통 Aspect 구현 `Aspect : aug/laundry/aspects/FooterAspect.java` : [코드확인](https://github.com/KIMSEUNGWO/TeamProject_Second/blob/main/src/main/java/aug/laundry/aspects/FooterAspect.java)
-   Category enum 구현 `Enum : aug/laundry/enums/category/Category.java`  : [코드확인](https://github.com/KIMSEUNGWO/TeamProject_Second/blob/main/src/main/java/aug/laundry/enums/category/Category.java)
-   HTML, CSS 80% 제작 `HTML : /templates/, CSS : /static/css/` 
-   Android 앱 구현(JAVA)

<details>
<summary><b>기능 설명 펼치기</b></summary>
<div markdown="1">

### 4.1. 전체 흐름

![세탁소URL](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/fcd46ce9-dffa-4e74-b9fe-949bb484c725)

### 4.2. 사용자 요청

-   **사용자 신청**
![주문](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/cda2f4a2-dbf5-4b79-b5bd-c7920d4644a5)
![주문2](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/12073fc1-a4a1-454f-a012-2337fa782dc0)

-   **주문내역 확인, 배달원 수거**
![픽업](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/dfff4380-0369-4d3d-b8bb-ae628b177f23)

-   **관리자 세탁물 검수 과정**
![검수](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/ed852666-b51a-4d13-b774-25d3545ad6b6)

-   **세탁 후 사용자에게 배송**
![배송](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/d885129e-9f68-4689-bcb0-ef40add82cf8)


### 4.3. 그 외 기능
-   **마이페이지 쿠폰, 포인트, 친구초대 기능 구현**
![마이페이지1](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/849da23a-cc37-47e7-8c83-aaeca20fabf1)

-   **주소,비밀번호 찾기, 새 비밀번호 발급, 탈퇴기능 구현**
![마이페이지2](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/14445af9-e469-487f-8aef-1ef54fc2f6ff">)

-   **구독 기능 구현**
![구독](https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/59fff444-3f69-4f75-a7f7-86484c340d7b)

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

### 5.2. 페이지 위치에 따른 footer 변화

-   페이지 위치에 따라 footer의 아이콘의 상태가 변경되는것을 구현하는데 그 목록에는 메인, 주문내역, 마이페이지가 있습니다.
-   기존에는 페이지마다 footer의 상태를 Model에 넣어반환하였으나 페이지가 계속 추가되면서 유지보수가 불가능하다는 느낌이 들었고, 이 부분을 개선하기 위해 Aspect를 도입했습니다.
-   AOP를 도입하면서 Aspect의 Class가 각 Controller에 의존하게되지만 기존에 사용했던 불필요한 코드들이 한번에 제거되었습니다.

<details>
<summary><b>Aspect 코드</b></summary>
<div markdown="1">

`aug/laundry/aspects/FooterAspect.java 의 일부`

```java
@Before("execution(* aug.laundry.controller.MainController.*(..))")
public void mainpageAspect(JoinPoint joinPoint) {
    log.info("MainPageController Aspect Before 실행 : {}", joinPoint.getSignature().getName());

    Object[] args = joinPoint.getArgs();
    for (Object arg : args) {
        if (arg instanceof Model) {
            ((Model) arg).addAttribute("footer", "main");
            return;
        }

    }
}
```
<img width="426" src="https://github.com/KIMSEUNGWO/TeamProject_Second/assets/128001994/d945ee7a-a7ac-4999-a222-bf95cb838a48">
</div>
</details>


</br>

## 6. 그 외 트러블 슈팅

<details>
<summary>AJAX 통신 시 MultipartFile 전송 실패</summary>
<div markdown="1">

-   다수의 항목에 대한 다수의 Multipart 파일과 메세지 전송 시도 - 실패
-   MultipartFile JSON 변환후 시도 - 실패
-   RepairFormData ( List<MultipartFile>, String, String ) 객체 생성, Map<String, RepairFormData>를 FormData에 넣어서 전송 - 실패
-   RepairFormData ( String, String ) 분리, Map<String, RepairFormData>와 List<MultipartFile> 를 Controller에 항목개수만큼 전송 - 성공

```java
@PostMapping(value = "/repair/order")
public @ResponseBody Map<String, Boolean> repairOrder(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                                                  @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                                                  @RequestPart("repairData") Map<String, RepairFormData> repairData,
                                                  @RequestParam(name = "files", required = false) List<MultipartFile> files) {
Map<String, Boolean> resultMap = new ConcurrentHashMap<>();
System.out.println("files = " + files);

// memberId, ordersDetailId == null 일경우 false 반환
boolean status = repairService.valid(memberId, ordersDetailId);
if (!status){
    resultMap.put("status", false);
    return resultMap;
}
Repair saveRepair = repairService.insertRepair(ordersDetailId, repairData, files);

if (saveRepair != null) {
    int saveFile = fileUploadService.saveFile(files, saveRepair.getRepairId(), FileUploadType.REPAIR);
    log.info("saveFile = {}", saveFile);
}
log.info("repairData = {}", repairData);
log.info("files = {}", files);

return resultMap;
}

```

</div>
</details>

</br>

## 7. 회고 / 느낀점

> 프로젝트를 들어가며
<br>
첫번째 프로젝트에 이어 두번째도 팀장을 맡게 되었습니다. 주제에 대한 고민을 하다가 세탁소와 사용자를 매칭 및 배달하는 중계플랫폼을 주제로 고민하다가 사용자와 회사를 직접 매칭하는 세탁플랫폼을 주제로 선정하게되었습니다.
두번째 프로젝트의 규모가 클것이라고 예상했지만 그 예상보다 훨신 더 큰 프로젝트였습니다. 프로젝트를 한번밖에 안해본 저에겐 1달이 안되는 기간동안 마무리할 수 있을까 란 생각이 먼저 들었던것같습니다.
하지만 그저 게시판정도를 만드는 프로젝트를 하고싶지는 않았습니다. 이제껏 배운걸 활용해서 만드는것도 중요하지만 안해본것을 스스로 공부해가며 활용해보는것이 더 중요하다고 생각했기 때문입니다.

> 프로젝트 과정속에서
<br>
당초 계획한 7명 중 1명이 개인적인 사유로 더 이상 프로젝트에 참여하지 못하게되었습니다. 1차, 2차 프로젝트 모두 한명씩 빠지게 되어 많이 아쉬웠습니다. 빠진인원에 대한 역할도 제가 맡게 되어 아침부터 저녁 10시까지 거의 매일을 코딩했던것같습니다.

