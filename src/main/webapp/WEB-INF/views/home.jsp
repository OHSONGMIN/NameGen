<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>닉네임 생성기</title>

    <style>
        .tabs {
            display: flex;
            list-style-type: none;
            padding: 0;
            margin-bottom: 10px;
        }

        .tabs li {
            margin-right: 10px;
        }

        .tabs button.active {
            font-weight: bold;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
        }

        .result {
            margin-top: 20px;
        }
    </style>
</head>

<body>

<h1>닉네임 생성기</h1>

<!-- 탭 영역 -->
<ul class="tabs">
    <li><button class="active" onclick="showTab(0)">인스타 아이디</button></li>
    <li><button onclick="showTab(1)">게임 아이디</button></li>
    <li><button onclick="showTab(2)">자동차 애칭</button></li>
</ul>

<!-- 인스타 탭 -->
<div class="tab-content active" id="tab-0">
    <input type="text" id="input-insta" placeholder="단어 입력" />
    <button type="button" onclick="generateInsta()">생성하기</button>
</div>

<!-- 게임 탭 -->
<div class="tab-content" id="tab-1">
    <input type="text" id="input-game" placeholder="단어 입력" />
    <button type="button" onclick="generateGame()">생성하기</button>
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
<script>
    function showTab(index) {
        const tabs = document.querySelectorAll('.tabs button');
        tabs.forEach((tab, i) => {
            tab.classList.toggle('active', i === index);
        });

        const contents = document.querySelectorAll('.tab-content');
        contents.forEach((content, i) => {
            content.classList.toggle('active', i === index);
        });

        document.getElementById('result').innerText = '결과가 여기에 표시됩니다.';
    }

    function generateInsta() {
        const keyword = document.getElementById('input-insta').value.trim();

        $.ajax({
            url: '/api/nickname/insta',
            method: 'POST',
            data: { keyword: keyword },
            success: showResult,
            error: showError
        });
    }

    function generateGame() {
        const keyword = document.getElementById('input-game').value.trim();

        $.ajax({
            url: '/api/nickname/game',
            method: 'POST',
            data: { keyword: keyword },
            success: showResult,
            error: showError
        });
    }

    function generateCar() {
        const keyword1 = document.getElementById('input-car-1').value.trim();
        const keyword2 = document.getElementById('input-car-2').value.trim();

        if (keyword1 === '' && keyword2 === '') {
            alert('최소 하나의 단어를 입력해야 합니다.');
            return;
        }

        $.ajax({
            url: '/api/nickname/car',
            method: 'POST',
            data: {
                keyword1: keyword1,
                keyword2: keyword2
            },
            success: showResult,
            error: showError
        });
    }

    function showResult(response) {

    }

    function showError(xhr, status, error) {

    }
</script>

<!-- jQuery 추가 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</body>
</html>
