function percentCalc(who, percent){
     if(percent%1 != 0){    //소숫점일 경우 소수점뒤 한자리만 보이도록
        percent = percent.toFixed(1);
     }
     if(todoNum === 0){
        percent = 0;
     }
     who.innerText = percent + '%';
     who.style.width = 'calc((100vw - 90px) * ' + percent / 100 + ')';
     who.style.transition = 'width 0.5s ease';
}