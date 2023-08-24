function UsingID(){
    if(`$param.error`){
        alert("이미 사용 중인 아이디입니다.");
        return false;
    }
    else{
        return true
    }
}

function validateForm() {
    let pwCheckResult = PWConfirm();
    let usingIdResult = UsingId();

    // 둘 다 true인 경우에만 폼 제출을 허용하도록 설정
    return pwCheckResult && usingIdResult;
}
