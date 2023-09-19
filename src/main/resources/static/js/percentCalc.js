function percentCalc(who, percent){
     if(percent %1 != 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
        percent = percent.toFixed(1);
     }
     if(todoNum === 0){
        percent = 0;
     }
     who.innerText = percent + '%';
     who.style.width = 'calc((100vw - 90px) * ' + percent / 100 + ')';
     who.style.transition = 'width 0.5s ease';
}

function whoWin(){
    let whoWinText = document.getElementById('text');
    let percentDifference = Math.abs(currentUserPercent - partnerUserPercent);
    if(percentDifference %1 !== 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
       percentDifference = percentDifference.toFixed(1);
    }

    //퍼센트 채우기
    if(currentUserPercent > partnerUserPercent){
        whoWinText.innerText = currentUserName + '님이 ' + percentDifference + '% 차이로 이기고 있어요!';
    }
    if(currentUserPercent === partnerUserPercent){
        if(currentUserPercent %1 != 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
           currentUserPercent = currentUserPercent.toFixed(1);
        }
        whoWinText.innerText = currentUserName + '님과 ' + partnerUserName + '님이 ' + currentUserPercent+  '%로\n 동점입니다!';
    }
    else{
        whoWinText.innerText = partnerUserName + '님이 ' + percentDifference + '% 차이로 이기고 있어요!';
    }
}