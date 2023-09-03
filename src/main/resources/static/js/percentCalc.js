function percentCalc(who){
    percent = (clicked / todoNum) * 100;
     if(percent%1 != 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
        percent = percent.toFixed(1);
     }
     who.innerText = percent + '%';
     who.style.width = 'calc((100vw - 90px) * ' + percent / 100 + ')';
}