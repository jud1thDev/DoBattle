window.onload = function (){ inputValue(); }

function inputValue(){
    let today = new Date();
    let inputDate = document.getElementById('todayDate');

    let year = today.getFullYear();

    let month = today.getMonth() + 1;
    if(month < 10){
        month = "0" + String(today.getMonth() + 1);
    }

    let date = today.getDate();
    if(date < 10){
        date = "0" + String(today.getDate);
    }

    inputDate.value = (year + "-" + month + "-" + date);
}