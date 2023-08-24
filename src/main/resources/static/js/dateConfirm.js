function dateConfirm(){
    let today = new Date();
    let Pickdate = document.getElementById("pickDate");
    let pickedDate = new Date(Pickdate.value);
    //let pickedDate = new Date("2023-01-08");
    today = new Date(today.getFullYear(), today.getMonth(),today.getDate(),0,0);
    pickedDate = new Date(pickedDate.getFullYear(), pickedDate.getMonth(), pickedDate.getDate(),0,0);
    console.log(today);
    console.log(pickedDate);

    if(pickedDate < today){
        alert("오늘 이전의 날짜는 선택할 수 없습니다.");
        return false;
    }
    return true;
}