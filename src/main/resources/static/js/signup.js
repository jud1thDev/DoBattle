function UsingID(){
    if(`$param.error`){
        alert("이미 사용 중인 아이디입니다.");
        return false;
    }
    else{
        return true;
    }
}

//function PWConfirm(){
//    let pwcheck = document.getElementById("battle-pw").value;   //비밀번호
//    let pwrecheck = document.getElementById("battle-pwre").value;   //비밀번호확인 입력
//
//    if(pwcheck !== pwrecheck){
//        alert("비밀번호가 일치하지 않습니다.");
//        return false;   //폼제출 방지
//    }
//    if((pwcheck === pwrecheck) && (pwcheck !== "")){
//        return true;   //폼제출
//    }
//}
//
//function validateForm() {
//    let pwCheckResult = PWConfirm();
//    let usingIdResult = UsingId();
//
//    // 둘 다 true인 경우에만 폼 제출을 허용하도록 설정
//    return pwCheckResult && usingIdResult;
//}
//
//function practice(){
//    let pwcheck = document.getElementById("battle-pw").value;   //비밀번호
//    let pwrecheck = document.getElementById("battle-pwre").value;   //비밀번호확인 입력
//
//    if(pwcheck !== pwrecheck){
//        alert("비밀번호가 일치하지 않습니다.");
//        return false;   //폼제출 방지
//    }
//    else if(`$param.error`){
//        alert("이미 사용 중인 아이디입니다.");
//        console.log("아이디사용중");
//        return false;
//    }
//    else{
//        return true;
//    }
//}
