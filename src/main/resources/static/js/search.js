const search = document.querySelector('[data-search]');
const cardsContainer = document.querySelector('[data-cards-container]');
const cardTemplate = document.querySelector('[data-user-card-template]');
const searchUrl = 'http://localhost:8080/search/';
const avatarUrl = 'http://localhost:8080/image/avatar/';
const profilePart = '/shrimp/';
var xhr = new XMLHttpRequest();
xhr.responseType = 'json';

hideContainer();

function hideContainer() {
    cardsContainer.style.display = 'none';
}

function showContainer() {
    cardsContainer.style.display = 'flex';
}

search.addEventListener("input", (e)=> {
    const value = e.target.value;

    if(value.length > 0) {
        cardsContainer.innerHTML = '';
        showContainer();
        xhr.open('GET', searchUrl + value);
        xhr.send();
    } else if (value.length == 0) {
        hideContainer();
    }
});

xhr.onload = function() {
    let response = xhr.response;
    let startForm = document.forms.length;

    response.forEach(function(user) {
        const card = cardTemplate.content.cloneNode(true).children[0];
        const form = card.querySelector('[data-card-form]');
        const photo = card.querySelector('[data-card-photo]');
        const nameSpan = card.querySelector('[data-card-username]');
        const dateSpan = card.querySelector('[data-card-date]');

        var name = user.pfImgName === null ? '0' : user.pfImgName;
        photo.src =  avatarUrl + name;
        nameSpan.textContent = user.uname;
        dateSpan.textContent = 'Created: ' + user.created.substring(0, 10);

        cardsContainer.append(card);

        document.forms[startForm].action = profilePart + user.id;
        startForm++;
    });
}