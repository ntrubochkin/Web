const btn = document.querySelector('.change-info-btn');
const form = document.querySelector('.change-info-form');

btn.addEventListener('click', function() {
    form.hidden = !form.hidden;
});