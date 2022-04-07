const input = document.querySelector('#input-area');
const span = document.querySelector('.symbols-count-span');
const MAX_MESSAGE_LEN = 150;

function setSpanText() {
    span.textContent = `${input.value.length}/${MAX_MESSAGE_LEN}`;
}

input.addEventListener('input', function() {
    if(input.value.length >= MAX_MESSAGE_LEN) {
        input.value = input.value.substring(0, MAX_MESSAGE_LEN);
    }
    setSpanText();
});

setSpanText();