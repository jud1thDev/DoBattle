let todoNum = document.querySelectorAll('#battle-bottom > form').length;    //몇번째 todo인지 class명 저장용
let txtFieldNum = 0; // 줄줄이 투두 수정용

let me = document.querySelector('#me > .progress > .first');
let other = document.querySelector('#other > .progress > .first');

//새로고침시 바로 실행!!
window.onload = function() {
    changeFireColor();  //불색깔 변화
    console.log(currentUserPercent);
    console.log(partnerUserPercent);
    percentCalc(me, currentUserPercent);    //내 퍼센트 길이 변경
    percentCalc(other, partnerUserPercent); //상대방 퍼센트 길이 변경
//    setOnlyTodayDate(); //오늘 날짜에 해당하는 투두데이터 불러오기
    whoWin();
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
}