window.onload = function () {
    buildCalendar();
    document.getElementById("click-date").innerText = (nowMonth.getMonth() + 1) + '/' + today.getDate();
    whoWin();
}

let nowMonth = new Date();  // 현재 달을 페이지를 로드한 날의 달로 초기화
let today = new Date();     // 오늘 날 저장용
let changeDay;  //밑에 결과창용

function buildCalendar(){
    let firstDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth(), 1);   //이번달 1일
    let lastDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth()+1, 0);  //이번달 마지막일

    let Calendar_tbody = document.querySelector(".Calendar > tbody");
    document.getElementById("calYear").innerText = nowMonth.getFullYear();
    document.getElementById("calMonth").innerText = nowMonth.getMonth() + 1;

    //달 옮길 떄 이전것 남아있는것 막기
    while (Calendar_tbody.rows.length > 0) {
        Calendar_tbody.deleteRow(Calendar_tbody.rows.length - 1);
    }

    //달력만들기 시작
    let nowRow = Calendar_tbody.insertRow();

    //1일 이전 달력
    for (let day = 0; day < firstDate.getDay(); day++) {
        let nowColumn = nowRow.insertCell();
    }

    //1일 이후 달력
    let nowDay = new Date(firstDate.getFullYear(), firstDate.getMonth(), firstDate.getDate(), 0, 0); //1일부터 시작 (시간 0시 0초로 맞춤)
    while (nowDay <= lastDate) {
        if (nowDay.getDay() === 0) { // 일요일인 경우 새로운 행을 추가
            nowRow = Calendar_tbody.insertRow();
        }
        let nowColumn = nowRow.insertCell();

        //불svg 넣기
        let newDivFire = document.createElement("div");
        let imgElement = document.createElement("img");
        //오늘 이후는 투명도 70%
        if(today >= nowDay){
            imgElement.src = "/image/cal-fire-white.svg";
        } else{
            imgElement.src = "/image/cal-fire-white70.svg";
        }
        newDivFire.appendChild(imgElement);
        nowColumn.appendChild(newDivFire);

        //날짜 글씨 넣기
        let newDivDate = document.createElement("div");
        newDivDate.innerHTML = nowDay.getDate();        // 추가한 열에 날짜 입력
        nowColumn.appendChild(newDivDate);

        //오늘 날짜 주황색으로 표시
        if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() && nowDay.getDate() == today.getDate()){
            newDivDate.className = "today";
        }

        //밑에 날짜 뜨기 위한 id값 저장
        nowColumn.id = nowDay.getDate();
        newDivFire.id = nowDay.getDate();
        //클릭시 밑에 날짜뜨는 (오늘 이전의 날만)
        if(today >= nowDay){
            newDivFire.onclick = function() {
                changeDay = newDivFire.id;
                changeDate(changeDay);
            };
        }

        // 다음 날짜로 이동
        nowDay.setDate(nowDay.getDate() + 1);
    }
}


function prevCalendar(){
    nowMonth.setMonth(nowMonth.getMonth()-1);
    buildCalendar();
}

function nextCalendar(){
    nowMonth.setMonth(nowMonth.getMonth()+1);
    buildCalendar();
}

function whoWin(){
    let percentDifference = Math.abs(currentUserPercent - partnerUserPercent);
    console.log(currentUserPercent);
    console.log(partnerUserPercent);
    let whoWinPercent = document.querySelector("#whoWin");

        if(percentDifference %1 !== 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
           percentDifference = percentDifference.toFixed(1);
        }

        //승리자 가리기
        if(currentUserPercent > partnerUserPercent){
            whoWinPercent.innerText = currentUserName + '님이 ' + percentDifference + '% 차이로\n 이기고 있어요!';
        }
        else if(currentUserPercent === partnerUserPercent){
            if(currentUserPercent %1 != 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
               currentUserPercent = currentUserPercent.toFixed(1);
            }
            whoWinPercent.innerText = currentUserName + '님과 ' + partnerUserName + '님이 ' + currentUserPercent+  '%로\n 동점입니다!';
        }
        else{
            whoWinPercent.innerText = partnerUserName + '님이 ' + percentDifference + '% 차이로\n 앞서가고 있어요!';
        }
}