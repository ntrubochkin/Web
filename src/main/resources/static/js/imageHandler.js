const selectImage = document.querySelector('.image-upload-btn');
const inputFile = document.querySelector('#file-input');

selectImage.addEventListener('click', function() {
    inputFile.click();
});

inputFile.addEventListener('change', function() {
    const img = this.files[0];
    console.log(img);
});