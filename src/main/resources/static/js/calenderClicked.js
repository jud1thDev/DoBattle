function changeDate(date){
    let getWithId = document.getElementById(String(date));
    let realDay = getWithId.innerText;
    document.getElementById("click-date").innerText = (nowMonth.getMonth() + 1) + '/' + realDay;

    //클릭한 요일 풀로 저장용
    let clickedDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth(), date);
    let inside = document.querySelectorAll('.battle-result > div')[1];
    if(today <= clickedDate){
        inside.style.display = 'none';
    }
    else{
        inside.style.display = 'flex';
    }

    //api 호출
    let year = clickedDate.getFullYear();
    let month = (clickedDate.getMonth() + 1).toString().padStart(2, '0');
    let day = (clickedDate.getDate()).toString().padStart(2, '0');

    let querySend = '/calender/dateclick/' + battleCode + '/'+ year+'-'+month+'-'+day;

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
            currentUserPercent = data.currentUserPercentage;
            partnerUserPercent = data.partnerDTOs[0].partnerPercent;    //우선 1명만 불러오는걸로..
            //클릭하면 정보 바뀌기
            let percentDifference = Math.abs(currentUserPercent - partnerUserPercent);
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
        })
        .catch(error => {
            // 오류 발생 시 처리
            console.log('There was a problem with the fetch operation:', error);
        });
}