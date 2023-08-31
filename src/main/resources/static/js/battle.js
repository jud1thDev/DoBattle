let todoNum = 2;    // 몇 번째 todo인지 class명 저장용
let txtFieldNum = 0;   // 줄줄이 투두 수정용
let clicked = 0;    // 단순 투두 몇 개 끝냄?
let percent = 0;

function todoClick() {
    while (txtFieldNum == 0) {
        let todo;
        let battleBottom = document.getElementById("battle-bottom");

        let newDiv = document.createElement("div");    // 전체 감쌀 div
        let newText = document.createElement("input");
        newText.type = "text";
        newText.className = "input-text";
        newText.placeholder = "입력";

        battleBottom.appendChild(newDiv);
        newDiv.appendChild(newText);

        txtFieldNum++;  // 줄줄이 방지

        newText.addEventListener("keyup", function(event) {
            if (event.key === "Enter") {
                battleBottom.removeChild(newDiv);
                todo = newText.value;

                let todoDataInput = document.getElementById("todoDataValue");
                todoDataInput.value = todo;

                if (!todo) {
                    alert("오늘 할 일을 입력하세요.");
                    return;
                }

                let newDiv2 = document.createElement("form");    // 밖에 감싸줄 form 태그
                newDiv2.setAttribute("action", "/battle/saveTodoData");
                newDiv2.setAttribute("method", "post");
                newDiv2.id = todoNum; // 버튼 클릭시 색 변화를 위해

                let todoList = document.createElement("div");   // todo 저장
                todoList.className = "todo-list";
                todoList.innerText = todo;

                let fireImg = document.createElement("input");    // 불 input 체크박스
                fireImg.type = "button";
                fireImg.value = "notDone";
                fireImg.className = "fire-button";
                fireImg.onclick = function() { fireOrange(newDiv2.id); };

                let hiddenInput1 = document.createElement("input");
                hiddenInput1.type = "hidden"; // 숨겨진 필드로 데이터 전달
                hiddenInput1.name = "todoDataValue"; // 백엔드에서 사용!!!!!!
                hiddenInput1.value = todo; // 'todoDataValue' 값을 설정

                let hiddenInput2 = document.createElement("input");
                hiddenInput2.type = "hidden";
                hiddenInput2.name = "value";
                hiddenInput2.value = "notDone"; // 'value' 값을 설정

                battleBottom.appendChild(newDiv2);
                newDiv2.appendChild(todoList);
                newDiv2.appendChild(fireImg);
                newDiv2.appendChild(hiddenInput1);
                newDiv2.appendChild(hiddenInput2);

                todoNum++;
                txtFieldNum--;
            }
        });
    }
}

function fireOrange(num) {
    let allDiv = document.getElementById(num);
    let todoList = allDiv.querySelector(".todo-list");
    let fireIMG = allDiv.querySelector(".fire-button");

    if (fireIMG.value === 'notDone') {
        fireIMG.style.backgroundImage = "url('../image/cal-fire-orange.svg')";
        fireIMG.value = 'done';
        todoList.style.borderBottom = "2px solid #FF5C00";
        clicked++;
        percent = (clicked / todoNum) * 100;
        console.log(percent);
    } else {
        fireIMG.style.backgroundImage = "url('../image/todo-fire.svg')";
        fireIMG.value = 'notDone';
        todoList.style.borderBottom = "2px solid white";
    }
}

function sendTodoDataToServer(battleCode, todoDataValue, value) {
    let form = document.createElement("form");
    form.method = "POST";
    form.action = "/battle/saveTodoData";

    let battleCodeInput = document.createElement("input");
    battleCodeInput.type = "hidden";
    battleCodeInput.name = "battleCode";
    battleCodeInput.value = battleCode;
    form.appendChild(battleCodeInput);

    let todoDataValueInput = document.createElement("input");
    todoDataValueInput.type = "hidden";
    todoDataValueInput.name = "todoDataValue";
    todoDataValueInput.value = todoDataValue;
    form.appendChild(todoDataValueInput);

    let valueInput = document.createElement("input");
    valueInput.type = "hidden";
    valueInput.name = "value";
    valueInput.value = value;
    form.appendChild(valueInput);

    document.body.appendChild(form);
    form.submit();
}
