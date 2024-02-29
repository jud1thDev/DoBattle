window.onload = function () {
    CalendarCallBack();
    document.getElementById("click-date").innerText = (nowMonth.getMonth() + 1) + '/' + today.getDate();
    whoWin();
}

let nowMonth = new Date();  // 현재 달을 페이지를 로드한 날의 달로 초기화
let today = new Date();     // 오늘 날 저장용
let changeDay;  //밑에 결과창용
let serverData; //서버에서 넘어온 결과 저장용

function CalendarCallBack(){
    //api 호출
    let querySend = '/calender/fire/' + battleCode;

    fetch(querySend,{
        method: "GET"
    }).then(response => {
        //응답이 성공적으로 받아졌을 때 처리
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        serverData = data;
        //캘린더 만들기 불러오기
        buildCalendar();
    })
    .catch(error => {
        // 오류 발생 시 처리
        console.log('There was a problem with the fetch operation:', error);
    });
}

function buildCalendar(){
    /**달력만들기*/
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
        //서버에서 불러오기 위한 LocalDate형 날짜
        let serverDate = nowDay.getFullYear() + "-" + (nowDay.getMonth() + 1).toString().padStart(2, '0') + "-" + nowDay.getDate().toString().padStart(2, '0');
        let startDate = new Date(start);

        if (nowDay.getDay() === 0) { // 일요일인 경우 새로운 행을 추가
            nowRow = Calendar_tbody.insertRow();
        }
        let nowColumn = nowRow.insertCell();

        //불svg 넣기
        let newDivFire = document.createElement("div");
        let imgElement = document.createElement("img");
        //오늘 이후는 투명도 70%, 오늘 이후&시작날짜 이전의 날짜는 클릭 불가
        if(nowDay <= today && nowDay >= startDate){
            if(serverData[serverDate] == "동점"){
                imgElement.src = "/image/cal-fire-white.svg";
            }
            else if(serverData[serverDate] == "파트너"){
                imgElement.src = "/image/cal-fire-grey.svg";
            }
            else{
                imgElement.src = "/image/cal-fire-orange.svg";
            }

            newDivFire.onclick = function() {
                changeDay = newDivFire.id;
                changeDate(changeDay);
            };
        } else{
            imgElement.src = "/image/cal-fire-white70.svg";
        }
        newDivFire.appendChild(imgElement);
        nowColumn.appendChild(newDivFire);

        //날짜 글씨 넣기
        let newDivDate = document.createElement("div");
        newDivDate.innerHTML = nowDay.getDate();        // 추가한 열에 날짜 입력
        nowColumn.appendChild(newDivDate);

        //오늘 날짜 글씨 주황색으로 표시
        if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() && nowDay.getDate() == today.getDate()){
            newDivDate.className = "today";
        }

        //밑에 날짜 뜨기 위한 id값 저장
        nowColumn.id = nowDay.getDate();
        newDivFire.id = nowDay.getDate();

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