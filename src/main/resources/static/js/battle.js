let todoNum = 1;    //몇번째 todo인지 class명 저장용
let txtFieldNum = 0;   //줄줄이 투두 수정용

function todoClick(){
    while(txtFieldNum == 0){
        let todo;
        let battleBottom = document.getElementById("battle-bottom");

        let newDiv = document.createElement("div");    //전체 감쌀 div
        let newText = document.createElement("input");
        newText.type = "text";
        newText.className = "input-text";
        newText.placeholder = "입력";

        battleBottom.appendChild(newDiv);
        newDiv.appendChild(newText);

        txtFieldNum++;  //줄줄이 방지

        newText.addEventListener("keyup", function(event) {
            if (event.key === "Enter") {
                battleBottom.removeChild(newDiv);
                todo = newText.value;

                let newDiv2 = document.createElement("form");    //밖에 감싸줄 form 태그
                newDiv2.setAttribute("action", "/battle");
                newDiv2.setAttribute("method", "post");

                let todoList = document.createElement("div");   //todo 저장
                todoList.className = "todo-list";
                todoList.id = String(todoNum);   //border 색 변화 위해
                todoList.innerText = todo;

                let fireImg = document.createElement("input");    //불 input 체크박스
                fireImg.type = "button";
                fireImg.className = "fire-button";
                fireImg.value = "notDone";
                fireImg.id = String(todoNum);
                fireImg.onclick = function() { fireOrange(this); };

                let hiddenInput = document.createElement("input");
                hiddenInput.type = "hidden"; // 숨겨진 필드로 데이터 전달
                hiddenInput.name = "todoData"; // 백엔드에서 사용!!!!!!!!!!
                hiddenInput.value = todo;

                battleBottom.appendChild(newDiv2);
                newDiv2.appendChild(todoList);
                newDiv2.appendChild(fireImg);
                newDiv2.appendChild(hiddenInput);

                todoNum ++;
                txtFieldNum--;
            }
        });
    }
}


function fireOrange(self){
    let num = self.id;
    let borderElements = document.getElementById(num);
    if(self.value === 'notDone') { //이게 불 주황색.
        self.style.backgroundImage = "url('../image/cal-fire-orange.svg')";
        self.value = 'done';
        borderElements.style.borderBottom = "2px solid #FF5C00";
    }
    else{
        self.style.backgroundImage = "url('../image/todo-fire.svg')";
        self.value = 'notDone';
        borderElements.style.borderBottom = "2px solid white";
    }
}

