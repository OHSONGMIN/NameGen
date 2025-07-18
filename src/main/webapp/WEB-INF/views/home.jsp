<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>닉네임 생성기</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 100px 0 30px 0;
            padding: 50px 20px; /* 위아래 여백 충분히 */
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
            box-sizing: border-box;
            background-color: #fff;
        }

        .container {
            text-align: center;
            max-width: 550px;
            width: 100%;
        }

        h1 {
            font-size: 34px;
            margin: 30px 0 40px; /* 위쪽 넉넉, 아래도 시원하게 */
            text-align: center;
        }

        .tabs {
            display: flex;
            justify-content: center;
            list-style-type: none;
            padding: 0;
            margin-bottom: 30px; /* 탭 아래도 여백 */
        }

        .tabs li {
            margin: 0 10px;
        }

        .tabs button {
            padding: 10px 20px;
            border: none;
            background: none;
            font-size: 18px;
            cursor: pointer;
        }

        .tabs button.active {
            font-weight: bold;
            text-decoration: underline;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        .tab-content input[type="text"] {
            padding: 12px;
            margin: 10px 0;
            width: 80%;
            max-width: 350px;
            box-sizing: border-box;
            font-size: 17px;
            text-align: center;
        }

        .tab-content button[type="button"] {
            width: 80%;
            max-width: 350px;
            padding: 16px 0;
            margin-top: 20px;
            font-size: 20px;
            font-weight: bold;
            background-color: #8C8C8C; /* 덜 까만 짙은 회색 */
            color: #ffffff;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        .result {
            margin-top: 45px;
            background-color: #E1E1E1;
            padding: 25px;
            /*border-radius: 12px;*/
            text-align: center;
            font-size: 19px;
        }

        .result ul {
            padding-left: 10px;
            margin: 15px 0 0;
            text-align: left; /* 닉네임 리스트는 좌측 정렬 */
            display: inline-block; /* 중앙 영역 안에서만 좌측정렬 */
        }
    </style>

</head>

<body>
<div dlass="container">

<h1>닉네임 생성기</h1>

<!-- 탭 영역 -->
<ul class="tabs">
    <li><button class="active" onclick="showTab(0)">게임 닉네임</button></li>
    <li><button onclick="showTab(1)">인스타 아이디</button></li>
    <li><button onclick="showTab(2)">내 자동차 애칭</button></li>
</ul>

<!-- 게임 탭 -->
<div class="tab-content active" id="tab-0">
    <input type="text" id="input-game" placeholder="단어 입력" />
    <button type="button" onclick="generateGame()">생성하기</button>
</div>

<!-- 인스타 탭 -->
<div class="tab-content" id="tab-1">
    <input type="text" id="input-insta" placeholder="단어 입력" />
    <button type="button" onclick="generateInsta()">생성하기</button>
</div>

<!-- 자동차 탭 -->
<div class="tab-content" id="tab-2">
    <input type="text" id="input-car-1" placeholder="차종 입력" />
    <input type="text" id="input-car-2" placeholder="단어 입력" />
    <button type="button" onclick="generateCar()">생성하기</button>
</div>

<!-- 결과 영역 -->
<div class="result" id="result">
    결과가 여기에 표시됩니다.
</div>

<!-- 스크립트 -->
<!-- jQuery 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    function showTab(index) {
        const tabs = document.querySelectorAll('.tabs button');
        const contents = document.querySelectorAll('.tab-content');

        // 탭 버튼 <button></button>
        for (let i = 0; i < tabs.length; i++) {
            if (i === index) {
                tabs[i].classList.add('active');
            }
            else {
                tabs[i].classList.remove('active');
            }
        }

        // 탭 콘텐츠 <div></div>
        for (let i = 0; i < contents.length; i++) {
            if (i === index) {
                contents[i].classList.add('active');
            }
            else {
                contents[i].classList.remove('active');
            }
        }
    }

    function generateGame() {
        const keyword = document.getElementById('input-game').value.trim();

        $.ajax({
            url: '/api/nickname/game',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ keyword: keyword }),
            success: showResult,
            error: showError
        });
    }

    function generateInsta() {
        const keyword = document.getElementById('input-insta').value.trim();

        $.ajax({
            url: '/api/nickname/instagram',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ keyword: keyword }),
            success: showResult,
            error: showError
        });
    }

    function generateCar() {
        const car = document.getElementById('input-car-1').value.trim();
        const keyword = document.getElementById('input-car-2').value.trim();

        if (car === '' && keyword === '') {
            alert('최소 하나의 단어를 입력해야 합니다.');
            return;
        }

        $.ajax({
            url: '/api/nickname/car',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                car: car,
                keyword: keyword }),
            success: showResult,
            error: showError
        });
    }

    function showResult(response) {
        const nicknames = response.nicknames;

        const resultHtml = "<ul>" +
            nicknames.map(nick => "<li>" + nick + "</li>").join('') +
            "</ul>";
        document.getElementById('result').innerHTML = resultHtml;

    }

    function showError(xhr, status, error) {
        console.error("에러 발생:", error);

        document.getElementById('result').innerText = '닉네임 생성 중 오류가 발생했습니다.';
    }
</script>

</div>
</body>
</html>
