function todoClick() {
  while (txtFieldNum === 0) {
    let todo;   //입력한 문구 저장용
    let battleBottom = document.getElementById("battle-bottom");

    let newDiv = document.createElement("form"); // 전체 감쌀 div
    newDiv.setAttribute("method", "post");
    newDiv.setAttribute("action", '/battle/' + battleCode + '/saveTodoData');

    let newText = document.createElement("input");  //입력받을 창
    newText.type = "text";
    newText.className = "input-text";
    newText.placeholder = "입력";
    newText.name = "todoDataValue";

    let hiddenInput1 = document.createElement("input");   //기본 value 값 지정용
    hiddenInput1.type = "hidden"; // 숨겨진 필드로 데이터 전달
    hiddenInput1.name = "value"; // 백엔드에서 사용
    hiddenInput1.value = 'notDone';

    battleBottom.appendChild(newDiv);
    newDiv.appendChild(newText);
    newDiv.appendChild(hiddenInput1);

    txtFieldNum++; // 줄줄이 방지

    newText.addEventListener("keyup", function (event) {
      if (event.key === "Enter") {
        const form = self.closest('form');
        if(form){
          form.submit();
        } //폼 그냥 바로 제출

        txtFieldNum--;
      }
    });
  }
}
