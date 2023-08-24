function changePage(url){
    window.location.href = url;
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