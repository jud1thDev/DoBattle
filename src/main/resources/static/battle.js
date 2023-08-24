let todoNum = 1;    //몇번째 todo인지 class명 저장용

function todoClick(){
    let todo;
    let battleBottom = document.getElementById("battle-bottom");
    let newDiv = document.createElement("div");
    let newText = document.createElement("input");
    newText.type = "text";
    newText.className = "input-text";
    newText.placeholder = "입력";
    battleBottom.appendChild(newDiv);
    newDiv.appendChild(newText);

    newText.addEventListener("keyup", function(event) {
        if (event.key === "Enter") {
            battleBottom.removeChild(newDiv);
            todo = newText.value;

            let newDiv2 = document.createElement("div");    //밖에 감싸줄 div
            let todoList = document.createElement("div");   //todo 저장
            todoList.className = "todo-list";
            todoList.id = String(todoNum);   //border 색 변화 위해
            todoList.innerText = todo;
            let fireImg = document.createElement("img");    //불 사진
            fireImg.src = "../image/todo-fire.svg";
            fireImg.value = "notDone";
            fireImg.id = String(todoNum);
            fireImg.onclick = function() { fireOrange(this); };
            battleBottom.appendChild(newDiv2);
            newDiv2.appendChild(todoList);
            newDiv2.appendChild(fireImg);

            todoNum ++;
        }
    });
}


function fireOrange(self){
    let num = self.id;
    let borderElements = document.getElementById(num);
    if(self.value === 'notDone') { //이게 불 주황색.
        self.src = "../image/cal-fire-orange.svg";
        self.value = 'done';
        borderElements.style.borderBottom = "2px solid #FF5C00";
    }
    else{
        self.src = "../image/todo-fire.svg";
        self.value = 'notDone';
        borderElements.style.borderBottom = "2px solid white";
    }
}