let todoNum = document.querySelectorAll('#battle-bottom > form').length;    //몇번째 todo인지 class명 저장용
let txtFieldNum = 0; // 줄줄이 투두 수정용
let clicked = 0; // 단순 투두 몇 개 끝냄?
let percent = 0;

function todoClick() {
  while (txtFieldNum === 0) {
    let todo;   //입력한 문구 저장용
    let battleBottom = document.getElementById("battle-bottom");

    let newDiv = document.createElement("form"); // 전체 감쌀 div
    let newText = document.createElement("input");
    newText.type = "text";
    newText.className = "input-text";
    newText.placeholder = "입력";

    battleBottom.appendChild(newDiv);
    newDiv.appendChild(newText);

    txtFieldNum++; // 줄줄이 방지

    newText.addEventListener("keyup", function (event) {
      if (event.key === "Enter") {
        battleBottom.removeChild(newDiv);
        todo = newText.value;

        let todoDataInput = document.getElementById("todoDataValue");
        todoDataInput.value = todo;

        if (!todo) {
          alert("오늘 할 일을 입력하세요.");
          return;
        }

        let newForm = document.createElement("form"); // 밖에 감싸줄 form 태그
        newForm.method = "POST";
        newForm.action = "/battle/saveTodoData";
        newForm.id = "todo-form"; // 폼에 ID 추가

        let todoList = document.createElement("div"); // todo 저장
        todoList.className = "todo-list";
        todoList.innerText = todo;

        let fireImg = document.createElement("input"); // 불 input 체크박스
        fireImg.type = "button";
        fireImg.value = "notDone";
        fireImg.className = "fire-button";
        fireImg.onclick = function () {
          fireOrange(newForm.id);
        };

        let hiddenInput1 = document.createElement("input");
        hiddenInput1.type = "hidden"; // 숨겨진 필드로 데이터 전달
        hiddenInput1.name = "todoDataValue"; // 백엔드에서 사용
        hiddenInput1.value = todo;

        let hiddenInput2 = document.createElement("input");
        hiddenInput2.type = "hidden";
        hiddenInput2.name = "value";
        hiddenInput2.value = "notDone";

        battleBottom.appendChild(newForm);
        newForm.appendChild(todoList);
        newForm.appendChild(fireImg);
        newForm.appendChild(hiddenInput1);
        newForm.appendChild(hiddenInput2);

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

  if (fireIMG.value === "notDone") {
    fireIMG.style.backgroundImage = "url('../image/cal-fire-orange.svg')";
    fireIMG.value = "done";
    todoList.style.borderBottom = "2px solid #FF5C00";
    clicked++;
    percent = (clicked / todoNum) * 100;
    console.log(percent);
  } else {
    fireIMG.style.backgroundImage = "url('../image/todo-fire.svg')";
    fireIMG.value = "notDone";
    todoList.style.borderBottom = "2px solid white";
  }
}

function saveTodoData() {
  var battleCode = document.getElementById("battleCode").value;
  var todoDataValue = document.getElementById("todoDataValue").value;
  var value = document.getElementById("value").value;

  $.ajax({
    type: "POST",
    url: "/battle/" + battleCode + "/saveTodoData",
    data: {
      battleCode: battleCode,
      todoDataValue: todoDataValue,
      value: value,
    },
    success: function (response) {
      console.log(response);
      // 여기에 서버 응답을 처리하는 로직을 추가할 수 있습니다.
    },
    error: function () {
      alert("데이터 저장 중 오류가 발생했습니다.");
    },
  });
}