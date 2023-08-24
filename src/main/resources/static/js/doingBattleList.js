window.onload = function () { makeList(); }

function makeList(){
    for(let i=1; i<=5; i++){ //i 개수는 백이랑 연결
        let mainBox = document.createElement("div");    //가장 바깥 div
        mainBox.className = "bottom-box";
        mainBox.id = i; //몇번째 상자인지 체크용
        mainBox.onclick = function () { changePage('battle'); };
        document.getElementById("main-bottom").appendChild(mainBox);

        let battleName = document.createElement("div"); //배틀명
        battleName.className = "battle-name";
        battleName.innerText = "배틀이름 " + i;
        mainBox.appendChild(battleName);

        let battleDate = document.createElement("div"); //배틀기간
        battleDate.className = "battle-date";
        battleDate.innerText = "8.18~12.13";
        mainBox.appendChild(battleDate);

        let battleversus = document.createElement("div");
        battleversus.className = "versus";
        battleversus.innerText = "vs 상대이름";
        mainBox.appendChild(battleversus);
        console.log("실행끝");
    }
}