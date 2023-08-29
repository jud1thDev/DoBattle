function dateConfirm(){
    let startdate = document.getElementById("startDate");
    let startedDate = new Date(startdate.value);

    let finishdate = document.getElementById("finishDate");
    let finishedDate = new Date(finishdate.value);

    if(startedDate > finishedDate){
        alert("시작날짜가 종료 날짜보다 빠릅니다.");
        return false;
    }
    return true;
}