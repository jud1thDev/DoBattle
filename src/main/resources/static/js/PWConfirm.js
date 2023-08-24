function PWConfirm(){
    let pwcheck = document.getElementById("battle-pw").value;   //비밀번호
    let pwrecheck = document.getElementById("battle-pwre").value;   //비밀번호확인 입력

    if(pwcheck !== pwrecheck){
        alert("비밀번호가 일치하지 않습니다.");
        return false;   //폼제출 방지
    }
    if((pwcheck === pwrecheck) && (pwcheck !== "")){
        return true;   //폼제출
    }
}