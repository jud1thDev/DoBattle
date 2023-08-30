function changePage(url){
    window.location.href = url;
}

function battlePage(battleId){
    window.location.href = `battle.html?battleId=${battleId}`;
}


function goToBattlePage(element) {
    // 해당 .bottom-box 요소를 찾기 위해 가장 가까운 부모 요소를 선택합니다.
    var bottomBox = element.closest('.bottom-box');

    let battleId = bottomBox.id;
    let battleName = bottomBox.querySelector('.battle-name').textContent;
    let battleDate = bottomBox.querySelector('.battle-date').textContent;
    let battlePartner= bottomBox.querySelector('.versus').textContent;

    console.log(battleId);
    console.log(battleName);
    console.log(battleDate);
    console.log(battlePartner);

    const battleInfo = {
        battleId: battleId,
        battleName: battleName,
        battleDate: battleDate,
        battlePartner: battlePartner
    };

    // JSON 형태로 battleInfo를 문자열로 변환하여 localStorage에 저장
    localStorage.setItem("battleInfo", JSON.stringify(battleInfo));

    window.location.href = 'battle';
}

function changeMenu(url, num){
    window.location.href = url;
    let Else = document.querySelectorAll('#menu-bar > div > a');
    for (let i=0; i<Else.length; i++) {
        Else[i].style.fontWeight = 'normal';
    }

    let changeA = document.querySelector(`#menu-bar > div > a:nth-child(${num})`);
    changeA.style.fontWeight = 'bold';
}