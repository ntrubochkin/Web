const search = document.querySelector('[data-search]');
const cardsContainer = document.querySelector('[data-cards-container]');
const cardTemplate = document.querySelector('[data-user-card-template]');

const localhostUrl = 'http://localhost:8080';
const searchUrl = localhostUrl + '/search/';
const avatarUrl = localhostUrl + '/image/avatar/';
const profilePart = localhostUrl + '/shrimp/';

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
    let formCounter = document.forms.length;

    response.forEach(function(user) {
        const card = cardTemplate.content.cloneNode(true).children[0];
        const photo = card.querySelector('[data-card-photo]');
        const nameSpan = card.querySelector('[data-card-username]');
        const dateSpan = card.querySelector('[data-card-date]');

        photo.src =  avatarUrl + user.pfImgName;
        nameSpan.textContent = user.uname;
        dateSpan.textContent = 'Created: ' + user.created;

        cardsContainer.append(card);

        document.forms[formCounter].action = profilePart + user.id;
        formCounter++;
    });
}