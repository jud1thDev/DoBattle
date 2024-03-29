function percentCalc(who, percent){
     if(percent %1 != 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
        percent = percent.toFixed(1);
     }
     else if(percent === 0){
        percent = 0;
     }
     else if(percent === null){
        percent = 0;
     }
     who.innerText = percent + '%';
     who.style.width = 'calc((100vw - 90px) * ' + percent / 100 + ')';
}

function whoWin(){
    let whoWinText = document.getElementById('text');
    let percentDifference = Math.abs(currentUserPercent - partnerUserPercent);
    if(percentDifference %1 !== 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
       percentDifference = percentDifference.toFixed(1);
    }

    //승리자 가리기
    if(currentUserPercent > partnerUserPercent){
        whoWinText.innerText = currentUserName + '님이 ' + percentDifference + '% 차이로\n 이기고 있어요!';
    }
    else if(currentUserPercent === partnerUserPercent){
        if(currentUserPercent %1 != 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
           currentUserPercent = currentUserPercent.toFixed(1);
        }
        whoWinText.innerText = currentUserName + '님과 ' + partnerUserName + '님이 ' + currentUserPercent+  '%로\n 동점입니다!';
    }
    else{
        whoWinText.innerText = partnerUserName + '님이 ' + percentDifference + '% 차이로\n 앞서가고 있어요!';
    }
}