let todoNum = document.querySelectorAll('#battle-bottom > form').length;    //몇번째 todo인지 class명 저장용
let txtFieldNum = 0; // 줄줄이 투두 수정용

//퍼센트계산용 변수들
let clicked = 0;    //단순 투두 몇개 끝냄?
let checkValue = document.querySelectorAll('.fire-button');
for(let i=0; i<checkValue.length; i++){
    if(checkValue[i].value === 'done')
        clicked++;  //value값이 done인만큼 개수 저장
}
let percent = (clicked/todoNum)*100;


//길이 변경용 변수들
let me = document.querySelector('#me > .progress > .first');
let other = document.querySelector('#other > .progress > .first');

//새로고침시 바로 실행!!
window.onload = function() {
    changeFireColor();  //불색깔 변화
    percentCalc(me, percent);    //내 퍼센트 길이 변경
//    percentCalc(other); //상대방 퍼센트 길이 변경
    deleteVs();     //상대방이름 vs 없애기
    setOnlyTodayDate(); //오늘 날짜에 해당하는 투두데이터 불러오기
}

//실행시 오늘 날짜의 투두데이터만 불러올 수있도록
function setOnlyTodayDate(){
    let today = new Date();
    today = new Date(today.getFullYear(), today.getMonth(), today.getDate(), 0, 0);
}

//상대방에 vs없애기
function deleteVs(){
    let originalString = document.querySelector('#other > p');
    let modifiedString = originalString.innerText.replace("vs", "").trim();
    originalString.innerText = modifiedString;
}

//처음 열자마자 value값에 따라서 불 색 바뀌어있도록
function changeFireColor(){
    let fireValue = document.querySelectorAll('.fire-button');
    for(let i=0; i<fireValue.length; i++){
        if(fireValue[i].value === 'done'){    //todo 실행 취소
            fireValue[i].style.backgroundImage = "url('../image/cal-fire-orange.svg')";
            let form = fireValue[i].closest('form');
            let borderDown = form.querySelector('.todo-list');
            borderDown.style.borderBottom = "2px solid #FF5C00";
        }

    }
}

//value값 바꾸고 form 제출
function changeValueAndSubmit(self) {
    if (self.value === 'notDone') {
        self.value = 'done';
    } else if (self.value === 'done') {
        self.value = 'notDone';
    }

    const todoDataId = self.closest('form').querySelector('input[name="todoDataId"]').value;
    const value = self.value;

    // AJAX 요청 보냄
    $.ajax({
        type: "POST",
        url: `/battle/${battleCode}/updateTodoData`,
        data: {
            todoDataId: todoDataId,
            value: value
        },
        success: function (response) {
            console.log(response);
        },
        error: function () {
            alert("데이터 업데이트 중 오류가 발생했습니다.");
        },
    });
}

//function saveTodoData() {
//  var battleCode = document.getElementById("battleCode").value;
//  var todoDataValue = document.getElementById("todoDataValue").value;
//  var value = document.getElementById("value").value;
//
//  $.ajax({
//    type: "POST",
//    url: "/battle/" + battleCode + "/saveTodoData",
//    data: {
//      battleCode: battleCode,
//      todoDataValue: todoDataValue,
//      value: value,
//    },
//    success: function (response) {
//      console.log(response);
//      // 여기에 서버 응답을 처리하는 로직을 추가할 수 있습니다.
//    },
//    error: function () {
//      alert("데이터 저장 중 오류가 발생했습니다.");
//    },
//  });
//}